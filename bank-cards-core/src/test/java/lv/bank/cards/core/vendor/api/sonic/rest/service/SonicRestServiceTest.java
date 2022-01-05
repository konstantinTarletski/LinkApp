package lv.bank.cards.core.vendor.api.sonic.rest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenization;
import lv.bank.cards.core.utils.LinkAppProperties;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SonicRestServiceTest {

    private final static String CARD = "4785850000001234";

    @Test
    public void testPushNotificationBody() throws JsonProcessingException {

        PcdCard pcdCard = new PcdCard();
        pcdCard.setIdCard("idCard");
        pcdCard.setRegion("EE");
        pcdCard.setCard(CARD);

        PcdCardPendingTokenization pcdCardPendingTokenization = new PcdCardPendingTokenization();
        pcdCardPendingTokenization.setDeviceType("IOS");
        pcdCardPendingTokenization.setBankAppDeviceId("BankAppDeviceId");
        pcdCardPendingTokenization.setBankAppPushId("BankAppPushId");

        addLinkAppProperty(LinkAppProperties.PUSH_NOTIFICATION_TOKEN_PROVISIONING_TEMPLATE_ID_KEY, "templateId");
        addLinkAppProperty(LinkAppProperties.PUSH_NOTIFICATION_TOKEN_PROVISIONING_PROJECT_ID_KEY, "projectId");

        Map<String, Object> body = SonicRestService.getPushNotificationBody(pcdCard, pcdCardPendingTokenization);

        assertEquals(body.get("messageCategory"), "MANDATORY");
        assertEquals(body.get("personalId"), "idCard");
        assertEquals(body.get("source"), "LinkApp");
        assertNull(body.get("ttl"));
        assertEquals(body.get("bankCountry"), "EE");

        assertEquals(((Map<String, Object>) body.get("target")).get("deviceId"), "BankAppDeviceId");
        assertEquals(((Map<String, Object>) body.get("target")).get("deviceToken"), "BankAppPushId");
        assertEquals(((Map<String, Object>) body.get("target")).get("deviceType"), "IOS");

        assertEquals(((Map<String, Object>) body.get("project")).get("projectId"), "projectId");

        assertEquals(((Map<String, Object>) body.get("messageContent")).get("templateId"), "templateId");
        assertNull(((Map<String, Object>) body.get("messageContent")).get("custom"));

        assertEquals((((List<Map<String, Object>>) body.get("params")).get(0)).get("value"), "1234");//CARD
        assertEquals((((List<Map<String, Object>>) body.get("params")).get(0)).get("name"), "{{CustomParameter}}");

        ObjectMapper mapper = new ObjectMapper();
        String bodyString = mapper.writeValueAsString(body);
        assertNotNull(bodyString);
    }


    public static void addLinkAppProperty(String name, String value) {
        try {
            Field prop = LinkAppProperties.class.getDeclaredField("PROPERTIES");
            prop.setAccessible(true);
            Properties props = (Properties) prop.get(null);
            if (props == null) {
                prop.set(null, new Properties());
            }
            props = (Properties) prop.get(LinkAppProperties.class);
            props.put(name, value);

            Field reread = LinkAppProperties.class.getDeclaredField("REREAD");
            reread.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(reread, reread.getModifiers() & ~Modifier.FINAL);
            reread.set(null, new AtomicBoolean(false));

        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
