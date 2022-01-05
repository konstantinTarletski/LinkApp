package lv.nordlb.cards.transmaster.requests.handlers.cms;

import lv.bank.cards.core.entity.cms.IzdAccParam;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdPreProcessingRow;
import lv.bank.cards.core.entity.cms.IzdTrtypeName;
import lv.bank.cards.core.entity.cms.IzdTrtypeNameId;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.JUnitHandlerTestBase;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to test pre processed payment information and deletion handler
 * @author saldabols
 */
public class DeletePreProcPaymentTest extends JUnitHandlerTestBase {

	private DeletePreProcPayment handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException, NamingException {
		when(context.lookup(CMSSoapAPIWrapper.JNDI_NAME)).thenReturn(cmsSoapAPIWrapper);
		handler = spy(new DeletePreProcPayment());
	}
	
	@Test 
	public void handle_list() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		SubRequest request = getSubrequest("delete-pre-proc-payment");
		String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
		
		// There is no card number
		checkRequestFormatException(handler, request, "No action provided!");
		
		addElementOnRootElement(request, "action", "list");
		addElementOnRootElement(request, "limit", "x");
		
		// Limit not number
		checkRequestFormatException(handler, request, "Incorect limit number provided!");
		
		request = getSubrequest("delete-pre-proc-payment");
		addElementOnRootElement(request, "action", "list");
		addElementOnRootElement(request, "limit", "0");
		
		// Zero is not acceptable
		checkRequestFormatException(handler, request, "No list limit provided!");
		
		request = getSubrequest("delete-pre-proc-payment");
		addElementOnRootElement(request, "action", "list");
		addElementOnRootElement(request, "limit", "2");
		IzdPreProcessingRow visa = new IzdPreProcessingRow();
		IzdCard v = new IzdCard();
		v.setCard("4775733282237579");
		visa.setCard(v);
		visa.setInternalNo(BigDecimal.valueOf(100));
		visa.setTrType("VType");
		visa.setAmount(10L);
		visa.setCcy("EUR");
		visa.setTranDateTime(new Date());
		visa.setAccountNo(BigDecimal.valueOf(1));
		IzdTrtypeName vType = new IzdTrtypeName();
		vType.setName("Visa type");
		when(cardManager.getIzdTrTypeNameByType(eq(new IzdTrtypeNameId("VType", "2", "00")))).thenReturn(vType);
		IzdAccount vAccount = new IzdAccount();
		vAccount.setIzdAccParam(new IzdAccParam());
		vAccount.getIzdAccParam().setUfield5("vField");
		when(cardManager.getIzdAccountByAccountNr(eq(BigDecimal.valueOf(1)))).thenReturn(vAccount);
		IzdPreProcessingRow master = new IzdPreProcessingRow();
		IzdCard m = new IzdCard();
		m.setCard("5775733282237579");
		master.setCard(m);
		master.setInternalNo(BigDecimal.valueOf(200));
		master.setTrType("MType");
		master.setAmount(20L);
		master.setCcy("USD");
		master.setTranDateTime(new Date());
		master.setAccountNo(BigDecimal.valueOf(2));
		IzdTrtypeName msType = new IzdTrtypeName();
		msType.setName("Master type");
		when(cardManager.getIzdTrTypeNameByType(eq(new IzdTrtypeNameId("MType", "2", "00")))).thenReturn(msType);
		IzdAccount msAccount = new IzdAccount();
		msAccount.setIzdAccParam(new IzdAccParam());
		msAccount.getIzdAccParam().setUfield5("msField");
		when(cardManager.getIzdAccountByAccountNr(eq(BigDecimal.valueOf(2)))).thenReturn(msAccount);
		IzdPreProcessingRow maestro = new IzdPreProcessingRow();
		IzdCard mo = new IzdCard();
		mo.setCard("6775733282237579");
		maestro.setCard(mo);
		maestro.setInternalNo(BigDecimal.valueOf(300));
		maestro.setTrType("MeType");
		maestro.setAmount(30L);
		maestro.setCcy("LVL");
		maestro.setTranDateTime(new Date());
		maestro.setAccountNo(BigDecimal.valueOf(3));
		IzdTrtypeName meType = new IzdTrtypeName();
		meType.setName("Maestro type");
		when(cardManager.getIzdTrTypeNameByType(eq(new IzdTrtypeNameId("MeType", "2", "00")))).thenReturn(meType);
		IzdAccount meAccount = new IzdAccount();
		meAccount.setIzdAccParam(new IzdAccParam());
		meAccount.getIzdAccParam().setUfield5("meField");
		when(cardManager.getIzdAccountByAccountNr(eq(BigDecimal.valueOf(3)))).thenReturn(meAccount);
		when(cardManager.getIzdPreProcessingRowsComissionsLimit(2)).thenReturn(Arrays.asList(visa, master, maestro));
		
