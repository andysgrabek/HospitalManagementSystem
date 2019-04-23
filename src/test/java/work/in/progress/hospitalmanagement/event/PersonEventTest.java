package work.in.progress.hospitalmanagement.event;

import org.junit.Test;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.*;
import static work.in.progress.hospitalmanagement.event.PersonEvent.EDIT_EVENT;

public class PersonEventTest {

    @Test
    public void testPatientEditEvent_nonNullPatient() {
        Patient patient = Mocks.patient();
        PersonEvent<Patient> personEvent = new PersonEvent<>(EDIT_EVENT, patient);
        assertNotNull(personEvent.getPerson());
    }

    @Test
    public void testPatientEditEvent_nullPatient() {
        Patient patient = Mocks.patient();
        PersonEvent<Patient> personEvent = new PersonEvent<>(EDIT_EVENT, null);
        assertNull(personEvent.getPerson());
        personEvent.setPerson(patient);
        assertNotNull(personEvent.getPerson());
    }
}