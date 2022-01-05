package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import java.math.BigDecimal;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Test class to test Priority Pass card information handler
 * @author saldabols
 */
public class PPCardInfoTest extends JUnitHandlerTestBase {

	private PPCardInfo handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException{
		handler = new PPCardInfo();
	}
	
	@Test 
	public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		String testCardNumber = "4999629000000001";
		SubRequest request = getSubrequest("get-pp-info");
		
		// There is no card number
		checkRequestFormatException(handler, request, "Specify card number");
		
		addElementOnRootElement(request, "card", testCardNumber);
		
		// Cannot find card
		checkRequestFormatException(handler, request, "Card with given number couldn't be found");
		
		PcdPpCard ppCard = new PcdPpCard();
		ppCard.setCardNumber(1234);
		ppCard.setStatus(BigDecimal.ZERO);
		ppCard.setOperator("Zaiga");
		ppCard.setComment("Blocked by Zaiga");
		PcdCard card = new PcdCard();
		card.setCard(testCardNumber);
		ppCard.setPcdCard(card);
		
		when(pcdabaNGManager.getPPCardInfoByCreditCard(testCardNumber)).thenReturn(ppCard);
		
		handler.handle(request);
		
		Element info = handler.compileResponse().getElement().element("get-pp-info");
		assertEquals("720141128000012341", info.element("card").getTextTrim());
		assertEquals("0", info.element("status").getTextTrim());
		assertEquals("Zaiga", info.element("operator").getTextTrim());
		assertEquals("Blocked by Zaiga", info.element("comments").getTextTrim());
	}

}
