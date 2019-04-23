package work.in.progress.hospitalmanagement;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * Singleton class to hold a reference to the spring {@link ConfigurableApplicationContext} to be used in classes that
 * are not annotated as spring components or just for utility purposes.
 * @author Andrzej Grabowski
 */
public final class ApplicationContextSingleton {

    private static ConfigurableApplicationContext context;

    /**
     * Hidden constructor to disable instantiation
     */
    private ApplicationContextSingleton() {

    }

    /**
     * Method to set the spring context. The context can only be reset to null.
     * @param ctx context to be set
     * @throws IllegalStateException if context is attempted to be changed
     */
    public static void setContext(ConfigurableApplicationContext ctx) {
        if (context == null || ctx == null) {
            context = ctx;
        } else {
          throw new IllegalStateException("Context already set");
        }
    }

    /**
     * Method returning the spring context.
     * @return the current context
     * @throws IllegalStateException when the context is null
     */
    public static ConfigurableApplicationContext getContext() {
        if (context == null) {
            throw new IllegalStateException("Context not set");
        } else {
            return context;
        }
    }
}
