package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.core.entity.cms.IzdAccParam;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdClAcct;
import lv.bank.cards.core.entity.linkApp.PcdAccParam;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdAgreement;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateDBWork;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditAgreementRequest;
import lv.nordlb.cards.transmaster.bo.interfaces.CommonManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class InformationChangeHandlerTest extends JUnitHandlerTestBase {

    protected InformationChangeHandler handler;
    protected CMSCallAPIWrapper cmsWraper = mock(CMSCallAPIWrapper.class);
    protected CommonManager commonManager = mock(CommonManager.class);
    protected CMSSoapAPIWrapper soapAPIWrapper = mock(CMSSoapAPIWrapper.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        when(context.lookup(CommonManager.JNDI_NAME)).thenReturn(commonManager);
        when(context.lookup(CMSSoapAPIWrapper.JNDI_NAME)).thenReturn(soapAPIWrapper);
        handler = spy(new InformationChangeHandler());
        handler.wrap = cmsWraper;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException,
            RequestProcessingException, CMSCallAPIException, CMSSoapAPIException {
        SubRequest request = getSubrequest("information-change");

        handler.handle(request);
        assertEquals("<done><information-change/></done>", handler.compileResponse().asXML());

        Element cardElement = addElementOnRootElement(request, "card", "");
        addAttributeOnElement(cardElement, "pan", "4775733282237579");

        checkRequestProcessingException(handler, request, "Card with given number couldn't be found (pcd)");

        PcdCard pcdCard = new PcdCard();
        pcdCard.setCard("4775733282237579");
        pcdCard.setBankC("23");
        pcdCard.setGroupc("50");
        pcdCard.setPcdAgreement(new PcdAgreement());
        pcdCard.getPcdAgreement().setAgreement(4321L);
        pcdCard.setPcdAccounts(new HashSet<PcdAccount>());
        pcdCard.setContactless(0);
        PcdAccount account = new PcdAccount();
        account.setAccountNo(BigDecimal.TEN);
        account.setCardAcct("ca1");
        account.setPcdAccParam(new PcdAccParam());
        account.getPcdAccParam().setCondSet("123");
        pcdCard.getPcdAccounts().add(account);
        when(pcdabaNGManager.getCardByCardNumber("4775733282237579")).thenReturn(pcdCard);

        checkRequestProcessingException(handler, request, "Card with given number couldn't be found (izd)");

        IzdCard izdCard = new IzdCard();
        izdCard.setCard("4775733282237579");
        izdCard.setIzdClAcct(new IzdClAcct());
        izdCard.getIzdClAcct().setCardAcct("ca1");
        izdCard.getIzdClAcct().setIzdAccount(new IzdAccount());
        izdCard.getIzdClAcct().getIzdAccount().setAccountNo(BigDecimal.TEN);
        izdCard.getIzdClAcct().getIzdAccount().setIzdAccParam(new IzdAccParam());
        izdCard.getIzdClAcct().getIzdAccount().getIzdAccParam().setCondSet("123");
        when(cardManager.getIzdCardByCardNumber("4775733282237579")).thenReturn(izdCard);
        when(cardManager.doWork(any(UpdateDBWork.class))).thenReturn("success");

        handler.handle(request);
        assertEquals("<done><information-change><card pan=\"4775733282237579\">"
                + "<status>OK</status></card></information-change></done>", handler.compileResponse().asXML());

        verifyZeroInteractions(commonManager);

        addElement(cardElement, "card-password", "pass");
        addElement(cardElement, "language", "1");
        addElement(cardElement, "renew", "J");
        addElement(cardElement, "statement-mode", "11");
        addElement(cardElement, "stmt-street", "street");
        addElement(cardElement, "stmt-city", "city");
        addElement(cardElement, "stmt-zip", "zip");
        addElement(cardElement, "stmt-country", "LVL");
        addElement(cardElement, "contactless", "1");

        Element cardElement2 = addElementOnRootElement(request, "card", "");
        addAttributeOnElement(cardElement2, "pan", "5775733282237579");
        PcdCard pcdCard2 = new PcdCard();
        pcdCard2.setCard("5775733282237579");
        pcdCard2.setBankC("23");
        pcdCard2.setGroupc("50");
        pcdCard2.setPcdAgreement(new PcdAgreement());
        pcdCard2.getPcdAgreement().setAgreement(4321L);
        when(pcdabaNGManager.getCardByCardNumber("5775733282237579")).thenReturn(pcdCard2);
        IzdCard izdCard2 = new IzdCard();
        izdCard2.setCard("5775733282237579");
        izdCard2.setIzdClAcct(new IzdClAcct());
        izdCard2.getIzdClAcct().setCardAcct("ca2");
        izdCard2.getIzdClAcct().setIzdAccount(new IzdAccount());
        izdCard2.getIzdClAcct().getIzdAccount().setAccountNo(BigDecimal.ONE);
        izdCard2.getIzdClAcct().getIzdAccount().setIzdAccParam(new IzdAccParam());
        izdCard2.getIzdClAcct().getIzdAccount().getIzdAccParam().setCondSet("323");
        when(cardManager.getIzdCardByCardNumber("5775733282237579")).thenReturn(izdCard2);

        handler.handle(request);

        ArgumentCaptor<UpdateDBWork> arg = ArgumentCaptor.forClass(UpdateDBWork.class);
        ArgumentCaptor<RowTypeEditAgreementRequest> editAgreementArg = ArgumentCaptor.forClass(RowTypeEditAgreementRequest.class);

        verify(cardManager, times(2)).doWork(arg.capture());
        verify(soapAPIWrapper, times(1)).editAgreement(editAgreementArg.capture());
        verify(cmsWraper).setChipTagValue("4775733282237579", "BF5B", "DF01020000");

        assertEquals(editAgreementArg.getAllValues().get(0).getAGRENOM().intValue(), 4321);
        assertEquals(editAgreementArg.getAllValues().get(0).getSTREET(), "street");
        assertEquals(editAgreementArg.getAllValues().get(0).getCITY(), "city");
        assertEquals(editAgreementArg.getAllValues().get(0).getPOSTIND(), "zip");
        assertEquals(editAgreementArg.getAllValues().get(0).getREPLANG(), "1");
        assertEquals(editAgreementArg.getAllValues().get(0).getDISTRIBMODE(), "11");
        assertEquals(editAgreementArg.getAllValues().get(0).getCOUNTRY(), "LVL");
        
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>card</DOC_NAME>"
                + "<OPERATION>update</OPERATION><EXTERNAL_ID>1</EXTERNAL_ID><details><CARD>4775733282237579</CARD><BANK_C>23</BANK_C>"
                + "<GROUPC>50</GROUPC><M_NAME>pass</M_NAME></details></document>"
                + "</Changes_request>", arg.getAllValues().get(0).getInputXML());

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>account</DOC_NAME>"
                + "<OPERATION>update</OPERATION><EXTERNAL_ID>1</EXTERNAL_ID><details><ACCOUNT_NO>10</ACCOUNT_NO><BANK_C>23</BANK_C>"
                + "<GROUPC>50</GROUPC><COND_SET>223</COND_SET></details></document>"
                + "</Changes_request>", arg.getAllValues().get(1).getInputXML());

        assertEquals("pass", pcdCard.getMName());
        assertEquals("1", pcdCard.getPcdAgreement().getRepLang());
        assertEquals("11", pcdCard.getPcdAgreement().getDistribMode());
        assertEquals("street", pcdCard.getPcdAgreement().getStreet());
        assertEquals("city", pcdCard.getPcdAgreement().getCity());
        assertEquals("zip", pcdCard.getPcdAgreement().getPostInd());
        assertEquals("LVL", pcdCard.getPcdAgreement().getCountry());

        assertEquals("<done><information-change><card pan=\"4775733282237579\">"
                + "<status>OK</status></card><card pan=\"5775733282237579\">"
                + "<status>OK</status></card></information-change></done>", handler.compileResponse().asXML());
    }
}
