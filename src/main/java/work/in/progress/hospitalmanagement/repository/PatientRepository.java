package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.Patient;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for generic CRUD operations and additional methods to retrieve {@link Patient} entries.
 *
 * @author jablonskiba
 */
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    List<Patient> findByName(String name);

    List<Patient> findBySurname(String surname);

    List<Patient> findByBirthDate(LocalDate birthDate);
}
