package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.HospitalStaff;

import java.util.Collection;

public interface HospitalStaffRepository extends JpaRepository<HospitalStaff, Integer> {
    Collection<HospitalStaff> findByName(String name);

    Collection<HospitalStaff> findBySurname(String surname);

    Collection<HospitalStaff> findByIdAndNameAndSurname(Integer id, String name, String surname);
}
