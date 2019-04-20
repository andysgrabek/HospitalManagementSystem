package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;

/**
 * Interface for generic CRUD operations and additional methods to retrieve
 * {@link InpatientAdmission} entries.
 *
 * @author jablonskiba
 */
public interface InpatientAdmissionRepository
        extends JpaRepository<InpatientAdmission, Integer> {

}
