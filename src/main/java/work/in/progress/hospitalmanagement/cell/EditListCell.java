package work.in.progress.hospitalmanagement.cell;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import lombok.Getter;
import work.in.progress.hospitalmanagement.event.ListCellEvent;

import static work.in.progress.hospitalmanagement.event.ListCellEvent.EDIT_EVENT;

public abstract class EditListCell<T> extends ListCell<T> {

    @Getter
    private Label label = new Label();
    @Getter
    private HBox hBox = new HBox();
    @Getter
    private JFXButton editButton;

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
            return;
        }
        hBox.getChildren().clear();
        label.getStyleClass().add("hms-text");
        editButton = new JFXButton("EDIT");
        editButton.setOnAction(event -> {
            Event e = new ListCellEvent<>(EDIT_EVENT, item);
            getListView().fireEvent(e);
        });
        editButton.getStyleClass().add("hms-button");
        hBox.getChildren().add(editButton);
        hBox.getChildren().add(label);
        editButton.setRipplerFill(Paint.valueOf("#f0ab8d"));
        editButton.setButtonType(JFXButton.ButtonType.RAISED);
        HBox.setHgrow(label, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        setGraphic(hBox);
    }
}
