package lv.bank.cards.soap.handlers;

import lv.bank.cards.core.linkApp.dao.PcdLogDAO;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.requests.SubRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class to test write log handler
 *
 * @author saldabols
 */
public class WriteLogTest extends JUnitTestBase {

    protected WriteLog handler;
    protected PcdLogDAO pcdLogDAO = mock(PcdLogDAO.class);

    @Before
    public void setUpTest() throws RequestPreparationException {
        handler = new WriteLog();
        handler.pcdLogDAO = pcdLogDAO;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest request = getSubrequest("write-log");

        // There is no log source
        checkRequestFormatException(handler, request, "Specify source tag");

        addElementOnRootElement(request, "source", "LinkApp");

        // There is no log operation
        checkRequestFormatException(handler, request, "Specify operation tag");

        addElementOnRootElement(request, "operation", "Add");

        // There is no log operator
        checkRequestFormatException(handler, request, "Specify operator tag");

        addElementOnRootElement(request, "operator", "Me");

        // There is no log result
        checkRequestFormatException(handler, request, "Specify result tag");

        addElementOnRootElement(request, "result", "Added");
        addElementOnRootElement(request, "text", "Comment");
        when(pcdLogDAO.write("LinkApp", "Add", "Me", "Comment", "Added")).thenReturn(3L);

        handler.handle(request);

        assertEquals("3", handler.compileResponse().getElement().element("write-log").element("log-id").getTextTrim());
    }
}
