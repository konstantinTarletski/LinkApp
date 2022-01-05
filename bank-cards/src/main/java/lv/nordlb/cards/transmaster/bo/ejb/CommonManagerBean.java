package lv.nordlb.cards.transmaster.bo.ejb;

import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.cms.dao.IzdConfigDAO;
import lv.bank.cards.core.cms.impl.CommonDAOHibernate;
import lv.bank.cards.core.cms.impl.IzdConfigDAOHibernate;
import lv.bank.cards.core.entity.cms.IzdCompany;
import lv.bank.cards.core.entity.cms.IzdConfig;
import lv.bank.cards.core.entity.cms.IzdCountry;
import lv.bank.cards.core.entity.cms.IzdStopCause;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.nordlb.cards.transmaster.bo.interfaces.CommonManager;
import org.hibernate.SQLQuery;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class CommonManagerBean implements CommonManager {

    protected CommonDAO commonDAO;
    protected IzdConfigDAO izdConfigDAO;

    public CommonManagerBean() {
        commonDAO = new CommonDAOHibernate();
        izdConfigDAO = new IzdConfigDAOHibernate();
    }

    @Override
    public Integer getFileId(String bankc, String groupc, String fileType, String jjj) {
        return commonDAO.getFileID(bankc, groupc, fileType, jjj);
    }

    @Override
    public String getBranchIdByExternalId(String externalId) {
        return commonDAO.getBranchIdByExternalId(externalId);
    }

    @Override
    public IzdCompany getIzdCompanyByRegCodeUR(String regCodeUR) throws DataIntegrityException {
        return commonDAO.findIzdCompanyByRegCodeUR(regCodeUR);
    }

    @Override
    public IzdCompany getIzdCompanyByCode(String code, String bankc) throws DataIntegrityException {
        return commonDAO.getIzdCompanyByCode(code, bankc);
    }

    @Override
    public IzdStopCause getIzdStopCauseByCause(String cause) throws DataIntegrityException {
        return commonDAO.findStopCauseByCause(cause);
    }

    @Override
    public IzdCountry getIzdCountryByShortCountryCode(String code) throws DataIntegrityException {
        return commonDAO.getIzdCountryByShortCountryCode(code);
    }

    @Override
    public List<IzdConfig> GetIzdConfig() {
        return izdConfigDAO.GetIzdConfig();
    }

    @Override
    public SQLQuery getDeliveryBranchDataHandoff(String bankCode, String groupCode){
        return commonDAO.getDeliveryBranchDataHandoff(bankCode, groupCode);
    }
}
