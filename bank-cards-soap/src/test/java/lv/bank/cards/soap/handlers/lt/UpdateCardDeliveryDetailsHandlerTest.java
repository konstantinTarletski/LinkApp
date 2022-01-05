package lv.bank.cards.soap.handlers.lt;

import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.utils.Constants;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateDBWork;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to test Card delivery details update handler
 *
 * @author saldabols
 */
public class UpdateCardDeliveryDetailsHandlerTest extends JUnitTestBase {

    private UpdateCardDeliveryDetailsHandler handler;
    private CMSCallAPIWrapper cmsWraper = mock(CMSCallAPIWrapper.class);
    protected CardDAO cardDAO = mock(CardDAO.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        handler = new UpdateCardDeliveryDetailsHandler();
        handler.setWrap(cmsWraper);
        handler.setCardDAO(cardDAO);
        handler.setCardsDAO(cardsDAO);
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest request = getSubrequest("get-pp-info");

        // There is no card number
        checkRequestFormatException(handler, request, "Please provide valid card number");

        addElementOnRootElement(request, "card", "4775733282237579");

        // There is no delivery address information
        checkRequestFormatException(handler, request, "Do not have delivery details");

        Element deliveryDetails = DocumentHelper.createElement("delivery-details");
        Element address = DocumentHelper.createElement("dlv_address");
        address.setText("Skankstes 12");
        deliveryDetails.add(address);

        request.getReq().getRootElement().add(deliveryDetails);

        // Delivery branch is mandatory
        checkRequestFormatException(handler, request, "Delivery details branch is mandatory");

        Element branch = DocumentHelper.createElement("branch");
        branch.setText(Constants.DELIVERY_BRANCH_LT_080);
        deliveryDetails.add(branch);

        // If delivery is to address then address fields are mandatory
        checkRequestFormatException(handler, request, "Missing value for delivery address country, region, street address");

        Element country = DocumentHelper.createElement("dlv_addr_country");
        country.setText("LV");
        deliveryDetails.add(country);
        Element region = DocumentHelper.createElement("dlv_addr_city");
        region.setText("Rigas nov.");
        deliveryDetails.add(region);
        Element street = DocumentHelper.createElement("dlv_addr_street2");
        street.setText("Skankstes iela 12");
        deliveryDetails.add(street);
        Element zip = DocumentHelper.createElement("dlv_addr_zip");
        zip.setText("LV-1012");
        deliveryDetails.add(zip);
        Element city = DocumentHelper.createElement("dlv_addr_street1");
        city.setText("Riga");
        deliveryDetails.add(city);
        Element addField = DocumentHelper.createElement("dlv_company");
        addField.setText("DNB");
        deliveryDetails.add(addField);

        // Cannot find card in LinkApp DB
        checkRequestProcessingException(handler, request, "Card with given number couldn't be found (pcd)");

        PcdCard card = new PcdCard();
        card.setBankC("23");
        card.setGroupc("50");
        when(cardsDAO.findByCardNumber("4775733282237579")).thenReturn(card);

        when(cardDAO.doWork(any(UpdateDBWork.class))).thenReturn("success");

        handler.handle(request);

        assertEquals("080", card.getUCod10());
        assertEquals(" LVRigas nov.                           Riga                                    Skankstes iela 12                  "
                + "                                             LV-1012         DNB                                                      "
                + "                 ", card.getUBField1());

        verify(cardsDAO).saveOrUpdate(card);
        verify(cardDAO).doWork(any(UpdateDBWork.class));

        assertEquals("4775733282237579", handler.compileResponse().getElement().element("update-card-delivery-details").element("card").getTextTrim());
        Element details = handler.compileResponse().getElement().element("update-card-delivery-details").element("delivery-details");
        assertEquals("Rigas nov., Riga, Skankstes iela 12, LV-1012", details.element("dlv_address").getTextTrim());
        assertEquals("LV", details.element("dlv_addr_country").getTextTrim());
        assertEquals("Rigas nov.", details.element("dlv_addr_city").getTextTrim());
        assertEquals("Riga", details.element("dlv_addr_street1").getTextTrim());
        assertEquals("Skankstes iela 12", details.element("dlv_addr_street2").getTextTrim());
        assertEquals("LV-1012", details.element("dlv_addr_zip").getTextTrim());
        assertEquals("DNB", details.element("dlv_company").getTextTrim());
        assertEquals("", details.element("dlv_language").getTextTrim());
        assertEquals(Constants.DELIVERY_BRANCH_LT_080, details.element("branch").getTextTrim());
    }
}
