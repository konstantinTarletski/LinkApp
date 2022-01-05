package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import javax.naming.NamingException;

import lv.bank.cards.core.entity.linkApp.PcdMerchantPar;
import lv.nordlb.cards.pcdabaNG.interfaces.MerchantManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test Merchant information handler
 * @author saldabols
 */
public class MerchantParamsTest extends JUnitHandlerTestBase {

	private MerchantParams handler;
	
	private MerchantManager merchantManager = mock(MerchantManager.class);
	
	@Before
	public void setUpTest() throws RequestPreparationException, NamingException{
		when(context.lookup(MerchantManager.JNDI_NAME)).thenReturn(merchantManager);
		handler = new MerchantParams();
	}
	
	@Test 
	public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		SubRequest request = getSubrequest("disp-merchantpar");
		
		// There is no client id
		checkRequestFormatException(handler, request, "Specify cif number");
		
		addElementOnRootElement(request, "cif", "T123123");
		
		PcdMerchantPar m1 = new PcdMerchantPar();
		m1.setChr1("m1");
		PcdMerchantPar m2 = new PcdMerchantPar();
		m2.setChr1("m2");
		when(merchantManager.GetDisMerchantPar("T123123")).thenReturn(Arrays.asList(m1, m2));
		
		handler.handle(request);
		
		assertEquals("m1", ((Element) handler.compileResponse().getElement().elements().get(0)).element("Chr1").getTextTrim());
		assertEquals("m2", ((Element) handler.compileResponse().getElement().elements().get(1)).element("Chr1").getTextTrim());
	}

}
