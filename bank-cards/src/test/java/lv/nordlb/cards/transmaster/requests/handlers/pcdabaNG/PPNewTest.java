package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Test class to test Priority Pass card ordering handler
 * @author saldabols
 */
public class PPNewTest extends JUnitHandlerTestBase {

	private PPNew handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException{
		handler = new PPNew();
	}
	
	@Test 
	public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		SubRequest request = getSubrequest("new-pp");
		String testCardNumber = "4999629000000001";
		String nonPPEligibleCardNumber = "4123450000000001";
		
		// There is no card number
		checkRequestFormatException(handler, request, "Specify card number");
		
		addElementOnRootElement(request, "card", testCardNumber);
		addElementOnRootElement(request, "comment", "Zelta klients");
		addElementOnRootElement(request, "operator", "Zaiga");
		
		PcdCard card = new PcdCard();
		card.setStatus1("1");
		card.setCard(nonPPEligibleCardNumber);
		when(pcdabaNGManager.getCardByCardNumber(testCardNumber)).thenReturn(card);

		// Card is not eligible for a Priority Pass
		checkRequestProcessingException(handler, request, "This card is not eligible for a Priority Pass!");

		card.setCard(testCardNumber);

		// Card is not active
		checkRequestProcessingException(handler, request, "Can't open Priority Pass for inactive card");
		
		card.setStatus1("0");
		PcdPpCard ppCard = new PcdPpCard();
		ppCard.setStatus(BigDecimal.ONE);
		when(pcdabaNGManager.getPPCardInfoByCreditCard(testCardNumber)).thenReturn(ppCard);
		
		// Already have PP card
		checkRequestProcessingException(handler, request, "This bank card has an active PP card!");
		
		ppCard.setStatus(BigDecimal.ZERO);// Has inactive PP card
		
		// Add card number when card is saved in DB
		when(pcdabaNGManager.saveOrUpdate(anyObject())).then(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if (args[0] instanceof PcdPpCard) {
					PcdPpCard ppCard = (PcdPpCard)args[0];
					ppCard.setCardNumber(1234);
					ppCard.setPcdCard(card);
					return ppCard;
				}
				return null;
			}
		});
		handler.handle(request);
		
		ArgumentCaptor<PcdPpCard> ppCardCaptor = ArgumentCaptor.forClass(PcdPpCard.class);
		assertEquals("New card is processed", handler.compileResponse().getElement().element("new-pp").element("result").getTextTrim());
		verify(pcdabaNGManager).saveOrUpdate(ppCardCaptor.capture());
		PcdPpCard savedCard = ppCardCaptor.getValue();
		assertNotNull(savedCard.getCtime());
		assertEquals("Zaiga", savedCard.getOperator());
		assertEquals(2, savedCard.getStatus().intValue());
		assertEquals(0, savedCard.getEmailStatus().intValue());
		assertEquals("Zelta klients", savedCard.getComment());
		assertEquals(card, savedCard.getPcdCard());
		verify(pcdabaNGManager).writeLog("pp-cards", "new-pp", "Zaiga", "Card: 720141128000012341 Cause: Zelta klients", "SUCCESSFUL");
	}

}
