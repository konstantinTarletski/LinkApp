package lv.bank.cards.soap.handlers;

import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.handlers.SonicNotificationHandler.RecipientInfoHolder;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class SonicNotificationHandlerTest extends JUnitTestBase {

	private SonicNotificationHandler handler;
	
	@Before
	public void setUpTest() throws RequestPreparationException, RequestProcessingException {
		handler = spy(new SonicNotificationHandler());
		doNothing().when(handler).sendSonicNotifications(anyString(), anyListOf(RecipientInfoHolder.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test 
	public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException{
		SubRequest request = getSubrequest("sonic-notification");
		
		// There is no card number
		checkRequestFormatException(handler, request, "Missing notification code");
		
		addElementOnRootElement(request, "notification-code", "4775");
		
		// Missing Recipient
		checkRequestFormatException(handler, request, "Missing recipients");
		
		addElementOnRootElement(request, "recipient", "recipient");
		
		// Must specify recipient address and address type
		checkRequestFormatException(handler, request, "Address or address type, address=null address type=null");
		
		
		request = getSubrequest("sonic-notification");
		addElementOnRootElement(request, "notification-code", "4775");
		Element recipient1 = DocumentHelper.createElement("recipient");
		Element address1 = DocumentHelper.createElement("address");
		address1.setText("aa@aa.aa");
		recipient1.add(address1);
		Element addressType1 = DocumentHelper.createElement("address-type");
		addressType1.setText("email");
		recipient1.add(addressType1);
		Element addressParam11 = DocumentHelper.createElement("param");
		addressParam11.addAttribute("code", "%a");
		addressParam11.setText("p11");
		recipient1.add(addressParam11);
		Element addressParam12 = DocumentHelper.createElement("param");
		addressParam12.setText("p12");
		recipient1.add(addressParam12);
		request.getReq().getRootElement().add(recipient1);
		
		Element recipient2 = DocumentHelper.createElement("recipient");
		Element address2 = DocumentHelper.createElement("address");
		address2.setText("22332233");
		recipient2.add(address2);
		Element addressType2 = DocumentHelper.createElement("address-type");
		addressType2.setText("phone");
		recipient2.add(addressType2);
		Element addressParam21 = DocumentHelper.createElement("param");
		addressParam21.addAttribute("code", "%2");
		addressParam21.setText("p21");
		recipient2.add(addressParam21);
		Element addressParam22 = DocumentHelper.createElement("param");
		addressParam22.setText("p22");
		addressParam22.addAttribute("code", "%3");
		recipient2.add(addressParam22);
		request.getReq().getRootElement().add(recipient2);
		
		handler.handle(request);
		
		assertEquals("Done", handler.compileResponse().getElement().element("sonic-notification").getText());
		
		Class<List<RecipientInfoHolder>> listClass = (Class<List<RecipientInfoHolder>>)(Class)List.class;
		ArgumentCaptor<List<RecipientInfoHolder>> listCapture = ArgumentCaptor.forClass(listClass);
		verify(handler).sendSonicNotifications(eq("4775"), listCapture.capture());
		
		List<RecipientInfoHolder> list = listCapture.getValue();
		assertEquals("aa@aa.aa", list.get(0).getAddress());
		assertEquals("email", list.get(0).getAddressType());
		assertEquals("p11", list.get(0).getParams().get(0).getParam());
		assertEquals("%a", list.get(0).getParams().get(0).getParamType());
		assertEquals("p12", list.get(0).getParams().get(1).getParam());
		assertEquals("%2", list.get(0).getParams().get(1).getParamType());
		assertEquals("22332233", list.get(1).getAddress());
		assertEquals("phone", list.get(1).getAddressType());
		assertEquals("p21", list.get(1).getParams().get(0).getParam());
		assertEquals("%2", list.get(1).getParams().get(0).getParamType());
		assertEquals("p22", list.get(1).getParams().get(1).getParam());
		assertEquals("%3", list.get(1).getParams().get(1).getParamType());
	}

}
