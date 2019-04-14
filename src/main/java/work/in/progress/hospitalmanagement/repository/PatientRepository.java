package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
