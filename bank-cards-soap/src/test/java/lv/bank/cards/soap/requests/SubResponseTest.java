package lv.bank.cards.soap.requests;

import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test class to test SubResponse methods
 *
 * @author saldabols
 */
public class SubResponseTest extends JUnitTestBase {

    @Test
    public void subResponse() throws RequestPreparationException, RequestFormatException {
        Map<String, String> map = new HashMap<>();
        map.put("doId", "1");
        map.put("outTemplate", "out");
        SubRequest request = getSubrequest("card-info", map);
        SubResponse response = SubResponse.forRequest(request);

        assertEquals("done", response.getElement().getName());
        assertEquals("1", response.getElement().attribute("doId").getText());
        assertEquals("out", response.getOutTemplateName());
    }
}
