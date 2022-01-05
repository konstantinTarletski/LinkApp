package lv.bank.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.ws.rs.core.Response;

import lv.bank.rest.dto.*;
import lv.bank.rest.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import lv.bank.rest.exception.BusinessException;

@RunWith(MockitoJUnitRunner.class)
public class CardsInterfaceRestTest {

	@InjectMocks
	private CardsInterfaceRest rest;

	@Mock
	private CardsService service;

	@Mock
	private ReservationService reservationService;

	@Test
	public void readCustomerCards_Positive() {
		String customer = "cust";
		CardListDO list = new CardListDO();

		when(service.readCustomerCards(customer, null)).thenReturn(list);

		Response response = rest.readCustomerCards(customer);

		assertEquals(200, response.getStatus());
		assertEquals(list, response.getEntity());
	}

	@Test
	public void readCardDetails_Positive() throws BusinessException {
		String card = "card";
		CardDetailsDO details = new CardDetailsDO();

		when(service.getCardDetails(null,card,null)).thenReturn(details);

		Response response = rest.readCardDetails(card);

		assertEquals(200, response.getStatus());
		assertEquals(details, response.getEntity());
	}

	@Test
	public void getCardBalances_Positive() throws BusinessException {
		String card = "card";
		CardBalanceListDO list = new CardBalanceListDO();

		when(service.getCardBalances(card)).thenReturn(list);

		Response response = rest.getCardBalances(card);

		assertEquals(200, response.getStatus());
		assertEquals(list, response.getEntity());
	}

	@Test
	public void getCardStatus_Positive() throws BusinessException {
		String card = "card";
		CardStatusDO status = new CardStatusDO();

		when(service.getCardStatusDO(card)).thenReturn(status);

		Response response = rest.getCardStatus(card);

		assertEquals(200, response.getStatus());
		assertEquals(status, response.getEntity());
	}

	@Test
	public void changeCardStatus_Positive() throws BusinessException {
		String card = "card";
		CardStatusDO status = new CardStatusDO();
		CardStatusDO newStatus = new CardStatusDO();

		when(service.changeCardStatus(card, newStatus)).thenReturn(status);

		Response response = rest.changeCardSatus(card, newStatus);

		assertEquals(200, response.getStatus());
		assertEquals(status, response.getEntity());
	}

	@Test
	public void changeCardAutoRenewal_Positive() throws BusinessException {
		String card = "card";
		CardAutoRenewalDO status = new CardAutoRenewalDO();
		CardAutoRenewalDO newStatus = new CardAutoRenewalDO();

		when(service.blockOrUnblockCardAutoRenewal(card, newStatus)).thenReturn(status);

		Response response = rest.blockOrUnblockCardAutoRenewal(card, newStatus);

		assertEquals(200, response.getStatus());
		assertEquals(status, response.getEntity());
	}

	@Test
	public void getCardNumberDetails_Positive() throws BusinessException {
		String card = "card";
		CardNumberDetailsDO details = new CardNumberDetailsDO();

		when(service.getCardNumberDetails(card)).thenReturn(details);

		Response response = rest.getCardNumberDetails(card);

		assertEquals(200, response.getStatus());
		assertEquals(details, response.getEntity());
	}

	@Test
	public void getCardCreditDetails_Positive() throws BusinessException {
		String card = "card";
		CardCreditDetailsDO details = new CardCreditDetailsDO();

		when(service.getCardCreditDetails(card)).thenReturn(details);

		Response response = rest.getCardCreditDetails(card);

		assertEquals(200, response.getStatus());
		assertEquals(details, response.getEntity());
	}

	@Test
	public void getCardDeliveryDetails_Positive() throws BusinessException {
		String card = "card";
		CardDeliveryDetailsWrapperDO details = new CardDeliveryDetailsWrapperDO();

		when(service.getCardDeliveryDetails(card)).thenReturn(details);

		Response response = rest.getCardDeliveryDetails(card);

		assertEquals(200, response.getStatus());
		assertEquals(details, response.getEntity());
	}

	@Test
	public void updateCardDeliveryDetails_Positive() throws BusinessException {
		String card = "card";
		CardDeliveryDetailsWrapperDO details = new CardDeliveryDetailsWrapperDO();

		when(service.updateCardDeliveryDetails(card, details)).thenReturn(details);

		Response response = rest.updateCardDeliveryDetails(card, details);

		assertEquals(200, response.getStatus());
		assertEquals(details, response.getEntity());
	}

