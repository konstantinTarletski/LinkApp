package lv.bank.cards.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextUtils {

    public static final char[] NATIONAL_CHARACTERS = {'Ā','Ä','Ą','Ē','Ę','Ė','Ī','Į','Ķ','Ļ','Ģ','Ņ','Š','Ū','Ų','Č','Ž','Ü','Õ','Ö'};
    public static final char[] REPLACEMENT_CHARACTERS = {'A','A','A','E','E','E','I','I','K','L','G','N','S','U','U','C','Z','U','O','O'};

    public static String replaceNationalCharactersWithLatin(String text){
        if (text != null){
            for (char character : text.toCharArray()){
                int charPosition = String.valueOf(NATIONAL_CHARACTERS).indexOf(Character.toUpperCase(character));
                if (charPosition != -1){
                    Character replaceWith = REPLACEMENT_CHARACTERS[charPosition];
                    if (Character.isLowerCase(character)){
                        replaceWith = Character.toLowerCase(replaceWith);
                    }
                    text = text.replace(character, replaceWith);
                }
            }
        }
        return text;
    }

    /**
     * Calculates similarity (0-100) between two strings.
     */
    public static int similarityPercentage(String string1, String string2) {
        if (string1 == null || string2 == null){
            return 0;
        }

        String longer = string1;
        String shorter = string2;

        if (string1.length() < string2.length()) {
            longer = string2;
            shorter = string1;
        }

        int longerLength = longer.length();
        // both strings are zero length
        if (longerLength == 0) {
            return 100;
        }

        return (int) Math.round((longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) / (double) longerLength * 100);
    }

    public static String normalizeNameForDiff(String name){
        return TextUtils.replaceNationalCharactersWithLatin(name).toUpperCase();
    }

    public static String removeEscapeCharacter(String text, boolean escape) {
        if (!StringUtils.isBlank(text) && escape)
            text = text.replace("\\\\", "!~~!").replace("\\", "").replace("!~~!", "\\");
        return text;
    }
}
