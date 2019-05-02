package work.in.progress.hospitalmanagement.cucumber;

import javafx.scene.Node;
import javafx.stage.Stage;
import org.testfx.framework.junit.ApplicationTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

/**
 * A common class for all integration tests that provides JavaFX related functions.
 * All cucumber step definition classes should extend it in order to interact with an interface.
 *
 * @author jablonskiba
 */
public abstract class ApplicationFXTest extends ApplicationTest {

    static LocalDate parseDateString(String dateString) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(dateString, formatter);
    }

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
