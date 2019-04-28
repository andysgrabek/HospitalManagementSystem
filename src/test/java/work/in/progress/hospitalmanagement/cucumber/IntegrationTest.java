package work.in.progress.hospitalmanagement.cucumber;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;
import work.in.progress.hospitalmanagement.HospitalManagementApplication;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

/**
 * A common class for all integration tests that provides proper Spring Boot and JavaFX initialisation and operation.
 * All cucumber step definition classes should extend it.
 *
 * @author jablonskiba
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class IntegrationTest extends ApplicationTest {

    private final static boolean IS_HEADLESS = false;

    static {
        if (IS_HEADLESS) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }

        try {
            ApplicationContextSingleton.setContext(SpringApplication.run(HospitalManagementApplication.class));
            ApplicationTest.launch(HospitalManagementApplication.class);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void start(Stage stage) {
        stage.show();
    }

    @After
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    /**
     * Sifts through stored nodes by their id ("#id"), their class (".class"), or the text it has ("text"),
     * depending on the query used, and returns the first {@link Node} that meets the given query.
     *
     * @param query the query to use
     * @param <T>   the type that extends {@code Node}
     * @return the first {@link Node} that matches the given query
     * @throws NoSuchElementException if the queried node was not found
     */
    @SuppressWarnings("unchecked")
    public <T extends Node> T find(final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }

}
