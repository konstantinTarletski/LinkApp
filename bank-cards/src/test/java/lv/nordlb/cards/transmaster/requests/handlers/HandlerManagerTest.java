package lv.nordlb.cards.transmaster.requests.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.JUnitHandlerTestBase;

import org.junit.Test;

/**
 * Test class to test HandlerManager methods
 * @author saldabols
 */
public class HandlerManagerTest extends JUnitHandlerTestBase {

	private HandlerManager manager = new HandlerManager();
	
	@Test
	public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
		SubRequest request = getSubrequest("reread-link-app-properties");
		assertEquals("done", manager.handle(request).getElement().getName());
		
		boolean hadError = false;
		try{
			manager.handle(getSubrequest("wrong"));
		} catch (RequestPreparationException e){
			hadError = true;
			assertEquals("Don't know how to handle function wrong", e.getMessage());
		}
		assertTrue(hadError);
	}
}
