package lv.nordlb.cards.transmaster.requests.handlers.cms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import lv.bank.cards.core.entity.linkApp.PcdAgreement;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdRepLang;
import lv.bank.cards.core.entity.linkApp.PcdRepLangPK;
import lv.bank.cards.core.entity.cms.IzdAgreement;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.JUnitHandlerTestBase;
import lv.bank.cards.core.utils.DataIntegrityException;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test SMS destination handler
 * @author saldabols
 */
public class ManageSMSDestinationTest extends JUnitHandlerTestBase {

	private ManageSMSDestination handler;

	@Before
	public void setUpTest() throws RequestPreparationException{
		handler = new ManageSMSDestination();
	}

	@Test
	public void handle() throws RequestPreparationException, RequestFormatException,
			RequestProcessingException, DataIntegrityException{
		SubRequest request = getSubrequest("manage-sms");

		// There is no card number
		checkRequestFormatException(handler, request, "Please provide valid card number");

		addElementOnRootElement(request, "card", "4775733282237579");
		addElementOnRootElement(request, "template", "a");
		addElementOnRootElement(request, "mobile", "23423423");
		addElementOnRootElement(request, "lang-code", "1");

		// Cannot find card in PCD DB 
		checkRequestProcessingException(handler, request, "Card with given number couldn't be found (pcd)");

		PcdCard card = new PcdCard();
		card.setPcdAgreement(new PcdAgreement());
		when(pcdabaNGManager.getCardByCardNumber("4775733282237579")).thenReturn(card);

		// Cannot find card in CMS DB
		checkRequestProcessingException(handler, request, "Card with given number couldn't be found (izd)");

		IzdCard izdCard = new IzdCard();
		izdCard.setIzdAgreement(new IzdAgreement());
		when(cardManager.getIzdCardByCardNumber("4775733282237579")).thenReturn(izdCard);
		PcdRepLang lang = new PcdRepLang();
		lang.setName("Latviešu");
		lang.setComp_id(new PcdRepLangPK("1", "23"));
		when(pcdabaNGManager.getRepLangByLangCode("1")).thenReturn(lang);

		handler.handle(request);

		assertEquals("a:23423423", card.getUField7());
		assertEquals("a:23423423", izdCard.getUField7());
		assertEquals("1", card.getPcdAgreement().getRepLang());
		assertEquals(lang, card.getPcdAgreement().getPcdRepLang());

		Element info = handler.compileResponse().getElement().element("card-sms-destination");
		assertEquals("a", info.element("template").getText());
		assertEquals("23423423", info.element("mobile").getText());
		assertEquals("4775733282237579", info.element("card").getText());
		assertEquals("Latviešu", info.element("lang-name").getText());
		assertEquals("1", info.element("lang-code").getText());

		verify(pcdabaNGManager, times(2)).saveOrUpdate(any(Object.class));
		verify(cardManager).saveOrUpdate(izdCard);
	}

}
