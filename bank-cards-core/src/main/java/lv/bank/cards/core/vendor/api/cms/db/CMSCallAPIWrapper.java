package lv.bank.cards.core.vendor.api.cms.db;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.vendor.api.cms.db.types.TLastError;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jdbc.ReturningWork;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

@Slf4j
public class CMSCallAPIWrapper {

    static private final String HibernateSessionFactory = "java:jboss/hibernate/CMSSessionFactory";
    private static SessionFactory sessionFactory;
    private final String bankCode;
    private final String groupCode;
    private final String mainCmsSchema;
    private final String baseApiPackage;
    private final String clientCardApiPackage;
    private final String rightsPackage;
    private final int causeTextLength = 1000;
    private final int actionTextLength = 1000;

    public CMSCallAPIWrapper() {
        this.bankCode = LinkAppProperties.getCmsBankCode();
        this.groupCode = LinkAppProperties.getCmsGroupCode();

        if ("23".equals(bankCode)) {
            mainCmsSchema = "ISSUING_LV";
        } else {
            mainCmsSchema = "ISSUING_LT";
        }

        baseApiPackage = mainCmsSchema + ".IZD_API";
        clientCardApiPackage = mainCmsSchema + ".IZD_API_CC";
        rightsPackage = mainCmsSchema + ".IZD_RESTR_ACC";

        try {
            sessionFactory = InitialContext.doLookup(HibernateSessionFactory);
        } catch (NamingException e) {
            log.error("CMSCallAPIWrapper", e);
        }
    }

    public static String escapeSQL(String text) {
        if (text != null)
            text = text.replace("'", "''");
        return text;
    }

    private Connection getConnection() {
        return ((SessionImpl) sessionFactory.getCurrentSession()).connection();
    }

    public String getReturnValue(int res) {
        switch (res) {
            case 0:
                return "function completed successfully";
            case 4:
                return "function completed with some warning information";
            case 8:
                return "function not completed due to Issuing defined error";
            case 12:
                return "Unexpected programm code error";
            default:
                return "Unknown return code";
        }
    }

    public void switchInsurance(String card, String bank, String group, String value) throws CMSCallAPIException {
        try {
            //getConnection().setAutoCommit(false);

            CallableStatement cstmt = getConnection().prepareCall("begin ?:=" + baseApiPackage + ".switch_insurance(?,?,?,?); end; ");
            log.debug("setting parameters...");
            cstmt.registerOutParameter(1, Types.NUMERIC);
            cstmt.setString(2, card);
            cstmt.setString(3, bank);
            cstmt.setString(4, group);
            cstmt.setString(5, value);
            log.debug("executing function...");
            cstmt.execute();
            log.debug("extraction reply...");
            int res = cstmt.getInt(1);
            log.debug("closing...");
            cstmt.close();
            log.debug("done.");
            if (res != 0) {
                log.error("Function returns code " + res + ": " + this.getReturnValue(res));
                TLastError lastErr = this.GetLastError(this.causeTextLength, this.actionTextLength);
                log.error("Error code: " + lastErr.getErrorCode());
                log.error("Error cause: " + lastErr.getErrorCause());
                log.error("Action: " + lastErr.getAction());
                throw new CMSCallAPIException(lastErr);
            }
        } catch (SQLException e) {
            log.error("Error: " + e.getMessage(), e);
            throw new CMSCallAPIException(e);
        }
    }

    public TLastError GetLastError(int causeTextLength, int actionTextLength)
            throws CMSCallAPIException {
        log.info("Called function: GetLastError()");
        log.debug("preparing call...");
        TLastError result;
        try {
            CallableStatement cstmt = getConnection().prepareCall("{call " + baseApiPackage + ".getlasterror(?,?,?,?,?) }");
            log.debug("setting parameters...");
            cstmt.setInt(1, causeTextLength);
            cstmt.setInt(2, actionTextLength);
            cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
            cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
            log.debug("executing function...");
            cstmt.execute();
            log.debug("extraction reply...");
            result = new TLastError();
            result.setErrorCode(cstmt.getString(3));
            result.setErrorCause(cstmt.getString(4));
            result.setAction(cstmt.getString(5));
            log.debug("closing...");
            cstmt.close();
            log.debug("done.");
            return result;
        } catch (SQLException e) {
            log.error("Error: " + e.getMessage());
            throw new CMSCallAPIException(e);
        }
    }

    public void setChipTagValue(String card, String chip, String value) throws CMSCallAPIException {
        try {
            CallableStatement cstmt = getConnection().prepareCall("begin ?:=" + baseApiPackage + ".SetChipTagValue(?,?,?); end; ");

            cstmt.registerOutParameter(1, Types.NUMERIC);
            cstmt.setString(2, card);
            cstmt.setString(3, chip);
            cstmt.setString(4, escapeSQL(value));

            cstmt.execute();
            int res = cstmt.getInt(1);
            cstmt.close();
            if (res != 0) {
                log.error("Function returns code " + res + ": " + this.getReturnValue(res));
                TLastError lastErr = this.GetLastError(this.causeTextLength, this.actionTextLength);
                log.error("Error code: " + lastErr.getErrorCode());
                log.error("Error cause: " + lastErr.getErrorCause());
                log.error("Action: " + lastErr.getAction());
                throw new CMSCallAPIException(lastErr);
            }
        } catch (SQLException e) {
            log.error("Error: " + e.getMessage(), e);
            throw new CMSCallAPIException(e);
        }
    }

