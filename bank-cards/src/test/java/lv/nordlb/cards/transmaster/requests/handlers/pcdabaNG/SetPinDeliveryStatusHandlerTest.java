package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCardsAddFields;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.core.utils.DataIntegrityException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test PIN delivery status change handler
 * @author saldabols
 */
public class SetPinDeliveryStatusHandlerTest extends JUnitHandlerTestBase {

	private SetPinDeliveryStatusHandler handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException{
		handler = new SetPinDeliveryStatusHandler();
	}
	
	@Test 
	public void handle() throws RequestPreparationException, RequestFormatException, 
			RequestProcessingException, DataIntegrityException{
		SubRequest request = getSubrequest("set-pin-delivery-status");
		
		// There is no card number
		checkRequestFormatException(handler, request, "Please provide valid card number");
		
		addElementOnRootElement(request, "card", "4775733282237579");
		
		// New PIN status is not specified
		checkRequestFormatException(handler, request, "Specify pin-delivery-status tag");
		
		addElementOnRootElement(request, "pin-delivery-status", "FAILED");
		
		// Status is not in the list
		checkRequestFormatException(handler, request, "Status is not in list 'AVAILABLE', 'REQUESTED', 'DELETED', 'DELIVERED'");
	
		request = getSubrequest("set-pin-delivery-status");
		addElementOnRootElement(request, "card", "4775733282237579");
		addElementOnRootElement(request, "pin-delivery-status", "DELIVERED");
		
		// Cannot find card in LinkApp DB
		checkRequestProcessingException(handler, request, "Card with given number couldn't be found (pcd)");
		
		PcdCard card = new PcdCard();
		card.setUAField1("code");
		when(pcdabaNGManager.getCardByCardNumber("4775733282237579")).thenReturn(card);
		
		// Cannot find card in CMS DB
		checkRequestProcessingException(handler, request, "Card with given number couldn't be found (izd)");
		
		IzdCard izdCard = new IzdCard();
		when(cardManager.getIzdCardByCardNumber("4775733282237579")).thenReturn(izdCard);
	
		// Card don't have additional fields
		checkRequestProcessingException(handler, request, "Card is not present in IZD_CARDS_ADD_FIELDS table");
		
		izdCard.setIzdCardsAddFields(new IzdCardsAddFields());
		izdCard.getIzdCardsAddFields().setUAField1("code");
		
		handler.handle(request);
		
		assertEquals("DELIVERED", card.getUAField2());
		assertEquals("DELIVERED", izdCard.getIzdCardsAddFields().getUAField2());
		assertNull(card.getUAField1());
		assertNull(izdCard.getIzdCardsAddFields().getUAField1());
		assertEquals("4775733282237579", handler.compileResponse().getElement().element("set-pin-delivery-status").element("card").getTextTrim());
		assertEquals("DELIVERED", handler.compileResponse().getElement().element("set-pin-delivery-status").element("pin-delivery-status").getTextTrim());
		
		verify(pcdabaNGManager).saveOrUpdate(card);
		verify(cardManager).saveOrUpdate(izdCard.getIzdCardsAddFields());
		
	}
}
