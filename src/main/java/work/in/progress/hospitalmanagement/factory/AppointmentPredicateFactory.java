package work.in.progress.hospitalmanagement.factory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentPredicateFactory {

    @Getter
    private static AppointmentPredicateFactory defaultFactory = new AppointmentPredicateFactory();

    public Predicate<OutpatientAdmission> datePredicate(LocalDate date) {
        return (date == null)
                ? admission -> true
                : admission -> admission.getVisitDate().toLocalDate().isEqual(date);
    }

    public Predicate<OutpatientAdmission> timePredicate(LocalTime time) {
        return (time == null)
                ? admission -> true
                : admission -> admission.getVisitDate().toLocalTime().equals(time);
    }

    public Predicate<OutpatientAdmission> departmentPredicate(Department department) {
        return (department == null)
                ? admission -> true
                : admission -> admission.getDepartment().getName().equals(department.getName());
    }
}
