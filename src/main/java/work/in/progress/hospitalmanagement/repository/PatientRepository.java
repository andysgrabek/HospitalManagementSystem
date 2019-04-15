package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.Patient;

import java.time.LocalDate;
import java.util.Collection;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Collection<Patient> findByName(String name);

    Collection<Patient> findBySurname(String surname);

    Collection<Patient> findByBirthDate(LocalDate birthDate);
}
