package lv.bank.cards.soap.requests;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@Slf4j
public abstract class SubRequestHandler {

    protected SubResponse subResponse;
    protected SubRequest subRequest = null;
    protected LocalDateTime lastUsed;

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        this.lastUsed = LocalDateTime.now();
        subRequest = r;
        subResponse = SubResponse.forRequest(subRequest);
    }

    public SubResponse compileResponse() {
        this.lastUsed = LocalDateTime.now();
        return subResponse;
    }

    public ResponseElement createElement(String name) {
        return subResponse.createElement(name);
    }

    public ResponseElement createElement(String name, String text) {
        return subResponse.createElement(name, text);
    }

    public static String getStringFromNode(String xpath, Document doc) {
        Object o = DocumentHelper.createXPath(xpath).evaluate(doc);
        if (o instanceof Node)
            return ((Node) o).getText();
        return null;
    }

    // returns text from node located at xpath
    public String getStringFromNode(String xpath) {
        return getStringFromNode(xpath, subRequest.getReq());
    }

    protected String getAttributeByName(String xpath) {
        Object o = DocumentHelper.createXPath(xpath).evaluate(subRequest.getReq());
        if (o instanceof Node) {
            log.trace("NAME: " + ((Node) o).getName() + " VALUE: " + ((Node) o).getText());
            return ((Node) o).getText();
        } else if (o instanceof List) {
            for (Object tuple : (List<?>) o) {
                if (tuple instanceof Node)
                    log.trace("NAME: " + ((Node) o).getName() + " VALUE: " + ((Node) o).getText());
                if ("operator".equals(((Node) o).getName())) return ((Node) o).getText();
            }
        }
        return null;
    }

    protected List<String> getStringListFromNode(String xpath) {
        Object o = DocumentHelper.createXPath(xpath).evaluate(subRequest.getReq());
        List<String> result = new LinkedList<>();
        if (o instanceof Node)
            result.add(((Node) o).getText());
        else if (o instanceof List) {
            for (Object tuple : (List<?>) o) {
                if (tuple instanceof Node)
                    result.add(((Node) tuple).getText());
            }
        }
        return result;
    }

    public boolean notUsedForSeconds(int secondsCount) {
        return ((this.lastUsed.plus(secondsCount, ChronoUnit.SECONDS)).isBefore(LocalDateTime.now()));
    }

}
