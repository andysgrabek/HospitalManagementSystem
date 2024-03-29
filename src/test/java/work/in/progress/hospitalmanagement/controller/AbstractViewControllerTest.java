package work.in.progress.hospitalmanagement.controller;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
import org.springframework.test.util.ReflectionTestUtils;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
              MainMenuViewController.class,
                TestViewController.class
        }
)
public class AbstractViewControllerTest implements ApplicationContextAware {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    private AbstractViewController controller;
    private ConfigurableApplicationContext context;
    private Stage stage;

    @Before
    public void setUp() throws InterruptedException {
        ApplicationContextSingleton.setContext(null);
        ApplicationContextSingleton.setContext(context);
        controller = AbstractViewController.instantiateViewController(TestViewController.class);
        Scene s = new Scene(controller.getRoot());
//        (stage = new Stage()).setScene(s);
    }

    @After
    public void tearDown() {
        ApplicationContextSingleton.setContext(null);
    }

    @Test(expected = IllegalStateException.class)
    public void getRoot_nullRoot() {
        AbstractViewController vc = new TestViewController();
        vc.getRoot();
    }

    @Test
    public void instantiateViewController_correctNaming() {
        AbstractViewController vc = AbstractViewController.instantiateViewController(TestViewController.class);
        assertNotNull(vc);
    }

    @Test
    public void backToMainMenuTest() {
        ReflectionTestUtils.invokeMethod(AbstractViewController.instantiateViewController(controller.getClass()), "backToMainMenu", new ActionEvent());
    }

    @Test
    public void presentViewController_animated() {
        controller.presentViewController(AbstractViewController.instantiateViewController(TestViewController.class), true);
    }

    @Test
    public void presentViewController_notAnimated() {
        controller.presentViewController(AbstractViewController.instantiateViewController(TestViewController.class), false);
    }

    @Test(expected = IllegalStateException.class)
    public void presentViewController_nullController() {
        controller.presentViewController(null, false);
    }

    @Test(expected = IllegalStateException.class)
    public void changeRootTest() {
        AnchorPane pane = new AnchorPane();
        AbstractViewController vc = AbstractViewController.instantiateViewController(TestViewController.class);
        vc.setRoot(pane);
    }

    @Test(expected = IllegalStateException.class)
    public void getScene_nullScene() {
        AbstractViewController vc = new TestViewController();
        vc.getScene();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;
    }
}