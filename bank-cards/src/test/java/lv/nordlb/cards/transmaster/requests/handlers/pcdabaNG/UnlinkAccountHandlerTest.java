package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.naming.NamingException;

import lv.bank.cards.core.entity.cms.IzdClAcct;
import lv.bank.cards.core.entity.linkApp.PcdAccParam;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.cms.IzdAccParam;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.nordlb.cards.transmaster.bo.interfaces.AccountManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.core.utils.DataIntegrityException;

import org.junit.Before;
import org.junit.Test;

public class UnlinkAccountHandlerTest extends JUnitHandlerTestBase {

	private UnlinkAccountHandler handler;
	
	private AccountManager accountManager = mock(AccountManager.class);
	
	
	@Before
	public void setUpTest() throws RequestPreparationException, NamingException{
		when(context.lookup(AccountManager.JNDI_NAME)).thenReturn(accountManager);
		handler = new UnlinkAccountHandler();
	}
	
	@Test
	public void handle()throws RequestPreparationException, RequestFormatException, 
			RequestProcessingException, DataIntegrityException{
		SubRequest request = getSubrequest("unlink-account");
		
		checkRequestFormatException(handler, request, "Missing card account");
		
		addElementOnRootElement(request, "card-account", "x1");
		checkRequestFormatException(handler, request, "Missing platon account");
		
		addElementOnRootElement(request, "platon-account", "p123");
		checkRequestFormatException(handler, request, "Card account is not a number");
		
		request = getSubrequest("unlink-account");
		addElementOnRootElement(request, "platon-account", "p123");
		addElementOnRootElement(request, "card-account", "321");
		
		PcdAccount account = new PcdAccount();
		account.setAccountNo(BigDecimal.ONE);
		account.setPcdAccParam(new PcdAccParam());
		account.getPcdAccParam().setUfield5("1");
		when(pcdabaNGManager.getAccountByAccountNo(eq(new BigDecimal("321")))).thenReturn(account);

		account.getPcdAccParam().setStatus("0");
		IzdAccount izdAccount = new IzdAccount();
		izdAccount.setAccountNo(BigDecimal.ONE);
		izdAccount.setIzdAccParam(new IzdAccParam());
		izdAccount.getIzdAccParam().setUfield5("1");
		when(accountManager.findAccountByAccountNo(eq(new BigDecimal("321")))).thenReturn(izdAccount);

		IzdClAcct izdClAcct = new IzdClAcct();
		izdClAcct.setIzdAccount(izdAccount);
		izdClAcct.setIban("LV00RIKO0000000000001");
		Set izdClAcctSet = new HashSet();
		izdClAcctSet.add(izdClAcct);
		izdAccount.setIzdClAccts(izdClAcctSet);

		checkRequestProcessingException(handler, request, "Card account has to be dormanted or closed");
		
		account.getPcdAccParam().setStatus("4");
		checkRequestProcessingException(handler, request, "Given platon account does not match with existing");
		
		izdAccount.getIzdAccParam().setUfield5("p123");
		
		handler.handle(request);
		
		assertNull(account.getPcdAccParam().getUfield5());
		assertNull(account.getIban());
		assertNull(izdAccount.getIzdAccParam().getUfield5());
		assertNull(izdAccount.getIzdClAccts().stream().findFirst().get().getIban());
		
		assertEquals("<done><unlink-account>done</unlink-account></done>", handler.compileResponse().asXML());
	}
}
