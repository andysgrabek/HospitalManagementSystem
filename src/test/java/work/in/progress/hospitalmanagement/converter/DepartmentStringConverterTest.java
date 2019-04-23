package work.in.progress.hospitalmanagement.converter;

import org.junit.Test;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class DepartmentStringConverterTest {

    @Test
    public void toStringTest() {
        DepartmentStringConverter converter =
                new DepartmentStringConverter(Collections.singletonList(Mocks.department()));
        assertEquals(Mocks.department().getName(), converter.toString(Mocks.department()));
    }

    @Test
    public void fromStringTest() {
        Department d = Mocks.department();
        DepartmentStringConverter converter =
                new DepartmentStringConverter(Collections.singletonList(d));
        assertEquals(d, converter.fromString(d.getName()));
    }
}