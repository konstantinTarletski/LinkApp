
package lv.bank.cards.soap.api.rtps.soap.types;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the lv.bank.cards.soap.api.rtps.soap.types package. 
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: lv.bank.cards.soap.api.rtps.soap.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Notify }
     * 
     */
    public Notify createNotify() {
        return new Notify();
    }

    /**
     * Create an instance of {@link Fields }
     * 
     */
    public Fields createFields() {
        return new Fields();
    }

    /**
     * Create an instance of {@link NotifyResponse }
     * 
     */
    public NotifyResponse createNotifyResponse() {
        return new NotifyResponse();
    }

    /**
     * Create an instance of {@link Field }
     * 
     */
    public Field createField() {
        return new Field();
    }

}
