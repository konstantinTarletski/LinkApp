package lv.bank.cards.soap.handlers;

import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * Class to test CardInfoAcs message handler
 * @author dobicinaitis
 */
public class CardInfoAcsTest extends JUnitTestBase {

    private CardInfoAcs handler;

    @Before
    public void setUpTest() throws RequestPreparationException {
        handler = new CardInfoAcs();
        handler.setCardsDAO(cardsDAO);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest request = getSubrequest("card-info-acs");
        String cardNumber = "4775730000001234";
        String productName = "Visa Debit";
        PcdCard pcdCard = new PcdCard();
        pcdCard.setRegion("LV");
        pcdCard.setIdCard("010101-12345");

        when(cardsDAO.findByCardNumber(cardNumber)).thenReturn(pcdCard);
        when(cardsDAO.getProductNameByCard(anyString())).thenReturn(productName);

        addElementOnRootElement(request, "card", cardNumber);
        handler.handle(request);
        checkCardInfo((Element)handler.compileResponse().getElement().elements().get(0), pcdCard.getIdCard(),
                pcdCard.getRegion(), productName);

    }

    @Test
    public void missingCardTag() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        checkRequestFormatException(handler, getSubrequest("card-info-acs"), "Specify card tag");
    }

    @Test
    public void invalidCardNumber()  throws RequestPreparationException, RequestFormatException{
        SubRequest request = getSubrequest("card-info-acs");
        addElementOnRootElement(request, "card", "wrong");
        checkRequestProcessingException(handler, request, "Please provide valid card number");
    }

    private void checkCardInfo(Element info, String idCard, String region, String productName) {
        assertEquals(idCard, info.element("person-code-card-holder").getTextTrim());
        assertEquals(region, info.element("country").getTextTrim());
        assertEquals(productName, info.element("product-name").getTextTrim());
    }
}

