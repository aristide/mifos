package org.mifos.application.customer.struts.action;


import org.mifos.application.accounts.business.AccountBO;
import org.mifos.application.accounts.loan.business.LoanBO;
import org.mifos.application.accounts.savings.business.SavingsBO;
import org.mifos.application.customer.business.CustomerBO;
import org.mifos.application.customer.util.helpers.CustomerStatus;
import org.mifos.application.meeting.business.MeetingBO;
import org.mifos.framework.MifosMockStrutsTestCase;
import org.mifos.framework.hibernate.helper.HibernateUtil;
import org.mifos.framework.security.util.UserContext;
import org.mifos.framework.util.helpers.Constants;
import org.mifos.framework.util.helpers.TestObjectFactory;

public class TestCustomerAction extends MifosMockStrutsTestCase {

	private UserContext userContext;

	private CustomerBO client;

	private CustomerBO group;

	private CustomerBO center;

	private AccountBO account;

	private LoanBO groupAccount;

	private LoanBO clientAccount;

	private SavingsBO centerSavingsAccount;

	private SavingsBO groupSavingsAccount;

	private SavingsBO clientSavingsAccount;
	
	private String flowKey;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		userContext = TestObjectFactory.getContext();
		request.getSession().setAttribute(Constants.USERCONTEXT, userContext);
		addRequestParameter("recordLoanOfficerId", "1");
		addRequestParameter("recordOfficeId", "1");
		request.getSession(false).setAttribute("ActivityContext", TestObjectFactory.getActivityContext());
		request.getSession().setAttribute(Constants.USERCONTEXT, userContext);

		flowKey = createFlow(request, CustomerAction.class);
		request.setAttribute(Constants.CURRENTFLOWKEY, flowKey);
	}

	@Override
	public void tearDown() throws Exception {
		TestObjectFactory.cleanUp(centerSavingsAccount);
		TestObjectFactory.cleanUp(groupSavingsAccount);
		TestObjectFactory.cleanUp(clientSavingsAccount);
		TestObjectFactory.cleanUp(groupAccount);
		TestObjectFactory.cleanUp(clientAccount);
		TestObjectFactory.cleanUp(account);
		TestObjectFactory.cleanUp(client);
		TestObjectFactory.cleanUp(group);
		TestObjectFactory.cleanUp(center);
		HibernateUtil.closeSession();
		super.tearDown();
	}

	public void testForwardWaiveChargeDue() {
		createInitialObjects();
		setRequestPathInfo("/customerAction.do");
		addRequestParameter("method", "waiveChargeDue");
		addRequestParameter("type", "Client");
		AccountBO accountBO = client.getCustomerAccount();
		addRequestParameter("accountId", accountBO.getAccountId().toString());
		getRequest().getSession().setAttribute("security_param", "Client");
		addRequestParameter(Constants.CURRENTFLOWKEY, flowKey);
		actionPerform();
		verifyForward("waiveChargesDue_Success");
		verifyNoActionErrors();
		verifyNoActionMessages();
		assertNotNull(request.getAttribute(Constants.CURRENTFLOWKEY));
	}

	public void testForwardWaiveChargeOverDue() {
		createInitialObjects();
		setRequestPathInfo("/customerAction.do");
		addRequestParameter("method", "waiveChargeOverDue");
		addRequestParameter("type", "Client");
		AccountBO accountBO = client.getCustomerAccount();
		addRequestParameter("accountId", accountBO.getAccountId().toString());
		getRequest().getSession().setAttribute("security_param", "Client");
		addRequestParameter(Constants.CURRENTFLOWKEY, flowKey);
		actionPerform();
		verifyForward("waiveChargesOverDue_Success");
		verifyNoActionErrors();
		verifyNoActionMessages();
		assertNotNull(request.getAttribute(Constants.CURRENTFLOWKEY));
	}

	public void testGetAllActivity() {
		createInitialObjects();
		setRequestPathInfo("/customerAction.do");
		addRequestParameter("method", "getAllActivity");
		addRequestParameter("type", "Client");
		addRequestParameter("globalCustNum", client.getGlobalCustNum());
		getRequest().getSession().setAttribute("security_param", "Client");
		addRequestParameter(Constants.CURRENTFLOWKEY, flowKey);
		actionPerform();
		verifyForward("viewClientActivity");
		verifyNoActionErrors();
		verifyNoActionMessages();
		assertNotNull(request.getAttribute(Constants.CURRENTFLOWKEY));
	}

	private void createInitialObjects() {
		MeetingBO meeting = TestObjectFactory.createMeeting(TestObjectFactory
				.getTypicalMeeting());
		center = TestObjectFactory.createCenter("Center", meeting);
		group = TestObjectFactory.createGroupUnderCenter("Group", CustomerStatus.GROUP_ACTIVE, center);
		client = TestObjectFactory.createClient("Client", 
				CustomerStatus.CLIENT_ACTIVE,
				group);
	}

}
