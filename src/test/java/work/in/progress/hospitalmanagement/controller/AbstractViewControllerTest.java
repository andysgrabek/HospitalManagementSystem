package work.in.progress.hospitalmanagement.controller;

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
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;

import java.net.URL;
import java.util.ResourceBundle;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = { AbstractViewControllerTest.class, TestViewController.class })
public class AbstractViewControllerTest implements ApplicationContextAware {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    private AbstractViewController controller;
    private ConfigurableApplicationContext context;
    private Stage stage;


    @Before
    public void setUp() {
        ApplicationContextSingleton.setContext(null);
        ApplicationContextSingleton.setContext(context);
        controller = AbstractViewController.instantiateViewController(TestViewController.class);
        Scene s = new Scene(controller.getRoot());
        (stage = new Stage()).setScene(s);
    }

    @After
    public void tearDown() {
        ApplicationContextSingleton.setContext(null);
    }

    @Test
    public void instantiateViewController_correctNaming() {
        AbstractViewController vc = AbstractViewController.instantiateViewController(TestViewController.class);
        assertNotNull(vc);
    }

    @Test(expected = IllegalStateException.class)
    public void instantiateViewController_unknownControllerClass() {
        AbstractViewController unknown = new AbstractViewController() {
            @Override
            public void initialize(URL location, ResourceBundle resources) {

            }
        };
        AbstractViewController.instantiateViewController(unknown.getClass());
    }

    @Test(expected = IllegalStateException.class)
    public void instantiateViewController_nullContext() {
        ApplicationContextSingleton.setContext(null);
        AbstractViewController vc = AbstractViewController.instantiateViewController(TestViewController.class);
    }

    @Test
    public void presentViewController_animated() {
        controller.presentViewController(AbstractViewController.instantiateViewController(TestViewController.class), true);
    }

    @Test
    public void presentViewController_notAnimated() {
        AbstractViewController vc = AbstractViewController.instantiateViewController(TestViewController.class);
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
    public void getRoot_nullRoot() {
        AbstractViewController vc = new TestViewController();
        vc.getRoot();
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