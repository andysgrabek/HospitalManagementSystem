package work.in.progress.hospitalmanagement.event;

import org.junit.Test;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.*;
import static work.in.progress.hospitalmanagement.event.ListCellEvent.EDIT_EVENT;

public class ListCellEventTest {

    @Test
    public void testPatientEditEvent_nonNullPatient() {
        Patient patient = Mocks.patient();
        ListCellEvent<Patient> listCellEvent = new ListCellEvent<>(EDIT_EVENT, patient);
        assertNotNull(listCellEvent.getSubject());
    }

    @Test
    public void testPatientEditEvent_nullPatient() {
        Patient patient = Mocks.patient();
        ListCellEvent<Patient> listCellEvent = new ListCellEvent<>(EDIT_EVENT, null);
        assertNull(listCellEvent.getSubject());
        listCellEvent.setSubject(patient);
        assertNotNull(listCellEvent.getSubject());
    }
}