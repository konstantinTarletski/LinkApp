package lv.bank.cards.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lv.bank.cards.core.entity.linkApp.PcdCard;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardUtils {

    public static boolean cardCouldBeValid(String card) {
        if ((card != null) && (card.length() > 5))
            return true;
        else return false;
    }

    public static String checkGalactico(String card) {
        return (card.substring(0, 7).equals("4921755") || card.substring(0, 7).equals("4775735")) ? "1" : "0";
    }

    public static String getCardType(String card) {
        if (card.substring(0, 1).equals("4")) return "visa";
        if (card.substring(0, 1).equals("5")) return "master";
        if (card.substring(0, 1).equals("6")) return "maestro";
        return null;
    }

    public static String maskCardNumber(String text) {
        return text.replaceAll("([^\\d]|^)(\\d{6})\\d{6}(\\d{4})([^\\d]|$)", "$1$2******$3$4");
    }

    public static boolean isDepositInOutsourcedAtm(String processingCode, String merchantCategoryCode,
                                                   String acquirerInstitutionIdentificationCode) {
        if (processingCode == null || merchantCategoryCode == null || acquirerInstitutionIdentificationCode == null) {
            return false;
        }

        return (processingCode.startsWith("21") && // deposit
                "6011".equals(merchantCategoryCode) && // ATM
                acquirerInstitutionIdentificationCode.matches("417720|4289170|416352|478577")); // LV exNordea or EE LHV bank
    }

    public static String composeCentreIdFromPcdCard(PcdCard pcdCard) {
        if (pcdCard == null) return null;
        return composeCentreId(pcdCard.getBankC(), pcdCard.getGroupc());
    }

    public static String composeCentreId(String bankC, String groupC) {
        return "4280020" + bankC + groupC;
    }

    public static String getLast4Digits(String number) {
        if (number != null && number.length() > 4) {
            return number.substring(number.length() - 4);
        }
        return "";
    }

}
