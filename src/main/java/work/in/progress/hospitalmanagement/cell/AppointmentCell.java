package work.in.progress.hospitalmanagement.cell;

import work.in.progress.hospitalmanagement.model.OutpatientAdmission;

/**
 * Class representing the cell displayed in a {@link javafx.scene.control.ListView} in e.g.
 * {@link work.in.progress.hospitalmanagement.controller.PatientsWaitingViewController}
 * showing for when a patient is having an appointment (i.e. an {@link OutpatientAdmission}
 * @author Andrzej Grabowski
 */
public class AppointmentCell extends EditListCell<OutpatientAdmission> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateItem(OutpatientAdmission item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
            return;
        }
        getHBox().getChildren().remove(getEditButton());
        getLabel().setText(item.getPatient().getName()
                + " " + item.getPatient().getSurname()
                + " appointment for "
                + item.getVisitDate().getDayOfMonth()
                + "-" + item.getVisitDate().getMonthValue()
                + "-" + item.getVisitDate().getYear()
                + " at " + item.getVisitDate().getHour() + ":" + item.getVisitDate().getMinute()
                + " in " + item.getDepartment().getName());
        setGraphic(getHBox());
    }
}
