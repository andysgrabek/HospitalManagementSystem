package work.in.progress.hospitalmanagement.converter;

import javafx.util.StringConverter;
import work.in.progress.hospitalmanagement.model.HospitalStaff;

import java.util.List;

public class RoleStringConverter extends StringConverter<HospitalStaff.Role> {

    private List<HospitalStaff.Role> list;

    @Override
    public String toString(HospitalStaff.Role object) {
        return object.toString();
    }

    @Override
    public HospitalStaff.Role fromString(String string) {
        return HospitalStaff.Role.valueOf(string);
    }
}
