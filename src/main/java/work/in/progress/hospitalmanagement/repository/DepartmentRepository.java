package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
