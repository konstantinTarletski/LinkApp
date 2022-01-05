package lv.bank.cards.soap.handlers;

import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class to test LinkApp properties file rereading handler
 *
 * @author saldabols
 */
public class RereadLinkAppPropertiesHandlerTest extends JUnitTestBase {

    private RereadLinkAppPropertiesHandler handler;

    @Before
    public void setUpTest() throws RequestPreparationException {
        handler = new RereadLinkAppPropertiesHandler();
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest request = getSubrequest("reread-link-app-properties");
        LinkAppProperties.REREAD.set(false);
        LinkAppProperties.CRYPTING_KEY_INIT_DATE.set(Calendar.getInstance());
        LinkAppProperties.TRANSFORMER_INIT_DATE.set(Calendar.getInstance());

        handler.handle(request);

        assertTrue(LinkAppProperties.REREAD.get());
        assertNull(LinkAppProperties.CRYPTING_KEY_INIT_DATE.get());
        assertNull(LinkAppProperties.TRANSFORMER_INIT_DATE.get());
    }

}
