package work.in.progress.hospitalmanagement.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.repository.BedRepository;
import work.in.progress.hospitalmanagement.repository.DepartmentRepository;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                BedService.class,
                DepartmentService.class,
                DepartmentStringConverter.class,
                BedStringConverter.class
        }
)
public class BedStringConverterTest implements ApplicationContextAware {

    @MockBean
    private BedRepository bedRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    private ApplicationContext context;

    @Test
    public void toStringTest() {
        BedStringConverter bedStringConverter = context.getBean(BedStringConverter.class);
        assertEquals("Intensive Care 12E", bedStringConverter.toString(Mocks.bed()));
    }

    @Test
    public void fromStringTest() {
        BedStringConverter bedStringConverter = context.getBean(BedStringConverter.class);
        Bed bed = Mocks.bed();
        Bed bed1 = context.getBean(BedService.class).save(bed);
        assertEquals(bed1, bedStringConverter.fromString("Intensive Care 12E"));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}