package lv.bank.cards.core.vendor.api.sonic.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenization;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.vendor.api.sonic.rest.dto.SonicBaseDO;
import lv.bank.cards.core.vendor.api.sonic.rest.dto.SonicCardholderDO;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class SonicRestService {

    public static final String SONIC_BASE_URL = "/service/rest/v1/linkapp/";

    public static final String CARDHOLDER_URL = "cardholder";
    public static final String PUSH_NOTIFICATION_URL = "push-notification";

    public static final ObjectMapper mapper = new ObjectMapper();

    public static CloseableHttpClient getHttpConfig() {
        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectTimeout(Integer.parseInt(LinkAppProperties.getSonicTimeout()))
                .setConnectionRequestTimeout(Integer.parseInt(LinkAppProperties.getSonicTimeout()))
                .setSocketTimeout(Integer.parseInt(LinkAppProperties.getSonicTimeout()))
                .build();
        return HttpClientBuilder
                .create()
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    public static String getSonicFullUrl(String subUrl) {
        return LinkAppProperties.getSonicHost() + ":" + LinkAppProperties.getSonicRestPort() + SONIC_BASE_URL + subUrl;
    }

    public SonicCardholderDO getCardholder(String personalCode, String country) throws IOException {

        log.info("getCardholder BEGIN");

        HttpGet request = new HttpGet(getSonicFullUrl(CARDHOLDER_URL));
        request.addHeader("personalId", personalCode);
        request.addHeader("country", country);

        CloseableHttpClient httpClient = getHttpConfig();

        long time = System.currentTimeMillis();
        try (CloseableHttpResponse response = httpClient.execute(request)) {

            log.info("Sonic done in ms " + (System.currentTimeMillis() - time));
            log.info("getCardholder, {} response core = {}", CARDHOLDER_URL, response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                SonicCardholderDO doObject = mapper.readValue(EntityUtils.toString(entity), SonicCardholderDO.class);
                log.info("getCardholder response object = {}", doObject);
                return doObject;
            }
        }
        return null;
    }

    public SonicBaseDO sendPushNotification(PcdCard pcdCard, PcdCardPendingTokenization pcdCardPendingTokenization) throws IOException {

        log.info("sendPushNotification BEGIN");

        HttpPost request = new HttpPost(getSonicFullUrl(PUSH_NOTIFICATION_URL));
        request.addHeader("Content-Type", "application/json");

        Map<String, Object> body = getPushNotificationBody(pcdCard, pcdCardPendingTokenization);

        request.setEntity(new StringEntity(mapper.writeValueAsString(body)));

        CloseableHttpClient httpClient = getHttpConfig();

        long time = System.currentTimeMillis();
        try (CloseableHttpResponse response = httpClient.execute(request)) {

            log.info("Sonic done in ms " + (System.currentTimeMillis() - time));
            log.info("sendPushNotification, {} response core = {}", PUSH_NOTIFICATION_URL, response.getStatusLine().getStatusCode());

            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                SonicBaseDO doObject = mapper.readValue(EntityUtils.toString(responseEntity), SonicBaseDO.class);
                log.info("sendPushNotification response object = {}", doObject);
                return doObject;
            }
        }
        return null;
    }

    public static Map<String, Object> getPushNotificationBody(PcdCard pcdCard, PcdCardPendingTokenization pcdCardPendingTokenization) {

        Map<String, Object> payload = new HashMap<>();
        payload.put("personalId", pcdCard.getIdCard());
        payload.put("messageId", UUID.randomUUID().toString());
        payload.put("bankCountry", pcdCard.getRegion());
        payload.put("messageCategory", "MANDATORY");
        payload.put("ttl", null);
        payload.put("source", "LinkApp");

        Map<String, Object> target = new HashMap<>();
        target.put("deviceId", pcdCardPendingTokenization.getBankAppDeviceId());
        target.put("deviceToken", pcdCardPendingTokenization.getBankAppPushId());
        target.put("deviceType", pcdCardPendingTokenization.getDeviceType().toUpperCase());

        payload.put("target", target);

        Map<String, Object> project = new HashMap<>();
        project.put("projectId", LinkAppProperties.getPushNotificationTokenProvisioningProjectId());
        payload.put("project", project);

        Map<String, Object> messageContent = new HashMap<>();
        messageContent.put("templateId", LinkAppProperties.getPushNotificationTokenProvisioningTemplateId());
        messageContent.put("custom", null);
        payload.put("messageContent", messageContent);

        List<Map<String, String>> paramsList = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("name", "{{CustomParameter}}");
        params.put("value", CardUtils.getLast4Digits(pcdCard.getCard()));
        paramsList.add(params);
        payload.put("params", paramsList);

        return payload;
    }

}
