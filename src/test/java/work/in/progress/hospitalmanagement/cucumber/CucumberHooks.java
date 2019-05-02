package work.in.progress.hospitalmanagement.cucumber;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;
import work.in.progress.hospitalmanagement.HospitalManagementApplication;

/**
 * Provides hooks that are executed before and after each cucumber scenario with proper context.
 *
 * @author jablonskiba
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class CucumberHooks {

    /**
     * If {@code true} then cucumber tests are run in a headless mode
     */
    private static final boolean IS_HEADLESS = true;

    static {
        if (IS_HEADLESS) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    @Before
    public void beforeEachScenario() {
        try {
            ApplicationContextSingleton.setContext(null);
            final SpringApplicationBuilder springApplicationBuilder =
                    new SpringApplicationBuilder(HospitalManagementApplication.class);
            ApplicationContextSingleton.setContext(springApplicationBuilder.headless(false).run());
            ApplicationTest.launch(HospitalManagementApplication.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void afterEachScenario() {
        try {
            ConfigurableApplicationContext context = ApplicationContextSingleton.getContext();
            context.close();
            FxToolkit.cleanupStages();
        } catch (Exception ignored) {
        }
    }

}
