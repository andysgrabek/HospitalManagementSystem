package work.in.progress.hospitalmanagement.converter;

import javafx.util.StringConverter;
import work.in.progress.hospitalmanagement.model.HospitalStaff;

import java.util.List;

/**
 * Concretization of a {@link StringConverter} to convert a {@link HospitalStaff.Role} to a {@link String} and back
 * to be used with a {@link javafx.scene.control.ComboBox}
 * @author Andrzej Grabowski
 */
public class RoleStringConverter extends StringConverter<HospitalStaff.Role> {

    private List<HospitalStaff.Role> list;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(HospitalStaff.Role object) {
        return object.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HospitalStaff.Role fromString(String string) {
        return HospitalStaff.Role.valueOf(string);
    }
}
