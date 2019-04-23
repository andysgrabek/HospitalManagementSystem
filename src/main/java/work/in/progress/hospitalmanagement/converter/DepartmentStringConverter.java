package work.in.progress.hospitalmanagement.converter;

import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import work.in.progress.hospitalmanagement.model.Department;

import java.util.List;

/**
 * Concretization of a {@link StringConverter} to convert a {@link Department} to a {@link String} and back
 * to be used with a {@link javafx.scene.control.ComboBox}
 * @author Andrzej Grabowski
 */
@AllArgsConstructor
public class DepartmentStringConverter extends StringConverter<Department> {

    private List<Department> list;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(Department object) {
        return object.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Department fromString(String string) {
        return list.stream().filter(obj -> obj.getName().equals(string)).findFirst().orElse(null);
    }
}
