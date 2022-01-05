package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import lv.bank.cards.core.linkApp.dto.CardInfoDTO;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

public class CardInfoTest extends JUnitHandlerTestBase {
	
	private CardInfo info;
	
	@Before
	public void setUpTest() throws RequestPreparationException{
		info = new CardInfo();
	}
	
	@Test
	public void cardInfo_byCardNumber() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		String cardNumber = "4775733282237579";
		CardInfoDTO cardInfo = getCardInfo(cardNumber, "");
		when(pcdabaNGManager.getCardInfo(eq(cardNumber))).thenReturn(cardInfo);
		
		info.handle(getRequestWithCard("card-info", cardNumber, null));
		checkCardData(info.compileResponse().getElement().element("card-info"), cardNumber, "");
	}
	
	@Test
	public void cardInfo_byCif() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		String cif = "T22893";
		String cardNumber1 = "4775733282237579";
		CardInfoDTO cardInfo1 = getCardInfo(cardNumber1, "");
		String cardNumber2 = "4775733282237572";
		CardInfoDTO cardInfo2 = getCardInfo(cardNumber2, "2");
		
		when(pcdabaNGManager.getCardInfoByCif(eq(cif), eq("EE"), eq(false))).thenReturn(Arrays.asList(cardInfo1, cardInfo2));
		
		SubRequest request = getRequestWithCard("card-info", null, cif);
		addElementOnRootElement(request, "country", "EE");
		info.handle(request);
		
		checkCardData((Element)info.compileResponse().getElement().elements().get(0), cardNumber1, "");
		checkCardData((Element)info.compileResponse().getElement().elements().get(1), cardNumber2, "2");
	}

	@Test
	public void noCardNumberAndCif() throws RequestPreparationException, RequestProcessingException, RequestFormatException{
		checkRequestFormatException(info, getRequestWithCard("card-info", null, null), "Specify card or cif tag");
	}
	
	@Test
	public void invalidCardNumber()  throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		checkRequestProcessingException(info, getRequestWithCard("card-info", "wrong", null), "Please provide valid card number");
	}
	
	@Test
	public void cannotFindCard()  throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		checkRequestProcessingException(info, getRequestWithCard("card-info", "4775733282237579", null), "Please provide valid card number");
	}

	private void checkCardData(Element info, String cardNumber, String suffix){
		assertEquals(cardNumber, info.element("card").getTextTrim());
		assertEquals("VISA Dynamic" + suffix, info.element("prefix-desc").getTextTrim());
		assertEquals("SOME ONE" + suffix, info.element("embossing-name").getTextTrim());
		assertEquals("SOME ONE C" + suffix, info.element("card-name").getTextTrim());
		assertEquals("1803", info.element("expiry-date").getTextTrim());
		assertEquals("1804", info.element("expiry-date2").getTextTrim());
		assertEquals("123" + suffix, info.element("cvc").getTextTrim());
		assertEquals("T22893" + suffix, info.element("p-cif").getTextTrim());
		assertEquals("EUR", info.element("billing-currency").getTextTrim());
		assertEquals("33051502" + suffix, info.element("account-number").getTextTrim());
		assertEquals("50", info.element("card-group").getTextTrim());
		assertEquals("0", info.element("end-bal").getTextTrim());
		assertEquals("321312-4321" + suffix, info.element("client-person-code").getTextTrim());
		assertEquals("123123-1234" + suffix, info.element("person-code").getTextTrim());
		assertEquals("0", info.element("card-status1").getTextTrim());
		assertEquals("REQUESTED" + suffix, info.element("pin-delivery-status").getTextTrim());
		assertEquals("Pass" + suffix, info.element("password").getTextTrim());
		assertEquals("Bankā" + suffix, info.element("distrib-mode").getTextTrim());
		
		assertEquals("Skankstes 12" + suffix, info.element("distrib-address").element("street").getTextTrim());
		assertEquals("Riga" + suffix, info.element("distrib-address").element("city").getTextTrim());
		assertEquals("LV" + suffix, info.element("distrib-address").element("country").getTextTrim());
		assertEquals("LV-1012" + suffix, info.element("distrib-address").element("post-index").getTextTrim());
		assertEquals("32" + suffix, info.element("distrib-address").element("branch").getTextTrim());
		
		assertEquals("Rīga, Visvalža 3a-8, LV-1050", info.element("delivery-details").element("dlv_address").getTextTrim());
		assertEquals("LV", info.element("delivery-details").element("dlv_addr_country").getTextTrim());
		assertEquals("Rīga", info.element("delivery-details").element("dlv_addr_city").getTextTrim());
		assertEquals("Rīga", info.element("delivery-details").element("dlv_addr_street1").getTextTrim());
		assertEquals("Visvalža 3a-8", info.element("delivery-details").element("dlv_addr_street2").getTextTrim());
		assertEquals("LV-1050", info.element("delivery-details").element("dlv_addr_zip").getTextTrim());
		assertEquals("2", info.element("delivery-details").element("dlv_language").getTextTrim());
		assertEquals("", info.element("delivery-details").element("dlv_company").getTextTrim());
		assertEquals("31" + suffix, info.element("delivery-details").element("branch").getTextTrim());
	}
}
