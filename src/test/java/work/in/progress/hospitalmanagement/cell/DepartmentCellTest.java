package work.in.progress.hospitalmanagement.cell;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.repository.BedRepository;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = { DepartmentCell.class, BedService.class })
public class DepartmentCellTest implements ApplicationContextAware {

    @MockBean
    private BedService bedService;
    @MockBean
    private BedRepository bedRepository;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    private ConfigurableApplicationContext context;

    @Test
    public void updateItemTestNullItem() {
        Department department = null;
        DepartmentCell cell = context.getBean(DepartmentCell.class);
        cell.updateItem(department, false);
        assertNull(cell.getGraphic());
    }

    @Test
    public void updateItemTestEmptyCell() {
        Department department = Mocks.department();
        DepartmentCell cell = context.getBean(DepartmentCell.class);
        cell.updateItem(department, true);
        assertNull(cell.getGraphic());
    }

    @Test
    public void updateItemTestLoadedCell() {
        Department department = Mocks.department();
        DepartmentCell cell = context.getBean(DepartmentCell.class);
        cell.updateItem(department, false);
        assertNotNull(cell.getGraphic());
        assertEquals(3, cell.getHBox().getChildren().size());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;
    }
}