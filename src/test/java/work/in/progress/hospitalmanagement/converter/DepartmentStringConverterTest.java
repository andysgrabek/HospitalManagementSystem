package work.in.progress.hospitalmanagement.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.repository.DepartmentRepository;
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
public class DepartmentStringConverterTest implements ApplicationContextAware {

    @MockBean
    private DepartmentRepository departmentRepository;
    private ApplicationContext context;

    @Test
    public void toStringTest() {
        DepartmentStringConverter converter = context.getBean(DepartmentStringConverter.class);
        assertEquals(Mocks.department().getName(), converter.toString(Mocks.department()));
    }

    @Test
    public void fromStringTest() {
        Department d = Mocks.department();
        Department p = context.getBean(DepartmentService.class).save(d);
        DepartmentStringConverter converter = context.getBean(DepartmentStringConverter.class);
        assertEquals(p, converter.fromString(d.getName()));
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}