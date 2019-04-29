package work.in.progress.hospitalmanagement.factory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Predicate;

/**
 * Factory class for the predicates used to filter the {@link javafx.scene.control.ListView} where appointments
 * of patients are displayed
 * @author Andrzej Grabowski
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentPredicateFactory {

    @Getter
    private static AppointmentPredicateFactory defaultFactory = new AppointmentPredicateFactory();

    /**
     * Creates a predicate on the visit date
     * @param date visit date
     * @return simple date predicate
     */
    public Predicate<OutpatientAdmission> datePredicate(LocalDate date) {
        return (date == null)
                ? admission -> true
                : admission -> admission.getVisitDate().toLocalDate().isEqual(date);
    }

    /**
     * Creates a predicate on the visit time
     * @param time visit time
     * @return simple time predicate
     */
    public Predicate<OutpatientAdmission> timePredicate(LocalTime time) {
        return (time == null)
                ? admission -> true
                : admission -> admission.getVisitDate().toLocalTime().equals(time);
    }

    /**
     * Creates a predicate on the visit department
     * @param department visit department
     * @return simple department predicate
     */
    public Predicate<OutpatientAdmission> departmentPredicate(Department department) {
        return (department == null)
                ? admission -> true
                : admission -> admission.getDepartment().getName().equals(department.getName());
    }
}
