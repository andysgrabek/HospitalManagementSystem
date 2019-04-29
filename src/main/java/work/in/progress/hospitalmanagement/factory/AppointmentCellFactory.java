package work.in.progress.hospitalmanagement.factory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import work.in.progress.hospitalmanagement.cell.AppointmentCell;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;

/**
 * Factory for the {@link AppointmentCell}s
 * @author Andrzej Grabowski
 */
public class AppointmentCellFactory implements Callback<ListView<OutpatientAdmission>, ListCell<OutpatientAdmission>> {
    @Override
    public ListCell<OutpatientAdmission> call(ListView<OutpatientAdmission> param) {
        return new AppointmentCell();
    }
}
