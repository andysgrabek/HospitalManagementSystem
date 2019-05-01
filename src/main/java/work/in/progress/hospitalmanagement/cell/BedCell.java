package work.in.progress.hospitalmanagement.cell;

import work.in.progress.hospitalmanagement.model.Bed;

/**
 * Class representing the cell displayed in a {@link javafx.scene.control.ListView} in e.g.
 * {@link work.in.progress.hospitalmanagement.controller.DepartmentManagementViewController}
 * representing the beds available in a single {@link work.in.progress.hospitalmanagement.model.Department}
 * @author Andrzej Grabowski
 */
public class BedCell extends DeleteListCell<Bed> {
    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateItem(Bed item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
            return;
        }
        String occupant = "";
        if (item.getAdmission().isPresent()) {
            occupant = ", occupied by: " + item.getAdmission().get().getPatient().getSurname();
        }
        getLabel().setText("Room " + item.getRoomNumber() + occupant);
        setGraphic(getHBox());
    }
}
