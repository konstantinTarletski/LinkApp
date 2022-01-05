package lv.bank.cards.core.vendor.api.rtps.db;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.vendor.api.rtps.db.types.TLastError;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

@Slf4j
public class RTPSCallAPIWrapper {

    static private final String HibernateSessionFactory = "java:jboss/hibernate/RTPSSessionFactory";

    private String packageNameWrapper = "STIP_CALL_API_WRAPPER";
    private String packageName="STIP_CALL_API";

    private static SessionFactory sessionFactory;

    public RTPSCallAPIWrapper() {
        try {
            sessionFactory = InitialContext.doLookup(HibernateSessionFactory);
        } catch (NamingException e) {
            log.error("RTPSCallAPIWrapper", e);
        }
    }

    private Connection getConnection() {
        return ((SessionImpl) sessionFactory.getCurrentSession()).connection();
    }

    public TLastError GetLastError(int errorTextMaxLength) throws RTPSCallAPIException {
        log.info("Called function: GetLastError()");
        log.debug("preparing call...");
        try {
            CallableStatement cstmt = getConnection().prepareCall("{call " + packageNameWrapper + ".getlasterror(?,?,?,?) }");
            log.debug("setting parameters...");
            cstmt.setInt(1, errorTextMaxLength);
            cstmt.registerOutParameter(2, java.sql.Types.NUMERIC);
            cstmt.registerOutParameter(3, java.sql.Types.NUMERIC);
            cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            log.debug("executing function...");
            cstmt.execute();
            log.debug("extraction reply...");
            TLastError result = new TLastError();
            result.setErrorCode(cstmt.getInt(2));
            result.setOraErrCode(cstmt.getInt(3));
            result.setErrorText(cstmt.getString(4));
            log.debug("closing...");
            cstmt.close();
            log.debug("done.");
            return result;
        } catch (SQLException e) {
            log.error("Error: " + e.getMessage());
            throw new RTPSCallAPIException(e);
        }
    }

    public void AddCardToRMSStop(String centreId, String cardNumber, String ruleExpr, Long priority,
                                 String actionCode, String description) throws RTPSCallAPIException {
        log.debug("Called function: AddCardToRMSStop()");
        log.debug("preparing call...");
        try {
            CallableStatement cstmt = getConnection().prepareCall("begin ?:=" + packageNameWrapper +
                    ".AddCardToRMSStop(?,?,?,?,?,?); end; ");
            log.debug("setting parameters...");
            cstmt.registerOutParameter(1, Types.NUMERIC);
            cstmt.setString(2, centreId);
            cstmt.setString(3, cardNumber);
            cstmt.setString(4, ruleExpr);
            if (priority != null)
                cstmt.setLong(5, priority.longValue());
            else
                cstmt.setNull(5, Types.INTEGER);
            cstmt.setString(6, actionCode);
            cstmt.setString(7, description);
            log.debug("executing function...");
            cstmt.execute();
            log.debug("extraction reply...");
            int res = cstmt.getInt(1);
            log.debug("closing...");
            cstmt.close();
            log.debug("done.");
            if (res == 0) {
                log.error("Function returns false");
                TLastError lastErr = this.GetLastError(1000);
                log.error("Error code: " + lastErr.getErrorCode());
                log.error("OraError code: " + lastErr.getOraErrCode());
                log.error("Error text: " + lastErr.getErrorText());
                throw new RTPSCallAPIException(lastErr);
            }
        } catch (SQLException e) {
            log.error("Error: " + e.getMessage(), e);
            throw new RTPSCallAPIException(e);
        }
    }

    public void RemoveCardFromRMSStop(String centreId, String cardNumber, String ruleExpr, Integer priority,
                                      String actionCode, String description) throws RTPSCallAPIException {
        log.debug("Called function: RemoveCardFromRMSStop()");
        log.debug("preparing call...");

        try {
            CallableStatement cstmt = getConnection().prepareCall("begin ?:=" + packageNameWrapper +
                    ".RemoveCardFromRMSStop(?,?,?,?,?,?); end; ");
            log.debug("setting parameters...");
            cstmt.registerOutParameter(1, Types.NUMERIC);
            cstmt.setString(2, centreId);
            cstmt.setString(3, cardNumber);
            cstmt.setString(4, ruleExpr);
            if (priority != null)
                cstmt.setLong(5, priority.longValue());
            else
                cstmt.setNull(5, Types.INTEGER);
            cstmt.setString(6, actionCode);
            cstmt.setString(7, description);
            log.debug("executing function...");
            cstmt.execute();
            log.debug("extraction reply...");
            int res = cstmt.getInt(1);
            log.debug("closing...");
            cstmt.close();
            log.debug("done.");
            if (res == 0) {
                log.error("Function returns false");
                TLastError lastErr = this.GetLastError(1000);
                log.error("Error code: " + lastErr.getErrorCode());
                log.error("OraError code: " + lastErr.getOraErrCode());
                log.error("Error text: " + lastErr.getErrorText());
                throw new RTPSCallAPIException(lastErr);
            }
        } catch (SQLException e) {
            log.error("Error: " + e.getMessage(), e);
            throw new RTPSCallAPIException(e);
        }
    }

    public void setAccBonusAmount(String centreId, String account, Long amount
    ) throws RTPSCallAPIException{
        log.debug("Called function: SetCardStatus()");
        log.debug("preparing call...");
        CallableStatement cstmt;
        try {
            cstmt = getConnection().prepareCall("begin ?:="+ packageName+
                    ".SetAccBonusAmount_N(?,?,?); end; ");

            cstmt.registerOutParameter(1,Types.INTEGER);
            cstmt.setString(2, centreId);
            cstmt.setString(3, account);
            if(amount != null)
                cstmt.setLong(4, amount);
            else
                cstmt.setNull(4, Types.INTEGER);

            cstmt.execute();
            int res=cstmt.getInt(1);
            cstmt.close();
            if (res==0){
                log.error("Function returns false");
                TLastError lastErr = this.GetLastError(1000);
                log.error("Error code: " + lastErr.getErrorCode());
                log.error("OraError code: " + lastErr.getOraErrCode());
                log.error("Error text: " + lastErr.getErrorText());
                throw new RTPSCallAPIException(lastErr);
            }
        } catch (SQLException e) {
            log.error("Error: " + e.getMessage(), e);
            throw new RTPSCallAPIException(e);
        }
    }

}
