package lv.bank.cards.soap.requests;

import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test class to test SubRequest methods
 *
 * @author saldabols
 */
public class SubRequestTest extends JUnitTestBase {

    @Test
    public void subRequest() throws RequestPreparationException, RequestFormatException {
        Map<String, String> map = new HashMap<>();
        map.put("doId", "1");
        map.put("outTemplate", "out");
        map.put("template", "template");
        map.put("dateFormat", "yyyy-MM-dd");
        SubRequest request = getSubrequest("card-info", map);

        assertEquals("1", request.getDoId());
        assertEquals("card-info", request.getFunction());
        assertEquals("out", request.getOutTemplateName());
        assertEquals("yyyy-MM-dd", ((SimpleDateFormat) request.getDateFormat()).toPattern());
    }
}
