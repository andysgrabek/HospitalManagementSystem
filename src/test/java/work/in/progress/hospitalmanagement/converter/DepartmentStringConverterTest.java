package work.in.progress.hospitalmanagement.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                DepartmentStringConverter.class,
                DepartmentService.class
        }
)
public class DepartmentStringConverterTest {

    @MockBean
    private DepartmentService departmentService;

    @Test
    public void toStringTest() {
        DepartmentStringConverter converter =
                new DepartmentStringConverter(departmentService);
        assertEquals(Mocks.department().getName(), converter.toString(Mocks.department()));
    }

    @Test
    public void fromStringTest() {
        Department d = Mocks.department();
        DepartmentStringConverter converter =
                new DepartmentStringConverter(departmentService);
        assertEquals(d, converter.fromString(d.getName()));
    }
}