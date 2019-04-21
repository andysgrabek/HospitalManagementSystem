package work.in.progress.hospitalmanagement.cell;


import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import work.in.progress.hospitalmanagement.event.PatientEditEvent;
import work.in.progress.hospitalmanagement.model.Patient;

import static work.in.progress.hospitalmanagement.event.PatientEditEvent.DELETE_EVENT;
import static work.in.progress.hospitalmanagement.event.PatientEditEvent.EDIT_EVENT;

/**
 * Class to display a {@link Patient} in a {@link javafx.scene.control.ListView} with an edit button.
 * {@link PatientEditEvent} EDIT_EVENT is emitted when the edit button is pressed on the
 * containing {@link javafx.scene.control.ListView}
 * @author Andrzej Grabowski
 */
public class PatientCell extends ListCell<Patient> {

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
        HBox hBox = new HBox();
        Label label =
                new Label(item.getName() + " " + item.getSurname() + ", born " + item.getBirthDate().toString());
        Button button1 = new JFXButton("edit");
        button1.setOnAction(event -> {
            Event e = new PatientEditEvent(EDIT_EVENT, item);
            getListView().fireEvent(e);
        });
        Button button2 = new JFXButton("delete");
        button2.setOnAction(event -> {
            Event e = new PatientEditEvent(DELETE_EVENT, item);
            getListView().fireEvent(e);
        });
        hBox.getChildren().add(label);
        hBox.getChildren().add(button1);
        hBox.getChildren().add(button2);
        HBox.setHgrow(label, Priority.ALWAYS);
        setGraphic(hBox);
    }
}
