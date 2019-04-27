package work.in.progress.hospitalmanagement.repository;

import org.springframework.transaction.annotation.Transactional;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;

/**
 * Interface for generic CRUD operations and additional methods to retrieve
 * {@link InpatientAdmission} entries.
 *
 * @author jablonskiba
 */
@Transactional
public interface InpatientAdmissionRepository
        extends AdmissionRepository<InpatientAdmission> {
}
