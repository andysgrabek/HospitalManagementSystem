package work.in.progress.hospitalmanagement.cell;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import lombok.Getter;
import work.in.progress.hospitalmanagement.event.ListCellEvent;
import work.in.progress.hospitalmanagement.factory.ButtonFactory;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.model.Patient;

import static work.in.progress.hospitalmanagement.event.ListCellEvent.NEW_EVENT;

public class PatientAdmissionCell extends PersonCell<Patient> {

    @Getter
    private JFXButton createButton;

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateItem(Patient item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
            return;
        }
        createButton = ButtonFactory.getDefaultFactory().defaultButton("Admit");
        createButton.setOnAction(event -> {
            Event e = new ListCellEvent<>(NEW_EVENT, item);
            getListView().fireEvent(e);
        });
        getDeleteButton().setText("Discharge");
        getDeleteButton().setPrefWidth(ButtonFactory.getLARGE_BUTTON_WIDTH());
        if (item.getAdmission().isPresent()) {
            if (item.getAdmission().get() instanceof OutpatientAdmission) {
                getHBox().getChildren().remove(getEditButton());
            }
            getLabel().setText("ID: "
                    + item.getId()
                    + ", "
                    + item.getName()
                    + " " + item.getSurname()
                    + " admitted to "
                    + item.getAdmission().get().getDepartment().getName());
        } else {
            getHBox().getChildren().remove(getDeleteButton());
            getHBox().getChildren().remove(getEditButton());
            getHBox().getChildren().add(getCreateButton());
            getLabel().setText("ID: "
                    + item.getId()
                    + ", "
                    + item.getName()
                    + " " + item.getSurname());
        }
        setGraphic(getHBox());
    }
}
