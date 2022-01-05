package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import java.math.BigDecimal;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import lv.bank.cards.core.entity.cms.IzdClAcct;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.nordlb.cards.transmaster.bo.interfaces.AccountManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.core.utils.DataIntegrityException;

import org.apache.commons.lang.StringUtils;

public class UnlinkAccountHandler extends SubRequestHandler{
	
	private PcdabaNGManager pcdabaNGManager = null;
	private AccountManager accountManager = null;

	
	public UnlinkAccountHandler() throws RequestPreparationException {
		super();
		try {
			pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
			accountManager = (AccountManager) new InitialContext().lookup(AccountManager.JNDI_NAME);
		} catch (NamingException e) {
			throw new RequestPreparationException(e, this);
		}
	}
	
	public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
		super.handle(r);
		String cardAccount = getStringFromNode("/do/card-account");
		String platonAccount = getStringFromNode("/do/platon-account");
		
		if(StringUtils.isBlank(cardAccount)){
			throw new RequestFormatException("Missing card account");
		}
		
		if(StringUtils.isBlank(platonAccount)){
			throw new RequestFormatException("Missing platon account");
		}
		
		BigDecimal accountId = null;
		try{
			accountId = new BigDecimal(cardAccount);
		} catch(NumberFormatException e){
			throw new RequestFormatException("Card account is not a number");
		}
		
		PcdAccount account = pcdabaNGManager.getAccountByAccountNo(accountId);
		
		if("0".equals(account.getPcdAccParam().getStatus())){
			throw new RequestProcessingException("Card account has to be dormanted or closed");
		}
		
		IzdAccount izdAccount;
		try {
			izdAccount = accountManager.findAccountByAccountNo(accountId);
		} catch (DataIntegrityException e) {
			e.printStackTrace();
			throw new RequestProcessingException(e);
		}
		
		if(!platonAccount.equals(izdAccount.getIzdAccParam().getUfield5())){
			throw new RequestProcessingException("Given platon account does not match with existing");
		}
		
		account.getPcdAccParam().setUfield5(null);
		account.setIban(null);
		pcdabaNGManager.saveOrUpdate(account);
		izdAccount.getIzdAccParam().setUfield5(null);

		try {
			for (IzdClAcct izdClAcct : izdAccount.getIzdClAccts()) {
				if (izdClAcct.getIzdAccount().getAccountNo().equals(account.getAccountNo())){
					izdClAcct.setIban(null);
					accountManager.saveOrUpdate(izdClAcct);
				}
			}
			accountManager.saveOrUpdate(izdAccount);
		} catch (DataIntegrityException e) {
			e.printStackTrace();
			throw new RequestProcessingException(e);
		}
		
		createElement("unlink-account", "done");
	}

}
