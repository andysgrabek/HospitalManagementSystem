package work.in.progress.hospitalmanagement.cell;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentCellTest implements ApplicationContextAware {

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