package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.HospitalStaff;

import java.util.Optional;

public interface HospitalStaffRepository extends JpaRepository<HospitalStaff, Integer> {
    Optional<HospitalStaff> findByName(String name);

    Optional<HospitalStaff> findBySurname(String surname);

    Optional<HospitalStaff> findByIdAndNameAndSurname(Integer id, String name, String surname);
}