	@Test
	public void getCardCVC_Positive() throws BusinessException {
		String card = "card";
		CardCvcDO cvc = new CardCvcDO();

		when(service.getCardCVC(card)).thenReturn(cvc);

		Response response = rest.getCardCVC(card);

		assertEquals(200, response.getStatus());
		assertEquals(cvc, response.getEntity());
	}

	@Test
	public void getReservations_Positive() throws BusinessException {
		String account = "account";
		String fromDate = "2018-10-01";
		String toDate = "2018-10-17";
		BigDecimal fromAmount = BigDecimal.valueOf(10);
		BigDecimal toAmount = BigDecimal.valueOf(100);
		String shopName = "shop";
		String cardNumbers = "4921754900973448,4921754900973447,492175490097344";

		ReservationListDO reservations = new ReservationListDO();

		when(reservationService.getReservations(account, null, fromDate, toDate, fromAmount, toAmount, shopName, Arrays.asList(cardNumbers.split(",")))).thenReturn(reservations);

		Response response = rest.getReservations(account, fromDate, toDate, fromAmount, toAmount, shopName, cardNumbers);

		assertEquals(200, response.getStatus());
		assertEquals(reservations, response.getEntity());
	}

	@Test
	public void activateCard_Positive() throws BusinessException {
		String card = "card";

		Response response = rest.activateCard(card);

		assertEquals(204, response.getStatus());
		verify(service).activateCard(card);
	}

	@Test
	public void changeEcomm_Positive() throws BusinessException {
		String card = "card";
		EcommStatusDO status = new EcommStatusDO();
		EcommStatusDO newStatus = new EcommStatusDO();

		when(service.changeEcomm(card, newStatus)).thenReturn(status);

		Response response = rest.changeEcomm(card, newStatus);

		assertEquals(200, response.getStatus());
		assertEquals(status, response.getEntity());
	}

	@Test
	public void changeContactless_Positive() throws BusinessException {
		String card = "card";
		ContactlessStatusDO status = new ContactlessStatusDO();
		ContactlessStatusDO newStatus = new ContactlessStatusDO();

		when(service.changeContactlessStatus(card, newStatus)).thenReturn(status);

		Response response = rest.changeContactless(card, newStatus);

		assertEquals(200, response.getStatus());
		assertEquals(status, response.getEntity());
	}

	@Test
	public void getCardLimits_Positive() throws BusinessException {
		String card = "card";
		CardLimitsDO limit = new CardLimitsDO();

		when(service.getCardLimits(card)).thenReturn(limit);

		Response response = rest.getCardLimits(card);

		assertEquals(200, response.getStatus());
		assertEquals(limit, response.getEntity());
	}

	@Test
	public void setCardLimits_Positive() throws BusinessException {
		String card = "card";
		CardLimitsDO limit = CardLimitsDO.builder().id("AQ").build();

		when(service.setCardLimits(card, limit)).thenReturn(limit);

		Response response = rest.setCardLimits(card, limit);

		assertEquals(200, response.getStatus());
		assertEquals(limit, response.getEntity());
	}

	@Test
	public void getPhoneNumberForVtsOtp_Positive() throws BusinessException {
		String card = "card";
		CardNumberDO cardNumber = new CardNumberDO();
		cardNumber.setCardNumber(card);
		VisaTokenServicePhoneDO phone = new VisaTokenServicePhoneDO();
		phone.setPhone("+371 20000000");

		when(service.getPhoneByCardNumber(card)).thenReturn(phone);

		Response response = rest.getPhoneNumberForVtsOtp(cardNumber);

		assertEquals(200, response.getStatus());
		assertEquals(phone, response.getEntity());
	}

	@Test
	public void sendVtsOtp_Positive() throws BusinessException {
		String card = "card";
		VisaTokenServicePhoneDO phone = new VisaTokenServicePhoneDO();
		phone.setPhone("+371 20000000");

		VisaTokenServiceOtpDO otp = new VisaTokenServiceOtpDO();
		otp.setOtp("12345");
		otp.setCardNumber(card);

		when(service.getPhoneByCardNumber(card)).thenReturn(phone);
		when(service.sendOtp(otp)).thenReturn(otp);

		Response response = rest.sendVtsOtp(otp);

		assertEquals(204, response.getStatus());
	}
}
