package work.in.progress.hospitalmanagement.cell;

import work.in.progress.hospitalmanagement.model.Bed;

public class BedCell extends DeleteListCell<Bed> {
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
