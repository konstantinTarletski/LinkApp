package lv.bank.cards.core.utils;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;

/**
 * Test class to test Crypting with 3DEs methods
 * @author saldabols
 */
public class Crypting3DESTest {

	@Test
	public void encrypting(){
		addLinkAppProperty(LinkAppProperties.AUTHENTIFICATION_CODE_KEY, "F6A5508BC0EB4B33F58B5C120FB76A47F6A5508BC0EB4B33");
		assertEquals("3789836EA9AA0D89", Crypting3DES.encrypting("4312"));
		assertEquals("AFC88DC4A9FE8733", Crypting3DES.encrypting("12345678"));
		assertEquals("AFC88DC4A9FE873364EA456AFF635CF1", Crypting3DES.encrypting("123456789"));
	}

	@Test
	public void decrypting(){
		addLinkAppProperty(LinkAppProperties.AUTHENTIFICATION_CODE_KEY, "F6A5508BC0EB4B33F58B5C120FB76A47F6A5508BC0EB4B33");
		assertEquals("4312", Crypting3DES.decrypting("3789836EA9AA0D89"));
		assertEquals("12345678", Crypting3DES.decrypting("AFC88DC4A9FE8733"));
		assertEquals("123456789", Crypting3DES.decrypting("AFC88DC4A9FE873364EA456AFF635CF1"));
	}

	public static void addLinkAppProperty(String name, String value){
		try {
			Field prop = LinkAppProperties.class.getDeclaredField("PROPERTIES");
			prop.setAccessible(true);
			Properties props = (Properties)prop.get(null);
			if(props == null){
				prop.set(null, new Properties());
			}
			props = (Properties)prop.get(LinkAppProperties.class);
			props.put(name, value);

			Field reread = LinkAppProperties.class.getDeclaredField("REREAD");
			reread.setAccessible(true);
			Field modifiersField = Field.class.getDeclaredField( "modifiers" );
			modifiersField.setAccessible( true );
			modifiersField.setInt( reread, reread.getModifiers() & ~Modifier.FINAL );
			reread.set(null, new AtomicBoolean(false));

		} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
