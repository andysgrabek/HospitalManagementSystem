package work.in.progress.hospitalmanagement.factory;

import javafx.scene.control.ListView;
import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.cell.BedCell;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;

import static org.junit.Assert.assertNotNull;

public class BedCellFactoryTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void callTest() {
        ListView<Bed> listView = new ListView<>();
        BedCellFactory factory = new BedCellFactory();
        BedCell cell = (BedCell) factory.call(listView);
        assertNotNull(cell);
    }
}