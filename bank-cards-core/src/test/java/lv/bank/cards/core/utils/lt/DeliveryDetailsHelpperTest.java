package lv.bank.cards.core.utils.lt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test class to test Delivery address details helper
 *
 * @author saldabols
 */
public class DeliveryDetailsHelpperTest {

    @Test
    public void deliveryDetailsFromFields() {
        DeliveryDetailsHelper details = new DeliveryDetailsHelper(
                //"2",
                "LT", "Viïòas apriòíis", "Viïòa", "Brîvîbas 100", "LT-1012",
                "Main Bank", "some");
        assertEquals("Viïòas apriòíis, Viïòa, Brîvîbas 100, LT-1012", details.getAddressString());
        assertEquals(" LTViïòas apriòíis                      Viïòa                                   Brîvîbas 100                                                                    LT-1012         Main Bank                                         some                    ", details.getDetails());

        //assertEquals("", details.getLanguage());
        assertEquals("LT", details.getCountry());
        assertEquals("Viïòas apriòíis", details.getRegion());
        assertEquals("Viïòa", details.getCity());
        assertEquals("Brîvîbas 100", details.getAddress());
        assertEquals("LT-1012", details.getZipCode());
        assertEquals("Main Bank", details.getAdditionalFields());
        assertEquals("some", details.getAddressCode());

        details = new DeliveryDetailsHelper("2LTViïòas apriòíis                      Viïòa                                   Brîvîbas 100                                                                    LT-1012         Main Bank                                         some                    ");
        assertEquals("Viïòas apriòíis, Viïòa, Brîvîbas 100, LT-1012", details.getAddressString());
        assertEquals(" LTViïòas apriòíis                      Viïòa                                   Brîvîbas 100                                                                    LT-1012         Main Bank                                         some                    ", details.getDetails());

        //assertEquals("", details.getLanguage());
        assertEquals("LT", details.getCountry());
        assertEquals("Viïòas apriòíis", details.getRegion());
        assertEquals("Viïòa", details.getCity());
        assertEquals("Brîvîbas 100", details.getAddress());
        assertEquals("LT-1012", details.getZipCode());
        assertEquals("Main Bank", details.getAdditionalFields());
        assertEquals("some", details.getAddressCode());

        // Test address string if missing some text
        details.setCity(null);
        assertEquals("Viïòas apriòíis, Brîvîbas 100, LT-1012", details.getAddressString());
        details.setZipCode(null);
        assertEquals("Viïòas apriòíis, Brîvîbas 100", details.getAddressString());

        // Test max length for fields
        details = new DeliveryDetailsHelper(
                //"12",
                "123",
                "1234567890123456789012345678901234567890",
                "12345678901234567890123456789012345678901",
                "123456789012345678901234567890123456789012345678901234567890123456789012345678901",
                "12345678901234567890",
                "123456789012345678901234567890123456789012345678901",
                "123456789012345678901234567890");
        //assertEquals("", details.getLanguage());
        assertEquals("12", details.getCountry());
        assertEquals("1234567890123456789012345678901234567", details.getRegion());
        assertEquals("1234567890123456789012345678901234567890", details.getCity());
        assertEquals("12345678901234567890123456789012345678901234567890123456789012345678901234567890", details.getAddress());
        assertEquals("1234567890123456", details.getZipCode());
        assertEquals("12345678901234567890123456789012345678901234567890", details.getAdditionalFields());
        assertEquals("123456789012345678901234", details.getAddressCode());
        assertFalse(details.getDetails().substring(1).contains(" "));
        assertEquals(250, details.getDetails().length());

        details = new DeliveryDetailsHelper(null);
        assertEquals("", details.getAddressString());
    }
}
