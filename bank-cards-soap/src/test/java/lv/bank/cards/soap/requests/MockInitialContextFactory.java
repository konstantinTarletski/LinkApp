package lv.bank.cards.soap.requests;

import javax.naming.Context;
import javax.naming.spi.InitialContextFactory;
import java.util.Hashtable;

/**
 * Class which helps to mock initialize context
 */
public class MockInitialContextFactory implements InitialContextFactory {

    private static final ThreadLocal<Context> currentContext = new ThreadLocal<>();

    @Override
    public Context getInitialContext(Hashtable<?, ?> environment) {
        return currentContext.get();
    }

    public static void setCurrentContext(Context context) {
        currentContext.set(context);
    }

    public static void clearCurrentContext() {
        currentContext.remove();
    }
}
