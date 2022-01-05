package lv.bank.cards.soap.handlers.lt;

import lv.bank.cards.core.cms.dao.IzdCardTokensDAO;
import lv.bank.cards.core.rtps.dao.StipRmsStoplistDAO;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to test Card details handler
 *
 * @author saldabols
 */
public class CardOverviewTest extends JUnitTestBase {

    protected IzdCardTokensDAO izdCardTokensDAO = mock(IzdCardTokensDAO.class);
    protected StipRmsStoplistDAO stipRmsStoplistDAO = mock(StipRmsStoplistDAO.class);
    protected CardOverview handler;

    @Before
    public void setUpTest() throws RequestPreparationException {
        handler = new CardOverview();
        handler.izdCardTokensDAO = izdCardTokensDAO;
        handler.cardsDAO = cardsDAO;
        handler.stipRmsStoplistDAO = stipRmsStoplistDAO;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        // Missing card tag
        SubRequest request = getSubrequest("card-overview");
        checkRequestFormatException(handler, request, "Specify client or account");

        // Number of records format
        addElementOnRootElement(request, "number-of-records", "a");
        checkRequestFormatException(handler, request, "Value for field number-of-records is not number");

        // From record format
        addElementOnRootElement(request, "from-record", "a");
        checkRequestFormatException(handler, request, "Value for field from-record is not number");

        request = getSubrequest("card-overview");
        addElementOnRootElement(request, "client", "3123");
        addElementOnRootElement(request, "account-number", "a123");
        addElementOnRootElement(request, "card-status", "1,2");
        addElementOnRootElement(request, "delivery-block", "6");
        addElementOnRootElement(request, "card-type", "477573");
        addElementOnRootElement(request, "holder-name", "holder");
        addElementOnRootElement(request, "last-digits", "79");
        addElementOnRootElement(request, "from-record", "2");
        addElementOnRootElement(request, "number-of-records", "3");

        Date date = new Date(1445193926640L);

        Object[] result = new Object[19];
        result[0] = "4775733282237579";
        result[1] = "supp";
        result[2] = "1";
        result[3] = "477473";
        result[4] = "6";
        result[5] = new Date();
        result[6] = date;
        result[7] = "con";
        result[8] = "holder";
        result[9] = "block";
        result[10] = "condName";
        result[11] = "Requested";
        result[12] = " LTViïòas apriòíis                      Viïòa                                   Brîvîbas 100                                                                    LT-1012         Main Bank                                         Main Bank               ";
        result[13] = "9999";
        result[14] = "6";
        result[15] = "account";
        result[16] = "1";
        result[17] = "7";
        result[18] = 1;
        Object[] blank = new Object[19];
        blank[0] = "4775733282237479";
        blank[5] = date;
        blank[11] = "Requested";
        blank[17] = "J";
        blank[18] = 0;

        List<Object[]> results = new ArrayList<Object[]>();
        results.add(result);
        results.add(blank);
        when(cardsDAO.getCardsOverviewForCLientTotal("3123", "a123", "('1','2')", "6", "477573", "holder", "79")).thenReturn(5L);
        when(cardsDAO.getCardsOverviewForCLient("3123", "a123", "('1','2')", "6", "477573", "holder", "79", 1, 3)).thenReturn(results);

        handler.handle(request);

        verify(cardsDAO).getCardsOverviewForCLientTotal("3123", "a123", "('1','2')", "6", "477573", "holder", "79");
        assertEquals("<done><card-overview>"
                        + "<from-record>1</from-record><number-of-records>3</number-of-records><total-records>5</total-records>"
                        + "<card><account-number>account</account-number><card-number>4775733282237579</card-number>"
                        + "<main-supplementary>supp</main-supplementary><card-status>1</card-status><BIN-code>477473</BIN-code>"
                        + "<delivery-status>6</delivery-status><expiry-date>1510</expiry-date><condition-set>con</condition-set>"
                        + "<card-holder>holder</card-holder><block-reason>block</block-reason>"
                        + "<condition-set-name>condName</condition-set-name><pin-status>Requested</pin-status>"
                        + "<delivery-address>Brîvîbas 100, Viïòa, Viïòas apriòíis, LT, LT-1012, Main Bank</delivery-address>"
                        + "<add-info></add-info><delivery-branch>9999</delivery-branch><delivery-block>6</delivery-block>"
                        + "<pin-block>1</pin-block><contactless>1</contactless></card>"
                        + "<card><account-number></account-number><card-number>4775733282237479</card-number>"
                        + "<main-supplementary></main-supplementary><card-status></card-status><BIN-code></BIN-code>"
                        + "<delivery-status>1</delivery-status><expiry-date>1510</expiry-date><condition-set></condition-set>"
                        + "<card-holder></card-holder><block-reason></block-reason><condition-set-name></condition-set-name>"
                        + "<pin-status>Deleted</pin-status><delivery-address></delivery-address><add-info></add-info>"
                        + "<delivery-branch></delivery-branch><delivery-block>1</delivery-block><pin-block>0</pin-block>"
                        + "<contactless>0</contactless>"
                        + "</card></card-overview></done>",
                handler.compileResponse().asXML());

    }
}
