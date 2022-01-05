package lv.bank.cards.soap.requests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Test class to test Response element methods
 * @author saldabols
 */
public class ResponseElementTest {

	@Test
	public void createResopnseElement(){
		ResponseElement element = new ResponseElement(false);
		assertNull(element.getElement());
		element.createElement("done");
		assertEquals("done", element.getElement().getName());
		ResponseElement info = element.createElement("card-info");
		info.addText("info");
		element.createElement("card", "12344321");
		assertEquals("info", element.getElement().element("card-info").getText());
		assertEquals("12344321", element.getElement().element("card").getText());
		element.addText("someText");
		element.addAttribute("format", "batch");
		assertEquals("someText", element.getElement().getText());
		assertEquals("batch", element.getElement().attribute("format").getText());
		
		element = new ResponseElement("done", false);
		assertEquals("<done/>", element.asXML());
	}
}
