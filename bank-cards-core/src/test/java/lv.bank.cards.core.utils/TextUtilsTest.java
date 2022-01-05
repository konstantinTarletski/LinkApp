package lv.bank.cards.core.utils;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TextUtilsTest {

    @Test
    public void replaceNationalCharactersWithLatinTest(){
        assertEquals(null, TextUtils.replaceNationalCharactersWithLatin(null));
        assertEquals("Peteris Kocins", TextUtils.replaceNationalCharactersWithLatin("Pēteris Kočinš"));
        assertEquals("AAAEEEIIKLGNSUUCZUOOaaaeeeiiklgnsuuczuoo",
                TextUtils.replaceNationalCharactersWithLatin("ĀÄĄĒĘĖĪĮĶĻĢŅŠŪŲČŽÜÕÖāäąēęėīįķļģņšūųčžüõö"));
    }

    @Test
    public void similarityPercentageTest(){
        assertEquals(0, TextUtils.similarityPercentage(null, null));
        assertEquals(0, TextUtils.similarityPercentage(null, "void"));
        assertEquals(0, TextUtils.similarityPercentage("The Hulk", "Iron Man"));
        assertEquals(30, TextUtils.similarityPercentage("Batman", "Bruce Wane"));
        assertEquals(82, TextUtils.similarityPercentage("Clark Kent", "Claire Kent"));
        assertEquals(92, TextUtils.similarityPercentage("Peter Parker", "Pete Parker"));
        assertEquals(100, TextUtils.similarityPercentage("Superman", "Superman"));
    }

    @Test
    public void normalizeNameForDiffTest(){
        assertEquals("PETERIS KOCINS", TextUtils.normalizeNameForDiff("Pēteris Kočinš"));
    }
}