package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test Card number getting by personal code
 * @author saldabols
 */
public class GetCardsByPersonalCodeTest extends JUnitHandlerTestBase {

	private GetCardsByPersonalCode handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException{
		handler = new GetCardsByPersonalCode();
	}
	
	@Test 
	public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		SubRequest request = getSubrequest("get-cards-by-personal-code");
		
		// There is no client id
		checkRequestFormatException(handler, request, "Specify personal code");
		
		addElementOnRootElement(request, "personal-code", "123123-1234");
		addElementOnRootElement(request, "country", "EE");
		
		when(pcdabaNGManager.getCardsByPersonalCode("123123-1234", "EE")).thenReturn(Arrays.asList("4775733282237579", "4775733282237570"));
		
		handler.handle(request);
		
		assertEquals("4775733282237579", ((Element) handler.compileResponse().getElement().element("get-cards-by-personal-code").elements().get(0)).getTextTrim());
		assertEquals("4775733282237570", ((Element) handler.compileResponse().getElement().element("get-cards-by-personal-code").elements().get(1)).getTextTrim());
	}
}
