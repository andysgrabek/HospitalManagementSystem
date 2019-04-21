package work.in.progress.hospitalmanagement.factory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import work.in.progress.hospitalmanagement.cell.PatientCell;
import work.in.progress.hospitalmanagement.model.Patient;

/**
 * Custom cell factory class to be used with a {@link ListView} containing {@link Patient} objects.
 * @author Andrzej Grabowski
 */
public class PatientCellFactory implements Callback<ListView<Patient>, ListCell<Patient>> {

    @Override
    public ListCell<Patient> call(ListView<Patient> listView) {
        return new PatientCell();
    }

}
