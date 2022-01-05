package lv.nordlb.cards.transmaster.requests.handlers.cms;

import lv.bank.cards.core.entity.cms.IzdPreProcessingRow;
import lv.bank.cards.core.entity.cms.IzdTrtypeNameId;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.nordlb.cards.transmaster.bo.interfaces.CardManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Andris Dobicinaitis
 */

/* How to use..
 *
 * Get list of X commissions from izd_pre_processing_row:
 *   Request:
 *     <do what='delete-pre-proc-payment'>
 *        <action>list</action>
 *        <limit>3</limit>
 *     </do>
 *
 *   Response:
 *     <done>
 *       <list>
 *         <payment>
 *            <id>28280809</id>
 *            <card>4921754900107061</card>
 *            <card-type>VISA</card-type>
 *            <tr-type>325</tr-type>
 *            <tr-type-name>Annual fee (base)</tr-type-name>
 *            <amount>1000</amount>
 *            <ccy>LVL</ccy>
 *            <date>11072007</date>
 *            <account-tr>225290</account-tr>
 *            <account-pl>1301014921</account-pl>
 *         </payment>
 *         <payment>
 *            <id>32453394</id>
 *            <card>4775733280913337</card>
 *            <card-type>VISA</card-type>
 *            <tr-type>325</tr-type>
 *            <tr-type-name>Annual fee (base)</tr-type-name>
 *            <amount>300</amount>
 *            <ccy>LVL</ccy>
 *            <date>01122007</date>
 *            <account-tr>206813</account-tr>
 *            <account-pl>1300849603</account-pl>
 *         </payment>
 *      </list>
 *     </done>
 *
 * Delete one ore more payments
 *   Request:
 *     <do what='delete-pre-proc-payment'>
 *     <action>delete</action>
 *        <id>28280809</id>
 *        <id>32453394</id>
 *        <id>22419598</id>
 *     </do>
 *
 *   Response:
 *     <done>
 *        <deleted>
 *          <id>28280809</id>
 *        </deleted>
 *        <deleted>
 *          <id>32453394</id>
 *        </deleted>
 *        <deleted>
 *          <id>22419598</id>
 *        </deleted>
 *     </done>
 */

public class DeletePreProcPayment extends SubRequestHandler {

    protected final CardManager cardManager;
    protected final CMSSoapAPIWrapper soapAPIWrapper;

    public DeletePreProcPayment() throws RequestPreparationException {
        super();
        try {
            cardManager = (CardManager) new InitialContext().lookup(CardManager.JNDI_NAME);
            soapAPIWrapper = (CMSSoapAPIWrapper) new InitialContext().lookup(CMSSoapAPIWrapper.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        ;
        String dateTimeFormat = "ddMMyyyy";
        String action = getStringFromNode("/do/action");
        String cardType;
        int limit = 0;

        if (action == null) {
            throw new RequestFormatException("No action provided!", this);
        } else if (action.equals("list")) { // Get list of commissions
            try {
                limit = Integer.parseInt(getStringFromNode("/do/limit"));
            } catch (NumberFormatException e) {
                throw new RequestFormatException("Incorect limit number provided!", this);
            }
            if (limit == 0)
                throw new RequestFormatException("No list limit provided!", this);

            ResponseElement response = createElement("list");

            List<IzdPreProcessingRow> commissionList = cardManager.getIzdPreProcessingRowsComissionsLimit(limit);
            SimpleDateFormat formatter = new SimpleDateFormat();
            formatter.applyPattern(dateTimeFormat);

            for (IzdPreProcessingRow result : commissionList) {
                switch (Integer.parseInt(result.getCard().getCard().substring(0, 1))) {
                    case 4:
                        cardType = "VISA";
                        break;
                    case 5:
                        cardType = "MasterCard";
                        break;
                    case 6:
                        cardType = "Maestro";
                        break;
                    default:
                        cardType = "Unknown card type"; //throw new RequestProcessingException("Unknown card type", this);
                        break;
                }

                ResponseElement element = response.createElement("payment");
                element.createElement("id", String.valueOf(result.getInternalNo()));
                element.createElement("card", result.getCard().getCard());
                element.createElement("card-type", cardType);
                element.createElement("tr-type", result.getTrType());
                element.createElement("tr-type-name", cardManager.getIzdTrTypeNameByType(new IzdTrtypeNameId(result.getTrType(), "2", "00")).getName());
                element.createElement("amount", String.valueOf(result.getAmount()));
                element.createElement("ccy", result.getCcy());
                element.createElement("date", formatter.format(result.getTranDateTime()));
                element.createElement("account-tr", String.valueOf(result.getAccountNo()));
                element.createElement("account-pl", String.valueOf(cardManager.getIzdAccountByAccountNr(result.getAccountNo()).getIzdAccParam().getUfield5()));
            }
        } else if (action.equals("delete")) { // Delete commissions

            List<String> ids = getStringListFromNode("/do/id");

            if (ids.isEmpty())
                throw new RequestFormatException("No payment ID provided!", this);

            for (String id : ids) {
                try {
                    Integer.parseInt(id);
                } catch (NumberFormatException e) {
                    throw new RequestFormatException("Incorrect payment ID provided!", this);
                }

                try {
                    BigDecimal internalNo = new BigDecimal(id);
                    IzdPreProcessingRow row = cardManager.getIzdPreProcessingRowByInternalNo(internalNo);
                    soapAPIWrapper.cancelTransaction(internalNo, row.getAccountNo(), row.getTrType(),
                            row.getTranCcy(), BigDecimal.valueOf(row.getAmount()));
                } catch (CMSSoapAPIException e) {
                    throw new RequestProcessingException(e.getMessage(), this);
                }

                ResponseElement response = createElement("deleted");
                response.createElement("id", id);
            }
        } else
            throw new RequestFormatException("Wrong action provided!", this);
    }
}
