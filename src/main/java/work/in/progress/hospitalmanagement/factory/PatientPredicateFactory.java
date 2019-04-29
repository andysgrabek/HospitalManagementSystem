package work.in.progress.hospitalmanagement.factory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.Patient;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Factory for predicates working on {@link Patient} objects
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatientPredicateFactory {

    @Getter
    private static PatientPredicateFactory defaultFactory = new PatientPredicateFactory();

    /**
     * Creates a predicate on the patient name
     * @param name patient name
     * @return simple name predicate
     */
    public Predicate<Patient> namePredicate(String name) {
        return (name == null || name.length() == 0) ? patient -> true
                : patient -> patient.getName().toLowerCase().contains(name.toLowerCase());
    }

    /**
     * Creates a predicate on the patient surname
     * @param surname patient surname
     * @return simple surname predicate
     */
    public Predicate<Patient> surnamePredicate(String surname) {
        return (surname == null || surname.length() == 0) ? patient -> true
                : patient -> patient.getSurname().toLowerCase().contains(surname.toLowerCase());
    }

    /**
     * Creates a predicate on the patient birth date
     * @param date patient's birth date
     * @return simple birth date predicate
     */
    public Predicate<Patient> datePredicate(LocalDate date) {
        return (date == null)
                ? patient -> true
                : patient -> patient.getBirthDate().isEqual(date);
    }

    /**
     * Creates a predicate on the department that the patient is admitted to
     * @param department department the patient is admitted to
     * @return simple department predicate
     */
    public Predicate<Patient> departmentPredicate(Department department) {
        return (department == null)
                ? patient -> true
                : patient -> {
            if (patient.getAdmission().isPresent()) {
                return patient.getAdmission().get().getDepartment().equals(department);
            } else {
                return false;
            }
        };
    }
}
