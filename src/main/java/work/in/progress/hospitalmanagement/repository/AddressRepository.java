package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
