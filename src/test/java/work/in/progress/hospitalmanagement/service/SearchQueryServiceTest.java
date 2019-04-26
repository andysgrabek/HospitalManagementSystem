package work.in.progress.hospitalmanagement.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.model.SearchQuery;
import work.in.progress.hospitalmanagement.repository.SearchQueryRepository;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class SearchQueryServiceTest {

    private SearchQueryService searchQueryService;

    @MockBean
    private SearchQueryRepository searchQueryRepository;

    @MockBean
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Tuple> typedQuery;

    @Before
    public void setUp() {
        searchQueryService = new SearchQueryService(searchQueryRepository, entityManager);
    }

    @Test
    public void whenSearchQueryExecuted_thenResultsShouldBeFound() {
        final String expression = "SELECT name FROM Department";
        Mockito.when(entityManager.createQuery(expression, Tuple.class)).thenReturn(typedQuery);
        Mockito.when(typedQuery.getResultList()).thenReturn(Arrays.asList(null, null));

        final SearchQuery searchQuery = new SearchQuery("Service Test Query", expression);
        assertThat(searchQueryService.execute(searchQuery)).hasSize(2);
    }

}
