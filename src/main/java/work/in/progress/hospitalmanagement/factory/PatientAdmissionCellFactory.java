package work.in.progress.hospitalmanagement.factory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import work.in.progress.hospitalmanagement.cell.PatientAdmissionCell;
import work.in.progress.hospitalmanagement.model.Patient;

/**
 * Custom cell factory class to be used with a {@link ListView} containing {@link Patient} objects in views where
 * {@link work.in.progress.hospitalmanagement.model.Admission}s are presented in the interface.
 * @author Andrzej Grabowski
 */
public class PatientAdmissionCellFactory implements Callback<ListView<Patient>, ListCell<Patient>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ListCell<Patient> call(ListView<Patient> listView) {
        return new PatientAdmissionCell();
    }

}
