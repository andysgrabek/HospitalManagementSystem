package work.in.progress.hospitalmanagement.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DepartmentModelTest {

    @Test
    public void whenToStringCalled_thenProperStringShouldBeReturned() {
        Department department = Mocks.department();
        assertThat(department.toString()).isEqualTo("Department(" +
                "name=" + department.getName() + ", " +
                "beds=" + department.getBeds() + ")");
    }

}
