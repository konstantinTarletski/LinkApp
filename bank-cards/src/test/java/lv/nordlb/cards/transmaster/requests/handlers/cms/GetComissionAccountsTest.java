package lv.nordlb.cards.transmaster.requests.handlers.cms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;

import lv.bank.cards.core.entity.cms.IzdPreProcessingRow;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.JUnitHandlerTestBase;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test comission listing by account handler
 * @author saldabols
 */
public class GetComissionAccountsTest extends JUnitHandlerTestBase {

	private GetComissionAccounts handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException{
		handler = new GetComissionAccounts();
	}
	
	@Test 
	public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		SubRequest request = getSubrequest("get-comission-accounts");
		
		addElementOnRootElement(request, "limit", "5");
		
		IzdPreProcessingRow row1 = new IzdPreProcessingRow();
		row1.setAccountNo(BigDecimal.valueOf(1));
		IzdPreProcessingRow row2 = new IzdPreProcessingRow();
		row2.setAccountNo(BigDecimal.valueOf(2));
		IzdPreProcessingRow row3 = new IzdPreProcessingRow();
		row3.setAccountNo(BigDecimal.valueOf(3));
		
		when(cardManager.getIzdPreProcessingRowsByAccLimit(5)).thenReturn(Arrays.asList(row1, row2, row3));
		
		handler.handle(request);
		
		assertEquals("1", ((Element)handler.compileResponse().getElement().element("get-comission-accounts").elements().get(0)).getText());
		assertEquals("2", ((Element)handler.compileResponse().getElement().element("get-comission-accounts").elements().get(1)).getText());
		assertEquals("3", ((Element)handler.compileResponse().getElement().element("get-comission-accounts").elements().get(2)).getText());
	}
}
