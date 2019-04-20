package work.in.progress.hospitalmanagement.event;

import org.junit.Test;
import org.mockito.Mock;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.*;
import static work.in.progress.hospitalmanagement.event.PatientEditEvent.PATIENT_EDIT_EVENT_EVENT_TYPE;

public class PatientEditEventTest {

    @Test
    public void testPatientEditEvent_nonNullPatient() {
        Patient patient = Mocks.patient();
        PatientEditEvent patientEditEvent = new PatientEditEvent(PATIENT_EDIT_EVENT_EVENT_TYPE, patient);
        assertNotNull(patientEditEvent.getPatient());
    }

    @Test
    public void testPatientEditEvent_nullPatient() {
        Patient patient = Mocks.patient();
        PatientEditEvent patientEditEvent = new PatientEditEvent(PATIENT_EDIT_EVENT_EVENT_TYPE, null);
        assertNull(patientEditEvent.getPatient());
        patientEditEvent.setPatient(patient);
        assertNotNull(patientEditEvent.getPatient());
    }
}