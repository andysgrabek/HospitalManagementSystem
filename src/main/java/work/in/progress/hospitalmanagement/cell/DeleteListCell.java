package work.in.progress.hospitalmanagement.cell;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import lombok.Getter;
import work.in.progress.hospitalmanagement.event.ListCellEvent;
import work.in.progress.hospitalmanagement.factory.ButtonFactory;

import static work.in.progress.hospitalmanagement.event.ListCellEvent.DELETE_EVENT;

/**
 * A class to be used as a template for cells in {@link javafx.scene.control.ListView}
 * elements requiring a delete button alongside an edit button
 * @param <T> the type of the object represented
 * @author Andrzej Grabowski
 */
public abstract class DeleteListCell<T> extends EditListCell<T> {

    @Getter
    private JFXButton deleteButton;

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
        deleteButton = ButtonFactory.getDefaultFactory().defaultButton("Delete");
        deleteButton.setOnAction(event -> {
            Event e = new ListCellEvent<T>(DELETE_EVENT, item);
            getListView().fireEvent(e);
        });
        getHBox().getChildren().add(getHBox().getChildren().indexOf(getEditButton()) + 1, deleteButton);
        setGraphic(getHBox());
    }
}
