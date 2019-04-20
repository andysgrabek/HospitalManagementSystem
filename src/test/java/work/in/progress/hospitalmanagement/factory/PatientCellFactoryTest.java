package work.in.progress.hospitalmanagement.factory;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.control.ListView;
import org.junit.Test;
import org.junit.runner.RunWith;
import work.in.progress.hospitalmanagement.cell.PatientCell;
import work.in.progress.hospitalmanagement.model.Patient;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class PatientCellFactoryTest {

    @Test
    public void call() {
        ListView<Patient> listView = new ListView<>();
        PatientCellFactory patientCellFactory = new PatientCellFactory();
        PatientCell cell = (PatientCell) patientCellFactory.call(listView);
        assertNotNull(cell);
    }
}