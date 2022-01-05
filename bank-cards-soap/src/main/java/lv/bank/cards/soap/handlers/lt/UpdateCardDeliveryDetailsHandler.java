package lv.bank.cards.soap.handlers.lt;

import lv.bank.cards.core.utils.Constants;
import lv.bank.cards.core.utils.DeliveryDetailsHelperBase;
import lv.bank.cards.core.utils.lt.DeliveryDetailsHelper;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.handlers.UpdateCardDeliveryDetailsBaseHandler;
import lv.bank.cards.soap.requests.ResponseElement;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UpdateCardDeliveryDetailsHandler extends UpdateCardDeliveryDetailsBaseHandler {

    @Override
    public String resolveBranch(String dlv_branch) {
        return dlv_branch;
    }

    @Override
    public DeliveryDetailsHelperBase getDeliveryDetailsHelperBaseFromString(String detailsString){
        return new DeliveryDetailsHelper(detailsString);
    }

    @Override
    public DeliveryDetailsHelperBase validateAndGetDeliveryDetailsHelper(
            String dlv_language, String dlv_addr_country, String dlv_addr_city, String dlv_addr_street1,
            String dlv_addr_street2, String dlv_addr_zip, String dlv_company, String dlv_addr_code, String dlv_branch
    ) throws RequestFormatException {

        DeliveryDetailsHelper details = new DeliveryDetailsHelper(
                //dlv_language,
                dlv_addr_country, dlv_addr_city, dlv_addr_street1,
                dlv_addr_street2, dlv_addr_zip, dlv_company, dlv_addr_code
        );

        if (Constants.DELIVERY_BRANCH_LT_080.equals(dlv_branch)) {
            List<String> missing = new ArrayList<>();
            if (StringUtils.isBlank(dlv_addr_country)) {
                missing.add("country");
            }
            if (StringUtils.isBlank(dlv_addr_city)) {
                missing.add("region");
            }
            if (StringUtils.isBlank(dlv_addr_street2)) {
                missing.add("street address");
            }
            if (!missing.isEmpty()) {
                StringBuilder missingFields = new StringBuilder();
                for (String value : missing) {
                    if (missingFields.length() > 0)
                        missingFields.append(", ");
                    missingFields.append(value);
                }
                throw new RequestFormatException("Missing value for delivery address " + missingFields.toString(), this);
            }
        }
        return details;
    }

    @Override
    public void addToResponse(ResponseElement detailElement, String dlv_branch, DeliveryDetailsHelperBase details) {
        detailElement.createElement("branch", dlv_branch);
        detailElement.createElement("dlv_addr_code", details.getAddressCode());
    }

}
