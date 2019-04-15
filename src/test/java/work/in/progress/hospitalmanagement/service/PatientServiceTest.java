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
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PatientServiceTest {

    private PatientService patientService;

    @MockBean
    private PatientRepository patientRepository;

    private final static String patientName = "John";
    private final static String patientSurname = "Smith";
    private final static LocalDate patientBirthDate = LocalDate.of(1410, 7, 15);

    @Before
    public void setUp() {
        patientService = new PatientService(patientRepository);
        Patient patient = Patient.builder()
                .name(patientName)
                .surname(patientSurname)
                .homeAddress(new Address("Energy 1","Copenhagen", 12345))
                .isAlive(true)
                .phoneNumber("123456789")
                .birthDate(patientBirthDate)
                .build();

        Mockito.when(patientRepository.findByName(patient.getName()))
                .thenReturn(Optional.of(patient));
        Mockito.when(patientRepository.findBySurname(patient.getSurname()))
                .thenReturn(Optional.of(patient));
        Mockito.when(patientRepository.findByBirthDate(patient.getBirthDate()))
                .thenReturn(Optional.of(patient));
    }

    @Test
    public void whenValidName_thenPatientShouldBeFound() {
        Optional<Patient> result = patientService.findByName(patientName);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getName()).isEqualTo(patientName);
    }

    @Test
    public void whenValidSurname_thenPatientShouldBeFound() {
        Optional<Patient> result = patientService.findBySurname(patientSurname);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getSurname()).isEqualTo(patientSurname);
    }

    @Test
    public void whenValidBirthDate_thenPatientShouldBeFound() {
        Optional<Patient> result = patientService.findByBirthDate(patientBirthDate);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getBirthDate()).isEqualTo(patientBirthDate);
    }

    @Test
    public void whenPatientRegistered_thenPatientShouldBeFound() {
        Patient patient = Patient.builder().build();
        Mockito.when(patientRepository.save(patient)).thenReturn(patient);

        Patient registered = patientService.registerPatient(patient);

        assertThat(registered).isNotNull();
    }

    @Test
    public void whenRegisteredPatientUnregistered_thenPatientShouldNotBeFound() {
        Patient patient = Patient.builder().build();
        Mockito.when(patientRepository.save(patient)).thenReturn(patient);

        Patient registered = patientService.registerPatient(patient);
        patientService.unregisterPatient(patient);

        Mockito.verify(patientRepository, Mockito.times(1)).delete(registered);
    }

}
