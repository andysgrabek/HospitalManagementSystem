package work.in.progress.hospitalmanagement.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.repository.PatientRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PatientModelTest {

    private final static String patientName = "John";
    private final static String patientSurname = "Smith";
    private final static LocalDate patientBirthDate = LocalDate.of(1410, 7, 15);
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PatientRepository patientRepository;

    @Before
    public void setUp() {
        entityManager.persist(Patient.builder().name(patientName).surname(patientSurname)
                .birthDate(patientBirthDate).phoneNumber("123456789").isAlive(true)
                .homeAddress(new Address("Energy 1", "Copenhagen", 12345)).build());
    }

    @Test
    public void whenValidName_thenPatientShouldBeFound() {
        List<Patient> result = patientRepository.findByName(patientName);

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get(0).getName()).isEqualTo(patientName);
    }

    @Test
    public void whenValidSurname_thenPatientShouldBeFound() {
        List<Patient> result = patientRepository.findBySurname(patientSurname);

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get(0).getSurname()).isEqualTo(patientSurname);
    }

    @Test
    public void whenValidBirthDate_thenPatientShouldBeFound() {
        List<Patient> result = patientRepository.findByBirthDate(patientBirthDate);

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get(0).getBirthDate()).isEqualTo(patientBirthDate);
    }

}
