package work.in.progress.hospitalmanagement.cell;

import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PatientCellTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void updateItemTestNullItem() {
        Patient patient = null;
        PatientCell cell = new PatientCell();
        cell.updateItem(patient, false);
        assertNull(cell.getGraphic());
    }

    @Test
    public void updateItemTestEmptyCell() {
        Patient patient = Mocks.patient();
        PatientCell cell = new PatientCell();
        cell.updateItem(patient, true);
        assertNull(cell.getGraphic());
    }

    @Test
    public void updateItemTestLoadedCell() {
        Patient patient = Mocks.patient();
        PatientCell cell = new PatientCell();
        cell.updateItem(patient, false);
        assertNotNull(cell.getGraphic());
    }
}