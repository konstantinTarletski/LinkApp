package lv.nordlb.cards.transmaster.requests.handlers.rtps;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.naming.NamingException;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCardsAddFields;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.service.CardService;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.JUnitHandlerTestBase;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.core.utils.DataIntegrityException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test Remove card from RMS stoplist handler
 * @author saldabols
 */
public class RemoveCardFromRMSTest extends JUnitHandlerTestBase {

	protected CardService helper = mock(CardService.class);
	protected RemoveCardFromRMS handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException, NamingException{
		handler = spy(new RemoveCardFromRMS());
		handler.mainWrapper = rtpsCallApiWraper;
		handler.helper = helper;
	}
	
	@Test 
	public void handle() throws RequestPreparationException, RequestFormatException, 
			RequestProcessingException, DataIntegrityException, RTPSCallAPIException{
		SubRequest request = getSubrequest("remove-card-from-rms");
		
		// There is no card number
		checkRequestFormatException(handler, request, "Please provide valid card number");
		
		addElementOnRootElement(request, "card", "4775733282237579");
		
		// Cannot get centre id
		checkRequestProcessingException(handler, request, "Card with given number couldn't be found");
		
		addElementOnRootElement(request, "centre-id", "42800202350");
		addElementOnRootElement(request, "priority", "1");
		
		// Wrong priority
		checkRequestProcessingException(handler, request, "Incorrect priority value");
		
		request = getSubrequest("remove-card-from-rms");
		addElementOnRootElement(request, "card", "4775733282237579");
		addElementOnRootElement(request, "centre-id", "42800202350");
		addElementOnRootElement(request, "priority", "-1");
		addElementOnRootElement(request, "rule-expr", "rule");
		addElementOnRootElement(request, "action-code", "aCode");
		addElementOnRootElement(request, "description", "desc");
		addAttributeOnRootElement(request, "template", "deliveryTimeBlock");
		
		// PIN status handling
		PcdCard card = new PcdCard();
		when(pcdabaNGManager.getCardByCardNumber("4775733282237579")).thenReturn(card);
		IzdCard izdCard = new IzdCard();
		izdCard.setIzdCardsAddFields(new IzdCardsAddFields());
		when(cardManager.getIzdCardByCardNumber("4775733282237579")).thenReturn(izdCard);
		
		handler.handle(request);
		
		assertEquals("4775733282237579", handler.compileResponse().getElement().element("remove-card-from-rms").element("removed").getText());
		assertEquals("DELIVERED", handler.compileResponse().getElement().element("remove-card-from-rms").element("pin-delivery-status").getText());
		
		verify(rtpsCallApiWraper).RemoveCardFromRMSStop("42800202350", "4775733282237579", "rule", -1, "aCode", "desc");
	}
}
