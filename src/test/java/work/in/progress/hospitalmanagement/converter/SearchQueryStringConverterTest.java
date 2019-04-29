package work.in.progress.hospitalmanagement.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.model.SearchQuery;
import work.in.progress.hospitalmanagement.repository.SearchQueryRepository;
import work.in.progress.hospitalmanagement.service.SearchQueryService;
import work.in.progress.hospitalmanagement.util.Mocks;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                SearchQueryService.class,
                SearchQueryStringConverter.class,
        }
)
public class SearchQueryStringConverterTest implements ApplicationContextAware {

    @MockBean
    private EntityManager entityManager;
    @MockBean
    private SearchQueryRepository searchQueryRepository;
    private ApplicationContext context;

    @Test
    public void toStringTest() {
        SearchQueryStringConverter converter = context.getBean(SearchQueryStringConverter.class);
        assertEquals(Mocks.searchQuery().getLabel(), converter.toString(Mocks.searchQuery()));
    }

    @Test
    public void fromStringTest() {
        SearchQuery d = Mocks.searchQuery();
        SearchQuery p = context.getBean(SearchQueryService.class).save(d);
        SearchQueryStringConverter converter = context.getBean(SearchQueryStringConverter.class);
        assertEquals(p, converter.fromString(d.getLabel()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}