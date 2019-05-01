package work.in.progress.hospitalmanagement.cucumber;

import javafx.scene.Node;
import javafx.stage.Stage;
import org.testfx.framework.junit.ApplicationTest;

import java.util.NoSuchElementException;

/**
 * A common class for all integration tests that provides proper Spring Boot and JavaFX initialisation and operation.
 * All cucumber step definition classes should extend it.
 *
 * @author jablonskiba
 */
public abstract class IntegrationTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        stage.show();
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
    <T extends Node> T find(final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }

}
