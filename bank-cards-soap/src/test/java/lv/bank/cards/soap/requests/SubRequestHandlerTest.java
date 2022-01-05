package lv.bank.cards.soap.requests;

import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test class to test SubRequestHandler methods
 *
 * @author saldabols
 */
public class SubRequestHandlerTest extends JUnitTestBase {

    @Test
    public void subRequestHandler() throws RequestPreparationException, RequestFormatException, RequestProcessingException, InterruptedException {
        SubRequestHandler handler = new SubRequestHandler() {};

        Map<String, String> map = new HashMap<>();
        map.put("doId", "1");
        SubRequest request = getSubrequest("card-info", map);
        addElementOnRootElement(request, "card", "123");
        addElementOnRootElement(request, "card", "321");
        addElementOnRootElement(request, "cif", "cif123");

        handler.handle(request);

        assertEquals(request, handler.subRequest);
        assertNotNull(handler.compileResponse());

        handler.createElement("card-info", "info");
        handler.createElement("other").addText("someText");

        assertEquals("info", handler.compileResponse().getElement().element("card-info").getText());
        assertEquals("someText", handler.compileResponse().getElement().element("other").getText());

        assertEquals("cif123", handler.getStringFromNode("/do/cif"));
        assertThat(handler.getStringListFromNode("/do/card"), hasItems("123", "321"));

        Thread.sleep(2000);

        assertTrue(handler.notUsedForSeconds(1));
        assertFalse(handler.notUsedForSeconds(20));

        // Use again and check
        handler.compileResponse();
        assertFalse(handler.notUsedForSeconds(1));
    }
}
