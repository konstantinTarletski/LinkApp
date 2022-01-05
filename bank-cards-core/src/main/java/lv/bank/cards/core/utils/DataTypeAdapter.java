package lv.bank.cards.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DataTypeAdapter {

    public static Date parseDateTime(String value){
        if(StringUtils.isBlank(value)){
            return null;
        }
        try{
            return DatatypeConverter.parseDateTime(value).getTime();
        }catch (Exception e){
            log.warn("parseDateTime, Could not parse date value = {}", value, e);
            return null;
        }
    }

    public static String printDateTime(Date value){
        if (value == null) {
            return null;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(value);
            return DatatypeConverter.printDateTime(c);
        }
    }

    public static Date parseDate(String value){
        if(StringUtils.isBlank(value)){
            return null;
        }
        try{
            return DatatypeConverter.parseDate(value).getTime();
        }catch (Exception e){
            log.warn("parseDate, Could not parse date value = {}", value, e);
            return null;
        }
    }

    public static String printDate(Date value){
        if (value == null) {
            return null;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(value);
            return DatatypeConverter.printTime(c);
        }
    }

    public static Date parseTime(String value){
        if(StringUtils.isBlank(value)){
            return null;
        }
        try{
            return DatatypeConverter.parseTime(value).getTime();
        }catch (Exception e){
            log.warn("parseTime, Could not parse date value = {}", value, e);
            return null;
        }
    }

    public static String printTime(Date value){
        if (value == null) {
            return null;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(value);
            return DatatypeConverter.printDate(c);
        }
    }

}