    /**
     * XML command to update entity
     */
    public class UpdateEntityXML {

        protected Document document;
        protected Element details;

        public UpdateEntityXML(String docName, String idName, String id, String bankC, String groupC) {
            document = DocumentHelper.createDocument();
            document.setXMLEncoding("UTF-8");
            Element doc = document.addElement("Changes_request")
                    .addElement("document");
            doc.addElement("version");
            doc.addElement("DOC_NAME").setText(docName);
            doc.addElement("OPERATION").setText("update");
            doc.addElement("EXTERNAL_ID").setText("1");

            details = doc.addElement("details");
            details.addElement(idName).setText(id);
            details.addElement("BANK_C").setText(bankC);
            details.addElement("GROUPC").setText(groupC);
        }

        public String getDocument() {
            return document.asXML();
        }

        public void setElement(String element, String value) {
            if (nodeIsOK(value)) details.addElement(element).addText(escapeSQL(value));
        }

        public void setElementWithoutEscape(String element, String value) {
            if (nodeIsOK(value)) details.addElement(element).addText(value);
        }

        public void setElementToNull(String element) {
            details.addElement(element).addAttribute("NULL", "TRUE");
        }

        private boolean nodeIsOK(String val) {
            return (val != null && !val.trim().isEmpty());
        }
    }

    public class UpdateCardXML extends UpdateEntityXML {
        public UpdateCardXML(String card, String bankC, String groupC) {
            super("card", "CARD", card, bankC, groupC);
        }
    }

    public class UpdateAccountXML extends UpdateEntityXML {
        public UpdateAccountXML(String account, String bankC, String groupC) {
            super("account", "ACCOUNT_NO", account, bankC, groupC);
        }
    }

    public class UpdateClientXML {

        protected Document document;
        protected Element details;

        public UpdateClientXML(String client, String bankC) {
            document = DocumentHelper.createDocument();
            document.setXMLEncoding("UTF-8");
            Element doc = document.addElement("Changes_request").addElement("document");
            doc.addElement("version");
            doc.addElement("DOC_NAME").setText("client");
            doc.addElement("OPERATION").setText("UPDATE");
            doc.addElement("EXTERNAL_ID").setText("1");

            details = doc.addElement("details");
            details.addElement("CLIENT").setText(client);
            details.addElement("BANK_C").setText(bankC);
        }

        public String getDocument() {
            return document.asXML();
        }

        public void setElement(String element, String value) {
            if (nodeIsOK(value)) details.addElement(element).addText(escapeSQL(value));
        }

        public void addElement(String element) {
            details.addElement(element);
        }

        private boolean nodeIsOK(String val) {
            return (val != null && !val.trim().isEmpty());
        }
    }

    public class UpdateDBWork implements ReturningWork<String> {

        @Getter
        @Setter
        private String inputXML;

        @Override
        public String execute(Connection conn) throws SQLException {

            log.info("Got this as input: " + inputXML);

            CallableStatement cstmt = conn.prepareCall("begin "
                    + rightsPackage + ".set_acc_bank(" + bankCode + "); "
                    + rightsPackage + ".set_acc_card_grup(" + groupCode + "); "
                    + "?:=" + clientCardApiPackage + ".Client_Card_Api(?); end; ");
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.setString(2, inputXML);
            cstmt.execute();
            int responseCode = cstmt.getInt(1);
            String responseXML = cstmt.getString(2);
            cstmt.close();
            if (responseCode != 0) {
                log.error("Function returns code " + responseCode + ": " + getReturnValue(responseCode));
                log.error("Response: " + responseXML);
                return responseXML;
            } else {
                return "success";
            }
        }
    }

    public class OrderPinEnvelopeWork implements ReturningWork<String> {

        private String bank;
        private String group;
        private String card;

        public void setParams(String bank, String group, String card) {
            this.bank = bank;
            this.group = group;
            this.card = card;
        }

        @Override
        public String execute(Connection conn) throws SQLException {

            CallableStatement cstmt = conn.prepareCall("begin ?:=" + baseApiPackage + ".orderpinenvelope(?,?,?); end; ");
            cstmt.registerOutParameter(1, Types.NUMERIC);
            cstmt.setString(2, card);
            cstmt.setString(3, bank);
            cstmt.setString(4, group);
            cstmt.execute();
            int res = cstmt.getInt(1);
            cstmt.close();
            if (res != 0) {
                log.error("Function returns code " + res + ": " + getReturnValue(res));
                try {
                    TLastError lastErr = GetLastError(causeTextLength, actionTextLength);
                    log.error("Error code: " + lastErr.getErrorCode());
                    log.error("Error cause: " + lastErr.getErrorCause());
                    log.error("Action: " + lastErr.getAction());
                } catch (CMSCallAPIException e) {
                    e.printStackTrace();
                }
                return getReturnValue(res);
            } else {
                return "success";
            }
        }
    }
}
