package lv.bank.cards.core.utils;

import java.util.Arrays;
import java.util.List;

public class VisaTokenServicesUtils {

    public static final String BINS_SEPARATOR = ",";
    public static final int MINIMAL_CARD_NUMBER_LENGTH = 6;

    public static boolean isBinEligible(String cardNumber){
        if(cardNumber == null || cardNumber.length() < MINIMAL_CARD_NUMBER_LENGTH){
            return false;
        }
        String bins = LinkAppProperties.getVisaTokenServiceBins();
        if(bins == null || bins.isEmpty()){
            return false;
        }
        List<String> binList = Arrays.asList(bins.split(BINS_SEPARATOR));
        if(binList.isEmpty()){
            return false;
        }

        return binList.contains(cardNumber.substring(0,MINIMAL_CARD_NUMBER_LENGTH));
    }

}
