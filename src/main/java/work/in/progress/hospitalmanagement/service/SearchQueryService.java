package work.in.progress.hospitalmanagement.service;

import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import work.in.progress.hospitalmanagement.model.SearchQuery;
import work.in.progress.hospitalmanagement.repository.SearchQueryRepository;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Provides a public API to manage {@link SearchQuery}.
 *
 * @author jablonskiba
 */
@Service
public class SearchQueryService extends AbstractService<SearchQuery, String> {

    private final EntityManager entityManager;

    @Autowired
    public SearchQueryService(SearchQueryRepository searchQueryRepository, EntityManager entityManager) {
        super(searchQueryRepository);
        this.entityManager = entityManager;
    }

    /**
     * Executes and retrieves a search query result.
     *
     * @param searchQuery the search query to execute on a current database
     * @return a query result tuples.
     */
    public List<Tuple> execute(SearchQuery searchQuery) {
        final TypedQuery<Tuple> query = entityManager.createQuery(searchQuery.getExpression(), Tuple.class);
        query.setHint(QueryHints.READ_ONLY, true);

        return query.getResultList();
    }

    @Override
    protected Sort defaultSort() {
        return Sort.by("label");
    }

}
