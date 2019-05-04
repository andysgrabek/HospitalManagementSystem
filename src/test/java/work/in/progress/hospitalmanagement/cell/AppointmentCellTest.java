package work.in.progress.hospitalmanagement.cell;

import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class AppointmentCellTest {

    @Rule
    public JavaFXThreadingRule threadingRule = new JavaFXThreadingRule();

    @Test
    public void updateItemTest() {
        OutpatientAdmission item = Mocks.outpatientAdmission();
        AppointmentCell appointmentCell = new AppointmentCell();
        appointmentCell.updateItem(item, false);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        assertEquals(item.getPatient().getName()
                        + " " + item.getPatient().getSurname()
                        + " appointment for "
                        + timeFormatter.format(item.getVisitDate())
                        + " in " + item.getDepartment().getName(),
                appointmentCell.getLabel().getText());
    }

    @Test
    public void updateItemTest_nullItem() {
        AppointmentCell appointmentCell = new AppointmentCell();
        appointmentCell.updateItem(null, false);
        assertNull(appointmentCell.getGraphic());
    }

}