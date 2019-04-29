package work.in.progress.hospitalmanagement.factory;

import org.junit.Test;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.util.function.Predicate;

import static org.junit.Assert.assertTrue;

public class AppointmentPredicateFactoryTest {

    @Test
    public void datePredicateTest() {
        Predicate<OutpatientAdmission> predicate
                = AppointmentPredicateFactory.getDefaultFactory().datePredicate(null);
        assertTrue(predicate.test(Mocks.outpatientAdmission()));
    }

    @Test
    public void timePredicateTest() {
        Predicate<OutpatientAdmission> predicate
                = AppointmentPredicateFactory.getDefaultFactory().timePredicate(null);
        assertTrue(predicate.test(Mocks.outpatientAdmission()));
    }

    @Test
    public void departmentPredicateTest() {
        Predicate<OutpatientAdmission> predicate
                = AppointmentPredicateFactory.getDefaultFactory().departmentPredicate(null);
        assertTrue(predicate.test(Mocks.outpatientAdmission()));
    }

    @Test
    public void datePredicateTest_null() {
        Predicate<OutpatientAdmission> predicate
                = AppointmentPredicateFactory.getDefaultFactory().datePredicate(
                        Mocks.outpatientAdmission().getVisitDate().toLocalDate());
        assertTrue(predicate.test(Mocks.outpatientAdmission()));
    }

    @Test
    public void timePredicateTest_null() {
        OutpatientAdmission o = Mocks.outpatientAdmission();
        Predicate<OutpatientAdmission> predicate
                = AppointmentPredicateFactory.getDefaultFactory().timePredicate(
                        o.getVisitDate().toLocalTime()
        );
        assertTrue(predicate.test(o));
    }

    @Test
    public void departmentPredicateTest_null() {
        Predicate<OutpatientAdmission> predicate
                = AppointmentPredicateFactory.getDefaultFactory().departmentPredicate(
                        Mocks.outpatientAdmission().getDepartment()
        );
        assertTrue(predicate.test(Mocks.outpatientAdmission()));
    }

}