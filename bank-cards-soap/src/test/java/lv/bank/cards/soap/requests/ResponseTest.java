package lv.bank.cards.soap.requests;

import lv.bank.cards.core.linkApp.PcdabaNGConstants;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class to test Response methods
 *
 * @author saldabols
 */
public class ResponseTest extends JUnitTestBase {

    protected final Context context = mock(Context.class);
    protected final SessionFactory sessionFactory =  mock(SessionFactory.class);

    @Rule
    public MockInitialContextRule mockInitialContextRule = new MockInitialContextRule(context);

    @Before
    public void initSystemSettings() throws NamingException {
        when(context.lookup(PcdabaNGConstants.HibernateSessionFactory)).thenReturn(sessionFactory);
    }

    @Test
    public void createBatch() throws RequestPreparationException, RequestFormatException {
        Response response = new Response();
        response.createBatch();
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<done format=\"batch\"/>", response.asXML());

        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("doId", "1");
        SubRequest request = getSubrequest("card-info", attributes);
        SubResponse subResponse = SubResponse.forRequest(request);
        subResponse.createElement("card-info");
        response.addSubResponse(subResponse);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<done format=\"batch\"><done doId=\"1\"><card-info/></done></done>", response.asXML());
    }

    @Test(expected = NullPointerException.class) // Cannot mock RtcuNGTemplatesManager, so expect NPE
    public void createBatch_template() throws RequestPreparationException, RequestFormatException {
        Response response = new Response();
        response.createBatch();
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<done format=\"batch\"/>", response.asXML());

        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("outTemplate", "update"); // Out template is not used. I think it is a bug
        SubRequest request = getSubrequest("card-info", attributes);
        SubResponse subResponse = SubResponse.forRequest(request);
        subResponse.createElement("card-info");
        response.addSubResponse(subResponse);
    }

    @Test
    public void addSubResponse() throws RequestPreparationException, RequestFormatException {
        Response response = new Response();
        SubRequest request = getSubrequest("card-info");
        SubResponse subResponse = SubResponse.forRequest(request);
        subResponse.createElement("card-info");
        response.addSubResponse(subResponse);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<done><card-info/></done>", response.asXML());
    }

    @Test(expected = NullPointerException.class) // Cannot mock RtcuNGTemplatesManager, so expect NPE
    public void addSubResponse_template() throws RequestPreparationException, RequestFormatException {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("outTemplate", "update");
        SubRequest request = getSubrequest("card-info", attributes);
        SubResponse subResponse = SubResponse.forRequest(request);
        new Response().addSubResponse(subResponse);
    }
}
