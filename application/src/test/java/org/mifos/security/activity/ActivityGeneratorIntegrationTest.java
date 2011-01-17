/*
 * Copyright (c) 2005-2010 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */

package org.mifos.security.activity;

import junit.framework.Assert;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import org.mifos.application.master.business.LookUpEntity;
import org.mifos.application.master.business.LookUpValueEntity;
import org.mifos.application.master.business.LookUpValueLocaleEntity;
import org.mifos.application.master.persistence.LegacyMasterDao;
import org.mifos.framework.MifosIntegrationTestCase;
import org.mifos.framework.exceptions.PersistenceException;
import org.mifos.framework.hibernate.helper.StaticHibernateUtil;
import org.mifos.framework.persistence.DatabaseMigrator;
import org.mifos.security.rolesandpermission.business.ActivityEntity;
import org.mifos.security.rolesandpermission.business.RoleBO;
import org.mifos.security.rolesandpermission.persistence.LegacyRolesPermissionsDao;
import org.mifos.security.rolesandpermission.util.helpers.RolesAndPermissionConstants;
import org.springframework.beans.factory.annotation.Autowired;

public class ActivityGeneratorIntegrationTest extends MifosIntegrationTestCase {

    @Autowired
    LegacyMasterDao legacyMasterDao;

    @Test
    public void testShouldInsertSuccessActivity() throws Exception {
        Session session = StaticHibernateUtil.getSessionTL();

        ActivityGenerator activityGenerator = new ActivityGenerator();
        LookUpEntity lookUpEntity = new LookUpEntity();
        lookUpEntity.setEntityId((short) LookUpEntity.ACTIVITY);

        short parentId = 13;

        activityGenerator.upgradeUsingHQL(DynamicLookUpValueCreationTypes.BirtReport, parentId, "abcd");
        int lookUpId = activityGenerator.getLookUpId();
       Assert.assertEquals("abcd", activityGenerator.getLookUpValueLocaleEntity(DatabaseMigrator.ENGLISH_LOCALE,
                lookUpId).getLookUpValue());
       Assert.assertEquals(ActivityGenerator.calculateDynamicActivityId(), (int) activityGenerator
                .getActivityEntity(lookUpId).getId() - 1);
        Query query = session.createQuery("from RoleActivityEntity r where r.activity = :activity and r.role = :role");
        query.setParameter("activity", activityGenerator.getActivityEntity(lookUpId));
        RoleBO roleBo = (RoleBO) session.load(RoleBO.class, (short) RolesAndPermissionConstants.ADMIN_ROLE);
        query.setParameter("role", roleBo);
       Assert.assertEquals(1, query.list().size());

    }

    @Test
    public void testShouldSuccessWhenChangeActivityParent() throws PersistenceException {
        LegacyRolesPermissionsDao rpp = new LegacyRolesPermissionsDao();
        ActivityEntity activity = rpp.getPersistentObject(ActivityEntity.class, Short
                .valueOf((short) 2));
       Assert.assertEquals(1, activity.getParent().getId().shortValue());
        ActivityGenerator.reparentActivityUsingHibernate((short) 2, (short) 13);
        activity = rpp.getPersistentObject(ActivityEntity.class, Short.valueOf((short) 2));
       Assert.assertEquals(13, activity.getParent().getId().shortValue());
        ActivityGenerator.reparentActivityUsingHibernate((short) 2, (short) 1);
    }

    @Test
    public void testShouldSuccessWhenChangeActivityMessage() throws Exception {
        LegacyRolesPermissionsDao rpp = new LegacyRolesPermissionsDao();
        ActivityEntity activityEntity = rpp.getPersistentObject(ActivityEntity.class, Short
                .valueOf((short) 3));
        Integer lookUpId = activityEntity.getActivityNameLookupValues().getLookUpId();
       Assert.assertEquals(373, lookUpId.intValue());

        short localeId = DatabaseMigrator.ENGLISH_LOCALE;
        LookUpValueLocaleEntity lookUpValueLocaleEntity = legacyMasterDao.retrieveOneLookUpValueLocaleEntity(localeId, lookUpId
                .intValue());
        Assert.assertNull(lookUpValueLocaleEntity.getLookUpValue());

        ActivityGenerator.changeActivityMessage((short) 3, localeId, "wahaha");
        lookUpValueLocaleEntity = legacyMasterDao.retrieveOneLookUpValueLocaleEntity(localeId, lookUpId.intValue());

       Assert.assertEquals("wahaha", lookUpValueLocaleEntity.getLookUpValue());
        ActivityGenerator.changeActivityMessage((short) 3, localeId, null);

    }

    @Test
    public void testShouldGenerateMinActivityIdWhenCalculate() throws Exception {
        short minActivityId = -32767;
        ActivityEntity activity = insertActivityForTest(minActivityId);
        Assert.assertEquals(minActivityId - 1, ActivityGenerator.calculateDynamicActivityId());
        deleteActivityForTest(activity);
    }


    private ActivityEntity insertActivityForTest(short activityId) throws PersistenceException {
        LegacyRolesPermissionsDao rpp = new LegacyRolesPermissionsDao();
        LookUpValueEntity anLookUp = new LookUpValueEntity();
        LookUpEntity lookUpEntity = legacyMasterDao.getPersistentObject(LookUpEntity.class, Short
                .valueOf((short) LookUpEntity.ACTIVITY));
        anLookUp.setLookUpEntity(lookUpEntity);
        ActivityEntity parent = legacyMasterDao.getPersistentObject(ActivityEntity.class, (short) 13);
        ActivityEntity activityEntity = new ActivityEntity(activityId, parent, anLookUp);
        rpp.createOrUpdate(anLookUp);
        rpp.createOrUpdate(activityEntity);
        return activityEntity;
    }

    private void deleteActivityForTest(ActivityEntity activityEntity) throws PersistenceException {
        LegacyRolesPermissionsDao rpp = new LegacyRolesPermissionsDao();
        rpp.getSession().clear();
        LookUpValueEntity anLookUp = activityEntity.getActivityNameLookupValues();
        rpp.delete(activityEntity);
        rpp.delete(anLookUp);
    }
}
