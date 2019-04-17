package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.Address;

/**
 * Interface for generic CRUD operations and additional methods to retrieve {@link Address} entries.
 *
 * @author jablonskiba
 */
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
