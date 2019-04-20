package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.repository.DepartmentRepository;

/**
 * Provides a public API to manage {@link work.in.progress.hospitalmanagement.model.Department}.
 *
 * @author jablonskiba
 */
@Service
public class DepartmentService extends AbstractService<Department, String> {

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        super(departmentRepository);
    }

    @Override
    protected Sort defaultSort() {
        return Sort.by("name");
    }
}
