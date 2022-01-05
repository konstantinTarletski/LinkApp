package lv.bank.cards.soap.api.rtps.soap.servise;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenization;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenizationPK;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.vendor.api.sonic.rest.service.SonicRestService;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.api.rtps.soap.types.Field;
import lv.bank.cards.soap.api.rtps.soap.types.Fields;
import lv.bank.cards.soap.api.rtps.soap.types.Notify;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.handlers.SonicNotificationHandler;
import lv.bank.cards.soap.service.WalletTokenService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VisaTokenServicesImplTest extends JUnitTestBase {

    private final static String EXAMPLE_M124_TAR = "M124002TAMACD0421         0011#########5566|403456789120||TCLN002enTCOR015301244579260756TDNA020VGVzdCdzIGlQaG9uZQ..TDTY00201TKPS00201TNUM006050100TPAR024V-3021200404385024525676TREF024DNITHE302124457926075797TRQI01140010030273TSID048044543E30563800203451348063080276F57714468672E0";
    private final static String EXAMPLE_M124_TCN = "M124002TCTCLN002enTCOR015301244589851419TKPS00201TNUM006140003TOKE004****TOKN016482063997000****TPAR024V-3021200421893229264686TREF024DNITHE302124458985142093TRQI01140010030273TSTT001CTTSP001VTTYP00202TWAC006tX4EFiV6330043700";

    private final static String CARD = "4785850000001234";
    private final static String VISA_VTS_REQUESTOR_ID_APPLE = "40010030273";
    private final static String VISA_VTS_REQUESTOR_ID_GOOGLE = "40010075001";
    private final static String CORR_ID = "301244589851419";

    protected final SonicRestService sonicRest = mock(SonicRestService.class);
    protected final SonicNotificationHandler sonicNotification = mock(SonicNotificationHandler.class);

    protected VisaTokenServicesImpl handler;

    @Before
    public void setUpTest() {
        handler = new VisaTokenServicesImpl();
        handler.cardsDAO = cardsDAO;
        handler.sonicRest = sonicRest;
        handler.sonicNotification = sonicNotification;
    }

    @Test
    public void mainFlowTarNewPcdCardPendingTokenizationNewPcdCardPendingTokenization() {

        PcdCard card = new PcdCard();
        card.setCard(CARD);

        when(cardsDAO.findByCardNumber(CARD)).thenReturn(card);

        addLinkAppProperty(LinkAppProperties.VISA_VTS_REQUESTOR_ID_APPLE_KEY, VISA_VTS_REQUESTOR_ID_APPLE);
        addLinkAppProperty(LinkAppProperties.VISA_VTS_REQUESTOR_ID_GOOGLE_KEY, VISA_VTS_REQUESTOR_ID_GOOGLE);

        handler.notify(getNotifyBody(EXAMPLE_M124_TAR));

        ArgumentCaptor<PcdCardPendingTokenization> pcdCardPendingTokenizationCaptor = ArgumentCaptor.forClass(PcdCardPendingTokenization.class);
        verify(cardsDAO, times(1)).saveOrUpdate(pcdCardPendingTokenizationCaptor.capture());

        PcdCardPendingTokenization pcdCardPendingTokenizationSaved = pcdCardPendingTokenizationCaptor.getAllValues().get(0);

        assertEquals(pcdCardPendingTokenizationSaved.getComp_id().getCard(), CARD);
        assertEquals(pcdCardPendingTokenizationSaved.getComp_id().getWalletDeviceId(), "044543E30563800203451348063080276F57714468672E0");
        assertEquals(pcdCardPendingTokenizationSaved.getSource(), VisaTokenServicesImpl.SOURCE_RTPS);
        assertEquals(pcdCardPendingTokenizationSaved.getDeviceType(), WalletTokenService.DEVICE_TYPE_IOS);
        assertNull(pcdCardPendingTokenizationSaved.getWalletAccountId()); //null in exampleM124Tar
        assertEquals(pcdCardPendingTokenizationSaved.getTokenRefId(), "DNITHE302124457926075797");
    }

    @Test
    public void mainFlowTarNewPcdCardPendingTokenizationExistingPcdCardPendingTokenization() {

        PcdCard card = new PcdCard();
        card.setCard(CARD);

        when(cardsDAO.findByCardNumber(CARD)).thenReturn(card);

        addLinkAppProperty(LinkAppProperties.VISA_VTS_REQUESTOR_ID_APPLE_KEY, VISA_VTS_REQUESTOR_ID_APPLE);
        addLinkAppProperty(LinkAppProperties.VISA_VTS_REQUESTOR_ID_GOOGLE_KEY, VISA_VTS_REQUESTOR_ID_GOOGLE);

        PcdCardPendingTokenization cardPendingTokenization = new PcdCardPendingTokenization();
        PcdCardPendingTokenizationPK pk = new PcdCardPendingTokenizationPK();
        pk.setWalletDeviceId("WalletDeviceId");
        pk.setCard(CARD);
        cardPendingTokenization.setComp_id(pk);

        when(cardsDAO.getPcdCardPendingTokenizationByKey(CARD, "044543E30563800203451348063080276F57714468672E0")).thenReturn(cardPendingTokenization);

        handler.notify(getNotifyBody(EXAMPLE_M124_TAR));

        ArgumentCaptor<PcdCardPendingTokenization> pcdCardPendingTokenizationCaptor = ArgumentCaptor.forClass(PcdCardPendingTokenization.class);
        verify(cardsDAO, times(1)).saveOrUpdate(pcdCardPendingTokenizationCaptor.capture());

        PcdCardPendingTokenization pcdCardPendingTokenizationSaved = pcdCardPendingTokenizationCaptor.getAllValues().get(0);
        assertEquals(pcdCardPendingTokenizationSaved.getComp_id().getCard(), CARD);
        assertEquals(pcdCardPendingTokenizationSaved.getComp_id().getWalletDeviceId(), "WalletDeviceId");
        assertNull(pcdCardPendingTokenizationSaved.getSource());
        assertEquals(pcdCardPendingTokenizationSaved.getTokenRefId(), "DNITHE302124457926075797");
    }

    @Test
    public void mainFlowTcnSonicNotification() throws RequestProcessingException {

        PcdCard card = new PcdCard();
        card.setCard(CARD);
        when(cardsDAO.findByCardNumber(CARD)).thenReturn(card);

        PcdCardPendingTokenization pcdCardPendingTokenization = new PcdCardPendingTokenization();
        pcdCardPendingTokenization.setSource("any");

        when(cardsDAO.getPcdCardPendingTokenizationByCorrId(CARD, CORR_ID)).thenReturn(pcdCardPendingTokenization);

        addLinkAppProperty(LinkAppProperties.VISA_VTS_REQUESTOR_ID_APPLE_KEY, VISA_VTS_REQUESTOR_ID_APPLE);
        addLinkAppProperty(LinkAppProperties.VISA_VTS_REQUESTOR_ID_GOOGLE_KEY, VISA_VTS_REQUESTOR_ID_GOOGLE);

        handler.notify(getNotifyBody(EXAMPLE_M124_TCN));

        ArgumentCaptor<PcdCardPendingTokenization> pcdCardPendingTokenizationCaptor = ArgumentCaptor.forClass(PcdCardPendingTokenization.class);
        verify(cardsDAO, times(1)).saveOrUpdate(pcdCardPendingTokenizationCaptor.capture());

        PcdCardPendingTokenization pcdCardPendingTokenizationSaved = pcdCardPendingTokenizationCaptor.getAllValues().get(0);
        assertEquals(pcdCardPendingTokenizationSaved.getTokenRefId(), "DNITHE302124458985142093");
        assertEquals(pcdCardPendingTokenizationSaved.getSource(), "any");
        assertEquals(pcdCardPendingTokenizationSaved.getTokenPan(), "482063997000****");
        assertEquals(pcdCardPendingTokenizationSaved.getDeviceType(), WalletTokenService.DEVICE_TYPE_IOS);
        assertEquals(pcdCardPendingTokenizationSaved.getWalletAccountId(), "tX4EFi");

        verify(sonicNotification).sendSonicNotifications(eq(VisaTokenServicesImpl.SONIC_NOTIFICATION_CODE), any(List.class));
    }

    @Test
    public void mainFlowTcnPushNotification() throws IOException {

        PcdCard card = new PcdCard();
        card.setCard(CARD);
        when(cardsDAO.findByCardNumber(CARD)).thenReturn(card);

        PcdCardPendingTokenization pcdCardPendingTokenization = new PcdCardPendingTokenization();
        pcdCardPendingTokenization.setSource(VisaTokenServicesImpl.SOURCE_MIB);
        pcdCardPendingTokenization.setBankAppPushId("any");

        when(cardsDAO.getPcdCardPendingTokenizationByCorrId(CARD, CORR_ID)).thenReturn(pcdCardPendingTokenization);

        addLinkAppProperty(LinkAppProperties.VISA_VTS_REQUESTOR_ID_APPLE_KEY, VISA_VTS_REQUESTOR_ID_APPLE);
        addLinkAppProperty(LinkAppProperties.VISA_VTS_REQUESTOR_ID_GOOGLE_KEY, VISA_VTS_REQUESTOR_ID_GOOGLE);

        handler.notify(getNotifyBody(EXAMPLE_M124_TCN));

        ArgumentCaptor<PcdCardPendingTokenization> pcdCardPendingTokenizationCaptor = ArgumentCaptor.forClass(PcdCardPendingTokenization.class);
        verify(cardsDAO, times(1)).saveOrUpdate(pcdCardPendingTokenizationCaptor.capture());

        PcdCardPendingTokenization pcdCardPendingTokenizationSaved = pcdCardPendingTokenizationCaptor.getAllValues().get(0);
        assertEquals(pcdCardPendingTokenizationSaved.getTokenRefId(), "DNITHE302124458985142093");
        assertEquals(pcdCardPendingTokenizationSaved.getSource(), VisaTokenServicesImpl.SOURCE_MIB);
        assertEquals(pcdCardPendingTokenizationSaved.getTokenPan(), "482063997000****");
        assertEquals(pcdCardPendingTokenizationSaved.getDeviceType(), WalletTokenService.DEVICE_TYPE_IOS);
        assertEquals(pcdCardPendingTokenizationSaved.getWalletAccountId(), "tX4EFi");

        verify(sonicRest).sendPushNotification(any(PcdCard.class), any(PcdCardPendingTokenization.class));
    }

    @Test
    public void getTagValueTest() {
        assertEquals("TA", VisaTokenServicesImpl.getTagValue(EXAMPLE_M124_TAR, VisaTokenServicesImpl.FLD_126_TAG_M124));
        assertEquals("TC", VisaTokenServicesImpl.getTagValue(EXAMPLE_M124_TCN, VisaTokenServicesImpl.FLD_126_TAG_M124));
        assertEquals("301244579260756", VisaTokenServicesImpl.getTagValue(EXAMPLE_M124_TAR, VisaTokenServicesImpl.FLD_126_TAG_TCOR));
        assertEquals("301244589851419", VisaTokenServicesImpl.getTagValue(EXAMPLE_M124_TCN, VisaTokenServicesImpl.FLD_126_TAG_TCOR));
        //Actual value length not 048 but 47 !!!
        assertEquals("044543E30563800203451348063080276F57714468672E0", VisaTokenServicesImpl.getTagValue(EXAMPLE_M124_TAR, VisaTokenServicesImpl.FLD_126_TAG_TSID));
        assertNull(VisaTokenServicesImpl.getTagValue(EXAMPLE_M124_TCN, VisaTokenServicesImpl.FLD_126_TAG_TSID));
    }

    public static Notify getNotifyBody(String field126Value) {
        Notify body = new Notify();
        Fields fields = new Fields();

        List<Field> itemList = new ArrayList<>();

        Field field126 = new Field();
        field126.setName(VisaTokenServicesImpl.FLD_126);
        field126.setValue(field126Value);
        itemList.add(field126);

        Field field002 = new Field();
        field002.setName(VisaTokenServicesImpl.FLD_002);
        field002.setValue(CARD);
        itemList.add(field002);

        fields.setItem(itemList);
        body.setFields(fields);
        return body;
    }

}
