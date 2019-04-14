package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.Patient;

import java.time.LocalDate;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByName(String name);

    Optional<Patient> findBySurname(String surname);

    Optional<Patient> findByBirthDate(LocalDate birthDate);
}
