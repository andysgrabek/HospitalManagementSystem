package work.in.progress.hospitalmanagement.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.repository.PatientRepository;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class AbstractServiceTest {

    private AbstractService<Patient, Integer> service;

    @MockBean
    private PatientRepository repository;

    @Before
    public void setUp() {
        service = new PatientService(repository);
    }

    @Test
    public void whenEntitySaved_thenEntityShouldBeFound() {
        Patient patient = Mocks.patient();
        Mockito.when(repository.save(patient)).thenReturn(patient);

        assertThat(service.save(patient)).isNotNull();
    }

    @Test
    public void whenEntityDeleted_thenEntityShouldNotBeFound() {
        Patient patient = Mocks.patient();
        service.delete(patient);

        Mockito.verify(repository, Mockito.times(1)).delete(patient);
    }

    @Test
    public void whenFindAll_thenEntitiesShouldBeFound() {
        Mockito.when(repository.findAll(service.defaultSort())).
                thenReturn(Arrays.asList(Mocks.patient(), Mocks.patient(), Mocks.patient()));

        assertThat(service.findAll().size()).isEqualTo(3);
    }

}
