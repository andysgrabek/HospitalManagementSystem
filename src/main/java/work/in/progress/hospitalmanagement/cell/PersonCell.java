package work.in.progress.hospitalmanagement.cell;

import work.in.progress.hospitalmanagement.model.Person;

/**
 * Class representing the cell displayed in a {@link javafx.scene.control.ListView} in e.g.
 * {@link work.in.progress.hospitalmanagement.controller.PatientRegistrationViewController}
 * representing a person registered with the health institution
 * @param <T> the type of the person represented
 * @author Andrzej Grabowski
 */
public class PersonCell<T extends Person> extends DeleteListCell<T> {

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
        getLabel().setText("ID: " + item.getId() + " " + item.getName() + " " + item.getSurname());
        setGraphic(getHBox());
    }
}
