package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.HospitalStaff;

public interface HospitalStaffRepository extends JpaRepository<HospitalStaff, Integer> {
}
