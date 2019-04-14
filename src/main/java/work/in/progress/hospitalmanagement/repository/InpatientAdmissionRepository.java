package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;

public interface InpatientAdmissionRepository extends JpaRepository<InpatientAdmission, Integer> {
}
