package org.mifos.application.personnel.business;

import org.mifos.application.customer.business.CustomerLevelEntity;
import org.mifos.application.customer.center.business.CenterBO;
import org.mifos.application.customer.util.helpers.CustomerLevel;

public class CustomerFixture {

	public static CenterBO createCenterBO(Integer customerId) {
		return createCenterBO(customerId, null);
	}

	public static CenterBO createCenterBO(Integer customerId,
			PersonnelBO loanOfficer) {
		CenterBO center = CenterBO.createInstanceForTest(customerId, new CustomerLevelEntity(
				CustomerLevel.CENTER), null, loanOfficer, customerId.toString());
		return center;
	}
}
