package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCardsPinBlocks;
import lv.bank.cards.core.entity.cms.IzdCardsPinBlocksPK;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.OrderPinEnvelopeWork;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateDBWork;
import lv.bank.cards.core.utils.DataIntegrityException;

import org.hibernate.jdbc.ReturningWork;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test PIN reminder ordering handler
 * @author saldabols
 */
public class OrderPinReminderHandlerTest extends JUnitHandlerTestBase {

	private OrderPinReminderHandler handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException{
		handler = new OrderPinReminderHandler();
	}
	
	@SuppressWarnings("unchecked")
	@Test 
	public void handle_validation() throws RequestPreparationException, RequestFormatException, 
			RequestProcessingException, DataIntegrityException{
		SubRequest request = getSubrequest("order-pin-reminder");
		
		// There is no card
		checkRequestFormatException(handler, request, "Please provide valid card number");
		
		addElementOnRootElement(request, "card", "4775733282237579");
		
		// There is no order number
		checkRequestFormatException(handler, request, "Order id must be specified");
		
		addElementOnRootElement(request, "order-id", "47");
		
		// Cannot find card in PCD DB
		checkRequestProcessingException(handler, request, "Card with given number couldn't be found (pcd)");
		
		PcdCard pcdCard = new PcdCard();
		when(pcdabaNGManager.getCardByCardNumber("4775733282237579")).thenReturn(pcdCard);
		
		// Cannot find card in CMS DB
		checkRequestProcessingException(handler, request, "Card with given number couldn't be found (izd)");
		
		IzdCard izdCard = new IzdCard();
		izdCard.setRenew("7");
		when(cardManager.getIzdCardByCardNumber("4775733282237579")).thenReturn(izdCard);
		
		// PIN reminder is already ordered
		checkRequestProcessingException(handler, request, "PIN reminder is already ordered");
		izdCard.setRenew("G");
		pcdCard.setUAField2("REQUESTED");
		checkRequestProcessingException(handler, request, "PIN reminder is already ordered");

		// PIN is already available to be received
		pcdCard.setUAField2("AVAILABLE");
		checkRequestProcessingException(handler, request, "PIN is already available to be received");

		pcdCard.setUAField2(null);
		izdCard.setRenew("5");
		
		// Cannot order PIN reminder because wrong status
		checkRequestProcessingException(handler, request, "Card status 5 is not in (J,D,R,E,G) (izd)");
		
		izdCard.setRenew("J");
		izdCard.setIzdCardsPinBlocks(new HashSet<IzdCardsPinBlocks>());
		
		// Cannot order PIN reminder because wrong status
		checkRequestProcessingException(handler, request, "Card missing PIN Block");
		
		izdCard.setSequenceNr("s1");
		izdCard.setIzdCardsPinBlocks(new HashSet<IzdCardsPinBlocks>(
				Arrays.asList(new IzdCardsPinBlocks(new IzdCardsPinBlocksPK("4775733282237579", "s1"), "PinBlock"))));
		addElementOnRootElement(request, "check", "TRUE");
		
		izdCard.setStatus1("2");
		// Cannot order PIN reminder because card is hard blocked
		checkRequestProcessingException(handler, request, "Card is hard blocked");
				
		izdCard.setStatus1("0");
		
		handler.handle(request);
		assertEquals("success", handler.compileResponse().getElement().element("order-pin-reminder").element("check").getTextTrim());
		verify(pcdabaNGManager, never()).getNextPcdPinIDWithAuthentificationCode(anyString());
		verify(pcdabaNGManager, never()).saveOrUpdate(anyObject());
		verify(cardManager, never()).doWork(any(ReturningWork.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test 
	public void handle_orderReminder() throws RequestPreparationException, RequestFormatException, 
			RequestProcessingException, DataIntegrityException{
		SubRequest request = getSubrequest("order-pin-reminder");
		addElementOnRootElement(request, "card", "4775733282237579");
		addElementOnRootElement(request, "order-id", "47");
		PcdCard pcdCard = new PcdCard();
		pcdCard.setBankC("23");
		pcdCard.setGroupc("50");
		when(pcdabaNGManager.getCardByCardNumber("4775733282237579")).thenReturn(pcdCard);
		IzdCard izdCard = new IzdCard();
		izdCard.setRenew("J");
		izdCard.setSequenceNr("s1");
		izdCard.setIzdCardsPinBlocks(new HashSet<IzdCardsPinBlocks>(
				Arrays.asList(new IzdCardsPinBlocks(new IzdCardsPinBlocksPK("4775733282237579", "s1"), "PinBlock"))));
		when(cardManager.getIzdCardByCardNumber("4775733282237579")).thenReturn(izdCard);

		when(pcdabaNGManager.getNextPcdPinIDWithAuthentificationCode("47")).thenReturn("12332112");
		when(cardManager.doWork(any(UpdateDBWork.class))).thenReturn("success");
		when(cardManager.doWork(any(OrderPinEnvelopeWork.class))).thenReturn("success");
		
		handler.handle(request);
		
		verify(pcdabaNGManager).getNextPcdPinIDWithAuthentificationCode("47");
		verify(pcdabaNGManager).saveOrUpdate(pcdCard);
		verify(cardManager, times(2)).doWork(any(ReturningWork.class));
	}

}
