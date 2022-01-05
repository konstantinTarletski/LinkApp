package lv.bank.cards.auth;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;

public class AuthRequestMaster {
    private static Logger log = Logger.getLogger(AuthRequestMaster.class);
    public static final String jaxbPackage = "lv.bank.cards.auth";
    private static int lastField = 128;

    public static ISOMsg toISOMsg(Iso iso) throws ISOException {
        ISOMsg m = new ISOMsg();

        m.setMTI(iso.getFld000());
        for (int i = 1; i <= lastField; i++) {
            String si = Integer.toString(i);

            try {
                String isoVal = (String) Iso.class.getMethod("getFld" + "00".substring(0, 3 - si.length()).concat(si), new Class[]{}).invoke(iso, new Object[]{});
                if (isoVal != null) {
                    log.info("Fld" + i + "='" + isoVal + "'");
                    m.set(i, isoVal);
                    //	log.info(i+":"+(String) Iso.class.getMethod("getFld"+"00".substring(0, 3 - si.length()).concat(si), new Class[] {}).invoke(iso, new Object[] {})+":"+(m.hasField(i)?"NULL":m.getValue(i)));
                    log.info("m[" + i + "]='" + m.getValue(i));
                }
            } catch (SecurityException e) {
                e.printStackTrace();
                throw new ISOException("Error during converting request to ISO", e);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new ISOException("Error during converting request to ISO", e);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new ISOException("Error during converting request to ISO", e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new ISOException("Error during converting request to ISO", e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new ISOException("Error during converting request to ISO", e);
            }
        }

        return m;
    }

    public static Iso fromISOMsg(ISOMsg m) throws ISOException {
        Iso iso = new Iso();

        for (int i = 0; i <= lastField; i++) {
            String si = Integer.toString(i);

            try {

                Iso.class.getMethod("setFld" + "00".substring(0, 3 - si.length()).concat(si), new Class[]{String.class}).invoke(iso, new Object[]{m.getString(i)});


            } catch (SecurityException e) {
                e.printStackTrace();
                throw new ISOException("Error during converting request to ISO", e);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new ISOException("Error during converting request to ISO", e);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new ISOException("Error during converting request to ISO", e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new ISOException("Error during converting request to ISO", e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new ISOException("Error during converting request to ISO", e);
            }
        }

        return iso;
    }

    public static AuthRequest instantiate(String fn) throws AuthorisationException {

        try {
            InputSource inSource = new InputSource(new StringReader(fn));
            log.debug("creating unmarshaller");
            Unmarshaller unmarshaller = AuthRequestMaster.getUnmarshaller(AuthRequestMaster.setContext(jaxbPackage));
            log.info("Unmarshalling:\n" + fn);
            try {
                AuthRequest request = (AuthRequest) unmarshaller.unmarshal(inSource);
                log.info("SUccessfully unmarshalled:\n");
                log.info(request.getIso().toString());
                return request;
            } catch (RuntimeException e) {
                log.debug(e + ":" + e.getMessage());
                e.printStackTrace();
                throw new AuthorisationException(e);
            } catch (Exception e) {
                log.debug(e + " " + e.getMessage());
                e.printStackTrace();
                throw new AuthorisationException(e);
            }
        } catch (/*JAXB*/RuntimeException e) {
            throw new AuthorisationException(e);
        }

    }

    public static String serialize(Object r) throws AuthorisationException {
        Marshaller m;

        try {
            m = AuthRequestMaster.setContext(jaxbPackage).createMarshaller();
        } catch (JAXBException e) {
            throw new AuthorisationException(e);
        }

        try {
            Writer w = new StringWriter();
            m.marshal(r, w);
            return w.toString();

        } catch (JAXBException e) {
            throw new AuthorisationException(e);
        }
    }

    public static JAXBContext setContext(String jaxbPackage) {
        JAXBContext jc = null;
        try {

            // Set the JAXB context to the package name we uses
            // when we created all the JAXB classes from the XSD
            jc = JAXBContext.newInstance(jaxbPackage);
            log.debug("setContext[" + jaxbPackage + "]");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        log.debug("setContext returns " + jc);
        return jc;
    }

    public static Unmarshaller getUnmarshaller(JAXBContext jc) {
        Unmarshaller u = null;

        try {
            // Create an unmarshaller (unmarshal from XML to Java objects)
            u = jc.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return u;
    }
}
