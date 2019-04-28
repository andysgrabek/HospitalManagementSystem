package work.in.progress.hospitalmanagement.cell;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;
import work.in.progress.hospitalmanagement.event.ListCellEvent;
import work.in.progress.hospitalmanagement.factory.ButtonFactory;

import static work.in.progress.hospitalmanagement.event.ListCellEvent.EDIT_EVENT;

/**
 * A class to be used as a template for cells in {@link javafx.scene.control.ListView}
 * elements requiring a delete button alongside an edit button
 * @param <T> the type of the object represented
 * @author Andrzej Grabowski
 */
public abstract class EditListCell<T> extends ListCell<T> {

    @Getter
    private Label label = new Label();
    @Getter
    private HBox hBox = new HBox();
    @Getter
    private JFXButton editButton;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
            return;
        }
        hBox.getChildren().clear();
        editButton = ButtonFactory.getDefaultFactory().defaultButton("Edit");
        label.getStyleClass().add("hms-text");
        editButton.setOnAction(event -> {
            Event e = new ListCellEvent<>(EDIT_EVENT, item);
            getListView().fireEvent(e);
        });
        hBox.getChildren().add(editButton);
        hBox.getChildren().add(label);
        HBox.setHgrow(label, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        setGraphic(hBox);
    }
}
