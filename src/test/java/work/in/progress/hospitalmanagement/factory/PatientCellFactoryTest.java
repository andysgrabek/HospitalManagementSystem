package work.in.progress.hospitalmanagement.factory;

import javafx.scene.control.ListView;
import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.cell.PatientCell;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;

import static org.junit.Assert.assertNotNull;

public class PatientCellFactoryTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void call() {
        ListView<Patient> listView = new ListView<>();
        PatientCellFactory patientCellFactory = new PatientCellFactory();
        PatientCell cell = (PatientCell) patientCellFactory.call(listView);
        assertNotNull(cell);
    }
}