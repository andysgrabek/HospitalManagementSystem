package work.in.progress.hospitalmanagement.converter;

import javafx.util.StringConverter;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.service.DepartmentService;

/**
 * Concretization of a {@link StringConverter} to convert a {@link Department} to a {@link String} and back
 * to be used with a {@link javafx.scene.control.ComboBox}
 * @author Andrzej Grabowski
 */
@Component
public class DepartmentStringConverter extends StringConverter<Department> {

    private final DepartmentService departmentService;

    public DepartmentStringConverter(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

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
        return departmentService.findAll()
                .stream().filter(obj -> obj.getName().equals(string)).findFirst().orElse(null);
    }
}
