package work.in.progress.hospitalmanagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ApplicationContextSingletonTest implements ApplicationContextAware {

    private ConfigurableApplicationContext configurableApplicationContext;

    @Test
    public void setAndGetContextTest() {
        ApplicationContextSingleton.setContext(null);
        ApplicationContextSingleton.setContext(configurableApplicationContext);
        ConfigurableApplicationContext context = ApplicationContextSingleton.getContext();
        assertEquals(configurableApplicationContext, context);
    }

    @Test(expected = IllegalStateException.class)
    public void setContextAgainTest() {
        ApplicationContextSingleton.setContext(null);
        ApplicationContextSingleton.setContext(configurableApplicationContext);
        ApplicationContextSingleton.setContext(configurableApplicationContext);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}