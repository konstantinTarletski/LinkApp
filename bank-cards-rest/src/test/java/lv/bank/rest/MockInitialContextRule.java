package lv.bank.rest;

import lombok.RequiredArgsConstructor;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.naming.Context;

/**
 * Class which helps to mock initialize context
 */
@RequiredArgsConstructor
public class MockInitialContextRule implements TestRule {

    private final Context context;

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                System.setProperty(Context.INITIAL_CONTEXT_FACTORY, MockInitialContextFactory.class.getName());
                MockInitialContextFactory.setCurrentContext(context);
                try {
                    base.evaluate();
                } finally {
                    System.clearProperty(Context.INITIAL_CONTEXT_FACTORY);
                    MockInitialContextFactory.clearCurrentContext();
                }
            }
        };
    }
}
