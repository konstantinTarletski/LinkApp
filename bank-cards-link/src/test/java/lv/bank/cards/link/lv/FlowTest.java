package lv.bank.cards.link.lv;

import lombok.SneakyThrows;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCondCard;
import lv.bank.cards.core.entity.cms.IzdCountry;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeRenewCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeReplaceCard;
import lv.bank.cards.link.Constants;
import lv.bank.cards.link.Order;
import lv.bank.cards.link.TestBase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class FlowTest extends TestBase {

    @Before
    @SneakyThrows
    public void initCMSSoapAPIWrapper() {
        super.initCMSSoapAPIWrapper();
        when(commonDAO.getIzdCountryByShortCountryCode(anyString())).thenReturn(new IzdCountry("LV", null, null, null));
        when(commonDAO.getBranchIdByExternalId(eq(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888))).thenReturn(EXTERNAL_BRANCH);
        mapper = new Mapper(commonDAO, cardDAO, cardsDAO, clientDAO, clientsDAO, productDAO, accountsDAO);
        orderValidator = new OrderValidator(commonDAO, cardDAO, cardsDAO, clientDAO, getMapper());
        orderProcessor = new OrderProcessor(cmsSoapAPIWrapperBean, cmsCallAPIWrapper, bankCardsWSWrapperDelegate,
                getMapper(), commonDAO, cardDAO, cardsDAO, accountDAO, clientDAO, clientsDAO);
    }

    @Test
    @SneakyThrows
    public void orderToRecord_pinField() {

        Order order = getValidOrder(Constants.CARD_REPLACE_ACTION);
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888);
        order.setAutoBlockCard("");
        addDeliveryAddress(order);

        IzdCard card = setUpIzdCard(REPLACED_CARD_NUMBER);
        card.setRenew("J");

        RowTypeReplaceCard resultObject = new RowTypeReplaceCard();
        resultObject.setNEWCARD(REPLACED_CARD_NUMBER);
        resultObject.setCHIPID(new BigDecimal(order.getApplicationId()));

        when(cardDAO.doWork(any(CMSCallAPIWrapper.UpdateDBWork.class))).thenReturn("success");
        when(cmsSoapAPIWrapperBean.replaceCard(any(RowTypeReplaceCard.class))).thenReturn(resultObject);

        getOrderValidator().validateOrder(order);
        getOrderProcessor().processOrder(order);
        verify(cardsDAO).getNextPcdPinIDWithAuthentificationCode("123");
    }

    @Test
    @SneakyThrows
    public void orderToRecord_pinField_notUsed() {

        Order order = getValidOrder(Constants.CARD_REPLACE_ACTION);
        order.setBranchToDeliverAt("B12"); // Card delivered to branch
        order.setAutoBlockCard("");
        addDeliveryAddress(order);

        IzdCard card = setUpIzdCard(REPLACED_CARD_NUMBER);
        card.setRenew("J");

        RowTypeReplaceCard resultObject = new RowTypeReplaceCard();
        resultObject.setNEWCARD(REPLACED_CARD_NUMBER);
        resultObject.setCHIPID(new BigDecimal(order.getApplicationId()));

        when(cardDAO.doWork(any(CMSCallAPIWrapper.UpdateDBWork.class))).thenReturn("success");
        when(cmsSoapAPIWrapperBean.replaceCard(any(RowTypeReplaceCard.class))).thenReturn(resultObject);

        getOrderValidator().validateOrder(order);
        getOrderProcessor().processOrder(order);

        verify(cardsDAO, never()).getNextPcdPinIDWithAuthentificationCode("123");
    }

    @Test
    @SneakyThrows
    public void cardReplace_block() {

        Order order = getValidOrder(Constants.CARD_REPLACE_ACTION);
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888);
        order.setAutoBlockCard("1");
        order.setReplaceName("repN");
        order.setCardName("cardN");
        order.setClientFirstname("clientF");
        order.setClientLastname("clientL");
        order.setClientNumberInAbs("cl123");
        order.setReportLanguage("2"); // not used
        addDeliveryAddress(order);

        IzdCard card = setUpIzdCard(DEFAULT_CARD_NUMBER);
        card.setRenew("J");

        IzdCard replacedCard = setUpIzdCard(REPLACED_CARD_NUMBER);

        String deliveryAddress = "1LVRiga reg.                            Skansktes 12                            Riga                                              LV-1048         DNB                                               ";

        RowTypeReplaceCard resultObject = new RowTypeReplaceCard();
        resultObject.setCARD(DEFAULT_CARD_NUMBER);
        resultObject.setNEWCARD(REPLACED_CARD_NUMBER);
        resultObject.setCHIPID(new BigDecimal(order.getApplicationId()));

        when(bankCardsWSWrapperDelegate.rtcungCall(anyString())).thenReturn(getCardStopListResponseXml(DEFAULT_CARD_NUMBER));
        when(cardDAO.doWork(any(CMSCallAPIWrapper.UpdateDBWork.class))).thenReturn("success");
        when(cmsSoapAPIWrapperBean.replaceCard(any(RowTypeReplaceCard.class))).thenReturn(resultObject);

        getOrderValidator().validateOrder(order);
        getOrderProcessor().processOrder(order);

        ArgumentCaptor<RowTypeReplaceCard> argReplace = ArgumentCaptor.forClass(RowTypeReplaceCard.class);
        verify(cmsSoapAPIWrapperBean, times(1)).replaceCard(argReplace.capture());
        assertEquals(card.getCard(), argReplace.getAllValues().get(0).getCARD());
        assertEquals(new BigDecimal(order.getApplicationId()), argReplace.getAllValues().get(0).getCHIPID());

        ArgumentCaptor<CMSCallAPIWrapper.UpdateDBWork> argWork = ArgumentCaptor.forClass(CMSCallAPIWrapper.UpdateDBWork.class);
        verify(cardDAO, times(1)).doWork(argWork.capture());
        assertEquals(getCardUpdateXml("update", REPLACED_CARD_NUMBER, null, U_AFIELD1_GENERATED_VALUE,
                "REQUESTED", null, null, EXTERNAL_BRANCH, deliveryAddress, "123"),
                argWork.getAllValues().get(0).getInputXML());

        verify(cardsDAO).getNextPcdPinIDWithAuthentificationCode("123");
        verify(bankCardsWSWrapperDelegate).rtcungCall(getCardStopListRequestXml(DEFAULT_CARD_NUMBER, order.getAutoBlockCard()));
    }

    @Test
    @SneakyThrows
    public void cardReplace_autoblock_false() {
        Order order = getValidOrder(Constants.CARD_REPLACE_ACTION);
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888);
        order.setAutoBlockCard("");
        order.setReplaceName("repN");
        order.setCardName("cardN");
        order.setClientFirstname("clientF");
        order.setClientLastname("clientL");
        order.setClientNumberInAbs("cl123");
        order.setReportLanguage("2");
        addDeliveryAddress(order);
        order.setDlvLanguage(null); // If no delivery language then use report language

        IzdCard card = setUpIzdCard(REPLACED_CARD_NUMBER);
        card.setRenew("J");

        RowTypeReplaceCard resultObject = new RowTypeReplaceCard();
        resultObject.setCARD(DEFAULT_CARD_NUMBER);
        resultObject.setNEWCARD(REPLACED_CARD_NUMBER);
        resultObject.setCHIPID(new BigDecimal(order.getApplicationId()));

        String deliveryAddress = "2LVRiga reg.                            Skansktes 12                            Riga                                              LV-1048         DNB                                               ";

        when(cmsSoapAPIWrapperBean.replaceCard(any(RowTypeReplaceCard.class))).thenReturn(resultObject);
        when(cardDAO.doWork(any(CMSCallAPIWrapper.UpdateDBWork.class))).thenReturn("success");

        getOrderValidator().validateOrder(order);
        getOrderProcessor().processOrder(order);

        ArgumentCaptor<RowTypeReplaceCard> argReplace = ArgumentCaptor.forClass(RowTypeReplaceCard.class);
        verify(cmsSoapAPIWrapperBean, times(1)).replaceCard(argReplace.capture());
        assertEquals(DEFAULT_CARD_NUMBER, argReplace.getAllValues().get(0).getCARD());
        assertEquals(new BigDecimal(order.getApplicationId()), argReplace.getAllValues().get(0).getCHIPID());

        ArgumentCaptor<CMSCallAPIWrapper.UpdateDBWork> argWork = ArgumentCaptor.forClass(CMSCallAPIWrapper.UpdateDBWork.class);
        verify(cardDAO, times(1)).doWork(argWork.capture());
        assertEquals(getCardUpdateXml("update", REPLACED_CARD_NUMBER, null, U_AFIELD1_GENERATED_VALUE,
                "REQUESTED", null, null, EXTERNAL_BRANCH, deliveryAddress, "123"),
                argWork.getAllValues().get(0).getInputXML());

        verify(cardsDAO).getNextPcdPinIDWithAuthentificationCode("123");
        verifyZeroInteractions(bankCardsWSWrapperDelegate);
    }

    @Test
    @SneakyThrows
    public void testCardRenewMainFlow() {
        Order order = getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888);
        order.setAutoBlockCard("1");
        order.setReplaceName("repN");
        order.setCardName("cardN");
        order.setClientFirstname("clientF");
        order.setClientLastname("clientL");
        order.setClientNumberInAbs("cl123");
        order.setCardValidityPeriod("3");
        addDeliveryAddress(order);

        String deliveryAddress = "1LVRiga reg.                            Skansktes 12                            Riga                                              LV-1048         DNB                                               ";

        IzdCard card = setUpIzdCard(DEFAULT_CARD_NUMBER);
        card.setStatus1("0");

        RowTypeRenewCard resultObject = new RowTypeRenewCard();
        resultObject.setNEWCARD(order.getCardNumber());
        resultObject.setCHIPID(new BigDecimal(order.getApplicationId()));

        IzdCondCard cond = getIzdCondCardMock(DEFAULT_CARD_NUMBER, 36);
        when(cmsSoapAPIWrapperBean.renewCard(any(RowTypeRenewCard.class))).thenReturn(resultObject);
        when(cardDAO.doWork(any(CMSCallAPIWrapper.UpdateDBWork.class))).thenReturn("success");

        getOrderValidator().validateOrder(order);
        getOrderProcessor().processOrder(order);

        ArgumentCaptor<RowTypeRenewCard> argReNew = ArgumentCaptor.forClass(RowTypeRenewCard.class);
        verify(cmsSoapAPIWrapperBean, times(1)).renewCard(argReNew.capture());
        assertEquals(order.getCardNumber(), argReNew.getAllValues().get(0).getCARD());
        assertEquals(new BigDecimal(order.getApplicationId()), argReNew.getAllValues().get(0).getCHIPID());

        ArgumentCaptor<CMSCallAPIWrapper.UpdateDBWork> argWork = ArgumentCaptor.forClass(CMSCallAPIWrapper.UpdateDBWork.class);
        verify(cardDAO, times(1)).doWork(argWork.capture());
        assertEquals(getCardUpdateXml("update", DEFAULT_CARD_NUMBER, null, U_AFIELD1_GENERATED_VALUE,
                "REQUESTED", null, null, EXTERNAL_BRANCH, deliveryAddress, "123"),
                argWork.getAllValues().get(0).getInputXML());

        verify(cardsDAO).getNextPcdPinIDWithAuthentificationCode("123");
        verifyZeroInteractions(bankCardsWSWrapperDelegate);
    }


    //order.setAutoBlockCard("1"); should be set after validation
    @Test
    @SneakyThrows
    public void cardRenew_toCardReplace_renewN() {
        Order order = getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888);
        order.setAutoBlockCard("");
        order.setReplaceName("repN");
        order.setCardName("cardN");
        order.setClientFirstname("clientF");
        order.setClientLastname("clientL");
        order.setClientNumberInAbs("cl123");
        order.setReportLanguage("2");
        order.setCardValidityPeriod("3");
        addDeliveryAddress(order);
        order.setDlvLanguage(null); // If no delivery language then use report language

        IzdCard card = setUpIzdCard(DEFAULT_CARD_NUMBER);
        card.setRenew("N"); // Card is blocked, must create new
        card.setExpiry1(new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000)));
        card.setStopCause("A");

        RowTypeReplaceCard resultObject = new RowTypeReplaceCard();
        resultObject.setNEWCARD(order.getCardNumber());
        resultObject.setCHIPID(new BigDecimal(order.getApplicationId()));

        String deliveryAddress = "2LVRiga reg.                            Skansktes 12                            Riga                                              LV-1048         DNB                                               ";

        IzdCondCard cond = getIzdCondCardMock(DEFAULT_CARD_NUMBER, 36);
        when(cardDAO.doWork(any(CMSCallAPIWrapper.UpdateDBWork.class))).thenReturn("success");
        when(bankCardsWSWrapperDelegate.rtcungCall(getCardStopListRequestXml(DEFAULT_CARD_NUMBER, "1"))).thenReturn(getCardStopListResponseXml(DEFAULT_CARD_NUMBER));
        when(bankCardsWSWrapperDelegate.rtcungCall(getCarAutoRenewRequestXml(DEFAULT_CARD_NUMBER))).thenReturn(getCarAutoRenewResponseXml(DEFAULT_CARD_NUMBER));
        when(cmsSoapAPIWrapperBean.replaceCard(any(RowTypeReplaceCard.class))).thenReturn(resultObject);

        getOrderValidator().validateOrder(order);
        getOrderProcessor().processOrder(order);

        assertEquals(order.getAutoBlockCard(), "1");
        ArgumentCaptor<RowTypeReplaceCard> argReplaceCard = ArgumentCaptor.forClass(RowTypeReplaceCard.class);
        verify(cmsSoapAPIWrapperBean, times(1)).replaceCard(argReplaceCard.capture());
        assertEquals(order.getCardNumber(), argReplaceCard.getAllValues().get(0).getCARD());
        assertEquals(new BigDecimal(order.getApplicationId()), argReplaceCard.getAllValues().get(0).getCHIPID());

        ArgumentCaptor<CMSCallAPIWrapper.UpdateDBWork> argWork = ArgumentCaptor.forClass(CMSCallAPIWrapper.UpdateDBWork.class);
        verify(cardDAO, times(1)).doWork(argWork.capture());
        assertEquals(getCardUpdateXml("update", DEFAULT_CARD_NUMBER, null, U_AFIELD1_GENERATED_VALUE,
                "REQUESTED", null, null, EXTERNAL_BRANCH, deliveryAddress, "123"),
                argWork.getAllValues().get(0).getInputXML());

        verify(cardsDAO).getNextPcdPinIDWithAuthentificationCode("123");
    }

				/*

	@Test 
	public void cardCreate_allNew() throws UnrecognizeableRecordException, 
			MandatoryFieldMissedException, DataIntegrityException, CreateException, 
			NamingException, RecordFormatException{
		OrderDTO order = getCardCreateFullOrder();
		
		IzdOfferedProduct prod = new IzdOfferedProduct();
		prod.setComp_id(new IzdOfferedProductPK("oProdId", BANKC, GROUPC));
		prod.setCompany("oComCode");
		prod.setRiskLevel("R1");
		prod.setCondAccnt("condA");
		prod.setChipDesignId(31L);
		prod.setChipAppId(41L);
		prod.setAuthLevel("A1");
		prod.setCondCard("condC");
		prod.setCardType("Debit");
		IzdCompany comp = new IzdCompany();
		comp.setName("oCompany");
		comp.setStreet("oStreet");
		comp.setCity("oCity");
		comp.setCountry("oLV");
		comp.setPostInd("oZip");
		when(productManager.findExactlyOneProduct(any(IzdOfferedProductDTO.class))).thenReturn(prod);
		when(commonManager.getIzdCompanyByCode(eq("oComCode"), eq(BANKC))).thenReturn(comp);
		when(pcdabaNGManager.getNewCardAcctId("accNo")).thenReturn("newAcc");
		
		List<TransmasterRecord> list = orderHelper.orderToRecord(order);
		
		assertEquals(4, list.size());
		
		Document result = DocumentHelper.createDocument(DocumentHelper.createElement("root"));
		Element cl1 = DocumentHelper.createElement("CLIENT");
		cl1.setText("cl1");
		result.getRootElement().add(cl1);
		Element agr1 = DocumentHelper.createElement("AGRE_NOM");
		agr1.setText("agr1");
		result.getRootElement().add(agr1);
		Element contract = DocumentHelper.createElement("CONTRACT");
		contract.setText("contract1");
		result.getRootElement().add(contract);
		Element tabKey = DocumentHelper.createElement("TAB_KEY");
		tabKey.setText("tabKey");
		result.getRootElement().add(tabKey);
		
		IEClientRecord client = (IEClientRecord) list.get(0);
		assertEquals(BANKC, client.getBankc());
		assertEquals(123, client.getOrderNumber().intValue());
		assertEquals("01", client.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>client</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<CL_TYPE>1</CL_TYPE><RESIDENT>r</RESIDENT><MIDLE_NAME>pass123</MIDLE_NAME><CLN_CAT>clCat</CLN_CAT>"
				+ "<R_PHONE>29292929</R_PHONE><F_NAMES>fName</F_NAMES><SURNAME>lName</SURNAME><CMPG_NAME>fName lName</CMPG_NAME>"
				+ "<PERSON_CODE>123123-1234</PERSON_CODE><R_STREET>cStreet</R_STREET><R_CITY>cCity</R_CITY><R_PCODE>cZip</R_PCODE>"
				+ "<REGION>LV</REGION><R_CNTRY>LV</R_CNTRY><C_SINCE>2011</C_SINCE><B_DATE>to_date('20010103', 'yyyymmdd')</B_DATE>"
				+ "<CMP_NAME>oCom</CMP_NAME><EMP_CODE>oComCode</EMP_CODE><EMP_NAME>oCompany</EMP_NAME>"
				+ "<EMP_ADR>oStreet oCity oLV oZip</EMP_ADR><BANK_C>23</BANK_C><CLIENT_B>clNum1</CLIENT_B><STATUS>10</STATUS>"
				+ "</details></document></Changes_request>",
				client.prepareXML(result));
		
		IEAgreementRecord agreement = (IEAgreementRecord)list.get(1);
		assertEquals(BANKC, agreement.getBankc());
		assertEquals("clNum1", agreement.getClientNumberInABS());
		assertEquals(GROUPC, agreement.getGroupc());
		assertEquals(123, agreement.getOrderNumber().intValue());
		assertEquals("01", agreement.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>agreement</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID>"
				+ "<details><BINCOD>477573</BINCOD><BANK_C>23</BANK_C><BANK_CODE>23</BANK_CODE><U_EMAILS>agr@eeme.nt</U_EMAILS>"
				+ "<REP_LANG>1</REP_LANG><U_COD4>fill</U_COD4><DISTRIB_MODE>3</DISTRIB_MODE><CITY>sCity</CITY>"
				+ "<STREET>sStreet</STREET><POST_IND>sip</POST_IND><COUNTRY>LV</COUNTRY><RISK_LEVEL>R1</RISK_LEVEL>"
				+ "<BRANCH>B12</BRANCH><CLIENT>cl1</CLIENT><ENROLLED>to_date('" + TODAY + "', 'yyyymmdd')</ENROLLED><STATUS>10</STATUS>"
				+ "<GROUPC>50</GROUPC></details></document></Changes_request>", 
				agreement.prepareXML(result));
		
		IEAccountRecord account = (IEAccountRecord)list.get(2);
		assertEquals(BANKC, account.getBankc());
		assertEquals("clNum1", account.getClientNumberInABS());
		assertEquals(GROUPC, account.getGroupc());
		assertEquals(123, account.getOrderNumber().intValue());
		assertEquals("01", account.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>account</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<COND_SET>condA</COND_SET><CARD_ACCT>newAcc</CARD_ACCT><CCY>EUR</CCY><UFIELD_5>accNo</UFIELD_5>"
				+ "<AGRE_NOM>agr1</AGRE_NOM><CLIENT>cl1</CLIENT><CONTRACT>contract1</CONTRACT><ADJUST_FLAG>0</ADJUST_FLAG>"
				+ "<STAT_CHANGE>1</STAT_CHANGE><CRD>0</CRD><MIN_BAL>0</MIN_BAL><NON_REDUCE_BAL>0</NON_REDUCE_BAL>"
				+ "<BANK_C>23</BANK_C><CYCLE>0</CYCLE><DEFAULT_INDICATOR>0</DEFAULT_INDICATOR><GROUPC>50</GROUPC><STATUS>0</STATUS>"
				+ "<C_ACCNT_TYPE>00</C_ACCNT_TYPE><LIM_INTR>0</LIM_INTR></details></document></Changes_request>", 
				account.prepareXML(result));
		
		IECardRecord card = (IECardRecord)list.get(3);
		assertEquals(BANKC, card.getBankc());
		assertEquals("clNum1", card.getClientNumberInABS());
		assertEquals(GROUPC, card.getGroupc());
		assertEquals(123, card.getOrderNumber().intValue());
		assertEquals("01", card.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>card</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<AGREEMENT_KEY>agreeKey</AGREEMENT_KEY><CL_ACCT_KEY>clAccKey</CL_ACCT_KEY><CARD_NUM>477573</CARD_NUM>"
				+ "<BASE_SUPP>1</BASE_SUPP><CARD_NAME>caName</CARD_NAME><F_NAMES>fName</F_NAMES><SURNAME>lName</SURNAME>"
				+ "<M_NAME>pass123</M_NAME><U_FIELD7>a1</U_FIELD7><ID_CARD>123123-1234</ID_CARD><U_COD9>ucod9</U_COD9>"
				+ "<U_FIELD8>123</U_FIELD8><REGION>LV</REGION><U_BFIELD1>1DCdCity                                dStreet1                       "
				+ "         dStreet2                                          dZip            dCom                           "
				+ "                   </U_BFIELD1><DESIGN_ID>21</DESIGN_ID><B_DATE>to_date('20010103', 'yyyymmdd')</B_DATE>"
				+ "<AUTH_LIMIT>A1</AUTH_LIMIT><COND_SET>condC</COND_SET><RISK_LEVEL>R1</RISK_LEVEL>"
				+ "<CARD_TYPE>Debit</CARD_TYPE><U_AFIELD2>REQUESTED</U_AFIELD2><U_AFIELD1>authCode</U_AFIELD1>"
				+ "<U_COD10>E888</U_COD10><CLIENT>cl1</CLIENT><PIN_FLAG>1</PIN_FLAG><BANK_C>23</BANK_C><GROUPC>50</GROUPC>"
				+ "<STATUS1>0</STATUS1></details></document></Changes_request>", 
				card.prepareXML(result));
		
		verifyZeroInteractions(rtcu);
		
		// To many products 
		when(productManager.findExactlyOneProduct(any(IzdOfferedProductDTO.class))).thenThrow(new TooManyRecordsFoundException("To many products"));
		checkMandatoryFieldMissed(order, "Can't get corresponding product");
	}
	
	@Test 
	public void cardCreate_allNewComp_Block() throws UnrecognizeableRecordException, 
			MandatoryFieldMissedException, DataIntegrityException, CreateException, 
			NamingException, RecordFormatException{
		OrderDTO order = getCardCreateFullOrder();
		order.put(OrderDTO.fields.block_card_no, "4775733282237570");
		order.put(OrderDTO.fields.autoBlockCard, "1");
		order.put(OrderDTO.fields.empCompanyRegNr, "regNo");
		
		IzdOfferedProduct prod = new IzdOfferedProduct();
		prod.setComp_id(new IzdOfferedProductPK("oProdId", BANKC, GROUPC));
		prod.setCompany("oComCode");
		prod.setRiskLevel("R1");
		prod.setCondAccnt("condA");
		prod.setChipDesignId(31L);
		prod.setChipAppId(41L);
		prod.setAuthLevel("A1");
		prod.setCondCard("condC");
		prod.setCardType("Debit");
		IzdCompany comp = new IzdCompany();
		comp.setName("oCompany");
		comp.setStreet("oStreet");
		comp.setCity("oCity");
		comp.setCountry("oLV");
		comp.setPostInd("oZip");
		comp.setComp_id(new IzdCompanyPK("oComCode", BANKC));
		when(productManager.findExactlyOneProduct(any(IzdOfferedProductDTO.class))).thenReturn(prod);
		when(commonManager.getIzdCompanyByRegCodeUR("regNo")).thenReturn(comp);
		when(pcdabaNGManager.getNewCardAcctId("accNo")).thenReturn("newAcc");
		when(rtcu.RTCUNGCall(anyString())).thenReturn("<done><add-card-to-stoplist>"
				+ "<added>4775733282237570</added></add-card-to-stoplist></done>");
		
		List<TransmasterRecord> list = orderHelper.orderToRecord(order);
		
		assertEquals(4, list.size());
		
		Document result = DocumentHelper.createDocument(DocumentHelper.createElement("root"));
		Element cl1 = DocumentHelper.createElement("CLIENT");
		cl1.setText("cl1");
		result.getRootElement().add(cl1);
		Element agr1 = DocumentHelper.createElement("AGRE_NOM");
		agr1.setText("agr1");
		result.getRootElement().add(agr1);
		Element contract = DocumentHelper.createElement("CONTRACT");
		contract.setText("contract1");
		result.getRootElement().add(contract);
		Element tabKey = DocumentHelper.createElement("TAB_KEY");
		tabKey.setText("tabKey");
		result.getRootElement().add(tabKey);
		
		IEClientRecord client = (IEClientRecord) list.get(0);
		assertEquals(BANKC, client.getBankc());
		assertEquals(123, client.getOrderNumber().intValue());
		assertEquals("01", client.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>client</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<CL_TYPE>1</CL_TYPE><RESIDENT>r</RESIDENT><MIDLE_NAME>pass123</MIDLE_NAME><CLN_CAT>clCat</CLN_CAT>"
				+ "<R_PHONE>29292929</R_PHONE><F_NAMES>fName</F_NAMES><SURNAME>lName</SURNAME><CMPG_NAME>fName lName</CMPG_NAME>"
				+ "<PERSON_CODE>123123-1234</PERSON_CODE><R_STREET>cStreet</R_STREET><R_CITY>cCity</R_CITY><R_PCODE>cZip</R_PCODE>"
				+ "<REGION>LV</REGION><R_CNTRY>LV</R_CNTRY><C_SINCE>2011</C_SINCE><B_DATE>to_date('20010103', 'yyyymmdd')</B_DATE>"
				+ "<CMP_NAME>oCom</CMP_NAME><EMP_CODE>oComCode</EMP_CODE><EMP_NAME>oCompany</EMP_NAME>"
				+ "<EMP_ADR>oStreet oCity oLV oZip</EMP_ADR><BANK_C>23</BANK_C><CLIENT_B>clNum1</CLIENT_B><STATUS>10</STATUS>"
				+ "</details></document></Changes_request>",
				client.prepareXML(result));
		
		IEAgreementRecord agreement = (IEAgreementRecord)list.get(1);
		assertEquals(BANKC, agreement.getBankc());
		assertEquals("clNum1", agreement.getClientNumberInABS());
		assertEquals(GROUPC, agreement.getGroupc());
		assertEquals(123, agreement.getOrderNumber().intValue());
		assertEquals("01", agreement.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>agreement</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID>"
				+ "<details><BINCOD>477573</BINCOD><BANK_C>23</BANK_C><BANK_CODE>23</BANK_CODE><U_EMAILS>agr@eeme.nt</U_EMAILS>"
				+ "<REP_LANG>1</REP_LANG><U_COD4>fill</U_COD4><DISTRIB_MODE>3</DISTRIB_MODE><CITY>sCity</CITY>"
				+ "<STREET>sStreet</STREET><POST_IND>sip</POST_IND><COUNTRY>LV</COUNTRY><RISK_LEVEL>R1</RISK_LEVEL>"
				+ "<BRANCH>B12</BRANCH><CLIENT>cl1</CLIENT><ENROLLED>to_date('" + TODAY + "', 'yyyymmdd')</ENROLLED><STATUS>10</STATUS>"
				+ "<GROUPC>50</GROUPC></details></document></Changes_request>", 
				agreement.prepareXML(result));
		
		IEAccountRecord account = (IEAccountRecord)list.get(2);
		assertEquals(BANKC, account.getBankc());
		assertEquals("clNum1", account.getClientNumberInABS());
		assertEquals(GROUPC, account.getGroupc());
		assertEquals(123, account.getOrderNumber().intValue());
		assertEquals("01", account.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>account</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<COND_SET>condA</COND_SET><CARD_ACCT>newAcc</CARD_ACCT><CCY>EUR</CCY><UFIELD_5>accNo</UFIELD_5>"
				+ "<AGRE_NOM>agr1</AGRE_NOM><CLIENT>cl1</CLIENT><CONTRACT>contract1</CONTRACT><ADJUST_FLAG>0</ADJUST_FLAG>"
				+ "<STAT_CHANGE>1</STAT_CHANGE><CRD>0</CRD><MIN_BAL>0</MIN_BAL><NON_REDUCE_BAL>0</NON_REDUCE_BAL>"
				+ "<BANK_C>23</BANK_C><CYCLE>0</CYCLE><DEFAULT_INDICATOR>0</DEFAULT_INDICATOR><GROUPC>50</GROUPC><STATUS>0</STATUS>"
				+ "<C_ACCNT_TYPE>00</C_ACCNT_TYPE><LIM_INTR>0</LIM_INTR></details></document></Changes_request>", 
				account.prepareXML(result));
		
		IECardRecord card = (IECardRecord)list.get(3);
		assertEquals(BANKC, card.getBankc());
		assertEquals("clNum1", card.getClientNumberInABS());
		assertEquals(GROUPC, card.getGroupc());
		assertEquals(123, card.getOrderNumber().intValue());
		assertEquals("01", card.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>card</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<AGREEMENT_KEY>agreeKey</AGREEMENT_KEY><CL_ACCT_KEY>clAccKey</CL_ACCT_KEY><CARD_NUM>477573</CARD_NUM>"
				+ "<BASE_SUPP>1</BASE_SUPP><CARD_NAME>caName</CARD_NAME><F_NAMES>fName</F_NAMES><SURNAME>lName</SURNAME>"
				+ "<M_NAME>pass123</M_NAME><U_FIELD7>a1</U_FIELD7><ID_CARD>123123-1234</ID_CARD><U_COD9>ucod9</U_COD9>"
				+ "<U_FIELD8>123</U_FIELD8><REGION>LV</REGION><U_BFIELD1>1DCdCity                                dStreet1                       "
				+ "         dStreet2                                          dZip            dCom                           "
				+ "                   </U_BFIELD1><DESIGN_ID>21</DESIGN_ID><B_DATE>to_date('20010103', 'yyyymmdd')</B_DATE>"
				+ "<AUTH_LIMIT>A1</AUTH_LIMIT><COND_SET>condC</COND_SET><RISK_LEVEL>R1</RISK_LEVEL>"
				+ "<CARD_TYPE>Debit</CARD_TYPE><U_AFIELD2>REQUESTED</U_AFIELD2><U_AFIELD1>authCode</U_AFIELD1>"
				+ "<U_COD10>E888</U_COD10><CLIENT>cl1</CLIENT><PIN_FLAG>1</PIN_FLAG><BANK_C>23</BANK_C><GROUPC>50</GROUPC>"
				+ "<STATUS1>0</STATUS1></details></document></Changes_request>", 
				card.prepareXML(result));
		
		verify(rtcu).RTCUNGCall("<do what=\"add-card-to-stoplist\"><card>4775733282237570</card><description>Auto-blocked for replacement</description><cause>1</cause></do>");
	
		// Missing client country
		order.put(OrderDTO.fields.clientCountry, null);
		checkDataIntegrity(order, "Missing client address country");
	}

	@Test
	public void cardCreate_allNewCustomRiskLevel() throws UnrecognizeableRecordException,
			MandatoryFieldMissedException, DataIntegrityException, CreateException,
			NamingException, RecordFormatException{
		OrderDTO order = getCardCreateFullOrder();
		order.put(OrderDTO.fields.riskLevel,"XX");

		IzdOfferedProduct prod = new IzdOfferedProduct();
		prod.setComp_id(new IzdOfferedProductPK("oProdId", BANKC, GROUPC));
		prod.setCompany("oComCode");
		prod.setRiskLevel("R1");
		prod.setCondAccnt("condA");
		prod.setChipDesignId(31L);
		prod.setChipAppId(41L);
		prod.setAuthLevel("A1");
		prod.setCondCard("condC");
		prod.setCardType("Debit");
		IzdCompany comp = new IzdCompany();
		comp.setName("oCompany");
		comp.setStreet("oStreet");
		comp.setCity("oCity");
		comp.setCountry("oLV");
		comp.setPostInd("oZip");
		when(productManager.findExactlyOneProduct(any(IzdOfferedProductDTO.class))).thenReturn(prod);
		when(commonManager.getIzdCompanyByCode(eq("oComCode"), eq(BANKC))).thenReturn(comp);
		when(pcdabaNGManager.getNewCardAcctId("accNo")).thenReturn("newAcc");
		when(cardManager.isRiskLevelLinkedToBin(any(),eq("XX"))).thenReturn(true);

		List<TransmasterRecord> list = orderHelper.orderToRecord(order);

		assertEquals(4, list.size());

		Document result = DocumentHelper.createDocument(DocumentHelper.createElement("root"));
		Element cl1 = DocumentHelper.createElement("CLIENT");
		cl1.setText("cl1");
		result.getRootElement().add(cl1);
		Element agr1 = DocumentHelper.createElement("AGRE_NOM");
		agr1.setText("agr1");
		result.getRootElement().add(agr1);
		Element contract = DocumentHelper.createElement("CONTRACT");
		contract.setText("contract1");
		result.getRootElement().add(contract);
		Element tabKey = DocumentHelper.createElement("TAB_KEY");
		tabKey.setText("tabKey");
		result.getRootElement().add(tabKey);

		IEClientRecord client = (IEClientRecord) list.get(0);
		assertEquals(BANKC, client.getBankc());
		assertEquals(123, client.getOrderNumber().intValue());
		assertEquals("01", client.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>client</DOC_NAME>"
						+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
						+ "<CL_TYPE>1</CL_TYPE><RESIDENT>r</RESIDENT><MIDLE_NAME>pass123</MIDLE_NAME><CLN_CAT>clCat</CLN_CAT>"
						+ "<R_PHONE>29292929</R_PHONE><F_NAMES>fName</F_NAMES><SURNAME>lName</SURNAME><CMPG_NAME>fName lName</CMPG_NAME>"
						+ "<PERSON_CODE>123123-1234</PERSON_CODE><R_STREET>cStreet</R_STREET><R_CITY>cCity</R_CITY><R_PCODE>cZip</R_PCODE>"
						+ "<REGION>LV</REGION><R_CNTRY>LV</R_CNTRY><C_SINCE>2011</C_SINCE><B_DATE>to_date('20010103', 'yyyymmdd')</B_DATE>"
						+ "<CMP_NAME>oCom</CMP_NAME><EMP_CODE>oComCode</EMP_CODE><EMP_NAME>oCompany</EMP_NAME>"
						+ "<EMP_ADR>oStreet oCity oLV oZip</EMP_ADR><BANK_C>23</BANK_C><CLIENT_B>clNum1</CLIENT_B><STATUS>10</STATUS>"
						+ "</details></document></Changes_request>",
				client.prepareXML(result));

		IEAgreementRecord agreement = (IEAgreementRecord)list.get(1);
		assertEquals(BANKC, agreement.getBankc());
		assertEquals("clNum1", agreement.getClientNumberInABS());
		assertEquals(GROUPC, agreement.getGroupc());
		assertEquals(123, agreement.getOrderNumber().intValue());
		assertEquals("01", agreement.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>agreement</DOC_NAME>"
						+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID>"
						+ "<details><BINCOD>477573</BINCOD><BANK_C>23</BANK_C><BANK_CODE>23</BANK_CODE><U_EMAILS>agr@eeme.nt</U_EMAILS>"
						+ "<REP_LANG>1</REP_LANG><U_COD4>fill</U_COD4><DISTRIB_MODE>3</DISTRIB_MODE><CITY>sCity</CITY>"
						+ "<STREET>sStreet</STREET><POST_IND>sip</POST_IND><COUNTRY>LV</COUNTRY><RISK_LEVEL>XX</RISK_LEVEL>"
						+ "<BRANCH>B12</BRANCH><CLIENT>cl1</CLIENT><ENROLLED>to_date('" + TODAY + "', 'yyyymmdd')</ENROLLED><STATUS>10</STATUS>"
						+ "<GROUPC>50</GROUPC></details></document></Changes_request>",
				agreement.prepareXML(result));

		IEAccountRecord account = (IEAccountRecord)list.get(2);
		assertEquals(BANKC, account.getBankc());
		assertEquals("clNum1", account.getClientNumberInABS());
		assertEquals(GROUPC, account.getGroupc());
		assertEquals(123, account.getOrderNumber().intValue());
		assertEquals("01", account.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>account</DOC_NAME>"
						+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
						+ "<COND_SET>condA</COND_SET><CARD_ACCT>newAcc</CARD_ACCT><CCY>EUR</CCY><UFIELD_5>accNo</UFIELD_5>"
						+ "<AGRE_NOM>agr1</AGRE_NOM><CLIENT>cl1</CLIENT><CONTRACT>contract1</CONTRACT><ADJUST_FLAG>0</ADJUST_FLAG>"
						+ "<STAT_CHANGE>1</STAT_CHANGE><CRD>0</CRD><MIN_BAL>0</MIN_BAL><NON_REDUCE_BAL>0</NON_REDUCE_BAL>"
						+ "<BANK_C>23</BANK_C><CYCLE>0</CYCLE><DEFAULT_INDICATOR>0</DEFAULT_INDICATOR><GROUPC>50</GROUPC><STATUS>0</STATUS>"
						+ "<C_ACCNT_TYPE>00</C_ACCNT_TYPE><LIM_INTR>0</LIM_INTR></details></document></Changes_request>",
				account.prepareXML(result));

		IECardRecord card = (IECardRecord)list.get(3);
		assertEquals(BANKC, card.getBankc());
		assertEquals("clNum1", card.getClientNumberInABS());
		assertEquals(GROUPC, card.getGroupc());
		assertEquals(123, card.getOrderNumber().intValue());
		assertEquals("01", card.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>card</DOC_NAME>"
						+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
						+ "<AGREEMENT_KEY>agreeKey</AGREEMENT_KEY><CL_ACCT_KEY>clAccKey</CL_ACCT_KEY><CARD_NUM>477573</CARD_NUM>"
						+ "<BASE_SUPP>1</BASE_SUPP><CARD_NAME>caName</CARD_NAME><F_NAMES>fName</F_NAMES><SURNAME>lName</SURNAME>"
						+ "<M_NAME>pass123</M_NAME><U_FIELD7>a1</U_FIELD7><ID_CARD>123123-1234</ID_CARD><U_COD9>ucod9</U_COD9>"
						+ "<U_FIELD8>123</U_FIELD8><REGION>LV</REGION><U_BFIELD1>1DCdCity                                dStreet1                       "
						+ "         dStreet2                                          dZip            dCom                           "
						+ "                   </U_BFIELD1><DESIGN_ID>21</DESIGN_ID><B_DATE>to_date('20010103', 'yyyymmdd')</B_DATE>"
						+ "<AUTH_LIMIT>A1</AUTH_LIMIT><COND_SET>condC</COND_SET><RISK_LEVEL>XX</RISK_LEVEL>"
						+ "<CARD_TYPE>Debit</CARD_TYPE><U_AFIELD2>REQUESTED</U_AFIELD2><U_AFIELD1>authCode</U_AFIELD1>"
						+ "<U_COD10>E888</U_COD10><CLIENT>cl1</CLIENT><PIN_FLAG>1</PIN_FLAG><BANK_C>23</BANK_C><GROUPC>50</GROUPC>"
						+ "<STATUS1>0</STATUS1></details></document></Changes_request>",
				card.prepareXML(result));

		order.put(OrderDTO.fields.riskLevel,"WRONG");
		checkDataIntegrity(order,"Risk Level [WRONG] is not linked to BIN [477573]");
	}

	@Test 
	public void cardCreate_existingClient() throws UnrecognizeableRecordException, 
			MandatoryFieldMissedException, DataIntegrityException, CreateException, 
			NamingException, RecordFormatException{
		OrderDTO order = getCardCreateFullOrder();
		order.put(OrderDTO.fields.empCompanyRegNr, "regNo");
		
		IzdClient existingClient = new IzdClient();
		existingClient.setComp_id(new IzdClientPK("eClient", BANKC));
		when(clientManager.findByCif("clNum1", "accNo", "LV")).thenReturn(existingClient);
		
		checkDataIntegrity(order, "Can't find company with RegNr=[regNo]");
		
		IzdOfferedProduct prod = new IzdOfferedProduct();
		prod.setComp_id(new IzdOfferedProductPK("oProdId", BANKC, GROUPC));
		prod.setCompany("oComCode");
		prod.setRiskLevel("R1");
		prod.setCondAccnt("condA");
		prod.setChipDesignId(31L);
		prod.setChipAppId(41L);
		prod.setAuthLevel("A1");
		prod.setCondCard("condC");
		prod.setCardType("Debit");
		IzdCompany comp = new IzdCompany();
		comp.setName("oCompany");
		comp.setStreet("oStreet");
		comp.setCity("oCity");
		comp.setCountry("oLV");
		comp.setPostInd("oZip");
		comp.setComp_id(new IzdCompanyPK("oComCode", BANKC));
		when(productManager.findExactlyOneProduct(any(IzdOfferedProductDTO.class))).thenReturn(prod);
		when(commonManager.getIzdCompanyByRegCodeUR("regNo")).thenReturn(comp);
		when(pcdabaNGManager.getNewCardAcctId("accNo")).thenReturn("newAcc");
		
		List<TransmasterRecord> list = orderHelper.orderToRecord(order);
		
		assertEquals(3, list.size());
		
		Document result = DocumentHelper.createDocument(DocumentHelper.createElement("root"));
		Element agr1 = DocumentHelper.createElement("AGRE_NOM");
		agr1.setText("agr1");
		result.getRootElement().add(agr1);
		Element contract = DocumentHelper.createElement("CONTRACT");
		contract.setText("contract1");
		result.getRootElement().add(contract);
		Element tabKey = DocumentHelper.createElement("TAB_KEY");
		tabKey.setText("tabKey");
		result.getRootElement().add(tabKey);
		
		IEAgreementRecord agreement = (IEAgreementRecord)list.get(0);
		assertEquals(BANKC, agreement.getBankc());
		assertEquals("clNum1", agreement.getClientNumberInABS());
		assertEquals(GROUPC, agreement.getGroupc());
		assertEquals(123, agreement.getOrderNumber().intValue());
		assertEquals("01", agreement.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>agreement</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<BINCOD>477573</BINCOD><CLIENT>eClient</CLIENT><BANK_C>23</BANK_C><BANK_CODE>23</BANK_CODE>"
				+ "<U_EMAILS>agr@eeme.nt</U_EMAILS><REP_LANG>1</REP_LANG><U_COD4>fill</U_COD4><DISTRIB_MODE>3</DISTRIB_MODE>"
				+ "<CITY>sCity</CITY><STREET>sStreet</STREET><POST_IND>sip</POST_IND><COUNTRY>LV</COUNTRY>"
				+ "<RISK_LEVEL>R1</RISK_LEVEL><BRANCH>B12</BRANCH><ENROLLED>to_date('" + TODAY + "', 'yyyymmdd')</ENROLLED>"
				+ "<STATUS>10</STATUS><GROUPC>50</GROUPC></details></document></Changes_request>", 
				agreement.prepareXML(result));
		
		IEAccountRecord account = (IEAccountRecord)list.get(1);
		assertEquals(BANKC, account.getBankc());
		assertEquals("clNum1", account.getClientNumberInABS());
		assertEquals(GROUPC, account.getGroupc());
		assertEquals(123, account.getOrderNumber().intValue());
		assertEquals("01", account.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>account</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<COND_SET>condA</COND_SET><CARD_ACCT>newAcc</CARD_ACCT><CLIENT>eClient</CLIENT><CCY>EUR</CCY><UFIELD_5>accNo</UFIELD_5>"
				+ "<AGRE_NOM>agr1</AGRE_NOM><CONTRACT>contract1</CONTRACT><ADJUST_FLAG>0</ADJUST_FLAG>"
				+ "<STAT_CHANGE>1</STAT_CHANGE><CRD>0</CRD><MIN_BAL>0</MIN_BAL><NON_REDUCE_BAL>0</NON_REDUCE_BAL>"
				+ "<BANK_C>23</BANK_C><CYCLE>0</CYCLE><DEFAULT_INDICATOR>0</DEFAULT_INDICATOR><GROUPC>50</GROUPC><STATUS>0</STATUS>"
				+ "<C_ACCNT_TYPE>00</C_ACCNT_TYPE><LIM_INTR>0</LIM_INTR></details></document></Changes_request>", 
				account.prepareXML(result));
		
		IECardRecord card = (IECardRecord)list.get(2);
		assertEquals(BANKC, card.getBankc());
		assertEquals("clNum1", card.getClientNumberInABS());
		assertEquals(GROUPC, card.getGroupc());
		assertEquals(123, card.getOrderNumber().intValue());
		assertEquals("01", card.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>card</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<CLIENT>eClient</CLIENT><AGREEMENT_KEY>agreeKey</AGREEMENT_KEY><CL_ACCT_KEY>clAccKey</CL_ACCT_KEY><CARD_NUM>477573</CARD_NUM>"
				+ "<BASE_SUPP>1</BASE_SUPP><CARD_NAME>caName</CARD_NAME><F_NAMES>fName</F_NAMES><SURNAME>lName</SURNAME>"
				+ "<M_NAME>pass123</M_NAME><U_FIELD7>a1</U_FIELD7><ID_CARD>123123-1234</ID_CARD><U_COD9>ucod9</U_COD9>"
				+ "<U_FIELD8>123</U_FIELD8><REGION>LV</REGION><U_BFIELD1>1DCdCity                                dStreet1                       "
				+ "         dStreet2                                          dZip            dCom                           "
				+ "                   </U_BFIELD1><DESIGN_ID>21</DESIGN_ID><B_DATE>to_date('20010103', 'yyyymmdd')</B_DATE>"
				+ "<AUTH_LIMIT>A1</AUTH_LIMIT><COND_SET>condC</COND_SET><RISK_LEVEL>R1</RISK_LEVEL>"
				+ "<CARD_TYPE>Debit</CARD_TYPE><U_AFIELD2>REQUESTED</U_AFIELD2><U_AFIELD1>authCode</U_AFIELD1>"
				+ "<U_COD10>E888</U_COD10><PIN_FLAG>1</PIN_FLAG><BANK_C>23</BANK_C><GROUPC>50</GROUPC>"
				+ "<STATUS1>0</STATUS1></details></document></Changes_request>", 
				card.prepareXML(result));
		
		verify(clientManager).saveOrUpdate(existingClient); // empCode is changed
	}
	
	@Test 
	public void cardCreate_existingAccount() throws UnrecognizeableRecordException, 
			MandatoryFieldMissedException, DataIntegrityException, CreateException, 
			NamingException, RecordFormatException{
		OrderDTO order = getCardCreateFullOrder();
		order.put(OrderDTO.fields.branchToDeliverAt, "bre"); 
		order.put(OrderDTO.fields.country, "EE"); 
		
		IzdClient exitingClient = new IzdClient();
		exitingClient.setComp_id(new IzdClientPK("eClient", BANKC));
		when(clientManager.findByCif("clNum1", "accNo", "EE")).thenReturn(exitingClient);
		
		IzdAccount existAccount = new IzdAccount();
		existAccount.setCardAcct("eCardAcc");
		Set<IzdClAcct> clAcc = new HashSet<IzdClAcct>();
		existAccount.setIzdClAccts(clAcc);
		existAccount.setAccountNo(new BigDecimal("3124"));
		existAccount.setIzdAccParam(new IzdAccParam());
		existAccount.getIzdAccParam().setStatus("0");
		when(accountManager.findAccountByIzdClientAndExternalNo(eq(exitingClient), eq("accNo"), eq("EE"))).thenReturn(existAccount);
		
		IzdOfferedProduct prod = new IzdOfferedProduct();
		prod.setComp_id(new IzdOfferedProductPK("oProdId", BANKC, GROUPC));
		prod.setCompany("oComCode");
		prod.setRiskLevel("R1");
		prod.setCondAccnt("condA");
		prod.setChipDesignId(31L);
		prod.setChipAppId(41L);
		prod.setAuthLevel("A1");
		prod.setCondCard("condC");
		prod.setCardType("Debit");
		when(productManager.findExactlyOneProduct(any(IzdOfferedProductDTO.class))).thenReturn(prod);
		
		List<TransmasterRecord> list = orderHelper.orderToRecord(order);
		
		assertEquals(2, list.size());
		
		Document result = DocumentHelper.createDocument(DocumentHelper.createElement("root"));
		Element agr1 = DocumentHelper.createElement("AGRE_NOM");
		agr1.setText("agr1");
		result.getRootElement().add(agr1);
		Element contract = DocumentHelper.createElement("CONTRACT");
		contract.setText("contract1");
		result.getRootElement().add(contract);
		Element tabKey = DocumentHelper.createElement("TAB_KEY");
		tabKey.setText("tabKey");
		result.getRootElement().add(tabKey);
		
		IEAgreementRecord agreement = (IEAgreementRecord)list.get(0);
		assertEquals(BANKC, agreement.getBankc());
		assertEquals("clNum1", agreement.getClientNumberInABS());
		assertEquals(GROUPC, agreement.getGroupc());
		assertEquals(123, agreement.getOrderNumber().intValue());
		assertEquals("01", agreement.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>agreement</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<BINCOD>477573</BINCOD><CLIENT>eClient</CLIENT><BANK_C>23</BANK_C><BANK_CODE>23</BANK_CODE>"
				+ "<U_EMAILS>agr@eeme.nt</U_EMAILS><REP_LANG>1</REP_LANG><U_COD4>fill</U_COD4><DISTRIB_MODE>3</DISTRIB_MODE>"
				+ "<CITY>sCity</CITY><STREET>sStreet</STREET><POST_IND>sip</POST_IND><COUNTRY>LV</COUNTRY>"
				+ "<RISK_LEVEL>R1</RISK_LEVEL><BRANCH>B12</BRANCH><ENROLLED>to_date('" + TODAY + "', 'yyyymmdd')</ENROLLED>"
				+ "<STATUS>10</STATUS><GROUPC>50</GROUPC></details></document></Changes_request>", 
				agreement.prepareXML(result));
		
		IECardRecord card = (IECardRecord)list.get(1);
		assertEquals(BANKC, card.getBankc());
		assertEquals("clNum1", card.getClientNumberInABS());
		assertEquals(GROUPC, card.getGroupc());
		assertEquals(123, card.getOrderNumber().intValue());
		assertEquals("01", card.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>card</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<CLIENT>eClient</CLIENT><AGREEMENT_KEY>agreeKey</AGREEMENT_KEY><CL_ACCT_KEY>clAccKey</CL_ACCT_KEY><CARD_NUM>477573</CARD_NUM>"
				+ "<BASE_SUPP>1</BASE_SUPP><CARD_NAME>caName</CARD_NAME><F_NAMES>fName</F_NAMES><SURNAME>lName</SURNAME>"
				+ "<M_NAME>pass123</M_NAME><U_FIELD7>a1</U_FIELD7><ID_CARD>123123-1234</ID_CARD><U_COD9>ucod9</U_COD9>"
				+ "<U_FIELD8>123</U_FIELD8><REGION>EE</REGION><U_BFIELD1>1DCdCity                                dStreet1                       "
				+ "         dStreet2                                          dZip            dCom                           "
				+ "                   </U_BFIELD1><DESIGN_ID>21</DESIGN_ID><B_DATE>to_date('20010103', 'yyyymmdd')</B_DATE>"
				+ "<AUTH_LIMIT>A1</AUTH_LIMIT><COND_SET>condC</COND_SET><RISK_LEVEL>R1</RISK_LEVEL>"
				+ "<CARD_TYPE>Debit</CARD_TYPE><U_COD10>B12</U_COD10><PIN_FLAG>1</PIN_FLAG><BANK_C>23</BANK_C><GROUPC>50</GROUPC>"
				+ "<STATUS1>0</STATUS1></details></document></Changes_request>", 
				card.prepareXML(result));
		
		verifyZeroInteractions(pcdabaNGManager);
		
		// Missing branch
		order.put(OrderDTO.fields.branch, "noB");
		when(commonManager.getBranchIdByExternalId("noB")).thenReturn(null);
		checkMandatoryFieldMissed(order, "There is no branch in NORDLB_BRANCHES for noB");
		
		// Multiple products
		when(productManager.findExactlyOneProduct(any(IzdOfferedProductDTO.class))).thenThrow(new TooManyRecordsFoundException("Too many"));
		checkMandatoryFieldMissed(order, "Can't get corresponding product");
		
		// Missing client country
		order.put(OrderDTO.fields.clientCountry, null);
		checkDataIntegrity(order, "Missing client address country");
	}
	
	@Test 
	public void cardCreate_existingAgreement() throws UnrecognizeableRecordException, 
			MandatoryFieldMissedException, DataIntegrityException, CreateException, 
			NamingException, RecordFormatException{
		OrderDTO order = getCardCreateFullOrder();
		order.put(OrderDTO.fields.branchToDeliverAt, "bre"); 
		
		IzdClient exitingClient = new IzdClient();
		exitingClient.setComp_id(new IzdClientPK("eClient", BANKC));
		when(clientManager.findByCif("clNum1", "accNo", "LV")).thenReturn(exitingClient);
		when(clientManager.findByCif("clNum1", "accNo", "EE")).thenReturn(exitingClient);
		
		IzdAccount existAccount = new IzdAccount();
		existAccount.setCardAcct("eCardAcc");
		existAccount.setAccountNo(new BigDecimal("3124"));
		existAccount.setIzdAccParam(new IzdAccParam());
		existAccount.getIzdAccParam().setStatus("0");
		Set<IzdClAcct> clAccs = new HashSet<IzdClAcct>();
		IzdClAcct clAcc = new IzdClAcct();
		Set<IzdCard> cards = new HashSet<IzdCard>();
		IzdCard izdCard = new IzdCard();
		izdCard.setBinCode("477573");
		izdCard.setIzdAgreement(new IzdAgreement());
		izdCard.getIzdAgreement().setAgreNom(4312L);
		cards.add(izdCard);
		clAcc.setIzdCards(cards);
		clAcc.setTabKey(new BigDecimal(4313));
		clAccs.add(clAcc);
		existAccount.setIzdClAccts(clAccs);
		when(accountManager.findAccountByIzdClientAndExternalNo(eq(exitingClient), eq("accNo"), eq("LV"))).thenReturn(existAccount);
		when(accountManager.findAccountByIzdClientAndExternalNo(eq(exitingClient), eq("accNo"), eq("EE"))).thenReturn(existAccount);
		
		IzdOfferedProduct prod = new IzdOfferedProduct();
		prod.setComp_id(new IzdOfferedProductPK("oProdId", BANKC, GROUPC));
		prod.setCompany("oComCode");
		prod.setRiskLevel("R1");
		prod.setCondAccnt("condA");
		prod.setChipDesignId(31L);
		prod.setChipAppId(41L);
		prod.setAuthLevel("A1");
		prod.setCondCard("condC");
		prod.setCardType("Debit");
		when(productManager.findExactlyOneProduct(any(IzdOfferedProductDTO.class))).thenReturn(prod);
		
		List<TransmasterRecord> list = orderHelper.orderToRecord(order);
		
		assertEquals(1, list.size());
		
		Document result = DocumentHelper.createDocument(DocumentHelper.createElement("root"));
		
		IECardRecord card = (IECardRecord)list.get(0);
		assertEquals(BANKC, card.getBankc());
		assertEquals("clNum1", card.getClientNumberInABS());
		assertEquals(GROUPC, card.getGroupc());
		assertEquals(123, card.getOrderNumber().intValue());
		assertEquals("01", card.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>card</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<CLIENT>eClient</CLIENT><AGREEMENT_KEY>4312</AGREEMENT_KEY><CL_ACCT_KEY>4313</CL_ACCT_KEY><CARD_NUM>477573</CARD_NUM>"
				+ "<BASE_SUPP>1</BASE_SUPP><CARD_NAME>caName</CARD_NAME><F_NAMES>fName</F_NAMES><SURNAME>lName</SURNAME>"
				+ "<M_NAME>pass123</M_NAME><U_FIELD7>a1</U_FIELD7><ID_CARD>123123-1234</ID_CARD><U_COD9>ucod9</U_COD9>"
				+ "<U_FIELD8>123</U_FIELD8><REGION>LV</REGION><U_BFIELD1>1DCdCity                                dStreet1                       "
				+ "         dStreet2                                          dZip            dCom                           "
				+ "                   </U_BFIELD1><DESIGN_ID>21</DESIGN_ID><B_DATE>to_date('20010103', 'yyyymmdd')</B_DATE>"
				+ "<AUTH_LIMIT>A1</AUTH_LIMIT><COND_SET>condC</COND_SET><RISK_LEVEL>R1</RISK_LEVEL>"
				+ "<CARD_TYPE>Debit</CARD_TYPE><U_COD10>B12</U_COD10><PIN_FLAG>1</PIN_FLAG><BANK_C>23</BANK_C><GROUPC>50</GROUPC>"
				+ "<STATUS1>0</STATUS1></details></document></Changes_request>", 
				card.prepareXML(result));
		
		verifyZeroInteractions(pcdabaNGManager);

		// Missing branch
		order.put(OrderDTO.fields.branchToDeliverAt, "noB");
		when(commonManager.getBranchIdByExternalId("noB")).thenReturn(null);
		checkMandatoryFieldMissed(order, "There is no branch in NORDLB_BRANCHES for noB");
		
		// Missing delivery address
		order.put(OrderDTO.fields.branchToDeliverAt, lv.nordlb.link.dataTransfer.OrderHelper.DELIVERY_BRANCH);
		order.put(OrderDTO.fields.dlv_addr_city, null);
		order.put(OrderDTO.fields.dlv_addr_code, null);
		order.put(OrderDTO.fields.dlv_addr_country, null);
		order.put(OrderDTO.fields.dlv_addr_street1, null);
		order.put(OrderDTO.fields.dlv_addr_street2, null);
		order.put(OrderDTO.fields.dlv_addr_zip, null);
		order.put(OrderDTO.fields.dlv_company, null);
		checkMandatoryFieldMissed(order, "Missing value for delivery address country, region, street address, zip code");
		
		// Missing mandatory field, delivery branch
		order.put(OrderDTO.fields.dlv_language, null);
		order.put(OrderDTO.fields.reportLanguage, null);
		checkMandatoryFieldMissed(order, "Missing value for delivery address language");
	}
	
	@Test 
	public void cardRenew_createNewCard() throws UnrecognizeableRecordException, 
			MandatoryFieldMissedException, DataIntegrityException, CreateException, 
			NamingException, RecordFormatException{
		OrderDTO order = getCardCreateFullOrder();
		order.setAction(OrderDTO.fields.cardRenewAction);
		order.put(OrderDTO.fields.cardNumber, "4652281123432121");
		order.put(OrderDTO.fields.country, "");
		
		
		IzdCard izdCard = setUpIzdCard("4652281123432121");
		izdCard.setStatus1("2");
		
		IzdClient exitingClient = new IzdClient();
		exitingClient.setComp_id(new IzdClientPK("eClient", BANKC));
		when(clientManager.findByCif("clNum1", "accNo", "LV")).thenReturn(exitingClient);
		
		IzdAccount existAccount = new IzdAccount();
		existAccount.setCardAcct("eCardAcc");
		existAccount.setAccountNo(new BigDecimal("3124"));
		existAccount.setIzdAccParam(new IzdAccParam());
		existAccount.getIzdAccParam().setStatus("0");
		Set<IzdClAcct> clAcc = new HashSet<IzdClAcct>();
		existAccount.setIzdClAccts(clAcc);
		when(accountManager.findAccountByIzdClientAndExternalNo(eq(exitingClient), eq("accNo"), eq("LV"))).thenReturn(existAccount);
		
		IzdOfferedProduct prod = new IzdOfferedProduct();
		prod.setComp_id(new IzdOfferedProductPK("oProdId", BANKC, GROUPC));
		prod.setCompany("oComCode");
		prod.setRiskLevel("R1");
		prod.setCondAccnt("condA");
		prod.setChipDesignId(31L);
		prod.setChipAppId(41L);
		prod.setAuthLevel("A1");
		prod.setCondCard("condC");
		prod.setCardType("Debit");
		when(productManager.findExactlyOneProduct(any(IzdOfferedProductDTO.class))).thenReturn(prod);
		
		List<TransmasterRecord> list = orderHelper.orderToRecord(order);
		
		assertEquals(2, list.size());
		
		Document result = DocumentHelper.createDocument(DocumentHelper.createElement("root"));
		Element agr1 = DocumentHelper.createElement("AGRE_NOM");
		agr1.setText("agr1");
		result.getRootElement().add(agr1);
		Element contract = DocumentHelper.createElement("CONTRACT");
		contract.setText("contract1");
		result.getRootElement().add(contract);
		Element tabKey = DocumentHelper.createElement("TAB_KEY");
		tabKey.setText("tabKey");
		result.getRootElement().add(tabKey);
		
		IEAgreementRecord agreement = (IEAgreementRecord)list.get(0);
		assertEquals(BANKC, agreement.getBankc());
		assertEquals("clNum1", agreement.getClientNumberInABS());
		assertEquals(GROUPC, agreement.getGroupc());
		assertEquals(123, agreement.getOrderNumber().intValue());
		assertEquals("01", agreement.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>agreement</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<BINCOD>465228</BINCOD><CLIENT>eClient</CLIENT><BANK_C>23</BANK_C><BANK_CODE>23</BANK_CODE>"
				+ "<U_EMAILS>agr@eeme.nt</U_EMAILS><REP_LANG>1</REP_LANG><U_COD4>fill</U_COD4><DISTRIB_MODE>3</DISTRIB_MODE>"
				+ "<CITY>sCity</CITY><STREET>sStreet</STREET><POST_IND>sip</POST_IND><COUNTRY>LV</COUNTRY>"
				+ "<RISK_LEVEL>R1</RISK_LEVEL><BRANCH>B12</BRANCH><ENROLLED>to_date('" + TODAY + "', 'yyyymmdd')</ENROLLED>"
				+ "<STATUS>10</STATUS><GROUPC>50</GROUPC></details></document></Changes_request>", 
				agreement.prepareXML(result));
		
		IECardRecord card = (IECardRecord)list.get(1);
		assertEquals(BANKC, card.getBankc());
		assertEquals("clNum1", card.getClientNumberInABS());
		assertEquals(GROUPC, card.getGroupc());
		assertEquals(123, card.getOrderNumber().intValue());
		assertEquals("01", card.getType());
		assertEquals(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/><DOC_NAME>card</DOC_NAME>"
				+ "<OPERATION>insert</OPERATION><BANK_C>23</BANK_C><GROUPC>50</GROUPC><EXTERNAL_ID>123</EXTERNAL_ID><details>"
				+ "<CLIENT>eClient</CLIENT><AGREEMENT_KEY>agreeKey</AGREEMENT_KEY><CL_ACCT_KEY>clAccKey</CL_ACCT_KEY><CARD_NUM>465228</CARD_NUM>"
				+ "<BASE_SUPP>1</BASE_SUPP><CARD_NAME>caName</CARD_NAME><F_NAMES>fName</F_NAMES><SURNAME>lName</SURNAME>"
				+ "<M_NAME>pass123</M_NAME><U_FIELD7>a1</U_FIELD7><ID_CARD>123123-1234</ID_CARD><U_COD9>ucod9</U_COD9>"
				+ "<U_FIELD8>123</U_FIELD8><REGION>LV</REGION><U_BFIELD1>1DCdCity                                dStreet1                       "
				+ "         dStreet2                                          dZip            dCom                           "
				+ "                   </U_BFIELD1><DESIGN_ID>21</DESIGN_ID><B_DATE>to_date('20010103', 'yyyymmdd')</B_DATE>"
				+ "<AUTH_LIMIT>A1</AUTH_LIMIT><COND_SET>condC</COND_SET><RISK_LEVEL>R1</RISK_LEVEL>"
				+ "<CARD_TYPE>Debit</CARD_TYPE><U_AFIELD2>REQUESTED</U_AFIELD2><U_AFIELD1>authCode</U_AFIELD1><U_COD10>E888</U_COD10><PIN_FLAG>1</PIN_FLAG><BANK_C>23</BANK_C><GROUPC>50</GROUPC>"
				+ "<STATUS1>0</STATUS1></details></document></Changes_request>", 
				card.prepareXML(result));
	}
	
	@Test
	public void deliveryAddressHelperTest(){
		DeliveryDetailsHelpper helper = orderHelper.new DeliveryDetailsHelpper("1", "LV", "Riga raj.", "Riga", "Skanskets 12", "LV-1212", "DNB");
		assertEquals("1LVRiga raj.                            Riga                                    Skanskets 12                                      LV-1212         DNB                                               ", helper.getDetails());
	
		helper = orderHelper.new DeliveryDetailsHelpper("1LVRiga raj.                            Riga                                    Skanskets 12                                      LV-1212         DNB                                               ");
		assertEquals("1", helper.getLanguage());
		assertEquals("LV", helper.getCountry());
		assertEquals("Riga raj.", helper.getRegion());
		assertEquals("Riga", helper.getCity());
		assertEquals("Skanskets 12", helper.getAddress());
		assertEquals("LV-1212", helper.getZipCode());
		assertEquals("DNB", helper.getAdditionalFields());

		helper = orderHelper.new DeliveryDetailsHelpper("1", "LV", null, null, "Skanskets 12", "LV-1212", null);
		assertEquals("1LV                                                                             Skanskets 12                                      LV-1212                                                           ", helper.getDetails());
	}
*/






    private Order getCardCreateFullOrder() {
        Order order = getValidOrder(Constants.CARD_CREATE_ACTION);
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888);
        order.setOrderId("123");
        order.setClientType("1");
        order.setClientNumberInAbs("clNum1");
        order.setResident("r");
        order.setCrdPasswd("pass123");
        order.setClientCategory("clCat");
        order.setOwnerPhone("29292929");
        order.setClientFirstname("fName");
        order.setClientLastname("lName");
        order.setClientPersonId("123123-1234");
        order.setClientStreet("cStreet");
        order.setClientCity("cCity");
        order.setClientZip("cZip");
        order.setClientCountry("cCountry");
        order.setBankClientSince("2011");
        order.setBirthdayMask("yyyy-MM-dd");
        order.setOwnerBirthday("2001-01-03");
        order.setClientBirthday("2001-01-03");
        order.setOwnCompanyName("oCom");
        order.setBin("477573");
        order.setEmail("agr@eeme.nt");
        order.setReportLanguage("1");
        order.setFillPlaceNg("fill");
        order.setRepDistributionMode("3");
        order.setStmtCity("sCity");
        order.setStmtStreet("sStreet");
        order.setStmtZip("sip");
        order.setBranch("B132");
        order.setAccountCcy("EUR");
        order.setAccountNoPlaton("accNo");
        order.setAgreementKey("agreeKey");
        order.setClAcctKey("clAccKey");
        order.setBaseSupp("1");
        order.setCardName("caName");
        order.setAuthNotifyDestination("a1");
        order.setUCod9("ucod9");
        order.setCardExpiry("2018");
        order.setDlvLanguage("1");
        order.setDlvAddrCountry("dCountry");
        order.setDlvAddrCity("dCity");
        order.setDlvAddrStreet1("dStreet1");
        order.setDlvAddrStreet2("dStreet2");
        order.setDlvAddrZip("dZip");
        order.setDlvCompany("dCom");
        order.setDesignId("21");
        order.setCountry(getCountry());
        return order;
    }

    public OrderProcessor getOrderProcessor() {
        return (OrderProcessor) super.orderProcessor;
    }

    public OrderValidator getOrderValidator() {
        return (OrderValidator) super.orderValidator;
    }

    public Mapper getMapper() {
        return (Mapper) super.mapper;
    }

    @Override
    public String getCountry() {
        return "LV";
    }
}
