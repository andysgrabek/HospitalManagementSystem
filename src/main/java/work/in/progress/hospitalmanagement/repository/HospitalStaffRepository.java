package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.HospitalStaff;

import java.util.List;
import java.util.Optional;

/**
 * Interface for generic CRUD operations and additional methods to retrieve
 * {@link HospitalStaff} entries.
 *
 * @author jablonskiba
 */
public interface HospitalStaffRepository extends JpaRepository<HospitalStaff, Integer> {

    List<HospitalStaff> findByName(String name);

    List<HospitalStaff> findBySurname(String surname);

    Optional<HospitalStaff> findByEmail(String email);

}
