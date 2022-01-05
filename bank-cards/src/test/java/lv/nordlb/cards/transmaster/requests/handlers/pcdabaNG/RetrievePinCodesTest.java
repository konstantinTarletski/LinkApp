package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.utils.LinkAppProperties;;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test Retrive PIN codes handler
 * @author saldabols
 */
public class RetrievePinCodesTest extends JUnitHandlerTestBase {

	private RetrievePinCodes handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException{
		LinkAppProperties.REREAD.set(false);
		addLinkAppProperty(LinkAppProperties.AUTHENTIFICATION_CODE_KEY, "F6A5508BC0EB4B33F58B5C120FB76A47F6A5508BC0EB4B33");
		handler = new RetrievePinCodes();
	}
	
	@Test 
	public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		SubRequest request = getSubrequest("check-cvc-retrieve-pin-codes");
		
		// There is no card number
		checkRequestFormatException(handler, request, "Please provide valid card number");
		
		addElementOnRootElement(request, "card", "4775733282237579");
		
		// Cannot find card
		checkRequestProcessingException(handler, request, "Card with given number couldn't be found (pcd)");
		
		PcdCard card = new PcdCard();
		when(pcdabaNGManager.getCardByCardNumber("4775733282237579")).thenReturn(card);
		
		// Card don't have PIN codes
		checkRequestProcessingException(handler, request, "Card do not have PIN ID (pcd)");
		
		card.setUAField1("1234567890098765432145EF0A203C36D1BB");
		
		handler.handle(request);
		
		Element info = handler.compileResponse().getElement().element("check-cvc-retrieve-pin-codes");
		assertEquals("4775733282237579", info.element("card").getTextTrim());
		assertEquals("1234567890", info.element("pin-id-code").getTextTrim());
		assertEquals("2134", info.element("pin-auth-code").getTextTrim());
	}

}
