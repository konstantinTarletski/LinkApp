package lv.bank.cards.soap.handlers.lt;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.IzdCardTokensDAO;
import lv.bank.cards.core.cms.impl.IzdCardTokensDAOHibernate;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.rtps.dao.StipRmsStoplistDAO;
import lv.bank.cards.core.rtps.impl.StipRmsStoplistDAOHibernate;
import lv.bank.cards.core.utils.lt.DeliveryDetailsHelper;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.WalletTokenService;
import lv.bank.cards.soap.service.dto.TokenWalletDo;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class CardOverview extends SubRequestHandler {

    public static final String EXPIRY_DATE_FORMAT = "yyMM";

    protected IzdCardTokensDAO izdCardTokensDAO;
    protected CardsDAO cardsDAO;
    protected StipRmsStoplistDAO stipRmsStoplistDAO;
    protected WalletTokenService walletTokenService;

    public CardOverview() {
        super();
        izdCardTokensDAO = new IzdCardTokensDAOHibernate();
        cardsDAO = new CardsDAOHibernate();
        stipRmsStoplistDAO = new StipRmsStoplistDAOHibernate();
        walletTokenService = new WalletTokenService();
    }

    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String client = getStringFromNode("/do/client");
        String accountNumber = getStringFromNode("/do/account-number");
        String cardStatus = getCardStatusSearchString(getStringFromNode("/do/card-status"));
        String deliveryBlock = getStringFromNode("/do/delivery-block");
        String cardtype = getStringFromNode("/do/card-type");
        String holderName = getStringFromNode("/do/holder-name");
        String lastDigits = getStringFromNode("/do/last-digits");
        String fromRecordString = getStringFromNode("/do/from-record");
        String numberOfRecordsString = getStringFromNode("/do/number-of-records");
        String deviceId = getStringFromNode("/do/device-id");
        String walletId = getStringFromNode("/do/wallet-id");

        if (StringUtils.isNotBlank(deviceId) && StringUtils.isNotBlank(walletId)) {
            throw new RequestFormatException("Either device-id (iOS) or wallet-id (Android) tag should be provided in request, not both", this);
        }

        int fromRecord = 0;
        int numberOfRecords = 0;
        if (!StringUtils.isBlank(fromRecordString)) {
            try {
                fromRecord = Integer.parseInt(fromRecordString);
                fromRecord = (fromRecord - 1 > 0) ? (fromRecord - 1) : 0; // Minus 1 because Uniquare starts counting from 1
            } catch (NumberFormatException e) {
                throw new RequestFormatException("Value for field from-record is not number", this);
            }
        }
        if (!StringUtils.isBlank(numberOfRecordsString)) {
            try {
                numberOfRecords = Integer.parseInt(numberOfRecordsString);
            } catch (NumberFormatException e) {
                throw new RequestFormatException("Value for field number-of-records is not number", this);
            }
        }

        if (StringUtils.isBlank(client) && StringUtils.isBlank(accountNumber)){
            throw new RequestFormatException("Specify client or account", this);
        }

        List<Object[]> result = cardsDAO.getCardsOverviewForCLient(client, accountNumber, cardStatus, deliveryBlock,
                cardtype, holderName, lastDigits, fromRecord, numberOfRecords);

        ResponseElement response = createElement("card-overview");
        response.createElement("from-record", Integer.toString(fromRecord));
        response.createElement("number-of-records", Integer.toString(numberOfRecords));
        response.createElement("total-records", result.isEmpty() ? "0" :
                Long.toString(cardsDAO.getCardsOverviewForCLientTotal(client, accountNumber, cardStatus,
                        deliveryBlock, cardtype, holderName, lastDigits)));
        DateFormat dateFormat = new SimpleDateFormat(EXPIRY_DATE_FORMAT);
        for (Object[] tuple : result) {
            Object[] row = tuple;
            final String cardNumber = ((row[0] != null) ? (String) row[0] : "");
            ResponseElement record = response.createElement("card");
            record.createElement("account-number", ((row[15] != null) ? (String) row[15] : ""));
            record.createElement("card-number", cardNumber);
            record.createElement("main-supplementary", ((row[1] != null) ? (String) row[1] : ""));
            record.createElement("card-status", (row[2] != null) ? (String) row[2] : "");
            record.createElement("BIN-code", ((row[3] != null) ? (String) row[3] : ""));
            String deliveryBlockValue = row[4] == null ?
                    ("2".equals(row[2]) ? "7"/*7 means blocked*/ : "1"/*1 means in production*/) :
                    (String) row[4];
            record.createElement("delivery-status", deliveryBlockValue);
            if (row[6] == null) { // If PcdCard.expiry2 is filled in, return it instead of expiry1
                record.createElement("expiry-date", ((row[5] != null) ? dateFormat.format((Date) row[5]) : ""));
            } else {
                record.createElement("expiry-date", (dateFormat.format((Date) row[6])));
            }
            record.createElement("condition-set", ((row[7] != null) ? (String) row[7] : ""));
            record.createElement("card-holder", ((row[8] != null) ? (String) row[8] : ""));
            record.createElement("block-reason", ((row[9] != null) ? (String) row[9] : ""));
            record.createElement("condition-set-name", ((row[10] != null) ? (String) row[10] : ""));
            // New fields
            String status = !StringUtils.isBlank((String) row[16]) &&
                    (row[11] == null || StringUtils.equalsIgnoreCase("delivered", (String) row[11]) ||
                            StringUtils.equalsIgnoreCase("deleted", (String) row[11])) ? "Delivered" : ((row[11] != null) ? (String) row[11] : "deleted");
            String renewStatus = row[17] != null ? (String) row[17] : "";
            if ("Requested".equalsIgnoreCase(status) && !"7".equals(renewStatus) && !"G".equals(renewStatus)) {
                status = "deleted"; // Show PIN status Rquested only if PIN reminder is ordered
            }
            record.createElement("pin-status", StringUtils.capitalize(StringUtils.substring(status, 0, 1)) + StringUtils.lowerCase(StringUtils.substring(status, 1)));
            DeliveryDetailsHelper helper = new DeliveryDetailsHelper((String) row[12]);
            record.createElement("delivery-address", "9999".equals(((row[13] != null) ? (String) row[13] : "")) ? helper.getAddressForCardOverview() : "");
            record.createElement("add-info", "");
            record.createElement("delivery-branch", ((row[13] != null) ? (String) row[13] : ""));

            record.createElement("delivery-block", deliveryBlockValue);

            record.createElement("pin-block", (StringUtils.isBlank((String) row[16]) ? "0" : "1"));
            record.createElement("contactless", ((Integer) row[18] == null ? "" : ((Integer) row[18]).toString()));

            TokenWalletDo token = walletTokenService.getWalletToken(cardNumber, deviceId, walletId);
            if (token != null) {
                ResponseElement tokenResponseElement = record.createElement("token");
                tokenResponseElement.createElement("eligible", String.valueOf(token.isTokenEligible()));
                tokenResponseElement.createElement("status", token.getTokenStatus());
                tokenResponseElement.createElement("ref-id", token.getTokenRefId());
            }
        }
    }

    protected String getCardStatusSearchString(String status) {
        if (StringUtils.isBlank(status))
            return null;
        else {
            status = status.replaceAll("\\s", "");
            status = status.replaceAll(",", "','");
            return "('" + status + "')";
        }
    }

}
