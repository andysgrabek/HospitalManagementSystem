package work.in.progress.hospitalmanagement.cell;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.scene.paint.Paint;
import work.in.progress.hospitalmanagement.event.ListCellEvent;

import static work.in.progress.hospitalmanagement.event.ListCellEvent.DELETE_EVENT;

public abstract class DeleteListCell<T> extends EditListCell<T> {

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
            return;
        }
        JFXButton deleteButton = new JFXButton("DELETE");
        deleteButton.setOnAction(event -> {
            Event e = new ListCellEvent<T>(DELETE_EVENT, item);
            getListView().fireEvent(e);
        });
        deleteButton.getStyleClass().add("hms-button");
        deleteButton.setRipplerFill(Paint.valueOf("#f0ab8d"));
        deleteButton.setButtonType(JFXButton.ButtonType.RAISED);
        getHBox().getChildren().add(getHBox().getChildren().indexOf(getEditButton()) + 1, deleteButton);
        setGraphic(getHBox());
    }
}
