package lv.bank.cards.soap.handlers;

import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class to test Card data handler
 * @author saldabols
 */
public class CardDataTest extends JUnitTestBase {

	private CardData handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException{
		handler = new CardData();
		handler.cardsDAO = cardsDAO;
	}
	
	@Test
	public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException{

		checkRequestFormatException(handler, getSubrequest("card-data"), "Specify either source either account tags");

		SubRequest request = getSubrequest("card-data");
		addElementOnRootElement(request, "card", "4775733282237579");
		addElementOnRootElement(request, "card", "4775733282237570");
		addElementOnRootElement(request, "account", "282237570");
		List<Object[]> result = new ArrayList<Object[]>();
		result.add(getResultRow("1"));
		result.add(getResultRow("2"));
		when(cardsDAO.findCardData(asList("4775733282237579", "4775733282237570"), asList("282237570"))).thenReturn(result);
		
		handler.handle(request);
		Element cardData = handler.compileResponse().getElement().element("card-data");
		checkCardData((Element)cardData.elements().get(0), "1");
		checkCardData((Element)cardData.elements().get(1), "2");
	}
	
	private Object[] getResultRow(String suffix){
		Object[] result = new Object[5];
		result[1] = "account" + suffix;
		result[2] = "card" + suffix;
		result[3] = "expiry" + suffix;
		result[4] = "cvc" + suffix;
		return result;
	}
	
	private void checkCardData(Element info, String suffix){
		assertEquals("account" + suffix, info.element("account").getTextTrim());
		assertEquals("card" + suffix, info.element("card").getTextTrim());
		assertEquals("expiry" + suffix, info.element("expiry").getTextTrim());
		assertEquals("cvc" + suffix, info.element("cvc2").getTextTrim());
	}
}
