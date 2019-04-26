package work.in.progress.hospitalmanagement.constraint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.in.progress.hospitalmanagement.model.SearchQuery;

import javax.validation.ConstraintValidator;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SqlExpressionValidatorTest {

    @Autowired
    private TestEntityManager entityManager;

    private ConstraintValidator<SqlExpression, String> sqlExpressionValidator;

    @Before
    public void setUp() {
        sqlExpressionValidator = new SqlExpressionValidator();
        ReflectionTestUtils.setField(sqlExpressionValidator, "entityManagerFactory",
                entityManager.getEntityManager().getEntityManagerFactory());

        sqlExpressionValidator.initialize(null);
    }

    @Test
    public void whenSqlExpressionInvalid_thenConstraintValidationShouldOccur() {
        SearchQuery searchQuery = new SearchQuery("TestQuery", "InvalidExpression");

        assertThat(sqlExpressionValidator.isValid(searchQuery.getExpression(), null)).isFalse();
    }

    @Test
    public void whenSqlExpressionValid_thenConstraintValidationShouldNotOccur() {
        SearchQuery searchQuery = new SearchQuery("TestQuery", "SELECT name FROM Department");

        assertThat(sqlExpressionValidator.isValid(searchQuery.getExpression(), null)).isTrue();
    }

}
