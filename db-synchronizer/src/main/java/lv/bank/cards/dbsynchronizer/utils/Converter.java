package lv.bank.cards.dbsynchronizer.utils;

import lv.bank.cards.dbsynchronizer.BusinessException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Converter {

    private static Map<String, AbstractSpecialConverter> availableConverters = new HashMap<>();

    public static void defConverter(AbstractSpecialConverter conv) {
        String converterSignature = conv.getSourceClass().getName() + "->" + conv.getDestinationClass().getName();
        if (availableConverters.containsKey(converterSignature)) {
            availableConverters.remove(converterSignature);
        }
        availableConverters.put(converterSignature, conv);
    }

    public static boolean isConverterAvailble(Class<?> s, Class<?> d) {
        return availableConverters.containsKey(s.getName() + "->" + d.getName());
    }

    public static Serializable convert(Object s, Class<?> d) throws BusinessException {
        AbstractSpecialConverter c = availableConverters.get(s.getClass().getName() + "->" + d.getName());
        if (c == null) {
            throw new BusinessException("No defined converters for [" + s.getClass().getName() + "->" + d.getName() + "]");
        }
        return c.convert(s);
    }

}
