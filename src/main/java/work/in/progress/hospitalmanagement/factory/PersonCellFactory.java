package work.in.progress.hospitalmanagement.factory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import work.in.progress.hospitalmanagement.cell.PersonCell;
import work.in.progress.hospitalmanagement.model.Person;

/**
 * Custom cell factory class to be used with a {@link ListView} containing {@link Person} objects.
 * @author Andrzej Grabowski
 */
public class PersonCellFactory<T extends Person> implements Callback<ListView<T>, ListCell<T>> {

    @Override
    public ListCell<T> call(ListView<T> listView) {
        return new PersonCell<T>();
    }

}
