package work.in.progress.hospitalmanagement.converter;

import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import work.in.progress.hospitalmanagement.model.Department;

import java.util.List;

@AllArgsConstructor
public class DepartmentStringConverter extends StringConverter<Department> {

    private List<Department> list;

    @Override
    public String toString(Department object) {
        return object.getName();
    }

    @Override
    public Department fromString(String string) {
        return list.stream().filter(obj -> obj.getName().equals(string)).findFirst().orElse(null);
    }
}
