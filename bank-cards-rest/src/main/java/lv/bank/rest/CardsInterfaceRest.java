package lv.bank.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import lv.bank.rest.dto.CardAutoRenewalDO;
import lv.bank.rest.dto.CardBalanceListDO;
import lv.bank.rest.dto.CardCreditDetailsDO;
import lv.bank.rest.dto.CardCvcDO;
import lv.bank.rest.dto.CardDeliveryDetailsWrapperDO;
import lv.bank.rest.dto.CardDetailsDO;
import lv.bank.rest.dto.CardLimitListDO;
import lv.bank.rest.dto.CardLimitsDO;
import lv.bank.rest.dto.CardListDO;
import lv.bank.rest.dto.CardNumberDO;
import lv.bank.rest.dto.CardNumberDetailsDO;
import lv.bank.rest.dto.CardStatusDO;
import lv.bank.rest.dto.CardTokenDO;
import lv.bank.rest.dto.CardTokensListDO;
import lv.bank.rest.dto.ContactlessStatusDO;
import lv.bank.rest.dto.EcommStatusDO;
import lv.bank.rest.dto.ErrorResponse;
import lv.bank.rest.dto.ReservationListDO;
import lv.bank.rest.dto.VisaTokenServiceOtpDO;
import lv.bank.rest.dto.VisaTokenServicePhoneDO;
import lv.bank.rest.exception.BusinessException;
import lv.bank.rest.exception.JsonErrorCode;
import lv.bank.rest.filter.RequestParamsUtility;
import lv.bank.rest.service.ReservationService;
import org.apache.commons.lang.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Stateless
@Path("v1")
@Api
@Slf4j
public class CardsInterfaceRest {

    @Inject
    private CardsService cardsService;

    @Inject
    private ReservationService reservationService;

    @CountryCheck
    @GET
    @Path("/customers/{customerId}/cards")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Read customers cards", tags = "CardsRestInterface", notes = "Read customer cards by customer id or personal code")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of customer cards", response = CardListDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Cards not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response readCustomerCards(@ApiParam(required = true, value = "Customer ID or personal ID") @PathParam("customerId") String customerId) {
        final CardListDO list = cardsService.readCustomerCards(customerId, RequestParamsUtility.getCountry());
        return Response.ok(list).build();
    }

