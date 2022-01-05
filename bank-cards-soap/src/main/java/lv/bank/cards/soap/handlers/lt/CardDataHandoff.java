package lv.bank.cards.soap.handlers.lt;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.handlers.CardDataHandoffBase;
import lv.bank.cards.soap.requests.SubRequest;
import org.hibernate.SQLQuery;

import java.time.LocalDateTime;

@Slf4j
public class CardDataHandoff extends CardDataHandoffBase {

    public CardDataHandoff() {
        super();
    }

    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {

        final LocalDateTime fileDate = LocalDateTime.now();
        final LocalDateTime inputDatetime = validateAndGetDateTimeString(r);
        final String inputDatetimeLong = FULL_DATE_TIME_INPUT_FORMAT.format(inputDatetime);

        log.info("Getting CardDataHandoffLT data");
        SQLQuery query = cardsDAO.findCardDataHandoffLT(inputDatetimeLong);
        writeFile(query, DataHandoffEnum.CARD_DATA_HANDOFF, fileDate, handoffLocation);

        log.info("Getting tokens data");
        query = commonDAO.getTokensDataHandoff(LinkAppProperties.getVisaVtsRequestorIdApple(), LinkAppProperties.getVisaVtsRequestorIdGoogle(),
                inputDatetimeLong, "LT");
        writeFile(query, DataHandoffEnum.TOKENS_LT, fileDate, handoffLocationTokens);

        log.info("Getting token_trans data");
        query = commonDAO.getTokensTransDataHandoff(LinkAppProperties.getVisaVtsRequestorIdApple(), LinkAppProperties.getVisaVtsRequestorIdGoogle(),
                inputDatetimeLong, "LT", LinkAppProperties.getCmsBankCode(), LinkAppProperties.getCmsGroupCode());
        writeFile(query, DataHandoffEnum.TOKEN_TRANS_LT, fileDate, handoffLocationTokens);

        writeResponse();
    }

}
