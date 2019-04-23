package work.in.progress.hospitalmanagement.cell;

import work.in.progress.hospitalmanagement.model.Person;

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
        getLabel().setText(item.getName() + " " + item.getSurname());
        setGraphic(getHBox());
    }
}
