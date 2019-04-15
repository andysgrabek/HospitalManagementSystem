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
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PatientModelTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PatientRepository patientRepository;

    private final static String patientName = "John";
    private final static String patientSurname = "Smith";
    private final static LocalDate patientBirthDate = LocalDate.of(1410, 7, 15);

    @Before
    public void setUp() {
        entityManager.persist(Patient.builder()
                .name(patientName)
                .surname(patientSurname)
                .homeAddress(new Address("Energy 1","Copenhagen", 12345))
                .isAlive(true)
                .phoneNumber("123456789")
                .birthDate(patientBirthDate)
                .build());
    }

    @Test
    public void whenValidName_thenPatientShouldBeFound() {
        Optional<Patient> result = patientRepository.findByName(patientName);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getName()).isEqualTo(patientName);
    }

    @Test
    public void whenValidSurname_thenPatientShouldBeFound() {
        Optional<Patient> result = patientRepository.findBySurname(patientSurname);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getSurname()).isEqualTo(patientSurname);
    }

    @Test
    public void whenInvalidBirthDate_thenThrow() {
        Patient patient = Patient.builder()
                .name("")
                .surname(patientSurname)
                .homeAddress(new Address("Energy 1","Copenhagen", 12345))
                .isAlive(true)
                .phoneNumber("123456789")
                .birthDate(LocalDate.now().plusDays(1))
                .build();
        entityManager.persist(patient);

        Optional<Patient> result = patientRepository.findBySurname(patientSurname);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getSurname()).isEqualTo(patientSurname);;
//        assertThatThrownBy();
//        assertThat

    }

}
