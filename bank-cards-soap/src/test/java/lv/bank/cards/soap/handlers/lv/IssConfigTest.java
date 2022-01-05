package lv.bank.cards.soap.handlers.lv;

import lv.bank.cards.core.cms.dao.IzdConfigDAO;
import lv.bank.cards.core.entity.cms.IzdConfig;
import lv.bank.cards.core.entity.cms.IzdConfigPK;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.requests.SubRequest;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class to test CMS configuration listing handler
 *
 * @author saldabols
 */
public class IssConfigTest extends JUnitTestBase {

    protected IssConfig handler;
    protected IzdConfigDAO izdConfigDAO = mock(IzdConfigDAO.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        handler = new IssConfig();
        handler.izdConfigDAO = izdConfigDAO;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest request = getSubrequest("izd-config", "yyyy-MM-dd");
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        IzdConfig conf1 = new IzdConfig();
        conf1.setId(new IzdConfigPK("23", "50", 1));
        conf1.setParamValue("v1");
        conf1.setParamName("n1");
        conf1.setParamComent("c1");
        conf1.setUsrid("u1");
        conf1.setCtime(new Date());
        IzdConfig conf2 = new IzdConfig();
        conf2.setId(new IzdConfigPK("23", "50", 2));
        conf2.setParamValue("v2");
        conf2.setParamName("n2");
        conf2.setParamComent("c2");
        conf2.setUsrid("u2");
        conf2.setCtime(new Date());

        when(izdConfigDAO.GetIzdConfig()).thenReturn(Arrays.asList(conf1, conf2));

        handler.handle(request);

        Element cInfo1 = (Element) handler.compileResponse().getElement().elements().get(0);
        assertEquals("23", cInfo1.element("BankC").getText());
        assertEquals("50", cInfo1.element("GroupC").getText());
        assertEquals("1", cInfo1.element("ParamNum").getText());
        assertEquals("v1", cInfo1.element("Value").getText());
        assertEquals("n1", cInfo1.element("Name").getText());
        assertEquals("c1", cInfo1.element("Comment").getText());
        assertEquals("u1", cInfo1.element("UserID").getText());
        assertEquals(today, cInfo1.element("Ctime").getText());

        Element cInfo2 = (Element) handler.compileResponse().getElement().elements().get(1);
        assertEquals("23", cInfo2.element("BankC").getText());
        assertEquals("50", cInfo2.element("GroupC").getText());
        assertEquals("2", cInfo2.element("ParamNum").getText());
        assertEquals("v2", cInfo2.element("Value").getText());
        assertEquals("n2", cInfo2.element("Name").getText());
        assertEquals("c2", cInfo2.element("Comment").getText());
        assertEquals("u2", cInfo2.element("UserID").getText());
        assertEquals(today, cInfo2.element("Ctime").getText());
    }

}
