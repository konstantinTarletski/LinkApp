package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import lv.bank.cards.core.entity.linkApp.PcdSlip;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test Slip information handler
 * @author saldabols
 */
public class SlipInfoTest extends JUnitHandlerTestBase {

	private SlipInfo handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException{
		handler = new SlipInfo();
	}
	
	@Test 
	public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		SubRequest request = getSubrequest("slip-info");
		
		// There is no card number
		checkRequestFormatException(handler, request, "Specify card number");
		
		addElementOnRootElement(request, "card", "4775733282237579");
		addElementOnRootElement(request, "from", "2016 03 14");
		
		// Wrong from date format
		checkRequestFormatException(handler, request, "Specify from tag in format yyyy-MM-dd HH24:mm:ss or yyyy-MM-dd");
		
		request = getSubrequest("slip-info", "yyyy-MM-dd");
		addElementOnRootElement(request, "card", "4775733282237579");
		addElementOnRootElement(request, "from", "2016-03-14");
		
		PcdSlip slip1 = getSlipElement("1", 10L);
		PcdSlip slip2 = getSlipElement("2", 20L);
		PcdSlip minSlip = new PcdSlip();
		minSlip.setAmount(130L);
		minSlip.setAccntCcy("EUR");
		minSlip.setTranType("minType");
		
		when(pcdabaNGManager.getTransactionDetails(eq("4775733282237579"), any(Date.class), eq((Date)null)))
			.thenReturn(Arrays.asList(slip1, slip2, minSlip));
		
		handler.handle(request);
		
		Element slipInfo1 = (Element)handler.compileResponse().getElement().element("get-slip-info").elements().get(0);
		checkSlipInfo(slipInfo1, "1", 10L);
		Element slipInfo2 = (Element)handler.compileResponse().getElement().element("get-slip-info").elements().get(1);
		checkSlipInfo(slipInfo2, "2", 20L);
		Element minSlipInfo = (Element)handler.compileResponse().getElement().element("get-slip-info").elements().get(2);
		assertEquals(Long.toString(130), minSlipInfo.element("amount").getTextTrim());
		assertEquals("EUR", minSlipInfo.element("accnt_ccy").getTextTrim());
		assertEquals("minType", minSlipInfo.element("tran_type").getTextTrim());
	}
	
	private PcdSlip getSlipElement(String suffix, long addValue){
		PcdSlip slip = new PcdSlip();
		slip.setPostDate(new Date());
		slip.setAmount(1000L + addValue);
		slip.setTranAmt(200 + addValue);
		slip.setTranCcy("EUR");
		slip.setAccntCcy("EUR");
		slip.setTranDateTime(new Date());
		slip.setAbvrName("MName" + suffix);
		slip.setAprCode("ACode" + suffix);
		slip.setDebCred(BigDecimal.valueOf(300 + addValue));
		slip.setMerchant("Mer" + suffix);
		slip.setTranType("TType" + suffix);
		slip.setStan("Stan" + suffix);
		slip.setCity("City"+ suffix);
		slip.setCountry("Country" + suffix);
		slip.setTrCode("TCode" + suffix);
		slip.setTrFee(400 + addValue);
		slip.setFee(500 + addValue);
		return slip;
	}
	
	private void checkSlipInfo(Element slipInfo, String suffix, long addValue){
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), slipInfo.element("post_date").getTextTrim());
		assertEquals(Long.toString(1000 + addValue), slipInfo.element("amount").getTextTrim());
		assertEquals(Long.toString(200 + addValue), slipInfo.element("tran_amt").getTextTrim());
		assertEquals("EUR", slipInfo.element("tran_ccy").getTextTrim());
		assertEquals("EUR", slipInfo.element("accnt_ccy").getTextTrim());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), slipInfo.element("tran_date_time").getTextTrim());
		assertEquals("MName" + suffix, slipInfo.element("merchant_name").getTextTrim());
		assertEquals("ACode" + suffix, slipInfo.element("apr_code").getTextTrim());
		assertEquals(BigDecimal.valueOf(300 + addValue).toPlainString(), slipInfo.element("deb_cred").getTextTrim());
		assertEquals("Mer" + suffix, slipInfo.element("merchant_id").getTextTrim());
		assertEquals("TType" + suffix, slipInfo.element("tran_type").getTextTrim());
		assertEquals("Stan" + suffix, slipInfo.element("stan").getTextTrim());
		assertEquals("City" + suffix, slipInfo.element("city").getTextTrim());
		assertEquals("Country" + suffix, slipInfo.element("country").getTextTrim());
		assertEquals("TCode" + suffix, slipInfo.element("tr_fee_code").getTextTrim());
		assertEquals(Long.toString(400 + addValue), slipInfo.element("tr_fee").getTextTrim());
		assertEquals(Long.toString(500 + addValue), slipInfo.element("fee").getTextTrim());
	}
}
