package lv.bank.cards.soap.handlers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.cms.impl.CommonDAOHibernate;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Slf4j
public abstract class CardDataHandoffBase extends SubRequestHandler {

    public static final String SHORT_DATE_FORMAT_STRING = "yyyy-MM-dd";
    public static final String FULL_DATE_TIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    public static final String FILE_NAME_POSTFIX_BASE_STRING = "yyyyMMdd";
    public static final String FILE_NAME_POSTFIX_CARD_DATA_HANDOFF_STRING = "yyyy-MM-dd_HHmmss";

    public static final DateTimeFormatter FILE_NAME_POSTFIX_CARD_DATA_HANDOFF = DateTimeFormatter.ofPattern(FILE_NAME_POSTFIX_CARD_DATA_HANDOFF_STRING);
    public static final DateTimeFormatter FILE_NAME_POSTFIX_BASE = DateTimeFormatter.ofPattern(FILE_NAME_POSTFIX_BASE_STRING);
    public static final DateTimeFormatter FULL_DATE_TIME_INPUT_FORMAT = DateTimeFormatter.ofPattern(FULL_DATE_TIME_FORMAT_STRING);

    public static final DateFormat SHORT_DATE_FORMAT = new SimpleDateFormat(SHORT_DATE_FORMAT_STRING);
    public static final DateFormat FULL_DATE_TIME_FORMAT = new SimpleDateFormat(FULL_DATE_TIME_FORMAT_STRING);

    protected final CardsDAO cardsDAO;
    protected final CommonDAO commonDAO;
    protected final String handoffLocation;
    protected final String handoffLocationTokens;
    protected final String temporaryHandoffLocation;

    public CardDataHandoffBase() {
        super();
        cardsDAO = new CardsDAOHibernate();
        commonDAO = new CommonDAOHibernate();
        handoffLocation = LinkAppProperties.getHandoffLocation();
        handoffLocationTokens = LinkAppProperties.getHandoffLocationTokens();
        temporaryHandoffLocation = handoffLocation + "tmp" + File.separator;
    }

