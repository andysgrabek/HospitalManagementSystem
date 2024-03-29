package work.in.progress.hospitalmanagement.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.model.Address;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.repository.PatientRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PatientServiceTest {

    private final static String patientName = "John";
    private final static String patientSurname = "Smith";
    private final static LocalDate patientBirthDate = LocalDate.of(1410, 7, 15);
    private PatientService patientService;
    @MockBean
    private PatientRepository patientRepository;

    @Before
    public void setUp() {
        patientService = new PatientService(patientRepository);
        Patient patient = Patient.builder().name(patientName).surname(patientSurname)
                .homeAddress(new Address("Energy 1", "Copenhagen", 12345)).isAlive(true)
                .phoneNumber("123456789").birthDate(patientBirthDate).build();

        Mockito.when(patientRepository.findByName(patient.getName()))
                .thenReturn(Collections.singletonList(patient));
        Mockito.when(patientRepository.findBySurname(patient.getSurname()))
                .thenReturn(Collections.singletonList(patient));
        Mockito.when(patientRepository.findByBirthDate(patient.getBirthDate()))
                .thenReturn(Collections.singletonList(patient));
    }

    @Test
    public void whenValidName_thenPatientShouldBeFound() {
        List<Patient> result = patientService.findByName(patientName);

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get(0).getName()).isEqualTo(patientName);
    }

    @Test
    public void whenValidSurname_thenPatientShouldBeFound() {
        List<Patient> result = patientService.findBySurname(patientSurname);

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get(0).getSurname()).isEqualTo(patientSurname);
    }

    @Test
    public void whenValidBirthDate_thenPatientShouldBeFound() {
        List<Patient> result = patientService.findByBirthDate(patientBirthDate);

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get(0).getBirthDate()).isEqualTo(patientBirthDate);
    }

}
