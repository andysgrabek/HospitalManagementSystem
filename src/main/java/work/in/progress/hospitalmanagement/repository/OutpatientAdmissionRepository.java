package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;

public interface OutpatientAdmissionRepository extends JpaRepository<OutpatientAdmission, Integer> {
}
