package work.in.progress.hospitalmanagement.model;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.in.progress.hospitalmanagement.util.Mocks;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class DefaultValidatorTest {

    private Validator validator;

    @Before
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        assertThat(validator.validate(Mocks.address())).isEmpty();
        assertThat(validator.validate(Mocks.patient())).isEmpty();
        assertThat(validator.validate(Mocks.department())).isEmpty();
        assertThat(validator.validate(Mocks.bed())).isEmpty();
    }

    @Test
    public void whenDepartmentNameTooLong_thenConstraintValidationShouldOccur() {
        Department department = Mocks.department();
        ReflectionTestUtils.setField(department, "name", StringUtils.repeat('a', 101));

        assertThat(validator.validate(department)).isNotEmpty();
    }

    @Test
    public void whenPersonNameNotMatchPattern_thenConstraintValidationShouldOccur() {
        Patient patient = Mocks.patient();
        ReflectionTestUtils.setField(patient, "name", "Joh7n");

        assertThat(validator.validate(patient)).isNotEmpty();
    }

    @Test
    public void whenZipCodeTooShort_thenConstraintValidationShouldOccur() {
        Address address = Mocks.address();
        address.setZipCode(123);

        assertThat(validator.validate(address)).isNotEmpty();
    }

    @Test
    public void whenBedRoomNumberNull_thenConstraintValidationShouldOccur() {
        Bed bed = Mocks.bed();
        bed.setRoomNumber(null);

        assertThat(validator.validate(bed)).isNotEmpty();
    }

    @Test
    public void whenPatientPhoneNumberInWrongFormat_thenConstraintValidationShouldOccur() {
        Patient patient = Mocks.patient();
        patient.setPhoneNumber("123ABC56789");

        assertThat(validator.validate(patient)).isNotEmpty();
    }

}
