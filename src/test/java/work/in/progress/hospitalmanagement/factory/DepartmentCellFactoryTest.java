package work.in.progress.hospitalmanagement.factory;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import org.junit.Before;
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
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;
import work.in.progress.hospitalmanagement.cell.DepartmentCell;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.repository.BedRepository;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                BedService.class,
                DepartmentCell.class
        }
)
public class DepartmentCellFactoryTest implements ApplicationContextAware {

    private ApplicationContext context;

    @MockBean
    private BedService bedService;
    @MockBean
    private BedRepository bedRepository;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Before
    public void setUp() {
        ApplicationContextSingleton.setContext((ConfigurableApplicationContext) context);
    }

    @Test
    public void callTest() {
        ListView<Department> listView = new ListView<>();
        listView.setItems(FXCollections.observableArrayList(Mocks.department()));
        DepartmentCellFactory factory = new DepartmentCellFactory();
        DepartmentCell cell = (DepartmentCell) factory.call(listView);
        assertNotNull(cell);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}