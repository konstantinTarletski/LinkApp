package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.core.entity.cms.IzdCcyTable;
import lv.bank.cards.core.entity.cms.IzdLock;
import lv.bank.cards.core.entity.linkApp.PcdAccParam;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.entity.linkApp.PcdCondAccntPK;
import lv.bank.cards.core.entity.rtps.CurrencyCode;
import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.entity.rtps.StipAccountPK;
import lv.bank.cards.core.entity.rtps.StipLocks;
import lv.bank.cards.core.entity.rtps.StipLocksMatch;
import lv.bank.cards.core.rtps.dao.StipLocksDAO;
import lv.bank.cards.core.utils.Constants;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.service.OtbService;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.nordlb.cards.transmaster.bo.interfaces.IzdLockManager;
import lv.nordlb.cards.transmaster.fo.interfaces.TMFManager;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetCardAccountBalancesTest extends JUnitHandlerTestBase {

	private GetCardAccountBalances handler;
	private PcdabaNGManager pcdabaNGManager = mock(PcdabaNGManager.class);
	private TMFManager tmfManager = mock(TMFManager.class);
	private IzdLockManager izdLockManager = mock(IzdLockManager.class);
	private OtbService otbService;
	private StipLocksDAO stipLocksDAO = mock(StipLocksDAO.class);
	
	@Before
	public void setUpTest() throws RequestPreparationException, NamingException{
		when(context.lookup(PcdabaNGManager.JNDI_NAME)).thenReturn(pcdabaNGManager);
		when(context.lookup(TMFManager.JNDI_NAME)).thenReturn(tmfManager);
		when(context.lookup(IzdLockManager.JNDI_NAME)).thenReturn(izdLockManager);
		handler = new GetCardAccountBalances();
		otbService = new OtbService(stipLocksDAO);
		handler.otbService = otbService;
	}

	@Test
	public void missingCoreAccountOrCifParam() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
		checkRequestFormatException(handler, getSubrequest("core-account"), "Specify cif or core-account");
	}

	@Test
	public void redundantCoreAccountOrCifParam() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
		SubRequest request = getSubrequest("get-card-account-balances");
		addElementOnRootElement(request, "cif", "A00000");
		addElementOnRootElement(request, "core-account", "1590000000");
		checkRequestFormatException(handler, request, "Specify either cif or core-account");
	}

	@Test
	public void missingCountryParam() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
		SubRequest request = getSubrequest("get-card-account-balances");
		addElementOnRootElement(request, "core-account", "1590000000");
		checkRequestFormatException(handler, request, "Specify country code");
	}

	@Test
	public void customersWithoutAccounts() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		String cif = "A00000";
		String country = "EE";
		List<PcdAccount> accountList = new ArrayList<>();

		SubRequest request = getSubrequest("get-card-account-balances");
		addElementOnRootElement(request, "cif", cif);
		addElementOnRootElement(request, "country", country);
		when(pcdabaNGManager.getActiveAccountsByCif(cif, country)).thenReturn(accountList);
		handler.handle(request);
		assertEquals(1, handler.compileResponse().getElement().elements("accounts").size());
	}

	@Test
	public void accountNotFound() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		String coreAccountNo = "1000000000";
		String country = "EE";

		SubRequest request = getSubrequest("get-card-account-balances");
		addElementOnRootElement(request, "core-account", coreAccountNo);
		addElementOnRootElement(request, "country", country);
		when(pcdabaNGManager.getAccountByCoreAccountNo(coreAccountNo, country)).thenReturn(null);
		handler.handle(request);
		assertEquals(1, handler.compileResponse().getElement().elements("accounts").size());
		assertEquals(0, handler.compileResponse().getElement().elements("account").size());
	}

	@Test
	public void accountIsNotActive() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		String coreAccountNo = "1000000000";
		String country = "EE";
		PcdAccount account = new PcdAccount();
		PcdAccParam accountParams = new PcdAccParam();
		accountParams.setStatus("4"); // closed
		account.setAccountNo(new BigDecimal(12345678));
		accountParams.setUfield5(coreAccountNo);
		account.setPcdAccParam(accountParams);

		SubRequest request = getSubrequest("get-card-account-balances");
		addElementOnRootElement(request, "core-account", coreAccountNo);
		addElementOnRootElement(request, "country", country);
		when(pcdabaNGManager.getAccountByCoreAccountNo(coreAccountNo, country)).thenReturn(account);
		handler.handle(request);
		assertEquals(1, handler.compileResponse().getElement().elements("accounts").size());
		assertEquals(0, handler.compileResponse().getElement().elements("account").size());
	}

	@Test
	public void accountIsMissingFromRTPS() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		String coreAccountNo = "1000000000";
		String country = "EE";
		PcdAccount account = new PcdAccount();
		PcdAccParam accountParams = new PcdAccParam();
		accountParams.setStatus("0"); // active
		account.setAccountNo(new BigDecimal(12345678));
		accountParams.setUfield5(coreAccountNo);
		account.setPcdAccParam(accountParams);

		SubRequest request = getSubrequest("get-card-account-balances");
		addElementOnRootElement(request, "core-account", coreAccountNo);
		addElementOnRootElement(request, "country", country);
		when(pcdabaNGManager.getAccountByCoreAccountNo(coreAccountNo, country)).thenReturn(account);
		when(tmfManager.findStipAccountByAccountNoAndCentreId(any(), any())).thenReturn(null);
		handler.handle(request);
		assertEquals(1, handler.compileResponse().getElement().elements("accounts").size());
		assertEquals(0, handler.compileResponse().getElement().elements("account").size());
	}

	@Test
	public void customerWithMultipleAccounts() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		String cif = "A00000";
		String country = "EE";
		List<PcdAccount> accountList = new ArrayList<>();

		// account #1
		PcdAccount pcdAccount1 = new PcdAccount();
		PcdAccParam pcdAccountParams1 = new PcdAccParam();
		pcdAccountParams1.setStatus("0"); // active
		pcdAccount1.setAccountNo(new BigDecimal(12345678));
		pcdAccountParams1.setUfield5("1000000001");
		pcdAccountParams1.setCondSet("001");
		pcdAccount1.setPcdAccParam(pcdAccountParams1);

		StipAccount stipAccount1 = new StipAccount();
		stipAccount1.setComp_id(new StipAccountPK("42800202350", "1"));
		stipAccount1.setCurrencyCode(new CurrencyCode());
		stipAccount1.getCurrencyCode().setCcyAlpha("EUR");
		stipAccount1.getCurrencyCode().setCcyNum("E1");
		stipAccount1.getCurrencyCode().setExpDot(2);
		stipAccount1.setInitialAmount(100L);
		stipAccount1.setBonusAmount(10L);
		stipAccount1.setCreditLimit(1000L);

		PcdCondAccntPK conditionSetPK1 = new PcdCondAccntPK("23", "50", pcdAccountParams1.getCondSet(), "EUR");
		PcdCondAccnt accountCondSet1 = new PcdCondAccnt();
		accountCondSet1.setDebIntrBaseP(BigDecimal.valueOf(18));

		accountList.add(pcdAccount1);

		when(tmfManager.findStipAccountByAccountNoAndCentreId(pcdAccount1.getAccountNo().toString(), "42800202350")).thenReturn(stipAccount1);
		when(stipLocksDAO.calculateTotalLockedAmount(stipAccount1)).thenReturn(12000L);
		when(pcdabaNGManager.getCondAccntByComp_Id(conditionSetPK1)).thenReturn(accountCondSet1);

		// account #2 - revolving credit
		PcdAccount pcdAccount2 = new PcdAccount();
		PcdAccParam pcdAccountParams2 = new PcdAccParam();
		pcdAccountParams2.setStatus("0"); // active
		pcdAccount2.setAccountNo(new BigDecimal(22345678));
		pcdAccountParams2.setUfield5("2000000002");
		pcdAccountParams2.setCondSet("002");
		pcdAccount2.setPcdAccParam(pcdAccountParams2);

		StipAccount stipAccount2 = new StipAccount();
		stipAccount2.setComp_id(new StipAccountPK("42800202350", "2"));
		stipAccount2.setCurrencyCode(new CurrencyCode());
		stipAccount2.getCurrencyCode().setCcyAlpha("EUR");
		stipAccount2.getCurrencyCode().setCcyNum("E2");
		stipAccount2.getCurrencyCode().setExpDot(2);
		stipAccount2.setInitialAmount(200L);
		stipAccount2.setBonusAmount(20L);
		stipAccount2.setCreditLimit(1000L);

		PcdCondAccntPK conditionSetPK2 = new PcdCondAccntPK("23", "50", pcdAccountParams2.getCondSet(), "EUR");
		PcdCondAccnt accountCondSet2 = new PcdCondAccnt();
		accountCondSet2.setDebIntrBaseP(null);
		accountCondSet2.setDebIntrOverPB(BigDecimal.valueOf(20)); // revolving credit

		accountList.add(pcdAccount2);

		when(tmfManager.findStipAccountByAccountNoAndCentreId(pcdAccount2.getAccountNo().toString(), "42800202350")).thenReturn(stipAccount2);
		when(tmfManager.getLockedAmountByStipAccount(stipAccount2)).thenReturn(12000L);
		when(pcdabaNGManager.getCondAccntByComp_Id(conditionSetPK2)).thenReturn(accountCondSet2);
		when(tmfManager.getLockedAmountByStipAccount(stipAccount2)).thenReturn(12000L);

		final IzdLock cmsLock1 = buildCmsLock(1L, 12000L);
		List<IzdLock> cmsLocks = new ArrayList<>();
		cmsLocks.add(cmsLock1);
		when(izdLockManager.findIzdLocksByAccount(any(), eq(false), any(), any(), any(), any(), any(), any())).thenReturn(cmsLocks);

		final StipLocks rtpsLock1 = buildRtpsLock(1L, 12000L);
		List<StipLocks> rtpsLocks = new ArrayList<>();
		rtpsLocks.add(rtpsLock1);
		when(tmfManager.findStipLocksByAccount(any(), eq(false), any(), any(), any(), any(), any(), any())).thenReturn(rtpsLocks);

		when(pcdabaNGManager.getActiveAccountsByCif(cif, country)).thenReturn(accountList);
		SubRequest request = getSubrequest("get-card-account-balances");
		addElementOnRootElement(request, "cif", cif);
		addElementOnRootElement(request, "country", country);

		handler.handle(request);
		Element resultAccount1 = (Element) handler.compileResponse().getElement().element("accounts").elements().get(0);
		assertEquals("1000000001", resultAccount1.element("core-account").getText());
		assertEquals("12345678", resultAccount1.element("card-account").getText());
		assertEquals("EUR", resultAccount1.element("currency-alpha").getText());
		assertEquals("10.00", resultAccount1.element("credit-granted").getText());
		assertEquals("128.90", resultAccount1.element("credit-used").getText());
		assertEquals("-118.90", resultAccount1.element("credit-available").getText());
		assertEquals("18", resultAccount1.element("interest-rate").getText());
		assertEquals("1.00", resultAccount1.element("amount-initial").getText());
		assertEquals("120.00", resultAccount1.element("amount-locked").getText());
		assertEquals("120.00", resultAccount1.element("amount-locked-client").getText());
		assertEquals("0.10", resultAccount1.element("amount-bonus").getText());
		assertEquals("-118.90", resultAccount1.element("amount-available").getText());

		Element resultAccount2 = (Element) handler.compileResponse().getElement().element("accounts").elements().get(1);
		assertEquals("20", resultAccount2.element("interest-rate").getText());
	}

	@Test
	public void accountWithoutCreditAndWithPositiveBalance() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		Long amountInitial = 10000L;
		Long creditGranted = 0L;
		Long amountLocked = 0L;
		Long amountBonus = 0L;

		Long expectedCreditUsed = 0L;
		Long expectedCreditAvailable = 0L;
		Long expectedAmountAvailable = 10000L;

		String cif = "A00000";
		String country = "EE";

		List<PcdAccount> accountList = new ArrayList<>();

		PcdAccount pcdAccount1 = new PcdAccount();
		PcdAccParam pcdAccountParams1 = new PcdAccParam();
		pcdAccountParams1.setStatus("0"); // active
		pcdAccount1.setAccountNo(new BigDecimal(12345678));
		pcdAccountParams1.setUfield5("1000000001");
		pcdAccountParams1.setCondSet("001");
		pcdAccount1.setPcdAccParam(pcdAccountParams1);

		StipAccount stipAccount1 = new StipAccount();
		stipAccount1.setComp_id(new StipAccountPK("42800202350", "1"));
		stipAccount1.setCurrencyCode(new CurrencyCode());
		stipAccount1.getCurrencyCode().setCcyAlpha("EUR");
		stipAccount1.getCurrencyCode().setCcyNum("E1");
		stipAccount1.getCurrencyCode().setExpDot(2);
		stipAccount1.setInitialAmount(amountInitial);
		stipAccount1.setBonusAmount(amountBonus);
		stipAccount1.setCreditLimit(creditGranted);

		PcdCondAccntPK conditionSetPK1 = new PcdCondAccntPK("23", "50", pcdAccountParams1.getCondSet(), "EUR");
		PcdCondAccnt accountCondSet1 = new PcdCondAccnt();
		accountCondSet1.setDebIntrBaseP(BigDecimal.valueOf(18));

		accountList.add(pcdAccount1);

		when(tmfManager.findStipAccountByAccountNoAndCentreId(pcdAccount1.getAccountNo().toString(), "42800202350")).thenReturn(stipAccount1);
		when(tmfManager.getLockedAmountByStipAccount(stipAccount1)).thenReturn(amountLocked);
		when(pcdabaNGManager.getCondAccntByComp_Id(conditionSetPK1)).thenReturn(accountCondSet1);

		when(pcdabaNGManager.getActiveAccountsByCif(cif, country)).thenReturn(accountList);
		SubRequest request = getSubrequest("get-card-account-balances");
		addElementOnRootElement(request, "cif", cif);
		addElementOnRootElement(request, "country", country);

		handler.handle(request);
		Element resultAccount1 = (Element) handler.compileResponse().getElement().element("accounts").elements().get(0);
		assertEquals(amountToFormattedString(creditGranted),resultAccount1.element("credit-granted").getText());
		assertEquals(amountToFormattedString(expectedCreditUsed),resultAccount1.element("credit-used").getText());
		assertEquals(amountToFormattedString(expectedCreditAvailable),resultAccount1.element("credit-available").getText());
		assertEquals(amountToFormattedString(amountInitial),resultAccount1.element("amount-initial").getText());
		assertEquals(amountToFormattedString(amountLocked),resultAccount1.element("amount-locked").getText());
		assertEquals(amountToFormattedString(amountBonus),resultAccount1.element("amount-bonus").getText());
		assertEquals(amountToFormattedString(expectedAmountAvailable),resultAccount1.element("amount-available").getText());
	}

	@Test
	public void accountWithoutCreditAndWithNegativeBalance() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		Long amountInitial = -10000L;
		Long creditGranted = 0L;
		Long amountLocked = 0L;
		Long amountBonus = 0L;

		Long expectedCreditUsed = 10000L;
		Long expectedCreditAvailable = -10000L;
		Long expectedAmountAvailable = -10000L;

		String cif = "A00000";
		String country = "EE";

		List<PcdAccount> accountList = new ArrayList<>();

		PcdAccount pcdAccount1 = new PcdAccount();
		PcdAccParam pcdAccountParams1 = new PcdAccParam();
		pcdAccountParams1.setStatus("0"); // active
		pcdAccount1.setAccountNo(new BigDecimal(12345678));
		pcdAccountParams1.setUfield5("1000000001");
		pcdAccountParams1.setCondSet("001");
		pcdAccount1.setPcdAccParam(pcdAccountParams1);

		StipAccount stipAccount1 = new StipAccount();
		stipAccount1.setComp_id(new StipAccountPK("42800202350", "1"));
		stipAccount1.setCurrencyCode(new CurrencyCode());
		stipAccount1.getCurrencyCode().setCcyAlpha("EUR");
		stipAccount1.getCurrencyCode().setCcyNum("E1");
		stipAccount1.getCurrencyCode().setExpDot(2);
		stipAccount1.setInitialAmount(amountInitial);
		stipAccount1.setBonusAmount(amountBonus);
		stipAccount1.setCreditLimit(creditGranted);

		PcdCondAccntPK conditionSetPK1 = new PcdCondAccntPK("23", "50", pcdAccountParams1.getCondSet(), "EUR");
		PcdCondAccnt accountCondSet1 = new PcdCondAccnt();
		accountCondSet1.setDebIntrBaseP(BigDecimal.valueOf(18));

		accountList.add(pcdAccount1);

		when(tmfManager.findStipAccountByAccountNoAndCentreId(pcdAccount1.getAccountNo().toString(), "42800202350")).thenReturn(stipAccount1);
		when(tmfManager.getLockedAmountByStipAccount(stipAccount1)).thenReturn(amountLocked);
		when(pcdabaNGManager.getCondAccntByComp_Id(conditionSetPK1)).thenReturn(accountCondSet1);

		when(pcdabaNGManager.getActiveAccountsByCif(cif, country)).thenReturn(accountList);
		SubRequest request = getSubrequest("get-card-account-balances");
		addElementOnRootElement(request, "cif", cif);
		addElementOnRootElement(request, "country", country);

		handler.handle(request);
		Element resultAccount1 = (Element) handler.compileResponse().getElement().element("accounts").elements().get(0);
		assertEquals(amountToFormattedString(creditGranted),resultAccount1.element("credit-granted").getText());
		assertEquals(amountToFormattedString(expectedCreditUsed),resultAccount1.element("credit-used").getText());
		assertEquals(amountToFormattedString(expectedCreditAvailable),resultAccount1.element("credit-available").getText());
		assertEquals(amountToFormattedString(amountInitial),resultAccount1.element("amount-initial").getText());
		assertEquals(amountToFormattedString(amountLocked),resultAccount1.element("amount-locked").getText());
		assertEquals(amountToFormattedString(amountBonus),resultAccount1.element("amount-bonus").getText());
		assertEquals(amountToFormattedString(expectedAmountAvailable),resultAccount1.element("amount-available").getText());
	}

	@Test
	public void accountWithCreditAndPositiveBalance() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		Long amountInitial = 10010L;
		Long creditGranted = 10000L;
		Long amountLocked = 0L;
		Long amountBonus = 0L;

		Long expectedCreditUsed = 0L;
		Long expectedCreditAvailable = 10000L;
		Long expectedAmountAvailable = 10010L;

		String cif = "A00000";
		String country = "EE";

		List<PcdAccount> accountList = new ArrayList<>();

		PcdAccount pcdAccount1 = new PcdAccount();
		PcdAccParam pcdAccountParams1 = new PcdAccParam();
		pcdAccountParams1.setStatus("0"); // active
		pcdAccount1.setAccountNo(new BigDecimal(12345678));
		pcdAccountParams1.setUfield5("1000000001");
		pcdAccountParams1.setCondSet("001");
		pcdAccount1.setPcdAccParam(pcdAccountParams1);

		StipAccount stipAccount1 = new StipAccount();
		stipAccount1.setComp_id(new StipAccountPK("42800202350", "1"));
		stipAccount1.setCurrencyCode(new CurrencyCode());
		stipAccount1.getCurrencyCode().setCcyAlpha("EUR");
		stipAccount1.getCurrencyCode().setCcyNum("E1");
		stipAccount1.getCurrencyCode().setExpDot(2);
		stipAccount1.setInitialAmount(amountInitial);
		stipAccount1.setBonusAmount(amountBonus);
		stipAccount1.setCreditLimit(creditGranted);

		PcdCondAccntPK conditionSetPK1 = new PcdCondAccntPK("23", "50", pcdAccountParams1.getCondSet(), "EUR");
		PcdCondAccnt accountCondSet1 = new PcdCondAccnt();
		accountCondSet1.setDebIntrBaseP(BigDecimal.valueOf(18));

		accountList.add(pcdAccount1);

		when(tmfManager.findStipAccountByAccountNoAndCentreId(pcdAccount1.getAccountNo().toString(), "42800202350")).thenReturn(stipAccount1);
		when(tmfManager.getLockedAmountByStipAccount(stipAccount1)).thenReturn(amountLocked);
		when(pcdabaNGManager.getCondAccntByComp_Id(conditionSetPK1)).thenReturn(accountCondSet1);

		when(pcdabaNGManager.getActiveAccountsByCif(cif, country)).thenReturn(accountList);
		SubRequest request = getSubrequest("get-card-account-balances");
		addElementOnRootElement(request, "cif", cif);
		addElementOnRootElement(request, "country", country);

		handler.handle(request);
		Element resultAccount1 = (Element) handler.compileResponse().getElement().element("accounts").elements().get(0);
		assertEquals(amountToFormattedString(creditGranted),resultAccount1.element("credit-granted").getText());
		assertEquals(amountToFormattedString(expectedCreditUsed),resultAccount1.element("credit-used").getText());
		assertEquals(amountToFormattedString(expectedCreditAvailable),resultAccount1.element("credit-available").getText());
		assertEquals(amountToFormattedString(amountInitial),resultAccount1.element("amount-initial").getText());
		assertEquals(amountToFormattedString(amountLocked),resultAccount1.element("amount-locked").getText());
		assertEquals(amountToFormattedString(amountBonus),resultAccount1.element("amount-bonus").getText());
		assertEquals(amountToFormattedString(expectedAmountAvailable),resultAccount1.element("amount-available").getText());
	}

	@Test
	public void accountWithCreditNegativeBalanceAndPositiveOtb() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		Long amountInitial = 4000L;
		Long creditGranted = 10000L;
		Long amountLocked = 0L;
		Long amountBonus = 0L;

		Long expectedCreditUsed = 6000L;
		Long expectedCreditAvailable = 4000L;
		Long expectedAmountAvailable = 4000L;

		String cif = "A00000";
		String country = "EE";

		List<PcdAccount> accountList = new ArrayList<>();

		PcdAccount pcdAccount1 = new PcdAccount();
		PcdAccParam pcdAccountParams1 = new PcdAccParam();
		pcdAccountParams1.setStatus("0"); // active
		pcdAccount1.setAccountNo(new BigDecimal(12345678));
		pcdAccountParams1.setUfield5("1000000001");
		pcdAccountParams1.setCondSet("001");
		pcdAccount1.setPcdAccParam(pcdAccountParams1);

		StipAccount stipAccount1 = new StipAccount();
		stipAccount1.setComp_id(new StipAccountPK("42800202350", "1"));
		stipAccount1.setCurrencyCode(new CurrencyCode());
		stipAccount1.getCurrencyCode().setCcyAlpha("EUR");
		stipAccount1.getCurrencyCode().setCcyNum("E1");
		stipAccount1.getCurrencyCode().setExpDot(2);
		stipAccount1.setInitialAmount(amountInitial);
		stipAccount1.setBonusAmount(amountBonus);
		stipAccount1.setCreditLimit(creditGranted);

		PcdCondAccntPK conditionSetPK1 = new PcdCondAccntPK("23", "50", pcdAccountParams1.getCondSet(), "EUR");
		PcdCondAccnt accountCondSet1 = new PcdCondAccnt();
		accountCondSet1.setDebIntrBaseP(BigDecimal.valueOf(18));

		accountList.add(pcdAccount1);

		when(tmfManager.findStipAccountByAccountNoAndCentreId(pcdAccount1.getAccountNo().toString(), "42800202350")).thenReturn(stipAccount1);
		when(tmfManager.getLockedAmountByStipAccount(stipAccount1)).thenReturn(amountLocked);
		when(pcdabaNGManager.getCondAccntByComp_Id(conditionSetPK1)).thenReturn(accountCondSet1);

		when(pcdabaNGManager.getActiveAccountsByCif(cif, country)).thenReturn(accountList);
		SubRequest request = getSubrequest("get-card-account-balances");
		addElementOnRootElement(request, "cif", cif);
		addElementOnRootElement(request, "country", country);

		handler.handle(request);
		Element resultAccount1 = (Element) handler.compileResponse().getElement().element("accounts").elements().get(0);
		assertEquals(amountToFormattedString(creditGranted),resultAccount1.element("credit-granted").getText());
		assertEquals(amountToFormattedString(expectedCreditUsed),resultAccount1.element("credit-used").getText());
		assertEquals(amountToFormattedString(expectedCreditAvailable),resultAccount1.element("credit-available").getText());
		assertEquals(amountToFormattedString(amountInitial),resultAccount1.element("amount-initial").getText());
		assertEquals(amountToFormattedString(amountLocked),resultAccount1.element("amount-locked").getText());
		assertEquals(amountToFormattedString(amountBonus),resultAccount1.element("amount-bonus").getText());
		assertEquals(amountToFormattedString(expectedAmountAvailable),resultAccount1.element("amount-available").getText());
	}

	@Test
	public void accountWithCreditNegativeBalanceAndNegativeOtb() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		Long amountInitial = -10000L;
		Long creditGranted = 10000L;
		Long amountLocked = 0L;
		Long amountBonus = 0L;

		Long expectedCreditUsed = 20000L;
		Long expectedCreditAvailable = -10000L;
		Long expectedAmountAvailable = -10000L;

		String cif = "A00000";
		String country = "EE";

		List<PcdAccount> accountList = new ArrayList<>();

		PcdAccount pcdAccount1 = new PcdAccount();
		PcdAccParam pcdAccountParams1 = new PcdAccParam();
		pcdAccountParams1.setStatus("0"); // active
		pcdAccount1.setAccountNo(new BigDecimal(12345678));
		pcdAccountParams1.setUfield5("1000000001");
		pcdAccountParams1.setCondSet("001");
		pcdAccount1.setPcdAccParam(pcdAccountParams1);

		StipAccount stipAccount1 = new StipAccount();
		stipAccount1.setComp_id(new StipAccountPK("42800202350", "1"));
		stipAccount1.setCurrencyCode(new CurrencyCode());
		stipAccount1.getCurrencyCode().setCcyAlpha("EUR");
		stipAccount1.getCurrencyCode().setCcyNum("E1");
		stipAccount1.getCurrencyCode().setExpDot(2);
		stipAccount1.setInitialAmount(amountInitial);
		stipAccount1.setBonusAmount(amountBonus);
		stipAccount1.setCreditLimit(creditGranted);

		PcdCondAccntPK conditionSetPK1 = new PcdCondAccntPK("23", "50", pcdAccountParams1.getCondSet(), "EUR");
		PcdCondAccnt accountCondSet1 = new PcdCondAccnt();
		accountCondSet1.setDebIntrBaseP(BigDecimal.valueOf(18));

		accountList.add(pcdAccount1);

		when(tmfManager.findStipAccountByAccountNoAndCentreId(pcdAccount1.getAccountNo().toString(), "42800202350")).thenReturn(stipAccount1);
		when(tmfManager.getLockedAmountByStipAccount(stipAccount1)).thenReturn(amountLocked);
		when(pcdabaNGManager.getCondAccntByComp_Id(conditionSetPK1)).thenReturn(accountCondSet1);

		when(pcdabaNGManager.getActiveAccountsByCif(cif, country)).thenReturn(accountList);
		SubRequest request = getSubrequest("get-card-account-balances");
		addElementOnRootElement(request, "cif", cif);
		addElementOnRootElement(request, "country", country);

		handler.handle(request);
		Element resultAccount1 = (Element) handler.compileResponse().getElement().element("accounts").elements().get(0);
		assertEquals(amountToFormattedString(creditGranted),resultAccount1.element("credit-granted").getText());
		assertEquals(amountToFormattedString(expectedCreditUsed),resultAccount1.element("credit-used").getText());
		assertEquals(amountToFormattedString(expectedCreditAvailable),resultAccount1.element("credit-available").getText());
		assertEquals(amountToFormattedString(amountInitial),resultAccount1.element("amount-initial").getText());
		assertEquals(amountToFormattedString(amountLocked),resultAccount1.element("amount-locked").getText());
		assertEquals(amountToFormattedString(amountBonus),resultAccount1.element("amount-bonus").getText());
		assertEquals(amountToFormattedString(expectedAmountAvailable),resultAccount1.element("amount-available").getText());
	}

	@Test
	public void accountWithInternalLocksToHideFromCustomer() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		Long amountInitial = 10010L;
		Long creditGranted = 10000L;
		Long amountBonus = 0L;
		String cif = "A00000";
		String country = "EE";

		// CMS lock setup
		final IzdLock cmsLockToShow1 = buildCmsLock(1L, 2000L);
		final IzdLock cmsLockToHide2 = buildCmsLock(2L, 1000L); // credit
		cmsLockToHide2.setProcCode("21"); // deposit
		cmsLockToHide2.setFld026("6011"); // ATM
		cmsLockToHide2.setFld032("416352"); // LHV bank ATM
		cmsLockToHide2.setLockingSign(1);
		List<IzdLock> cmsLocks = new ArrayList<>();
		cmsLocks.add(cmsLockToShow1);
		cmsLocks.add(cmsLockToHide2);
		when(izdLockManager.findIzdLocksByAccount(any(), eq(false), any(), any(), any(), any(), any(), any())).thenReturn(cmsLocks);

		// RTPS lock setup
		final StipLocks rtpsLockToHide2 = buildRtpsLock(2L, 1000L); // credit
		rtpsLockToHide2.getStipLocksMatch().setFld003("210000"); // deposit
		rtpsLockToHide2.getStipLocksMatch().setFld026("6011"); // ATM
		rtpsLockToHide2.getStipLocksMatch().setFld032("416352"); // LHV bank ATM
		rtpsLockToHide2.setAmount(-1000L);
		final StipLocks rtpsLockToShow3 = buildRtpsLock(3L, 3000L);
		final StipLocks rtpsLockToHide4 = buildRtpsLock(4L, 500L); // credit
		rtpsLockToHide4.getStipLocksMatch().setFld003("210000"); // deposit
		rtpsLockToHide4.getStipLocksMatch().setFld026("6011"); // ATM
		rtpsLockToHide4.getStipLocksMatch().setFld032("416352"); // LHV bank ATM
		rtpsLockToHide2.setAmount(-500L);
		List<StipLocks> rtpsLocks = new ArrayList<>();
		rtpsLocks.add(rtpsLockToHide2);
		rtpsLocks.add(rtpsLockToShow3);
		rtpsLocks.add(rtpsLockToHide4);
		when(tmfManager.findStipLocksByAccount(any(), eq(false), any(), any(), any(), any(), any(), any())).thenReturn(rtpsLocks);

		// Account setup
		List<PcdAccount> accountList = new ArrayList<>();
		PcdAccount pcdAccount1 = new PcdAccount();
		PcdAccParam pcdAccountParams1 = new PcdAccParam();
		pcdAccountParams1.setStatus("0"); // active
		pcdAccount1.setAccountNo(new BigDecimal(12345678));
		pcdAccountParams1.setUfield5("1000000001");
		pcdAccountParams1.setCondSet("001");
		pcdAccount1.setPcdAccParam(pcdAccountParams1);

		StipAccount stipAccount1 = new StipAccount();
		stipAccount1.setComp_id(new StipAccountPK("42800202350", "1"));
		stipAccount1.setCurrencyCode(new CurrencyCode());
		stipAccount1.getCurrencyCode().setCcyAlpha("EUR");
		stipAccount1.getCurrencyCode().setCcyNum("E1");
		stipAccount1.getCurrencyCode().setExpDot(2);
		stipAccount1.setInitialAmount(amountInitial);
		stipAccount1.setBonusAmount(amountBonus);
		stipAccount1.setCreditLimit(creditGranted);

		PcdCondAccntPK conditionSetPK1 = new PcdCondAccntPK("23", "50", pcdAccountParams1.getCondSet(), "EUR");
		PcdCondAccnt accountCondSet1 = new PcdCondAccnt();
		accountCondSet1.setDebIntrBaseP(BigDecimal.valueOf(18));

		accountList.add(pcdAccount1);

		when(tmfManager.findStipAccountByAccountNoAndCentreId(pcdAccount1.getAccountNo().toString(), "42800202350")).thenReturn(stipAccount1);
		when(stipLocksDAO.calculateTotalLockedAmount(stipAccount1)).thenReturn(3500L);
		when(pcdabaNGManager.getCondAccntByComp_Id(conditionSetPK1)).thenReturn(accountCondSet1);

		when(pcdabaNGManager.getActiveAccountsByCif(cif, country)).thenReturn(accountList);
		SubRequest request = getSubrequest("get-card-account-balances");
		addElementOnRootElement(request, "cif", cif);
		addElementOnRootElement(request, "country", country);

		addLinkAppProperty(LinkAppProperties.CMS_BANK_CODE, Constants.CMS_BANK_CODE_23);
		addLinkAppProperty(LinkAppProperties.CMS_GROUP_CODE, Constants.CMS_GROUP_CODE_50);

		handler.handle(request);
		Element resultAccount1 = (Element) handler.compileResponse().getElement().element("accounts").elements().get(0);
		assertEquals(amountToFormattedString(3500L),resultAccount1.element("amount-locked").getText());
		assertEquals(amountToFormattedString(2000L+3000L),resultAccount1.element("amount-locked-client").getText());
	}

	private String amountToFormattedString(Long amount){
		return String.format("%.2f",new BigDecimal(amount).divide(new BigDecimal(100)));
	}

	private IzdLock buildCmsLock(long rowNumber, Long billingAmount) {
		final IzdLock cmsLock = new IzdLock();
		cmsLock.setRowNumb(rowNumber);
		cmsLock.setLockingSign(0);
		cmsLock.setIzdCcyFld051(new IzdCcyTable());
		cmsLock.getIzdCcyFld051().setExp("2");
		cmsLock.setFld006(billingAmount);
		return cmsLock;
	}

	private StipLocks buildRtpsLock(long rowNumber, Long billingAmount) {
		final StipLocks rtpsLock = new StipLocks();
		rtpsLock.setRowNumb(rowNumber);
		rtpsLock.setAmount(billingAmount);
		rtpsLock.setStipLocksMatch(new StipLocksMatch());
		rtpsLock.getStipLocksMatch().setCurrencyCodeByFld049(new CurrencyCode());
		rtpsLock.getStipLocksMatch().setCurrencyCodeByFld051(new CurrencyCode());
		rtpsLock.getStipLocksMatch().getCurrencyCodeByFld051().setExpDot(2);
		rtpsLock.getStipLocksMatch().setFld006(billingAmount);
		return rtpsLock;
	}
}
