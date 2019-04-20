package work.in.progress.hospitalmanagement.cell;

import de.saxsys.javafx.test.JfxRunner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class PatientCellTest {

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