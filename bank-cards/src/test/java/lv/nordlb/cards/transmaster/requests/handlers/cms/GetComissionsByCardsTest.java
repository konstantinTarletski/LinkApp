package lv.nordlb.cards.transmaster.requests.handlers.cms;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdPreProcessingRow;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.JUnitHandlerTestBase;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Test class to test comission listing by card handler
 * @author saldabols
 */
public class GetComissionsByCardsTest extends JUnitHandlerTestBase {

	private GetComissionsByCards handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException{
		handler = new GetComissionsByCards();
	}
	
	@Test 
	public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		SubRequest request = getSubrequest("get-comissions");
		String today = new SimpleDateFormat("ddMMyyyy").format(new Date());
		
		// No cards given
		checkRequestFormatException(handler, request, "No card number provided!");
		
		addElementOnRootElement(request, "card", "4775733282237579");
		addElementOnRootElement(request, "card", "5775733282237579");
		
		IzdPreProcessingRow row1 = new IzdPreProcessingRow();
		IzdCard v = new IzdCard();
		v.setCard("4775733282237579");
		row1.setCard(v);
		row1.setCcy("EUR");
		row1.setTrType("t1");
		row1.setTranDateTime(new Date());
		row1.setAmount(100L);
		row1.setInternalNo(BigDecimal.valueOf(10));
		row1.setDealDesc("desc1");
		IzdPreProcessingRow row2 = new IzdPreProcessingRow();
		IzdCard m = new IzdCard();
		m.setCard("5775733282237579");
		row2.setCard(m);
		row2.setCcy("USD");
		row2.setTrType("t2");
		row2.setTranDateTime(new Date());
		row2.setAmount(200L);
		row2.setInternalNo(BigDecimal.valueOf(20));
		row2.setDealDesc("desc2");
		IzdPreProcessingRow row3 = new IzdPreProcessingRow();
		IzdCard mo = new IzdCard();
		mo.setCard("5775733282237579");
		row3.setCard(mo);
		row3.setCcy("USD");
		row3.setTrType("t3");
		row3.setTranDateTime(new Date());
		row3.setAmount(300L);
		row3.setInternalNo(BigDecimal.valueOf(30));
		row3.setDealDesc("desc3");
		IzdAccount account1 = new IzdAccount();
		account1.setEndBal(1000L);
		IzdAccount account2 = new IzdAccount();
		account2.setEndBal(2000L);
		IzdAccount account3 = new IzdAccount();
		account3.setEndBal(3000L);
		when(cardManager.getIzdPreProcessingRowsByCard("4775733282237579")).thenReturn(Arrays.asList(row1));
		when(cardManager.getIzdPreProcessingRowsByCard("5775733282237579")).thenReturn(Arrays.asList(row2, row3));
		when(cardManager.getIzdAccountByAccountNr(any(BigDecimal.class))).thenReturn(account1, account2, account3);
		
		handler.handle(request);
		
		Element rowInfo1 = (Element) handler.compileResponse().getElement().element("get-comissions").elements().get(0);
		assertEquals("4775733282237579", rowInfo1.element("card").getText());
		assertEquals("EUR", rowInfo1.element("ccy").getText());
		assertEquals("t1", rowInfo1.element("tr-type").getText());
		assertEquals(today, rowInfo1.element("date").getText());
		assertEquals("100", rowInfo1.element("amount").getText());
		assertEquals("1000", rowInfo1.element("balance").getText());
		assertEquals("10", rowInfo1.element("id").getText());
		assertEquals("desc1", rowInfo1.element("description").getText());
		
		Element rowInfo2 = (Element) handler.compileResponse().getElement().element("get-comissions").elements().get(1);
		assertEquals("5775733282237579", rowInfo2.element("card").getText());
		assertEquals("USD", rowInfo2.element("ccy").getText());
		assertEquals("t2", rowInfo2.element("tr-type").getText());
		assertEquals(today, rowInfo2.element("date").getText());
		assertEquals("200", rowInfo2.element("amount").getText());
		assertEquals("2000", rowInfo2.element("balance").getText());
		assertEquals("20", rowInfo2.element("id").getText());
		assertEquals("desc2", rowInfo2.element("description").getText());
		
		Element rowInfo3 = (Element) handler.compileResponse().getElement().element("get-comissions").elements().get(2);
		assertEquals("5775733282237579", rowInfo3.element("card").getText());
		assertEquals("USD", rowInfo3.element("ccy").getText());
		assertEquals("t3", rowInfo3.element("tr-type").getText());
		assertEquals(today, rowInfo3.element("date").getText());
		assertEquals("300", rowInfo3.element("amount").getText());
		assertEquals("3000", rowInfo3.element("balance").getText());
		assertEquals("30", rowInfo3.element("id").getText());
		assertEquals("desc3", rowInfo3.element("description").getText());
	}

}
