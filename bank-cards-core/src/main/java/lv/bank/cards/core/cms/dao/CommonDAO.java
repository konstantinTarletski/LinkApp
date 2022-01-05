/* $Id: CommonDAO.java 1 2006-08-09 13:17:00Z just $
 * Created on Feb 21, 2005
 *
 */
package lv.bank.cards.core.cms.dao;

import lv.bank.cards.core.entity.cms.IzdBinTable;
import lv.bank.cards.core.entity.cms.IzdBranch;
import lv.bank.cards.core.entity.cms.IzdCompany;
import lv.bank.cards.core.entity.cms.IzdCountry;
import lv.bank.cards.core.entity.cms.IzdInFile;
import lv.bank.cards.core.entity.cms.IzdStopCause;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.hibernate.SQLQuery;

public interface CommonDAO extends DAO {

    Integer getFileID(String bankc, String groupc, String fileType, String jjj);

    String getBranchIdByExternalId(String externalId);

    IzdCompany findIzdCompanyByRegCodeUR(String regCode) throws DataIntegrityException;

    IzdCompany getIzdCompanyByCode(String code, String bankc) throws DataIntegrityException;

    IzdStopCause findStopCauseByCause(String cause) throws DataIntegrityException;

    IzdCountry getIzdCountryByShortCountryCode(String code) throws DataIntegrityException;

    IzdBinTable findBinByCardOrBinCode(String card, String bin);

    String getExternalBranchId(String id);

    IzdBranch getIzdBranchByRegCodeUR(String regCode) throws DataIntegrityException;

    IzdInFile getIzdInFileByFilename(String filename) throws DataIntegrityException;

    SQLQuery getDeliveryBranchDataHandoff(String bankCode, String groupCode);

    SQLQuery getTokensDataHandoff(String requestorIdApple, String requestorIdGoogle, String datetime, String country);

    SQLQuery getTokensTransDataHandoff(String requestorIdApple, String requestorIdGoogle, String datetime, String country, String bankCode, String groupCode);

}
