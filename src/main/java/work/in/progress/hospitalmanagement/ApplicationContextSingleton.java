package work.in.progress.hospitalmanagement;

import org.springframework.context.ConfigurableApplicationContext;

public final class ApplicationContextSingleton {

    private static ConfigurableApplicationContext context;

    private ApplicationContextSingleton() {

    }

    public static void setContext(ConfigurableApplicationContext ctx) {
        if (context == null || ctx == null) {
            context = ctx;
        } else {
          throw new IllegalStateException("Context already set");
        }
    }

    public static ConfigurableApplicationContext getContext() {
        if (context == null) {
            throw new IllegalStateException("Context not set");
        } else {
            return context;
        }
    }
}
