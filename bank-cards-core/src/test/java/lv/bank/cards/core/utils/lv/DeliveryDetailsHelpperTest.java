package lv.bank.cards.core.utils.lv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class to test Delivery address details helper
 * @author saldabols
 */
public class DeliveryDetailsHelpperTest {
	
	@Test
	public void testHelper(){
		// Create form many fields
		DeliveryDetailsHelper helper = new DeliveryDetailsHelper("1", "LV", "Rigas reg", "Riga c", "Skankstes 12", "LV-1012", "DNB banka");
		Assert.assertEquals("1", helper.getLanguage());
		Assert.assertEquals("LV", helper.getCountry());
		Assert.assertEquals("Rigas reg", helper.getRegion());
		Assert.assertEquals("Riga c", helper.getCity());
		Assert.assertEquals("Skankstes 12", helper.getAddress());
		Assert.assertEquals("LV-1012", helper.getZipCode());
		Assert.assertEquals("DNB banka", helper.getAdditionalFields());
		Assert.assertEquals("Rigas reg, Riga c, Skankstes 12, LV-1012", helper.getAddressString());
		Assert.assertEquals("1LVRigas reg                            Riga c                                  Skankstes 12                                      LV-1012         DNB banka                                         ", helper.getDetails());
	
		// Create from one field
		helper = new DeliveryDetailsHelper("1LVRigas reg                            Riga c                                  Skankstes 12                                      LV-1012         DNB banka                                         ");
		Assert.assertEquals("1", helper.getLanguage());
		Assert.assertEquals("LV", helper.getCountry());
		Assert.assertEquals("Rigas reg", helper.getRegion());
		Assert.assertEquals("Riga c", helper.getCity());
		Assert.assertEquals("Skankstes 12", helper.getAddress());
		Assert.assertEquals("LV-1012", helper.getZipCode());
		Assert.assertEquals("DNB banka", helper.getAdditionalFields());
		Assert.assertEquals("Rigas reg, Riga c, Skankstes 12, LV-1012", helper.getAddressString());
		Assert.assertEquals("1LVRigas reg                            Riga c                                  Skankstes 12                                      LV-1012         DNB banka                                         ", helper.getDetails());
	
		// Test address string if missing some text
		helper.setCity(null);
		Assert.assertEquals("Rigas reg, Skankstes 12, LV-1012", helper.getAddressString());
		helper.setZipCode(null);
		Assert.assertEquals("Rigas reg, Skankstes 12", helper.getAddressString());
		
		// Test max length for fields
		helper = new DeliveryDetailsHelper("12", "123", "1234567890123456789012345678901234567890", "12345678901234567890123456789012345678901",
				"123456789012345678901234567890123456789012345678901", "12345678901234567890", "123456789012345678901234567890123456789012345678901");
		Assert.assertEquals("1", helper.getLanguage());
		Assert.assertEquals("12", helper.getCountry());
		Assert.assertEquals("1234567890123456789012345678901234567", helper.getRegion());
		Assert.assertEquals("1234567890123456789012345678901234567890", helper.getCity());
		Assert.assertEquals("12345678901234567890123456789012345678901234567890", helper.getAddress());
		Assert.assertEquals("1234567890123456", helper.getZipCode());
		Assert.assertEquals("12345678901234567890123456789012345678901234567890", helper.getAdditionalFields());
		assertFalse(helper.getDetails().contains(" "));
		Assert.assertEquals(196, helper.getDetails().length());
	}
}
