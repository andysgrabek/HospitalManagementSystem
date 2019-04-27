package work.in.progress.hospitalmanagement.converter;

import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import work.in.progress.hospitalmanagement.model.Bed;

import java.util.List;

/**
 * Concretization of a {@link StringConverter} to convert a {@link Bed} to a {@link String} and back
 * to be used with a {@link javafx.scene.control.ComboBox}
 * @author Andrzej Grabowski
 */
@AllArgsConstructor
public class BedStringConverter extends StringConverter<Bed> {

    private List<Bed> list;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(Bed object) {
        return object.getDepartment().getName() + " " + object.getRoomNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bed fromString(String string) {
        return list.stream().filter(object ->
                (object.getDepartment().getName() + " " + object.getRoomNumber())
                        .equals(string)).findFirst().orElse(null);
    }
}
