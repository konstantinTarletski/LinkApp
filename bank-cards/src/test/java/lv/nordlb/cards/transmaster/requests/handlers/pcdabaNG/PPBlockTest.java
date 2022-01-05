package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test Priority Pass card blocking handler
 * @author saldabols
 */
public class PPBlockTest extends JUnitHandlerTestBase {

	private PPBlock handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException{
		handler = new PPBlock();
	}
	
	@Test 
	public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		SubRequest request = getSubrequest("block-pp");
		
		// There is no card number
		checkRequestFormatException(handler, request, "Specify PP card number");
		
		addElementOnRootElement(request, "ppcard", "4775733282237579");
		addElementOnRootElement(request, "reason", "stolen");
		addElementOnRootElement(request, "comment", "Card is Stolen");
		addElementOnRootElement(request, "operator", "Zaiga");
		
		// Cannot find card
		checkRequestFormatException(handler, request, "Card with given number couldn't be found");
		
		PcdPpCard card = new PcdPpCard();
		when(pcdabaNGManager.getPPCardInfoByCardNumber("4775733282237579")).thenReturn(card);
		
		handler.handle(request);
		
		assertNotNull(card.getCtime());
		assertEquals("Zaiga", card.getOperator());
		assertEquals(0, card.getStatus().intValue());
		assertEquals(2, card.getEmailStatus().intValue());
		assertEquals("Cause: Stolen Comment: Card is Stolen", card.getComment());
		assertEquals("Card blocked", handler.compileResponse().getElement().element("block-pp").element("result").getTextTrim());
		verify(pcdabaNGManager).saveOrUpdate(card);
		verify(pcdabaNGManager).writeLog("pp-cards", "block-pp", "Zaiga", "Card: 4775733282237579 Cause: Cause: Stolen Comment: Card is Stolen", "SUCCESSFUL");
	}

}
