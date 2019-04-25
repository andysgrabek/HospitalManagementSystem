package work.in.progress.hospitalmanagement.cell;

import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.*;

public class DepartmentCellTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void updateItemTestNullItem() {
        Department department = null;
        DepartmentCell cell = new DepartmentCell();
        cell.updateItem(department, false);
        assertNull(cell.getGraphic());
    }

    @Test
    public void updateItemTestEmptyCell() {
        Department department = Mocks.department();
        DepartmentCell cell = new DepartmentCell();
        cell.updateItem(department, true);
        assertNull(cell.getGraphic());
    }

    @Test
    public void updateItemTestLoadedCell() {
        Department department = Mocks.department();
        DepartmentCell cell = new DepartmentCell();
        cell.updateItem(department, false);
        assertNotNull(cell.getGraphic());
        assertEquals(3, cell.getHBox().getChildren().size());
    }
}