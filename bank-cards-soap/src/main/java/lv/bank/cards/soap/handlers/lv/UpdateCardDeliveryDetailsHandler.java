package lv.bank.cards.soap.handlers.lv;

import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.cms.impl.CommonDAOHibernate;
import lv.bank.cards.core.utils.Constants;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.utils.DeliveryDetailsHelperBase;
import lv.bank.cards.core.utils.lv.DeliveryDetailsHelper;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.handlers.UpdateCardDeliveryDetailsBaseHandler;
import lv.bank.cards.soap.requests.ResponseElement;
import org.apache.commons.lang.StringUtils;

public class UpdateCardDeliveryDetailsHandler extends UpdateCardDeliveryDetailsBaseHandler {

    protected CommonDAO commonDAO;

    public UpdateCardDeliveryDetailsHandler() {
        super();
        commonDAO = new CommonDAOHibernate();
    }

    @Override
    public String resolveBranch(String dlv_branch) throws RequestProcessingException {
        String branch = "";
        if (StringUtils.isNotBlank(dlv_branch)) {
            String branchToSearch = StringUtils.leftPad(dlv_branch, 2, "0");
            branch = commonDAO.getBranchIdByExternalId(branchToSearch);
            if (branch == null) {
                throw new RequestProcessingException("There is no branch in NORDLB_BRANCHES for " + branchToSearch, this);
            }
        }
        return branch;
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

        DeliveryDetailsHelper details = new DeliveryDetailsHelper(dlv_language, dlv_addr_country,
                dlv_addr_city, dlv_addr_street1, dlv_addr_street2, dlv_addr_zip, dlv_company);

        if (Constants.DELIVERY_BRANCH_LV_888.equals(dlv_branch)) {
            try {
                details.checkData();
            } catch (DataIntegrityException e) {
                throw new RequestFormatException(e.getMessage(), this);
            }
        }
        return details;
    }

    @Override
    public void addToResponse(ResponseElement detailElement, String dlv_branch, DeliveryDetailsHelperBase details) {
        detailElement.createElement("branch", "00".equals(dlv_branch) ? "0" : StringUtils.stripStart(dlv_branch, "0"));
    }

}
