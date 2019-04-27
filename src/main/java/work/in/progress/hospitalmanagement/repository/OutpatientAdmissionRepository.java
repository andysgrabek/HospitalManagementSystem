package work.in.progress.hospitalmanagement.repository;

import org.springframework.transaction.annotation.Transactional;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;

/**
 * Interface for generic CRUD operations and additional methods to retrieve
 * {@link OutpatientAdmission} entries.
 *
 * @author jablonskiba
 */
@Transactional
public interface OutpatientAdmissionRepository
        extends AdmissionRepository<OutpatientAdmission> {
}
