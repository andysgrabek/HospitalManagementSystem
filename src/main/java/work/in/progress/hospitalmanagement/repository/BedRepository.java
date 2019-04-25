package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;

import java.util.Collection;

/**
 * Interface for generic CRUD operations and additional methods to retrieve {@link Bed}
 * entries.
 *
 * @author jablonskiba
 */
public interface BedRepository extends JpaRepository<Bed, Integer> {

    Collection<Bed> findByDepartmentAndAdmissionNotNull(Department department);

    Collection<Bed> findAllByDepartment(Department department);

}
