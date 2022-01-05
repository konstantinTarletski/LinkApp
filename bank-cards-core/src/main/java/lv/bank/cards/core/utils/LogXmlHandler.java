package lv.bank.cards.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Set;

@Slf4j
public class LogXmlHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public void close(MessageContext arg0) {
    }

    @Override
    public boolean handleFault(SOAPMessageContext smc) {
        logging(smc);
        return true;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext smc) {
        logging(smc);
        return true;
    }

    private void logging(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            smc.getMessage().writeTo(baos);
            String xmlString = null;

            Object encoding = smc.getMessage().getProperty(SOAPMessage.CHARACTER_SET_ENCODING);
            log.info("logging, encoding from SOAP message properties encoding = {}", encoding);
            if (encoding != null) {
                try {
                    Charset charset = Charset.forName(encoding.toString());
                    xmlString = new String(baos.toByteArray(), charset);
                } catch (UnsupportedCharsetException e) {
                    log.info("logging, UnsupportedCharsetException = {}", encoding);
                }
            }
            if (StringUtils.isBlank(xmlString)) {
                xmlString = baos.toString();
            }
            String xml = XmlUtils.getFormattedXml(xmlString);
            if(outboundProperty){
                log.info("logging, outgoing XML = {}", xml);
            } else {
                log.info("logging, incoming XML = {}", xml);
            }

        } catch (Exception e) {
            log.warn("Unable to get XML from message, get error message = {}", e.getMessage());
        }
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

}
