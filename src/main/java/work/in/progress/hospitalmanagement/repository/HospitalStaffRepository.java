package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.HospitalStaff;

import java.util.List;

public interface HospitalStaffRepository extends JpaRepository<HospitalStaff, Integer> {
    List<HospitalStaff> findByName(String name);

    List<HospitalStaff> findBySurname(String surname);

    List<HospitalStaff> findByIdAndNameAndSurname(Integer id, String name, String surname);
}
