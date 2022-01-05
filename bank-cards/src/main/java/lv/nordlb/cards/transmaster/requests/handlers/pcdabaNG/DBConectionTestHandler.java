package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.nordlb.cards.transmaster.bo.interfaces.CardManager;
import lv.bank.cards.core.entity.rtps.StipCard;
import lv.nordlb.cards.transmaster.fo.interfaces.StipCardManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

public class DBConectionTestHandler extends SubRequestHandler{
	
	private PcdabaNGManager pcdabaNGManager = null;
	private CardManager cardManager = null;
	private StipCardManager stipCardManager = null;
	
	public DBConectionTestHandler() throws RequestPreparationException {
		super();
		try {
			pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
			cardManager = (CardManager) new InitialContext().lookup(CardManager.JNDI_NAME);
			stipCardManager = (StipCardManager) new InitialContext().lookup(StipCardManager.JNDI_NAME);
		} catch (NamingException e) {
			throw new RequestPreparationException(e, this);
		}
	}
	
	public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
		super.handle(r);
		
		PcdCard card = pcdabaNGManager.getCardForTest();
		
		if(card == null){
			throw new RequestFormatException("Cannot find card in PCD");
		}
		
		IzdCard izdCard = cardManager.getIzdCardByCardNumber(card.getCard());
		
		if(izdCard == null)
			throw new RequestFormatException("Cannot find card in CMS");
		
		StipCard stipCard = stipCardManager.findStipCard(card.getCard());
		
		if(stipCard == null)
			throw new RequestFormatException("Cannot find card in RTPS");
		
		createElement("db-connection-test", "done");
	}
	
}
