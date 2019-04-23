package work.in.progress.hospitalmanagement.converter;

import org.junit.Test;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.HospitalStaff;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.util.Collections;

import static org.junit.Assert.*;

public class RoleStringConverterTest {

    @Test
    public void toStringTest() {
        HospitalStaff.Role d = Mocks.hospitalStaff().getRole();
        RoleStringConverter converter =
                new RoleStringConverter();
        assertEquals(d.toString(), converter.toString(d));
    }

    @Test
    public void fromStringTest() {
        HospitalStaff.Role d = Mocks.hospitalStaff().getRole();
        RoleStringConverter converter =
                new RoleStringConverter();
        assertEquals(d, converter.fromString(d.toString()));
    }
}