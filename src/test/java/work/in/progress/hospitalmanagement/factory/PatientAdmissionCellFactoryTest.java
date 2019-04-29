package work.in.progress.hospitalmanagement.factory;

import javafx.scene.control.ListView;
import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;

import static org.junit.Assert.assertNotNull;

public class PatientAdmissionCellFactoryTest {

    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();

    @Test
    public void callTest() {
        ListView<Patient> listView = new ListView<>();
        PatientAdmissionCellFactory factory = new PatientAdmissionCellFactory();
        assertNotNull(factory.call(listView));
    }
}