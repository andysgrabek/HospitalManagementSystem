package work.in.progress.hospitalmanagement.event;

import org.junit.Test;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.*;
import static work.in.progress.hospitalmanagement.event.PatientEditEvent.EDIT_EVENT;

public class PatientEditEventTest {

    @Test
    public void testPatientEditEvent_nonNullPatient() {
        Patient patient = Mocks.patient();
        PatientEditEvent patientEditEvent = new PatientEditEvent(EDIT_EVENT, patient);
        assertNotNull(patientEditEvent.getPatient());
    }

    @Test
    public void testPatientEditEvent_nullPatient() {
        Patient patient = Mocks.patient();
        PatientEditEvent patientEditEvent = new PatientEditEvent(EDIT_EVENT, null);
        assertNull(patientEditEvent.getPatient());
        patientEditEvent.setPatient(patient);
        assertNotNull(patientEditEvent.getPatient());
    }
}