//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b26-ea3 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.08.29 at 03:28:07 PM EEST 
//


package lv.bank.cards.auth;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for auth-response element declaration.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;element name="auth-response">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element ref="{}iso"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "iso"
})
@XmlRootElement(name = "auth-response")
public class AuthResponse {

    protected Iso iso;

    /**
     * Gets the value of the iso property.
     *
     * @return possible object is
     * {@link Iso }
     */
    public Iso getIso() {
        return iso;
    }

    /**
     * Sets the value of the iso property.
     *
     * @param value allowed object is
     *              {@link Iso }
     */
    public void setIso(Iso value) {
        this.iso = value;
    }

}