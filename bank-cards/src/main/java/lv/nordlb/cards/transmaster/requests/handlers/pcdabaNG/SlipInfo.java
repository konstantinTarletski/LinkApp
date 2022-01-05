package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.core.entity.linkApp.PcdSlip;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SlipInfo extends SubRequestHandler {

    private PcdabaNGManager pcdabaNGManager = null;
    public static final String fullDateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    public SlipInfo() throws RequestPreparationException {
        super();
        try {
            pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        Date fromTime = null, toTime = null;
        String card = getStringFromNode("/do/card");
        String from = getStringFromNode("/do/from");
        String to = getStringFromNode("/do/to");


        if (card == null || card.equals(""))
            throw new RequestFormatException("Specify card number", this);

        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern(fullDateTimeFormat);


        if (!(from == null || from.equals(""))) {
            if (from.length() == 10) {
                // if there is no time specified assume
                // it as start of day
                from += " 00:00:00";
            }
            try {
                fromTime = formatter.parse(from);
            } catch (Exception e) {
                throw new RequestFormatException("Specify from tag in format yyyy-MM-dd HH24:mm:ss or yyyy-MM-dd");
            }
        }

        if (!(to == null || to.equals(""))) {
            if (to.length() == 10) {
                // if there is no time specified assume
                // it as start of day
                to += " 00:00:00";
            }
            try {
                toTime = formatter.parse(to);
            } catch (Exception e) {
                throw new RequestFormatException("Specify to tag in format yyyy-MM-dd HH24:mm:ss or yyyy-MM-dd");
            }
        }

        List<PcdSlip> results = pcdabaNGManager.getTransactionDetails(card, fromTime, toTime);
        ResponseElement response = createElement("get-slip-info");
        formatter = (SimpleDateFormat) r.getDateFormat();
        for (PcdSlip result : results) {
            ResponseElement slip = response.createElement("slip");
            if (result.getPostDate() != null) {
                slip.createElement("post_date", formatter.format(result.getPostDate()));
            }//1
            slip.createElement("amount", String.valueOf(result.getAmount()));//2
            if (result.getTranAmt() != null) {
                slip.createElement("tran_amt", String.valueOf(result.getTranAmt()));
            }//3
            if (result.getTranCcy() != null) {
                slip.createElement("tran_ccy", result.getTranCcy());
            }//4
            slip.createElement("accnt_ccy", result.getAccntCcy());//5
            if (result.getTranDateTime() != null) {
                slip.createElement("tran_date_time", formatter.format(result.getTranDateTime()));
            }//6
            if (result.getAbvrName() != null) {
                slip.createElement("merchant_name", result.getAbvrName());
            }//7
            if (result.getAprCode() != null) {
                slip.createElement("apr_code", result.getAprCode());
            }//8
            if (result.getDebCred() != null) {
                slip.createElement("deb_cred", result.getDebCred().toPlainString());
            }//9
            if (result.getMerchant() != null) {
                slip.createElement("merchant_id", result.getMerchant());
            }//10
            slip.createElement("tran_type", result.getTranType());//11
            if (result.getStan() != null) {
                slip.createElement("stan", result.getStan());
            }//12
            if (result.getCity() != null) {
                slip.createElement("city", result.getCity());
            }//13
            if (result.getCountry() != null) {
                slip.createElement("country", result.getCountry());
            }//14
            if (result.getTrCode() != null) {
                slip.createElement("tr_fee_code", result.getTrCode());
            }//15
            if (result.getTrFee() != null) {
                slip.createElement("tr_fee", String.valueOf(result.getTrFee()));
            }//16
            if (result.getFee() != null) {
                slip.createElement("fee", String.valueOf(result.getFee()));
            }//17
        }
    }
}

