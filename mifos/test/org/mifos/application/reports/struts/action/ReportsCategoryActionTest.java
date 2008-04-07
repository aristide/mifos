package org.mifos.application.reports.struts.action;

import org.mifos.application.reports.struts.actionforms.ReportsCategoryActionForm;
import org.mifos.application.reports.util.helpers.ReportsConstants;
import org.mifos.application.rolesandpermission.business.ActivityEntity;
import org.mifos.application.rolesandpermission.utils.ActivityTestUtil;
import org.mifos.framework.MifosMockStrutsTestCase;

public class ReportsCategoryActionTest extends MifosMockStrutsTestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testShouldForwardToDefineNewCategoryPage() throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "loadDefineNewCategoryPage");
		actionPerform();
		verifyForward("load_success");
		verifyForwardPath("/pages/application/reports/jsp/defineNewReportsCategory.jsp");
		verifyNoActionErrors();
	}

	public void testShouldPreviewSuccessIfCategoryNameIsNotNull()
			throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "preview");
		addRequestParameter("categoryName", "NotNull");
		actionPerform();
		verifyForward("preview_success");
		verifyForwardPath("/pages/application/reports/jsp/defineNewReportsCategoryPreview.jsp");
		verifyNoActionErrors();
	}

	public void testShouldPreviewFailureIfReportCategoryNameIsNull() {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "preview");
		actionPerform();

		verifyForwardPath("/reportsCategoryAction.do?method=validate");
		String[] errors = { ReportsConstants.ERROR_CATEGORYNAME };
		verifyActionErrors(errors);
	}

	public void testCategoryNameShouldEqualsToInputValueWhenDoPreivewAction()
			throws Exception {
		String categoryName = "hahaCategoryName";
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "preview");
		addRequestParameter("categoryName", categoryName);
		actionPerform();
		assertEquals(categoryName,
				((ReportsCategoryActionForm) getActionForm()).getCategoryName());
	}

	public void testShouldSubmitSuccess() throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "addNewCategory");
		addRequestParameter("categoryName", "Haha");
		actionPerform();
		verifyForward("create_success");
		verifyForwardPath("/reportsCategoryAction.do?method=viewReportsCategory");
		verifyNoActionErrors();
	}

	public void testShouldForwardToDefineNewCategoryPageIfCategoryNameExisted() {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "preview");
		addRequestParameter("categoryName", "Haha");
		actionPerform();
		addRequestParameter("method", "preview");
		addRequestParameter("categoryName", "Haha");
		actionPerform();

		verifyForwardPath("/pages/application/reports/jsp/defineNewReportsCategory.jsp");
		String[] errors = { ReportsConstants.ERROR_CATEGORYNAMEALREADYEXIST };
		verifyActionErrors(errors);

	}

	public void testCategoryNameShouldBeNullBeforeDefineNewCategory()
			throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");

		ReportsCategoryActionForm form = new ReportsCategoryActionForm();
		String categoryName = "Not Null";
		form.setCategoryName(categoryName);
		setActionForm(form);

		addRequestParameter("method", "loadDefineNewCategoryPage");

		actionPerform();

		assertNull(((ReportsCategoryActionForm) getActionForm())
				.getCategoryName());
	}

	public void testShouldForwardToViewReportsCategoryPage() throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "viewReportsCategory");
		actionPerform();
		verifyForwardPath("/pages/application/reports/jsp/viewReportsCategory.jsp");
		verifyNoActionErrors();
	}

	public void testShouldForwardToEditReportsCategoryPages() throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "edit");
		addRequestParameter("categoryId", "1");
		actionPerform();
		verifyNoActionErrors();
		verifyForwardPath("/pages/application/reports/jsp/editReportsCategory.jsp");
		verifyNoActionErrors();
	}

	public void testShouldHaveReportCategoriesBOWhenViewReportsCategoryPage()
			throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "viewReportsCategory");
		actionPerform();
		assertNotNull(getSession().getAttribute(
				ReportsConstants.LISTOFREPORTCATEGORIES));
	}

	public void testCategoryNameShouldEqualsToInputValueWhenDoEditPreivewAction()
			throws Exception {
		String categoryName = "hahaCategoryName";
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "editPreview");
		addRequestParameter("categoryName", categoryName);
		addRequestParameter("categoryId", "1");
		actionPerform();
		assertEquals(categoryName,
				((ReportsCategoryActionForm) getActionForm()).getCategoryName());
	}

	public void testShouldSubmitSuccessAfterEdit() throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");

		addRequestParameter("method", "editThenSubmit");
		ReportsCategoryActionForm form = new ReportsCategoryActionForm();
		String categoryName = "neverExist";
		form.setCategoryName(categoryName);
		form.setCategoryId((short) 1);
		setActionForm(form);
		actionPerform();
		verifyForward("create_success");
		verifyForwardPath("/reportsCategoryAction.do?method=viewReportsCategory");
		verifyNoActionErrors();
	}

	public void testShouldForwardToConfirmDeleteReportsCategoryPage()
			throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "confirmDeleteReportsCategory");
		addRequestParameter("categoryId", "1");
		actionPerform();
		verifyForward("confirm_delete");
		verifyForwardPath("/pages/application/reports/jsp/confirmDeleteCategory.jsp");
	}

	public void testShouldForwardToConfirmDeleteReportsCategoryPageWhenDeleteReportCategoryHasReports()
			throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "deleteReportsCategory");
		addRequestParameter("categoryId", "1");
		actionPerform();
		verifyForward("confirm_delete");
		verifyForwardPath("/pages/application/reports/jsp/confirmDeleteCategory.jsp");
	}

	public void testShouldEditPreviewSuccessIfCategoryNameIsNotNull()
			throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "editPreview");
		addRequestParameter("categoryName", "NotNull");
		addRequestParameter("categoryId", "1");
		actionPerform();
		verifyForward("editpreview_success");
		verifyForwardPath("/pages/application/reports/jsp/edit_preview_reports_category.jsp");
		verifyNoActionErrors();
	}

	public void testShouldEditPreviewFailureIfReportCategoryNameIsNull()
			throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "editPreview");
		actionPerform();

		verifyForwardPath("/reportsCategoryAction.do?method=validate");
		String[] errors = { ReportsConstants.ERROR_CATEGORYNAME };
		verifyActionErrors(errors);
	}

	public void testShouldEditPreviewFailureIfReportCategoryNotEdit()
			throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");
		ReportsCategoryActionForm form = new ReportsCategoryActionForm();
		String categoryName = "categoryName3";
		form.setCategoryName(categoryName);
		form.setCategoryId((short) 1);
		setActionForm(form);
		addRequestParameter("method", "editThenSubmit");
		actionPerform();

		addRequestParameter("method", "editPreview");
		addRequestParameter("categoryName", "categoryName3");
		addRequestParameter("categoryId", "1");
		actionPerform();

		verifyForwardPath("/pages/application/reports/jsp/editReportsCategory.jsp");
		String[] errors = { ReportsConstants.ERROR_CATEGORYNAMENOTEDIT };
		verifyActionErrors(errors);
	}

	public void testShouldEditPreviewFailureIfReportCategoryAlreadyExist()
			throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "addNewCategory");
		addRequestParameter("categoryName", "jijiwaiwai");
		actionPerform();
		addRequestParameter("method", "editPreview");
		addRequestParameter("categoryName", "jijiwaiwai");
		addRequestParameter("categoryId", "1");
		actionPerform();

		verifyForwardPath("/pages/application/reports/jsp/editReportsCategory.jsp");
		String[] errors = { ReportsConstants.ERROR_CATEGORYNAMEALREADYEXIST };
		verifyActionErrors(errors);
	}

	public void testShouldPreviewSuccessAfterEdit() throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");

		ReportsCategoryActionForm form = new ReportsCategoryActionForm();
		String categoryName = "Not Null";
		form.setCategoryName(categoryName);
		form.setCategoryId((short) 1);
		setActionForm(form);

		addRequestParameter("method", "editPreview");

		actionPerform();
		verifyForwardPath("/pages/application/reports/jsp/edit_preview_reports_category.jsp");
		verifyNoActionErrors();
	}

	public void testShouldEditFailureIfReportCategoryAlreadyExist()
			throws Exception {
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "addNewCategory");
		addRequestParameter("categoryName", "wuwulala");
		actionPerform();

		ReportsCategoryActionForm form = new ReportsCategoryActionForm();
		String categoryName = "wuwulala";
		form.setCategoryName(categoryName);
		form.setCategoryId((short) 1);
		setActionForm(form);
		addRequestParameter("method", "editThenSubmit");
		actionPerform();

		verifyForwardPath("/pages/application/reports/jsp/editReportsCategory.jsp");
		String[] errors = { ReportsConstants.ERROR_CATEGORYNAMEALREADYEXIST };
		verifyActionErrors(errors);
	}

	public void testShouldCreateFailureWhenActivityIdOutOfRange()
			throws Exception {
		ActivityEntity activity = ActivityTestUtil
				.insertActivityForTest(Short.MIN_VALUE);
		setRequestPathInfo("/reportsCategoryAction.do");
		addRequestParameter("method", "addNewCategory");
		addRequestParameter("categoryName", "willFailureForRangeNewCategory");
		actionPerform();

		verifyForward("preview_failure");
		String[] errors = { ReportsConstants.ERROR_NOMOREDYNAMICACTIVITYID };
		verifyActionErrors(errors);

		ActivityTestUtil.deleteActivityForTest(activity);
	}


}
