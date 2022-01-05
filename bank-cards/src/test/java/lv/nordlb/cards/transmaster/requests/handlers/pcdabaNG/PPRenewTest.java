package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * Test class to test Priority Pass card renew handler
 * @author saldabols
 */
public class PPRenewTest extends JUnitHandlerTestBase {

	private PPRenew handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException{
		handler = new PPRenew();
	}
	
	@Test 
	public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		SubRequest request = getSubrequest("renew-pp");
		
		// There is no card number
		checkRequestFormatException(handler, request, "Specify PP card number");
		
		addElementOnRootElement(request, "ppcard", "720141128000012343");
		addElementOnRootElement(request, "reason", "stolen");
		addElementOnRootElement(request, "comment", "Card is Stolen");
		addElementOnRootElement(request, "operator", "Zaiga");
		
		// Cannot find card
		checkRequestProcessingException(handler, request, "No such Priority Pass card!");
				
		PcdPpCard card = new PcdPpCard();
		PcdCard c = new PcdCard();
		card.setPcdCard(c);
		when(pcdabaNGManager.getPPCardInfoByCardNumber("720141128000012343")).thenReturn(card);
		
		handler.handle(request);
		
		assertNotNull(card.getCtime());
		assertEquals("Zaiga", card.getOperator());
		assertEquals(0, card.getStatus().intValue());
		assertEquals(2, card.getEmailStatus().intValue());
		assertEquals("Cause: Stolen Comment: Card is Stolen", card.getComment());
		assertEquals("New card is processed", handler.compileResponse().getElement().element("renew-pp").element("result").getTextTrim());
		ArgumentCaptor<PcdPpCard> ppCardCaptor = ArgumentCaptor.forClass(PcdPpCard.class);
		verify(pcdabaNGManager, times(2)).saveOrUpdate(ppCardCaptor.capture());
		verify(pcdabaNGManager).writeLog("pp-cards", "renew-pp", "Zaiga", "Card: 720141128000012343 Cause: Stolen Comment: Card is Stolen", "SUCCESSFUL");
	
		PcdPpCard newCard = ppCardCaptor.getAllValues().get(1);
		assertEquals(card.getPcdCard(), newCard.getPcdCard());
		assertEquals(2, newCard.getStatus().intValue());
		assertEquals(0, newCard.getEmailStatus().intValue());
		assertNotNull(newCard.getCtime());
	}

}
