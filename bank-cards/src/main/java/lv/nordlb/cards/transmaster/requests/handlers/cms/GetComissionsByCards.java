package lv.nordlb.cards.transmaster.requests.handlers.cms;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import lv.bank.cards.core.entity.cms.IzdPreProcessingRow;
import lv.nordlb.cards.transmaster.bo.interfaces.CardManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

public class GetComissionsByCards extends SubRequestHandler{
	private CardManager cardManager = null;
	private String dateTimeFormat = "ddMMyyyy";
	
	public GetComissionsByCards() throws RequestPreparationException {
		super();	
		try {
			cardManager=(CardManager) new InitialContext().lookup(CardManager.JNDI_NAME);
		} catch (NamingException e) {
			throw new RequestPreparationException(e, this);
		}
	}
	
	@Override
	public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
		super.handle(r);
		
		List <String> cards = getStringListFromNode("/do/card");

		if (cards == null || cards.isEmpty())
			throw new RequestFormatException("No card number provided!", this);

		ResponseElement response = createElement("get-comissions");
		for (String card : cards){
			List<IzdPreProcessingRow> list = cardManager.getIzdPreProcessingRowsByCard(card);
			//if (!it.hasNext()) throw new RequestFormatException("No unprocessed transactions", this);
			SimpleDateFormat formatter = new SimpleDateFormat();
			formatter.applyPattern(dateTimeFormat);
			for (IzdPreProcessingRow result : list){
				ResponseElement element = response.createElement("transaction");
				element.createElement("card",result.getCard().getCard());
				element.createElement("ccy",result.getCcy());
				element.createElement("tr-type",result.getTrType());
				element.createElement("date",formatter.format(result.getTranDateTime()));
				element.createElement("amount",String.valueOf(result.getAmount()));
				element.createElement("balance",String.valueOf(cardManager.getIzdAccountByAccountNr(result.getAccountNo()).getEndBal()));
				element.createElement("id",String.valueOf(result.getInternalNo()));
				element.createElement("description",result.getDealDesc());
			}
		}   	
	}
}
