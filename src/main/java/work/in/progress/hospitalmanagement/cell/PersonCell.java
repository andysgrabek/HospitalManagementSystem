package work.in.progress.hospitalmanagement.cell;


import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import work.in.progress.hospitalmanagement.event.PersonEvent;
import work.in.progress.hospitalmanagement.model.Person;

import static work.in.progress.hospitalmanagement.event.PersonEvent.DELETE_EVENT;
import static work.in.progress.hospitalmanagement.event.PersonEvent.EDIT_EVENT;

/**
 * Class to display a {@link Person} in a {@link javafx.scene.control.ListView} with an edit button.
 * {@link PersonEvent} EDIT_EVENT is emitted when the edit button is pressed on the
 * containing {@link javafx.scene.control.ListView}. Also a DELETE_EVENT is emitted when the delete button is pressed.
 * @author Andrzej Grabowski
 */
public class PersonCell<T extends Person> extends ListCell<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
            return;
        }
        HBox hBox = new HBox();
        Label label =
                new Label(item.getName() + " " + item.getSurname());
        label.getStyleClass().add("hms-text");
        JFXButton button1 = new JFXButton("EDIT");
        button1.setOnAction(event -> {
            Event e = new PersonEvent(EDIT_EVENT, item);
            getListView().fireEvent(e);
        });
        button1.getStyleClass().add("hms-button");
        JFXButton button2 = new JFXButton("DELETE");
        button2.setOnAction(event -> {
            Event e = new PersonEvent(DELETE_EVENT, item);
            getListView().fireEvent(e);
        });
        button2.getStyleClass().add("hms-button");
        hBox.getChildren().add(button1);
        hBox.getChildren().add(button2);
        hBox.getChildren().add(label);
        button1.setRipplerFill(Paint.valueOf("#f0ab8d"));
        button2.setRipplerFill(Paint.valueOf("#f0ab8d"));
        button1.setButtonType(JFXButton.ButtonType.RAISED);
        button2.setButtonType(JFXButton.ButtonType.RAISED);
        HBox.setHgrow(label, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        setGraphic(hBox);
    }
}
