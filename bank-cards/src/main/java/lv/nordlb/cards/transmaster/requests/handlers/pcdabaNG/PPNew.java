package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import java.math.BigDecimal;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

	public class PPNew extends SubRequestHandler{
		private PcdabaNGManager pcdabaNGManager = null;

		public PPNew() throws RequestPreparationException {
			super();	
			try {
				pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
			} catch (NamingException e) {
				throw new RequestPreparationException(e, this);
			}
		}

		@Override
		public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
			super.handle(r);
			PcdPpCard result;
			String cardNr=getStringFromNode("/do/card");
			String comment=getStringFromNode("/do/comment");
			String operator=getStringFromNode("/do/operator");
			if (cardNr == null || cardNr.equals("")) throw new RequestFormatException("Specify card number", this);
			
			/*
			 * Check if payment card is active
			 */
			PcdCard pcdCard = pcdabaNGManager.getCardByCardNumber(cardNr);

			/*
			* Check if card product is eligible for Priority Pass
			*/
			if (!PcdPpCard.isPriorityPassEligible(pcdCard.getCard())){
				throw new RequestProcessingException("This card is not eligible for a Priority Pass!", this);
			}
			
			if (!pcdCard.getStatus1().equals("0")) {
				throw new RequestProcessingException("Can't open Priority Pass for inactive card", this);
			}
			
			/*
			 * Check for active PriorityPass cards
			 */
			result = pcdabaNGManager.getPPCardInfoByCreditCard(cardNr);
			if (result != null){
				if(result.getStatus().equals(BigDecimal.ONE)){
					throw new RequestProcessingException("This bank card has an active PP card!", this);
				}
			}
			result = new PcdPpCard();
			result.setCtime(new Date());
			result.setOperator(operator);
			result.setComment(comment);
			result.setStatus(BigDecimal.valueOf(2));
			result.setEmailStatus(BigDecimal.ZERO);
			
			result.setPcdCard(pcdCard);
			pcdabaNGManager.saveOrUpdate(result);
		    ResponseElement response = createElement("new-pp");
		    response.createElement("result","New card is processed");
		    pcdabaNGManager.writeLog("pp-cards", "new-pp", operator, "Card: "+result.getFullCardNr()+" Cause: "+comment , "SUCCESSFUL");
		}

	}

