package work.in.progress.hospitalmanagement.cell;

import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class PatientAdmissionCellTest {

    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();

    @Test
    public void updateItemTest_hasAdmission_Outpatient() {
        Patient p = Mocks.patient();
        p.setAdmission(Mocks.outpatientAdmission());
        PatientAdmissionCell cell = new PatientAdmissionCell();
        cell.updateItem(p, false);
        assertFalse(cell.getHBox().getChildren().contains(cell.getEditButton()));
    }

    @Test
    public void updateItemTest_hasAdmission_Inpatient() {
        Patient item = Mocks.patient();
        item.setAdmission(Mocks.inpatientAdmission());
        PatientAdmissionCell cell = new PatientAdmissionCell();
        cell.updateItem(item, false);
        assertEquals("ID: "
                + item.getId()
                + ", "
                + item.getName()
                + " " + item.getSurname()
                + " admitted to "
                + item.getAdmission().get().getDepartment().getName(),
                cell.getLabel().getText());
    }

    @Test
    public void updateItemTest_noAdmission() {
        Patient item = Mocks.patient();
        item.setAdmission(null);
        PatientAdmissionCell cell = new PatientAdmissionCell();
        cell.updateItem(item, false);
        assertEquals("ID: "
                + item.getId()
                + ", "
                + item.getName()
                + " " + item.getSurname(), cell.getLabel().getText());
    }

    @Test
    public void updateItemTest_nullItem() {
        PatientAdmissionCell cell = new PatientAdmissionCell();
        cell.updateItem(null, false);
        assertNull(cell.getGraphic());
    }

}