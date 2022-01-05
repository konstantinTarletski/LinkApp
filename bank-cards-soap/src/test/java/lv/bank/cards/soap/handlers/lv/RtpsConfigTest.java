package lv.bank.cards.soap.handlers.lv;

import lv.bank.cards.core.entity.rtps.Regdir;
import lv.bank.cards.core.rtps.dao.RegDirDAO;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class to test RTPS configuration information handler
 *
 * @author saldabols
 */
public class RtpsConfigTest extends JUnitTestBase {

    protected RtpsConfig handler;
    protected RegDirDAO regDirDAO = mock(RegDirDAO.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        handler = new RtpsConfig();
        handler.regDirDAO = regDirDAO;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException, DataIntegrityException {
        SubRequest request = getSubrequest("rtps-config");

        Regdir reg1 = new Regdir();
        reg1.setRegId(1L);
        reg1.setRegName("n1");
        reg1.setRegTypeId("t1");
        reg1.setReadOnly("r1");
        reg1.setRegValueTypeId("vt1");
        reg1.setRegValue("v1");
        Regdir reg2 = new Regdir();
        reg2.setRegName("n2");
        reg2.setRegTypeId("t2");
        reg2.setReadOnly("r2");
        reg2.setRegId(2L);

        when(regDirDAO.GetRegDir()).thenReturn(Arrays.asList(reg1, reg2));

        handler.handle(request);

        Element info1 = (Element) handler.compileResponse().getElement().elements("element").get(0);
        assertEquals("n1", info1.element("RegName").getText());
        assertEquals("t1", info1.element("RegTypeId").getText());
        assertEquals("r1", info1.element("RegReadOnly").getText());
        assertEquals("vt1", info1.element("RegValueTypeId").getText());
        assertEquals("v1", info1.element("RegValue").getText());
        assertEquals("1", info1.element("RegDir").getText());

        Element info2 = (Element) handler.compileResponse().getElement().elements("element").get(1);
        assertEquals("n2", info2.element("RegName").getText());
        assertEquals("t2", info2.element("RegTypeId").getText());
        assertEquals("r2", info2.element("RegReadOnly").getText());
        assertEquals("", info2.element("RegValueTypeId").getText());
        assertEquals("", info2.element("RegValue").getText());
        assertEquals("2", info2.element("RegDir").getText());
    }

}
