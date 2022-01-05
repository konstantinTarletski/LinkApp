package lv.nordlb.cards.transmaster.requests.handlers.cms;

import static lv.nordlb.cards.transmaster.requests.handlers.cms.AuthBonusHandler.DATE_FORMAT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.naming.NamingException;

import lv.bank.cards.core.entity.cms.IzdAccParam;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.nordlb.cards.transmaster.bo.interfaces.AccountManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.JUnitHandlerTestBase;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateDBWork;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIWrapper;
import lv.bank.cards.core.utils.DataIntegrityException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class AuthBonusHandlerTest extends JUnitHandlerTestBase {

	private AuthBonusHandler handler;
	
	private CMSCallAPIWrapper cmsWraper = mock(CMSCallAPIWrapper.class);
	
	private RTPSCallAPIWrapper rtpsWraper = mock(RTPSCallAPIWrapper.class);
	
	private AccountManager accountManager = mock(AccountManager.class);
	
	@Before
	public void setUpTest() throws RequestPreparationException, NamingException{
		when(context.lookup(AccountManager.JNDI_NAME)).thenReturn(accountManager);
		handler = spy(new AuthBonusHandler());
		handler.setWrap(cmsWraper);
		handler.setRtpsCallApi(rtpsWraper);
	}

	@Test
	public void handle() throws RequestPreparationException, RequestFormatException, 
			RequestProcessingException, CMSCallAPIException, DataIntegrityException, RTPSCallAPIException{
		
		SubRequest request = getRequest(null, null, null, null);
		
		// Missing everything
		checkRequestFormatException(handler, request, "Not specified account tag");
		
		// Missing amount
		request = getRequest("wrong", null, null, null);
		checkRequestFormatException(handler, request, "Not specified amount tag");
		
		// Missing expiry
		request = getRequest("wrong", "wrong", null, null);
		checkRequestFormatException(handler, request, "Not specified expiry tag");
		
		// Missing action
		request = getRequest("wrong", "wrong", "wrong", null);
		checkRequestFormatException(handler, request, "Not specified action tag");
		
		// Cannot parse account
		request = getRequest("wrong", "wrong", "wrong", "wrong");
		checkRequestFormatException(handler, request, "Wrong account number");
		
		// Cannot find account
		request = getRequest("1313", "wrong", "wrong", "wrong");
		checkRequestFormatException(handler, request, "Cannot find account");
		
		Calendar twoDays = Calendar.getInstance();
		twoDays.add(Calendar.DAY_OF_MONTH, 2);
		
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DAY_OF_MONTH, 1);
		
		IzdAccount acc = new IzdAccount();
		acc.setAccountNo(new BigDecimal("1313"));
		acc.setBankC("23");
		acc.setGroupC("50");
		acc.setIzdAccParam(new IzdAccParam());
		acc.getIzdAccParam().setAuthBonus(-300L);
		acc.getIzdAccParam().setAbExpirity(twoDays.getTime());
		when(accountManager.findAccountByAccountNo(new BigDecimal("1313"))).thenReturn(acc);
		
		// Cannot parse amount
		request = getRequest("1313", "wrong", "wrong", "wrong");
		checkRequestFormatException(handler, request, "Wrong amount format");
		
		// Positive amount
		request = getRequest("1313", "-999999999999", "wrong", "wrong");
		checkRequestFormatException(handler, request, "Wrong amount");
		
		// Cannot parse date
		request = getRequest("1313", "-4", "wrong", "wrong");
		checkRequestFormatException(handler, request, "Wrong expirey format");
		
		// Wrong action
		request = getRequest("1313", "-4", DATE_FORMAT.get().format(day.getTime()), "wrong");
		checkRequestFormatException(handler, request, "Wrong action");
		
		// Total bonus positive
		request = getRequest("1313", "4", DATE_FORMAT.get().format(day.getTime()), "add");
		checkRequestFormatException(handler, request, "Wrong amount after adding");
		
		request = getRequest("1313", "-36941.77", DATE_FORMAT.get().format(day.getTime()), "add");
		when(cardManager.doWork(any(UpdateDBWork.class))).thenReturn("success");
		
		handler.handle(request);
		
		ArgumentCaptor<UpdateDBWork> arg = ArgumentCaptor.forClass(UpdateDBWork.class);
		verify(cardManager, times(1)).doWork(arg.capture());
		verify(rtpsWraper).setAccBonusAmount("42800202350", "1313", -3694477L);
		
		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/>"
				+ "<DOC_NAME>account</DOC_NAME><OPERATION>update</OPERATION><EXTERNAL_ID>1</EXTERNAL_ID>"
				+ "<details><ACCOUNT_NO>1313</ACCOUNT_NO><BANK_C>23</BANK_C><GROUPC>50</GROUPC>"
				+ "<AUTH_BONUS>-3694477</AUTH_BONUS><AB_EXPIRITY>to_date('" + 
				DATE_FORMAT.get().format(twoDays.getTime()) + "','yyyy.mm.dd')</AB_EXPIRITY>"
						+ "</details></document></Changes_request>", arg.getAllValues().get(0).getInputXML());
	}
	
	private SubRequest getRequest(String account, String amount, String expiry, String action) 
			throws RequestPreparationException, RequestFormatException {
		SubRequest request = getSubrequest("auth-bonus");
		
		if(account != null)
			addElementOnRootElement(request, "account", account);
		if(amount != null) 
			addElementOnRootElement(request, "amount", amount);
		if(expiry != null)
			addElementOnRootElement(request, "expiry", expiry);
		if(action != null)
			addElementOnRootElement(request, "action", action);
		return request;
	}


}
