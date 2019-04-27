package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import work.in.progress.hospitalmanagement.model.Admission;

/**
 * Common interface for generic CRUD operations and additional methods to retrieve
 * entries that inherit from {@link Admission}.
 *
 * @author jablonskiba
 */
@NoRepositoryBean
public interface AdmissionRepository<T extends Admission>
        extends JpaRepository<T, Integer> {
}
