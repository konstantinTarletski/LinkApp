package lv.bank.cards.base.utils;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.utils.LinkAppProperties;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Set;

import static java.nio.charset.Charset.defaultCharset;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class IPAddressHandler implements SOAPHandler<SOAPMessageContext> {

    private static final String START_TIME = "start_time";
    private static final String SHOW_ADDS_FAULT_TEXT = "Show adS";
    public static final String REQUEST_ID_HEADER_KEY = "X-Request-ID";

    @Override
    public void close(MessageContext arg0) {

    }

    @Override
    public boolean handleFault(SOAPMessageContext arg0) {
        try {
            if (SHOW_ADDS_FAULT_TEXT.equals(arg0.getMessage().getSOAPBody().getFault().getFaultString())) {
                return true; // Don't add ads errors in error count because it is normal response
            }
        } catch (Exception e) {
            // Else ignore it and count as error
        }
        HttpServletRequest req = (HttpServletRequest) arg0.get(MessageContext.SERVLET_REQUEST);
        long timeSpent = 0;
        if (req != null) {
            Object startTimeObject = req.getAttribute(START_TIME);
            if (startTimeObject != null) {
                timeSpent = System.currentTimeMillis() - (long) startTimeObject;
                log.info("Response in {} ms", timeSpent);
            }
        }
        StatisticCounter.countResponse(req.getRemoteAddr(), timeSpent, true);
        return true;
    }

    private String getIPFromHostname(String hostname) {
        if (!Character.isDigit(hostname.toCharArray()[0])) {
            try {
                return InetAddress.getByName(hostname).getHostAddress();
            } catch (Exception e) {
            }
        }
        return "";
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean response = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        HttpServletRequest req = (HttpServletRequest) context.get(MessageContext.SERVLET_REQUEST);

        if (!response) {

            String ipAddress = req.getRemoteAddr();
            req.setAttribute(START_TIME, System.currentTimeMillis());
            log.info("Called from " + ipAddress);
            StatisticCounter.countRequest(ipAddress);

            String requestId = req.getHeader(REQUEST_ID_HEADER_KEY);
            if (requestId != null) {
                log.info("X-Request-ID: " + requestId);
            }

            String whiteList = LinkAppProperties.getIPAddressWhiteList();
            if (!StringUtils.isBlank(whiteList) && !"*".equals(whiteList)) {
                // Need to check white list
                boolean inList = false;
                for (String ip : whiteList.split(",")) {
                    if (ip != null && (ip.equals(ipAddress) || (getIPFromHostname(ip).equals(ipAddress)))) {
                        inList = true;
                        break;
                    }
                }
                if (!inList) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    try {
                        context.getMessage().writeTo(stream);
                        log.info("IP address " + ipAddress + " is not in white list, deny access. \nRequest:" + stream.toString(defaultCharset().name()));
                    } catch (SOAPException | IOException ignored) {
                    }
                    StatisticCounter.countResponse(ipAddress, System.currentTimeMillis() - (long) req.getAttribute(START_TIME), true);
                    generateSOAPErrMessage(context.getMessage(), "IP address " + ipAddress + " is not in white list, deny access");
                }
            }
            String blackList = LinkAppProperties.getIPAddressBlackList();
            if (!StringUtils.isBlank(blackList)) {
                // Need to check black list
                for (String ip : blackList.split(",")) {
                    if (ip.equals(ipAddress)) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        try {
                            context.getMessage().writeTo(stream);
                            log.info("IP address " + ipAddress + " is in black list, deny access. \nRequest:" + stream.toString(defaultCharset().name()));
                        } catch (SOAPException | IOException ignored) {
                        }
                        StatisticCounter.countResponse(ipAddress, System.currentTimeMillis() - (long) req.getAttribute(START_TIME), true);
                        generateSOAPErrMessage(context.getMessage(), "IP address " + ipAddress + " is in black list, deny access");
                    }
                }
            }

        } else {
            long timeSpent = System.currentTimeMillis() - (long) req.getAttribute(START_TIME);
            log.info("Response in " + timeSpent + " ms");
            boolean hasError = false;
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                context.getMessage().writeTo(stream);
                String message = new String(stream.toByteArray(), UTF_8);
                hasError = StringUtils.contains(message, "&lt;exception&gt;") ||
                        StringUtils.contains(message, "&lt;result&gt;failed") ||
                        StringUtils.contains(message, "&lt;status&gt;ERROR");
            } catch (Exception e) {

            }
            StatisticCounter.countResponse(req.getRemoteAddr(), timeSpent, hasError);
        }
        return true;
    }

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    private void generateSOAPErrMessage(SOAPMessage msg, String reason) {
        try {
            SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
            SOAPFault soapFault = soapBody.addFault();
            soapFault.setFaultString(reason);
            throw new SOAPFaultException(soapFault);
        } catch (SOAPException e) {
        }
    }
}
