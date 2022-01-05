package lv.bank.cards.dbsynchronizer.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

//@Slf4j
public abstract class AbstractSpecialConverter {

    private static final Logger log = Logger.getLogger(AbstractSpecialConverter.class);

    public Serializable convert(Object s) {
        Object o = null;
        try {
            o = getDestinationClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("convert, getDestinationClass", e);
        }

        try {
            BeanUtils.copyProperties(o, s);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("convert, copyProperties", e);
            throw new RuntimeException(e);
        }
        return (Serializable) o;
    }

    public abstract Class<?> getSourceClass();

    public abstract Class<?> getDestinationClass();
}
