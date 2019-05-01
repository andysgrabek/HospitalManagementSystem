package work.in.progress.hospitalmanagement.factory;

import org.junit.Test;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.util.function.Predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PatientPredicateFactoryTest {

    @Test
    public void namePredicate() {
        Predicate<Patient> predicate =
                PatientPredicateFactory.getDefaultFactory().namePredicate(Mocks.patient().getName());
        assertTrue(predicate.test(Mocks.patient()));
    }

    @Test
    public void surnamePredicate() {
        Predicate<Patient> predicate =
                PatientPredicateFactory.getDefaultFactory().surnamePredicate(Mocks.patient().getSurname());
        assertTrue(predicate.test(Mocks.patient()));
    }

    @Test
    public void datePredicate() {
        Predicate<Patient> predicate =
                PatientPredicateFactory.getDefaultFactory().datePredicate(Mocks.patient().getBirthDate());
        assertTrue(predicate.test(Mocks.patient()));
    }

    @Test
    public void departmentPredicate() {
        Patient p = Mocks.patient();
        p.setAdmission(Mocks.outpatientAdmission());
        Predicate<Patient> predicate =
                PatientPredicateFactory.getDefaultFactory()
                        .departmentPredicate(p.getAdmission().get().getDepartment());
        assertTrue(predicate.test(p));
    }

    @Test
    public void namePredicate_null() {
        Predicate<Patient> predicate =
                PatientPredicateFactory.getDefaultFactory().namePredicate(null);
        assertTrue(predicate.test(Mocks.patient()));
    }

    @Test
    public void surnamePredicate_null() {
        Predicate<Patient> predicate =
                PatientPredicateFactory.getDefaultFactory().surnamePredicate(null);
        assertTrue(predicate.test(Mocks.patient()));
    }

    @Test
    public void datePredicate_null() {
        Predicate<Patient> predicate =
                PatientPredicateFactory.getDefaultFactory().datePredicate(null);
        assertTrue(predicate.test(Mocks.patient()));
    }

    @Test
    public void departmentPredicate_null() {
        Predicate<Patient> predicate =
                PatientPredicateFactory.getDefaultFactory().departmentPredicate(null);
        assertTrue(predicate.test(Mocks.patient()));
    }

    @Test
    public void departmentPredicate_nullAdmission() {
        Predicate<Patient> predicate =
                PatientPredicateFactory.getDefaultFactory().departmentPredicate(Mocks.department());
        assertFalse(predicate.test(Mocks.patient()));
    }
}