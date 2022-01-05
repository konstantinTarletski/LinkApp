package lv.bank.cards.soap.requests;

import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.service.RtcuNGTemplatesService;
import org.dom4j.Element;
import org.mockito.Mockito;

/**
 * Helper class to test Subrequest
 *
 * @author saldabols
 */
public class SubRequestJUnit extends SubRequest {

    public SubRequestJUnit(Element req) throws RequestPreparationException, RequestFormatException {
        super(req, Mockito.mock(RtcuNGTemplatesService.class));
    }
}
