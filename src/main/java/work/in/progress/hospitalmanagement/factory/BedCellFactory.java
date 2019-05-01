package work.in.progress.hospitalmanagement.factory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import work.in.progress.hospitalmanagement.cell.BedCell;
import work.in.progress.hospitalmanagement.model.Bed;

/**
 * Factory for {@link BedCell}s to be used in views displaying details about a {@link Bed}
 * @author Andrzej Grabowski
 */
public class BedCellFactory implements Callback<ListView<Bed>, ListCell<Bed>> {
    @Override
    public ListCell<Bed> call(ListView<Bed> param) {
        return new BedCell();
    }
}
