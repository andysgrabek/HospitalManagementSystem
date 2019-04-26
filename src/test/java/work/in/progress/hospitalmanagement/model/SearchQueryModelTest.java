package work.in.progress.hospitalmanagement.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class SearchQueryModelTest {

    @Test
    public void whenToStringCalled_thenProperStringShouldBeReturned() {
        SearchQuery searchQuery = Mocks.searchQuery();
        assertThat(searchQuery.toString()).isEqualTo("SearchQuery(" +
                "label=" + searchQuery.getLabel() + ", " +
                "expression=" + searchQuery.getExpression() + ")");
    }

}
