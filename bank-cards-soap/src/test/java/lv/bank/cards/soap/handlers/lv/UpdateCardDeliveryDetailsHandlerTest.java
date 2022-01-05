package lv.bank.cards.soap.handlers.lv;

import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
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

    protected UpdateCardDeliveryDetailsHandler handler;
    protected CMSCallAPIWrapper cmsWraper = mock(CMSCallAPIWrapper.class);
    protected CommonDAO commonDAO = mock(CommonDAO.class);
    protected CardDAO cardDAO = mock(CardDAO.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        handler = new UpdateCardDeliveryDetailsHandler();
        handler.commonDAO = commonDAO;
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
        branch.setText(Constants.DELIVERY_BRANCH_LV_888);
        deliveryDetails.add(branch);

        // If delivery is to address then address fields are mandatory
        checkRequestFormatException(handler, request, "Missing value for delivery address country, region, street address, zip code");

        Element country = DocumentHelper.createElement("dlv_addr_country");
        country.setText("LV");
        deliveryDetails.add(country);
        Element region = DocumentHelper.createElement("dlv_addr_city");
        region.setText("Rīgas nov.");
        deliveryDetails.add(region);
        Element street = DocumentHelper.createElement("dlv_addr_street2");
        street.setText("Skankstes iela 12");
        deliveryDetails.add(street);
        Element zip = DocumentHelper.createElement("dlv_addr_zip");
        zip.setText("LV-1012");
        deliveryDetails.add(zip);
        Element city = DocumentHelper.createElement("dlv_addr_street1");
        city.setText("Rīga");
        deliveryDetails.add(city);
        Element addField = DocumentHelper.createElement("dlv_company");
        addField.setText("DNB");
        deliveryDetails.add(addField);

        // If delivery is to address then language is mandatory
        checkRequestFormatException(handler, request, "Missing value for delivery address language");

        Element language = DocumentHelper.createElement("dlv_language");
        language.setText("1");
        deliveryDetails.add(language);

        // Cannot find card in LinkApp DB
        checkRequestProcessingException(handler, request, "Card with given number couldn't be found (pcd)");

        PcdCard card = new PcdCard();
        card.setBankC("23");
        card.setGroupc("50");
        when(cardsDAO.findByCardNumber("4775733282237579")).thenReturn(card);

        // Cannot find card in CMS DB
        checkRequestProcessingException(handler, request, "There is no branch in NORDLB_BRANCHES for 888");

        when(commonDAO.getBranchIdByExternalId("888")).thenReturn("C888");
        when(cardDAO.doWork(any(UpdateDBWork.class))).thenReturn("success");

        handler.handle(request);

        assertEquals("C888", card.getUCod10());
        assertEquals("1LVRīgas nov.                           Rīga                                    Skankstes iela 12                                 LV-1012         DNB                                               ", card.getUBField1());

        verify(cardsDAO).saveOrUpdate(card);
        verify(cardDAO).doWork(any(UpdateDBWork.class));

        assertEquals("4775733282237579", handler.compileResponse().getElement().element("update-card-delivery-details").element("card").getTextTrim());
        Element details = handler.compileResponse().getElement().element("update-card-delivery-details").element("delivery-details");
        assertEquals("Rīgas nov., Rīga, Skankstes iela 12, LV-1012", details.element("dlv_address").getTextTrim());
        assertEquals("LV", details.element("dlv_addr_country").getTextTrim());
        assertEquals("Rīgas nov.", details.element("dlv_addr_city").getTextTrim());
        assertEquals("Rīga", details.element("dlv_addr_street1").getTextTrim());
        assertEquals("Skankstes iela 12", details.element("dlv_addr_street2").getTextTrim());
        assertEquals("LV-1012", details.element("dlv_addr_zip").getTextTrim());
        assertEquals("DNB", details.element("dlv_company").getTextTrim());
        assertEquals("1", details.element("dlv_language").getTextTrim());
        assertEquals("888", details.element("branch").getTextTrim());
    }
}
