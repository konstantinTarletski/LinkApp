package lv.bank.cards.soap.handlers.lv;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.handlers.CardDataHandoffBase;
import lv.bank.cards.soap.requests.SubRequest;
import org.hibernate.SQLQuery;

import java.time.LocalDateTime;

@Slf4j
public class CardDataHandoff extends CardDataHandoffBase {

    public CardDataHandoff() throws RequestPreparationException {
        super();
    }

    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {

        final LocalDateTime fileDate = LocalDateTime.now().minusDays(1);
        final LocalDateTime inputDatetime = validateAndGetDateTimeString(r);
        final String inputDatetimeLong = FULL_DATE_TIME_INPUT_FORMAT.format(inputDatetime);

        SQLQuery query;

        log.info("Getting BIN data");
        query = cardsDAO.findBinDataHandoff(inputDatetimeLong);
        writeFile(query, DataHandoffEnum.BIN, fileDate, handoffLocation); // for DHW
        writeFile(query, DataHandoffEnum.BIN_LV, fileDate, handoffLocation); // for CDWH
        writeFile(query, DataHandoffEnum.BIN_EE, fileDate, handoffLocation); // for CDWH

        log.info("Getting Card data (LV)");
        query = cardsDAO.findCardsDataHandoffForLDWH(inputDatetimeLong, "LV");
        writeFile(query, DataHandoffEnum.CARDS, fileDate, handoffLocation);
        query = cardsDAO.findCardsDataHandoff(inputDatetimeLong, "LV");
        writeFile(query, DataHandoffEnum.CARDS_LV, fileDate, handoffLocation);

        log.info("Getting Card data (EE)");
        query = cardsDAO.findCardsDataHandoff(inputDatetimeLong, "EE");
        writeFile(query, DataHandoffEnum.CARDS_EE, fileDate, handoffLocation);

        log.info("Getting Account Condition Set data");
        query = cardsDAO.findCondAccntDataHandoff(inputDatetimeLong);
        writeFile(query, DataHandoffEnum.COND_ACCNT, fileDate, handoffLocation);
        writeFile(query, DataHandoffEnum.COND_ACCNT_LV, fileDate, handoffLocation);
        writeFile(query, DataHandoffEnum.COND_ACCNT_EE, fileDate, handoffLocation);

        log.info("Getting Card Condition Set data");
        query = cardsDAO.findCondCardDataHandoff(inputDatetimeLong);
        writeFile(query, DataHandoffEnum.COND_CARD, fileDate, handoffLocation);
        writeFile(query, DataHandoffEnum.COND_CARD_LV, fileDate, handoffLocation);
        writeFile(query, DataHandoffEnum.COND_CARD_EE, fileDate, handoffLocation);

        log.info("Getting Service Fee data");
        query = cardsDAO.findServicesFeeDataHandoff(inputDatetimeLong);
        writeFile(query, DataHandoffEnum.SERVICES_FEE, fileDate, handoffLocation);
        writeFile(query, DataHandoffEnum.SERVICES_FEE_LV, fileDate, handoffLocation);
        writeFile(query, DataHandoffEnum.SERVICES_FEE_EE, fileDate, handoffLocation);

        log.info("Getting Stop Cause data");
        query = cardsDAO.getStopCausesDataHandoff(inputDatetimeLong);
        writeFile(query, DataHandoffEnum.STOP_CAUSES, fileDate, handoffLocation);
        writeFile(query, DataHandoffEnum.STOP_CAUSES_LV, fileDate, handoffLocation);
        writeFile(query, DataHandoffEnum.STOP_CAUSES_EE, fileDate, handoffLocation);

        log.info("Getting Branch data");
        query = commonDAO.getDeliveryBranchDataHandoff(LinkAppProperties.getCmsBankCode(), LinkAppProperties.getCmsGroupCode());
        writeFile(query, DataHandoffEnum.BRANCHES_LV, fileDate, handoffLocation);
        writeFile(query, DataHandoffEnum.BRANCHES_EE, fileDate, handoffLocation);

        log.info("Getting tokens data");
        query = commonDAO.getTokensDataHandoff(LinkAppProperties.getVisaVtsRequestorIdApple(), LinkAppProperties.getVisaVtsRequestorIdGoogle(), inputDatetimeLong, "LV");
        writeFile(query, DataHandoffEnum.TOKENS_LV, fileDate, handoffLocationTokens);
        query = commonDAO.getTokensDataHandoff(LinkAppProperties.getVisaVtsRequestorIdApple(), LinkAppProperties.getVisaVtsRequestorIdGoogle(), inputDatetimeLong, "EE");
        writeFile(query, DataHandoffEnum.TOKENS_EE, fileDate, handoffLocationTokens);

        log.info("Getting token_trans data");
        query = commonDAO.getTokensTransDataHandoff(LinkAppProperties.getVisaVtsRequestorIdApple(), LinkAppProperties.getVisaVtsRequestorIdGoogle(),
                inputDatetimeLong, "LV", LinkAppProperties.getCmsBankCode(), LinkAppProperties.getCmsGroupCode());
        writeFile(query, DataHandoffEnum.TOKEN_TRANS_LV, fileDate, handoffLocationTokens);
        query = commonDAO.getTokensTransDataHandoff(LinkAppProperties.getVisaVtsRequestorIdApple(), LinkAppProperties.getVisaVtsRequestorIdGoogle(),
                inputDatetimeLong, "EE", LinkAppProperties.getCmsBankCode(), LinkAppProperties.getCmsGroupCode());
        writeFile(query, DataHandoffEnum.TOKEN_TRANS_EE, fileDate, handoffLocationTokens);

        writeResponse();
    }

}
