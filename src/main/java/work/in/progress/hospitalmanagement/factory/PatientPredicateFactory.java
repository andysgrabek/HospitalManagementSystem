package work.in.progress.hospitalmanagement.factory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.Patient;

import java.time.LocalDate;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatientPredicateFactory {

    @Getter
    private static PatientPredicateFactory defaultFactory = new PatientPredicateFactory();

    public Predicate<Patient> namePredicate(String name) {
        return (name == null || name.length() == 0) ? patient -> true
                : patient -> patient.getName().toLowerCase().contains(name.toLowerCase());
    }

    public Predicate<Patient> surnamePredicate(String surname) {
        return (surname == null || surname.length() == 0) ? patient -> true
                : patient -> patient.getSurname().toLowerCase().contains(surname.toLowerCase());
    }

    public Predicate<Patient> datePredicate(LocalDate date) {
        return (date == null)
                ? patient -> true
                : patient -> patient.getBirthDate().isEqual(date);
    }

    public Predicate<Patient> departmentPredicate(Department department) {
        return (department == null)
                ? patient -> true
                : patient -> {
            if (patient.getAdmission().isPresent()) {
                return patient.getAdmission().get().getDepartment().equals(department);
            } else {
                return true;
            }
        };
    }
}
