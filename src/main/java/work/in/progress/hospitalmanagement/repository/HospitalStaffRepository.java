package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.HospitalStaff;

import java.util.List;
import java.util.Optional;

public interface HospitalStaffRepository extends JpaRepository<HospitalStaff, Integer> {
    List<HospitalStaff> findByName(String name);

    List<HospitalStaff> findBySurname(String surname);

    Optional<HospitalStaff> findByEmail(String email);
}
