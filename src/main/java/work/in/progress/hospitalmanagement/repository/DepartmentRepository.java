package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.Department;

/**
 * Interface for generic CRUD operations and additional methods to retrieve {@link Department} entries.
 *
 * @author jablonskiba
 */
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
