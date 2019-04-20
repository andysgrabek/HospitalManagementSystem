package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;

/**
 * Interface for generic CRUD operations and additional methods to retrieve
 * {@link OutpatientAdmission} entries.
 *
 * @author jablonskiba
 */
public interface OutpatientAdmissionRepository
        extends JpaRepository<OutpatientAdmission, Integer> {

}
