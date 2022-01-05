package lv.nordlb.cards;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

/**
 * Class which helps to mock initialize context
 */
public class MockInitialContextFactory implements InitialContextFactory {

    private static final ThreadLocal<Context> currentContext = new ThreadLocal<>();

    @Override
    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        return currentContext.get();
    }

    public static void setCurrentContext(Context context) {
        currentContext.set(context);
    }

    public static void clearCurrentContext() {
        currentContext.remove();
    }
}
