package work.in.progress.hospitalmanagement.factory;

import javafx.scene.control.ListView;
import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;

import static org.junit.Assert.*;

public class AppointmentCellFactoryTest {

    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();

    @Test
    public void callTest() {
        AppointmentCellFactory factory = new AppointmentCellFactory();
        assertNotNull(factory.call(new ListView<>()));
    }
}