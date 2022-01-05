package lv.bank.cards.soap.handlers.lv;

import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.entity.cms.IzdClientPK;
import lv.bank.cards.core.utils.Constants;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.naming.NamingException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LinkClientToCifTest extends JUnitTestBase {

    protected LinkClientToCif handler;
    protected CMSCallAPIWrapper callAPIWrapper = mock(CMSCallAPIWrapper.class);
    protected CardDAO cardDAO = mock(CardDAO.class);
    protected ClientDAO clientDAO = mock(ClientDAO.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        addLinkAppProperty(LinkAppProperties.CMS_BANK_CODE, Constants.CMS_BANK_CODE_23);
        handler = spy(new LinkClientToCif());
        handler.wrap = callAPIWrapper;
        handler.clientDAO = clientDAO;
        handler.cardDAO = cardDAO;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException, CMSCallAPIException {

        String client = "123456";
        String cif = "A12345";

        SubRequest request = getSubrequest("link-client-to-cif");

        // Missing client
        checkRequestFormatException(handler, request, "Specify client");

        // Missing CIF
        addElementOnRootElement(request, "client", client);
        checkRequestFormatException(handler, request, "Specify CIF");

        addElementOnRootElement(request, "cif", cif);

        // Client not found
        IzdClientPK izdClientPK = new IzdClientPK();
        izdClientPK.setClient(client);
        izdClientPK.setBankC(LinkAppProperties.getCmsBankCode());

        when(clientDAO.getObject(IzdClient.class, izdClientPK)).thenReturn(null);
        checkRequestProcessingException(handler, request, "Could not find izdClient with ID: " + client);

        // Client found
        IzdClient izdClient = new IzdClient();
        when(clientDAO.getObject(IzdClient.class, izdClientPK)).thenReturn(izdClient);
        when(cardDAO.doWork(any(CMSCallAPIWrapper.UpdateDBWork.class))).thenReturn("success");
        handler.handle(request);

        ArgumentCaptor<CMSCallAPIWrapper.UpdateDBWork> arg = ArgumentCaptor.forClass(CMSCallAPIWrapper.UpdateDBWork.class);
        verify(cardDAO, times(1)).doWork(arg.capture());

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>client</DOC_NAME>"
                + "<OPERATION>UPDATE</OPERATION><EXTERNAL_ID>1</EXTERNAL_ID><details><CLIENT>" + client + "</CLIENT>"
                + "<BANK_C>" + LinkAppProperties.getCmsBankCode() + "</BANK_C><CLIENT_B>" + cif + "</CLIENT_B></details>"
                + "</document></Changes_request>", arg.getAllValues().get(0).getInputXML());

        assertEquals("done", handler.compileResponse().getElement().element("link-client-to-cif").getText());
    }

}
