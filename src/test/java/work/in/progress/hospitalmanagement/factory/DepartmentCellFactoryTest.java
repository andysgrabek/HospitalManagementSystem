package work.in.progress.hospitalmanagement.factory;

import javafx.scene.control.ListView;
import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.cell.DepartmentCell;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;

import static org.junit.Assert.assertNotNull;

public class DepartmentCellFactoryTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void callTest() {
        ListView<Department> listView = new ListView<>();
        DepartmentCellFactory factory = new DepartmentCellFactory();
        DepartmentCell cell = (DepartmentCell) factory.call(listView);
        assertNotNull(cell);
    }
}