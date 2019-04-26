package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.in.progress.hospitalmanagement.model.SearchQuery;

/**
 * Interface for generic CRUD operations and additional methods to retrieve
 * {@link SearchQuery} entries.
 *
 * @author jablonskiba
 */
public interface SearchQueryRepository extends JpaRepository<SearchQuery, String> {
}
