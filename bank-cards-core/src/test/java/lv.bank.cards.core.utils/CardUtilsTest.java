package lv.bank.cards.core.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CardUtilsTest {

    @Test
    public void checkGalactico() {
        assertEquals("1", CardUtils.checkGalactico("4921755123654321"));
        assertEquals("1", CardUtils.checkGalactico("4775735123654321"));
        assertEquals("0", CardUtils.checkGalactico("1772735123654321"));
        assertEquals("0", CardUtils.checkGalactico("5775735123654321"));
    }

    @Test
    public void getCardType() {
        assertEquals("visa", CardUtils.getCardType("4921755123654321"));
        assertEquals("master", CardUtils.getCardType("5921755123654321"));
        assertEquals("maestro", CardUtils.getCardType("6921755123654321"));
        assertNull(CardUtils.getCardType("1921755123654321"));
        assertNull(CardUtils.getCardType("2921755123654321"));
        assertNull(CardUtils.getCardType("3921755123654321"));
        assertNull(CardUtils.getCardType("7921755123654321"));
        assertNull(CardUtils.getCardType("8921755123654321"));
        assertNull(CardUtils.getCardType("9921755123654321"));
        assertNull(CardUtils.getCardType("0921755123654321"));
    }
}