    @CountryCheck
    @GET
    @Path("/cards")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Read header customers cards", tags = "CardsRestInterface", notes = "Read customer cards by customer id or personal code taken from header")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of header customer cards", response = CardListDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Cards not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String"),
            @ApiImplicitParam(name = "PersonalId", value = "Customer Personal ID", required = true, paramType = "Header", dataType = "String"),
            @ApiImplicitParam(name = "Cif", value = "Customer Cif", required = true, paramType = "Header", dataType = "String")
    })
    public Response readCustomerCards() throws BusinessException {
        CardListDO list = new CardListDO();
        boolean foundParam = false;
        String customerId = RequestParamsUtility.getPersonalId();
        String country = RequestParamsUtility.getCountry();
        if (customerId != null && !customerId.isEmpty()) {
            foundParam = true;
            list = cardsService.readCustomerCards(customerId, country);
        }
        if (list.getCards().size() == 0) {
            customerId = RequestParamsUtility.getCif();
            if (customerId != null && !customerId.isEmpty()) {
                foundParam = true;
                list = cardsService.readCustomerCards(customerId, country);
            }
        }
        if (!foundParam) {
            throw BusinessException.create(JsonErrorCode.BAD_REQUEST, "readCustomerCards", "Missing personalId and/or cif param(s) in request header.");
        }
        return Response.ok(list).build();
    }

    @CountryCheck
    @GET
    @Path("/cards/{cardNumber}/credit")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Get card credit details", tags = "CardsRestInterface", notes = "Get card credit details by card id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cards credit details", response = CardCreditDetailsDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response getCardCreditDetails(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber) throws BusinessException {
        CardCreditDetailsDO details = cardsService.getCardCreditDetails(cardNumber);
        return Response.ok(details).build();
    }

    @CountryCheck
    @GET
    @Path("/cards/{cardNumber}")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Get cards details", tags = "CardsRestInterface", notes = "Get cards details by card id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cards details", response = CardDetailsDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String"),
            @ApiImplicitParam(name = "PersonalId", value = "Customer Personal ID", required = true, paramType = "Header", dataType = "String"),
    })
    public Response readCardDetails(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber) throws BusinessException {
        log.info("BEGIN /cards/{cardNumber}");
        CardDetailsDO details = cardsService.getCardDetails(RequestParamsUtility.getPersonalId(), cardNumber, RequestParamsUtility.getCountry());
        return Response.ok(details).build();
    }

    @CountryCheck
    @GET
    @Path("/cards/{cardNumber}/details")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Get cards number details", tags = "CardsRestInterface", notes = "Get cards number details by card id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cards number details", response = CardNumberDetailsDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response getCardNumberDetails(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber) throws BusinessException {
        CardNumberDetailsDO details = cardsService.getCardNumberDetails(cardNumber);
        return Response.ok(details).build();
    }

    @CountryCheck
    @GET
    @Path("/cards/{cardNumber}/balances")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Get cards balances", tags = "CardsRestInterface", notes = "Get cards balances by card id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cards balances", response = CardBalanceListDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response getCardBalances(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber) throws BusinessException {
        CardBalanceListDO balances = cardsService.getCardBalances(cardNumber);
        return Response.ok(balances).build();
    }

    @CountryCheck
    @GET
    @Path("/cards/{cardNumber}/status")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Get cards status", tags = "CardsRestInterface", notes = "Get cards status by card id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cards status", response = CardStatusDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response getCardStatus(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber) throws BusinessException {
        CardStatusDO status = cardsService.getCardStatusDO(cardNumber);
        return Response.ok(status).build();
    }

    @CountryCheck
    @PUT
    @Path("/cards/{cardNumber}/status")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Change cards status", tags = "CardsRestInterface", notes = "Change cards status by card id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cards status", response = CardStatusDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Data conflict", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response changeCardSatus(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber,
                                    @ApiParam(required = true, value = "Card status") @NotNull CardStatusDO status) throws BusinessException {
        CardStatusDO newStatus = cardsService.changeCardStatus(cardNumber, status);
        return Response.ok(newStatus).build();
    }

    @CountryCheck
    @PUT
    @Path("/cards/{cardNumber}/renewal")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Block or unblock cards auto renewal", tags = "CardsRestInterface", notes = "Block or unblock cards auto renewal by card id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cards auto renewal", response = CardAutoRenewalDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Data conflict", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response blockOrUnblockCardAutoRenewal(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber,
                                                  @ApiParam(required = true, value = "Card auto renewal") @NotNull CardAutoRenewalDO status) throws BusinessException {
        CardAutoRenewalDO newStatus = cardsService.blockOrUnblockCardAutoRenewal(cardNumber, status);
        return Response.ok(newStatus).build();
    }

    @CountryCheck
    @GET
    @Path("/cards/{cardNumber}/delivery")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Get cards delivery details", tags = "CardsRestInterface", notes = "Get cards delivery details by card id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cards delivery details", response = CardDeliveryDetailsWrapperDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response getCardDeliveryDetails(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber) throws BusinessException {
        CardDeliveryDetailsWrapperDO details = cardsService.getCardDeliveryDetails(cardNumber);
        return Response.ok(details).build();
    }

    @CountryCheck
    @PUT
    @Path("/cards/{cardNumber}/delivery")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Update cards delivery details", tags = "CardsRestInterface", notes = "Update cards delivery details by card id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cards delivery details", response = CardDeliveryDetailsWrapperDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Data conflict", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response updateCardDeliveryDetails(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber,
                                              @ApiParam(required = true, value = "Cards delivery details") @NotNull CardDeliveryDetailsWrapperDO newDetails) throws BusinessException {
        CardDeliveryDetailsWrapperDO details = cardsService.updateCardDeliveryDetails(cardNumber, newDetails);
        return Response.ok(details).build();
    }

    @CountryCheck
    @GET
    @Path("/cards/{cardNumber}/cvc")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Get cards cvc", tags = "CardsRestInterface", notes = "Get cards cvc by card id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cards status", response = CardCvcDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response getCardCVC(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber) throws BusinessException {
        CardCvcDO cvc = cardsService.getCardCVC(cardNumber);
        return Response.ok(cvc).build();
    }

    @CountryCheck
    @GET
    @Path("/reservations/{accountNo}")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Get account reservations", tags = "CardsRestInterface", notes = "Get account reservations by core account number")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Reservations", response = ReservationListDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String"),
    })
    public Response getReservations(@ApiParam(required = true, value = "Account number") @PathParam("accountNo") String accountNo,
                                    @ApiParam(value = "Filter by Reservations from date, format 'yyyy-MM-dd'") @QueryParam("fromDate") String fromDate,
                                    @ApiParam(value = "Filter by Reservations to date, format 'yyyy-MM-dd'") @QueryParam("toDate") String toDate,
                                    @ApiParam(value = "Filter by Minimum amount of transaction") @QueryParam("fromAmount") BigDecimal fromAmount,
                                    @ApiParam(value = "Filter by Maximum amount of transaction") @QueryParam("toAmount") BigDecimal toAmount,
                                    @ApiParam(value = "Filter by Shop name") @QueryParam("shopName") String shopName,
                                    @ApiParam(value = "Filter by card Number") @QueryParam("cardNumbers") String cardNumbers) throws BusinessException {

        List<String> cardNumbersList = null;
        if (cardNumbers != null) {
            cardNumbersList = Arrays.asList(cardNumbers.split(","));
        }
        ReservationListDO list = reservationService.getReservations(accountNo, RequestParamsUtility.getCountry(), fromDate, toDate, fromAmount, toAmount, shopName, cardNumbersList);
        return Response.ok(list).build();
    }

    @CountryCheck
    @POST
    @Path("/cards/{cardNumber}/activate")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Activate new card", tags = "CardsRestInterface", notes = "Activate new card after it is delivered to client")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Card is activated"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response activateCard(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber) throws BusinessException {
        cardsService.activateCard(cardNumber);
        return Response.noContent().build();
    }

    @CountryCheck
    @PUT
    @Path("/cards/{cardNumber}/ecomm")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Change card ECOMM status", tags = "CardsRestInterface", notes = "Change card ECOMM status by card number")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ECOMM status changed", response = EcommStatusDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Data conflict", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response changeEcomm(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber,
                                @ApiParam(required = true, value = "ECOMM status") @NotNull EcommStatusDO status) throws BusinessException {
        EcommStatusDO newStatus = cardsService.changeEcomm(cardNumber, status);
        return Response.ok(newStatus).build();
    }

    @CountryCheck
    @PUT
    @Path("/cards/{cardNumber}/contactless")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Change card contactless status", tags = "CardsRestInterface", notes = "Change card contactless status by card number")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Contactless status changed", response = ContactlessStatusDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Data conflict", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response changeContactless(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber,
                                      @ApiParam(required = true, value = "Contactless status") @NotNull ContactlessStatusDO status) throws BusinessException {
        ContactlessStatusDO newStatus = cardsService.changeContactlessStatus(cardNumber, status);
        return Response.ok(newStatus).build();
    }

    @CountryCheck
    @GET
    @Path("/cards-limits")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Get limits by risk level", tags = "CardsRestInterface", notes = "Get limits by risk level")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Limits", response = CardLimitsDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response getLimitsByRiskLevel(@QueryParam("id") String riskLevels) {

        CardLimitListDO limits = new CardLimitListDO();
        for (String riskLevel : riskLevels.split(",")) {
            CardLimitsDO limit = cardsService.getLimitsByRiskLevel(riskLevel);
            limits.getLimits().add(limit);
        }
        return Response.ok(limits).build();
    }

    @CountryCheck
    @GET
    @Path("/cards/{cardNumber}/limits")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Get card limits", tags = "CardsRestInterface", notes = "Get cards limits by card number")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Limits", response = CardLimitsDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response getCardLimits(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber) throws BusinessException {

        CardLimitsDO limits = cardsService.getCardLimits(cardNumber);
        return Response.ok(limits).build();
    }

    @CountryCheck
    @PUT
    @Path("/cards/{cardNumber}/limits")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Set card limits", tags = "CardsRestInterface", notes = "Set card limits by card number")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Limits", response = CardLimitsDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String")
    })
    public Response setCardLimits(@ApiParam(required = true, value = "Card ID") @PathParam("cardNumber") String cardNumber,
                                  @ApiParam(required = true, value = "Card limits") @NotNull CardLimitsDO limits) throws BusinessException {

        CardLimitsDO newLimits = cardsService.setCardLimits(cardNumber, limits);
        return Response.ok(newLimits).build();
    }

    @POST
    @Path("/cards/vts-phone")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get VTS phone number", tags = "CardsRestInterface", notes = "Returns a phone number for delivery of a One Time Passcode used during card provisioning in Visa Token Service")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cardholder phone number", response = VisaTokenServicePhoneDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    public Response getPhoneNumberForVtsOtp(@ApiParam(required = true, value = "Card number") @NotNull CardNumberDO cardNumber) throws BusinessException {
        log.info("BEGIN /cards/vts-phone");
        VisaTokenServicePhoneDO phone = cardsService.getPhoneByCardNumber(cardNumber.getCardNumber());
        return Response.ok(phone).build();
    }

    @POST
    @Path("/cards/vts-otp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Send VTS OTP", tags = "CardsRestInterface", notes = "Sends an SMS to cardholder with a One Time Passcode for card provisioning in Visa Token Service")
    @ApiResponses({
            @ApiResponse(code = 204, message = "One Time Passcode was sent to cardholder"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    public Response sendVtsOtp(@ApiParam(required = true, value = "Card number") @NotNull VisaTokenServiceOtpDO OtpDO) throws BusinessException {
        log.info("BEGIN /cards/vts-otp");
        cardsService.sendOtp(OtpDO);
        return Response.noContent().build();
    }

    @CountryCheck
    @GET
    @Path("/cards/tokens")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Read list of card tokens", tags = "CardsRestInterface", notes = "Get list of card tokens by cardholder personal code or customer CIF taken from header")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of card tokens", response = CardTokensListDO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Country", value = "Customer Country", required = true, paramType = "Header", dataType = "String"),
            @ApiImplicitParam(name = "PersonalId", value = "Customer Personal ID", required = true, paramType = "Header", dataType = "String"),
            @ApiImplicitParam(name = "Cif", value = "Customer Cif", required = true, paramType = "Header", dataType = "String"),
            @ApiImplicitParam(name = "DeviceId", value = "Customer Device Id", required = true, paramType = "Header", dataType = "String"),
            @ApiImplicitParam(name = "WalletId", value = "Customer Wallet Id", required = true, paramType = "Header", dataType = "String")
    })
    public Response getCardTokens() throws BusinessException {
        log.info("BEGIN /cards/tokens");
        CardTokensListDO responseObject = new CardTokensListDO();
        String personalId = RequestParamsUtility.getPersonalId();
        String cif = RequestParamsUtility.getCif();
        String country = RequestParamsUtility.getCountry();
        String deviceId = RequestParamsUtility.getDeviceId();
        String walletId = RequestParamsUtility.getWalletId();

        if (StringUtils.isBlank(personalId) || StringUtils.isBlank(cif)) {
            throw BusinessException.create(JsonErrorCode.BAD_REQUEST, "getCardTokens", "Missing personalId and/or cif param(s) in request header.");
        }

        if (StringUtils.isBlank(deviceId) && StringUtils.isBlank(walletId)) {
            throw BusinessException.create(JsonErrorCode.BAD_REQUEST, "getCardTokens", "Missing deviceId or walletId param in request header.");
        }

        if (StringUtils.isNotBlank(deviceId) && StringUtils.isNotBlank(walletId)) {
            throw BusinessException.create(JsonErrorCode.BAD_REQUEST, "getCardTokens", "Either deviceId (iOS) or walletId (Android) header should be provided in request, not both.");
        }

        List<CardTokenDO> list = cardsService.readCardsTokens(personalId, country, deviceId, walletId);
        if (list.isEmpty() && !StringUtils.isBlank(cif)) {
            list = cardsService.readCardsTokens(cif, country, deviceId, walletId);
        }

        responseObject.getCards().addAll(list);
        return Response.ok(responseObject).build();
    }


}