    public LocalDateTime validateAndGetDateTimeString(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String datetime = getStringFromNode("/do/datetime");
        LocalDateTime localDateTime;

        if (datetime == null || datetime.equals(""))
            throw new RequestFormatException("Specify datetime tag", this);

        if (datetime.length() == 10) { // if there is no time specified assume
            // it as start of day
            datetime += " 00:00:00";
        }

        try {
            localDateTime = LocalDateTime.parse(datetime, FULL_DATE_TIME_INPUT_FORMAT);
        } catch (Exception e) {
            throw new RequestFormatException("Specify date in format yyyy-MM-dd HH24:mm:ss or yyyy-MM-dd");
        }

        File directory = new File(temporaryHandoffLocation);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new RequestProcessingException("Could not create handoff file directory: " + temporaryHandoffLocation);
            }
        }

        return localDateTime;
    }

    protected void writeResponse() {
        ResponseElement response = createElement("card-data-handoff");
        response.createElement("file-name", (FILE_NAME_POSTFIX_BASE.format(LocalDateTime.now().minusDays(1))));
    }

    protected void writeFile(SQLQuery result, DataHandoffEnum configuration, LocalDateTime time, String destinationFolder) throws RequestProcessingException {
        StringBuilder fileName = new StringBuilder(configuration.getFileNamePrefix() + "_" + configuration.getDateFormat().format(time));
        if (StringUtils.isNotBlank(configuration.getFileName())) {
            fileName.append("_" + configuration.getFileName());
        }
        fileName.append(configuration.getFileExtension());
        writeFile(result,
                fileName.toString(),
                configuration.getRecordPrefix(),
                configuration.getRowEndSymbol(),
                configuration.getEncoding(),
                destinationFolder
        );
    }

    protected void writeFile(SQLQuery result, String fileName, String recordPrefix, String rowEndSymbol, String encoding, String destinationFolder) throws RequestProcessingException {

        log.info("Started  writing file = {} recordPrefix = {} encoding = {}", fileName, recordPrefix, encoding);

        result.setFetchSize(500);
        ScrollableResults res = result.scroll(ScrollMode.FORWARD_ONLY);
        Object[] row = null;

        try (OutputStream f1 = new FileOutputStream(temporaryHandoffLocation + fileName)) {

            while (res.next()) {

                row = res.get();
                StringBuilder source = new StringBuilder(recordPrefix);
                for (Object i : row) {
                    if (i instanceof Timestamp) {
                        source.append(FULL_DATE_TIME_FORMAT.format((Date) i));
                    } else if (i instanceof Date) {
                        source.append(SHORT_DATE_FORMAT.format((Date) i));
                    } else if (i instanceof BigDecimal) {
                        source.append(String.valueOf(i).replace('#', '+'));
                    } else {
                        source.append(i == null ? "" : ((String) i).replace('#', '+'));
                    }
                    source.append(rowEndSymbol);
                }
                source = new StringBuilder(source.substring(0, source.length() - 1));
                source.append("\n");
                f1.write(source.toString().getBytes(encoding));
            }

            f1.flush();
            f1.close();
            res.close();
        } catch (Exception e) {
            log.error("Error while accessing local file = {}, row = {}", fileName, row, e);
            throw new RequestProcessingException("Error while accessing local file '" + fileName + "':" + e.getMessage(), e);
        }

        try {
            Path temporaryFilePath = Paths.get(temporaryHandoffLocation + fileName);
            Path targetFilePath = Paths.get(destinationFolder + fileName);
            Files.move(temporaryFilePath, targetFilePath, REPLACE_EXISTING);
            log.info("Finished writing file = {}", fileName);
        } catch (IOException e) {
            log.error("Failed to move file '{}' to destination directory '{}': ", fileName, handoffLocation, e);
            throw new RequestProcessingException("Failed to move file '" + fileName + "' to destination directory '" +
                    handoffLocation + "': " + e.getMessage(), e);
        }
    }

    @RequiredArgsConstructor
    @Getter
    protected enum DataHandoffEnum {

        CARD_DATA_HANDOFF("", FILE_NAME_POSTFIX_CARD_DATA_HANDOFF, "CardDataHandoff", "", ".txt", "UTF-8", "\t"),

        BIN("bin", FILE_NAME_POSTFIX_BASE, "TMS", "", ".csv", "Windows-1257", "#"),
        BIN_EE("bin", FILE_NAME_POSTFIX_BASE, "EE_TMS", "EE#", ".csv", "Windows-1257", "#"),
        BIN_LV("bin", FILE_NAME_POSTFIX_BASE, "LV_TMS", "LV#", ".csv", "Windows-1257", "#"),

        CARDS("cards", FILE_NAME_POSTFIX_BASE, "TMS", "", ".csv", "Windows-1257", "#"),
        CARDS_EE("cards", FILE_NAME_POSTFIX_BASE, "EE_TMS", "EE#", ".csv", "Windows-1257", "#"),
        CARDS_LV("cards", FILE_NAME_POSTFIX_BASE, "LV_TMS", "LV#", ".csv", "Windows-1257", "#"),

        COND_ACCNT("cond_accnt", FILE_NAME_POSTFIX_BASE, "TMS", "", ".csv", "Windows-1257", "#"),
        COND_ACCNT_EE("cond_accnt", FILE_NAME_POSTFIX_BASE, "EE_TMS", "EE#", ".csv", "Windows-1257", "#"),
        COND_ACCNT_LV("cond_accnt", FILE_NAME_POSTFIX_BASE, "LV_TMS", "LV#", ".csv", "Windows-1257", "#"),

        COND_CARD("cond_card", FILE_NAME_POSTFIX_BASE, "TMS", "", ".csv", "Windows-1257", "#"),
        COND_CARD_EE("cond_card", FILE_NAME_POSTFIX_BASE, "EE_TMS", "EE#", ".csv", "Windows-1257", "#"),
        COND_CARD_LV("cond_card", FILE_NAME_POSTFIX_BASE, "LV_TMS", "LV#", ".csv", "Windows-1257", "#"),

        SERVICES_FEE("services_fee", FILE_NAME_POSTFIX_BASE, "TMS", "", ".csv", "Windows-1257", "#"),
        SERVICES_FEE_EE("services_fee", FILE_NAME_POSTFIX_BASE, "EE_TMS", "EE#", ".csv", "Windows-1257", "#"),
        SERVICES_FEE_LV("services_fee", FILE_NAME_POSTFIX_BASE, "LV_TMS", "LV#", ".csv", "Windows-1257", "#"),

        STOP_CAUSES("stop_causes", FILE_NAME_POSTFIX_BASE, "TMS", "", ".csv", "Windows-1257", "#"),
        STOP_CAUSES_EE("stop_causes", FILE_NAME_POSTFIX_BASE, "EE_TMS", "EE#", ".csv", "Windows-1257", "#"),
        STOP_CAUSES_LV("stop_causes", FILE_NAME_POSTFIX_BASE, "LV_TMS", "LV#", ".csv", "Windows-1257", "#"),

        BRANCHES_EE("branches", FILE_NAME_POSTFIX_BASE, "EE_TMS", "EE#", ".csv", "Windows-1257", "#"),
        BRANCHES_LV("branches", FILE_NAME_POSTFIX_BASE, "LV_TMS", "LV#", ".csv", "Windows-1257", "#"),

        TOKENS_EE("tokens", FILE_NAME_POSTFIX_BASE, "EE_TMS", "", ".csv", "Windows-1257", "#"),
        TOKENS_LV("tokens", FILE_NAME_POSTFIX_BASE, "LV_TMS", "", ".csv", "Windows-1257", "#"),
        TOKENS_LT("tokens", FILE_NAME_POSTFIX_BASE, "LT_TMS", "", ".csv", "Windows-1257", "#"),

        TOKEN_TRANS_EE("token_trans", FILE_NAME_POSTFIX_BASE, "EE_TMS", "", ".csv", "Windows-1257", "#"),
        TOKEN_TRANS_LV("token_trans", FILE_NAME_POSTFIX_BASE, "LV_TMS", "", ".csv", "Windows-1257", "#"),
        TOKEN_TRANS_LT("token_trans", FILE_NAME_POSTFIX_BASE, "LT_TMS", "", ".csv", "Windows-1257", "#"),
        ;

        final String fileName;
        final DateTimeFormatter dateFormat;
        final String fileNamePrefix;
        final String recordPrefix;
        final String fileExtension;
        final String encoding;
        final String rowEndSymbol;
    }

}
