package work.in.progress.hospitalmanagement.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                SplashScreenViewController.class,
                MainMenuViewController.class
        }
)
public class SplashScreenViewControllerTest implements ApplicationContextAware {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    private ConfigurableApplicationContext context;

    @Before
    public void setUp() {
        ApplicationContextSingleton.setContext(null);
        ApplicationContextSingleton.setContext(context);
    }

    @After
    public void tearDown() {
        ApplicationContextSingleton.setContext(null);
    }

    @Test
    public void initialize() {
        AbstractViewController vc = AbstractViewController.instantiateViewController(SplashScreenViewController.class);
        assertNotNull(vc);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;
    }
}