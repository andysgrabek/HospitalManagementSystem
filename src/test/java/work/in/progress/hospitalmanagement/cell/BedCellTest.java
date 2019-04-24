package work.in.progress.hospitalmanagement.cell;

import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BedCellTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void updateItemTestNullItem() {
        Bed bed = null;
        BedCell cell = new BedCell();
        cell.updateItem(bed, false);
        assertNull(cell.getGraphic());
    }

    @Test
    public void updateItemTestEmptyCell() {
        Bed bed = Mocks.bed();
        BedCell cell = new BedCell();
        cell.updateItem(bed, true);
        assertNull(cell.getGraphic());
    }

    @Test
    public void updateItemTestLoadedCell() {
        Bed bed = Mocks.bed();
        BedCell cell = new BedCell();
        bed.setAdmission(new InpatientAdmission(Mocks.patient(), bed));
        cell.updateItem(bed, false);
        assertNotNull(cell.getGraphic());
    }
}