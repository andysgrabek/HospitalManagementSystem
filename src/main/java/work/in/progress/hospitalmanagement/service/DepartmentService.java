package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.repository.DepartmentRepository;

/**
 * Provides a public API to manage {@link Department}.
 *
 * @author jablonskiba
 */
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

}
