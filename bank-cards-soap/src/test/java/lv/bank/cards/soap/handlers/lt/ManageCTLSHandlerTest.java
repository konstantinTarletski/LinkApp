package lv.bank.cards.soap.handlers.lt;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ManageCTLSHandlerTest extends JUnitTestBase {

    private ManageCTLSHandler handler;

    private CMSCallAPIWrapper cmsWraper = mock(CMSCallAPIWrapper.class);

    @Before
    public void setUpTest() throws RequestPreparationException {
        handler = spy(new ManageCTLSHandler());
        handler.setWrap(cmsWraper);
        handler.setCardsDAO(cardsDAO);
    }

    @Test
    public void handle() throws RequestPreparationException,
            RequestFormatException, RequestProcessingException,
            DataIntegrityException, CMSCallAPIException {
        SubRequest request = getSubrequest("manage-ctls");

        // There is no card
        checkRequestFormatException(handler, request, "Please provide valid card number");

        addElementOnRootElement(request, "card", "4775733282237579");
        addElementOnRootElement(request, "contactless", "1");

        // Cannot find card in PCD DB
        checkRequestProcessingException(handler, request, "Card with given number couldn't be found (pcd)");

        PcdCard pcdCard = new PcdCard();
        pcdCard.setCard("4775733282237579");
        when(cardsDAO.findByCardNumber("4775733282237579")).thenReturn(pcdCard);

        // Card has wrong contactless status
        checkRequestProcessingException(handler, request,
                "Cannot change contactless status for this card because current stutus is not 0 or 1");

        pcdCard.setContactless(3);

        checkRequestProcessingException(handler, request,
                "Cannot change contactless status for this card because current stutus is not 0 or 1");

        pcdCard.setContactless(0);

        handler.handle(request);

        assertEquals("<done><manage-ctls>done</manage-ctls></done>", handler.compileResponse().asXML());
        verify(cmsWraper).setChipTagValue("4775733282237579", ManageCTLSHandler.CONTACTLESS_CHIP_TAG, ManageCTLSHandler.CONTACTLESS_ENABLED);
    }
}
