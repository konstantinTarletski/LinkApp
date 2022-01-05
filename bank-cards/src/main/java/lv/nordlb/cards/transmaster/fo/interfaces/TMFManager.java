package lv.nordlb.cards.transmaster.fo.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import lv.bank.cards.core.entity.rtps.CardsException;
import lv.bank.cards.core.entity.rtps.Regdir;
import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.entity.rtps.StipLocks;
import lv.bank.cards.core.utils.DataIntegrityException;

@Local
public interface TMFManager {
	public static final String COMP_NAME="java:comp/env/ejb/TMFManager";
	public static final String JNDI_NAME="java:app/bankCards/TMFManagerBean";

	public CardsException findInCardsExceptionsList(String cardNumber) throws DataIntegrityException;
	public List<StipAccount> findStipAccountsByCardNumberAndCentreId(String cardNumber, String centreId ) throws DataIntegrityException;
	public List<StipLocks> findStipLocksByCardNumber( java.lang.String cardNumber,boolean mode );
	public List<StipLocks> findStipLocksByAccount(String account, boolean mode, Date fromDate, Date toDate, Long fromAmount, Long toAmount, String shop, List<String> cardNumbers);
	public Long getLockedAmountByStipAccount(StipAccount sa);
	public List<Regdir> getRegDir();
	public StipAccount findStipAccountByAccountNoAndCentreId(String accountNo, String centreId);
}