		handler.handle(request);
		
		Element visaInfo = (Element) handler.compileResponse().getElement().element("list").elements().get(0);
		assertEquals("100", visaInfo.element("id").getTextTrim());
		assertEquals("4775733282237579", visaInfo.element("card").getTextTrim());
		assertEquals("VISA", visaInfo.element("card-type").getTextTrim());
		assertEquals("VType", visaInfo.element("tr-type").getTextTrim());
		assertEquals("Visa type", visaInfo.element("tr-type-name").getTextTrim());
		assertEquals("10", visaInfo.element("amount").getTextTrim());
		assertEquals("EUR", visaInfo.element("ccy").getTextTrim());
		assertEquals(date, visaInfo.element("date").getTextTrim());
		assertEquals("1", visaInfo.element("account-tr").getTextTrim());
		assertEquals("vField", visaInfo.element("account-pl").getTextTrim());
		
		Element masterInfo = (Element) handler.compileResponse().getElement().element("list").elements().get(1);
		assertEquals("200", masterInfo.element("id").getTextTrim());
		assertEquals("5775733282237579", masterInfo.element("card").getTextTrim());
		assertEquals("MasterCard", masterInfo.element("card-type").getTextTrim());
		assertEquals("MType", masterInfo.element("tr-type").getTextTrim());
		assertEquals("Master type", masterInfo.element("tr-type-name").getTextTrim());
		assertEquals("20", masterInfo.element("amount").getTextTrim());
		assertEquals("USD", masterInfo.element("ccy").getTextTrim());
		assertEquals(date, masterInfo.element("date").getTextTrim());
		assertEquals("2", masterInfo.element("account-tr").getTextTrim());
		assertEquals("msField", masterInfo.element("account-pl").getTextTrim());

		Element maestroInfo = (Element) handler.compileResponse().getElement().element("list").elements().get(2);
		assertEquals("300", maestroInfo.element("id").getTextTrim());
		assertEquals("6775733282237579", maestroInfo.element("card").getTextTrim());
		assertEquals("Maestro", maestroInfo.element("card-type").getTextTrim());
		assertEquals("MeType", maestroInfo.element("tr-type").getTextTrim());
		assertEquals("Maestro type", maestroInfo.element("tr-type-name").getTextTrim());
		assertEquals("30", maestroInfo.element("amount").getTextTrim());
		assertEquals("LVL", maestroInfo.element("ccy").getTextTrim());
		assertEquals(date, maestroInfo.element("date").getTextTrim());
		assertEquals("3", maestroInfo.element("account-tr").getTextTrim());
		assertEquals("meField", maestroInfo.element("account-pl").getTextTrim());
	}
	
	@Test 
	public void handle_delete() throws RequestPreparationException, RequestFormatException,
			RequestProcessingException, CMSSoapAPIException {
		SubRequest request = getSubrequest("delete-pre-proc-payment");
		addElementOnRootElement(request, "action", "delete");
		
		// There is no IDs
		checkRequestFormatException(handler, request, "No payment ID provided!");
		
		addElementOnRootElement(request, "id", "x");
		
		// Incorrect ID
		checkRequestFormatException(handler, request, "Incorrect payment ID provided!");
		
		request = getSubrequest("delete-pre-proc-payment");
		addElementOnRootElement(request, "action", "delete");
		addElementOnRootElement(request, "id", "1");
		addElementOnRootElement(request, "id", "2");


		IzdPreProcessingRow row1 = new IzdPreProcessingRow();
		row1.setAmount(100L);
		row1.setCcy("EUR");
		when(cardManager.getIzdPreProcessingRowByInternalNo(eq(BigDecimal.valueOf(1)))).thenReturn(row1);

		IzdPreProcessingRow row2 = new IzdPreProcessingRow();
		row2.setAmount(100L);
		row2.setCcy("EUR");
		when(cardManager.getIzdPreProcessingRowByInternalNo(eq(BigDecimal.valueOf(2)))).thenReturn(row2);

		handler.handle(request);
		
		assertEquals("1", ((Element) handler.compileResponse().getElement().elements().get(0)).element("id").getText());
		assertEquals("2", ((Element) handler.compileResponse().getElement().elements().get(1)).element("id").getText());
		
		verify(cmsSoapAPIWrapper, times(2)).cancelTransaction(any(BigDecimal.class),
				any(BigDecimal.class), anyString(), anyString(), eq(new BigDecimal(100L))
		);
	}
}
