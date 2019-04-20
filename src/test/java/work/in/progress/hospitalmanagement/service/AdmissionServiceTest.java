package work.in.progress.hospitalmanagement.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.repository.InpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.repository.OutpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class AdmissionServiceTest {

    private AdmissionService admissionService;

    @MockBean
    private InpatientAdmissionRepository inpatientAdmissionRepository;

    @MockBean
    private OutpatientAdmissionRepository outpatientAdmissionRepository;

    @Before
    public void setUp() {
        admissionService = new AdmissionService(inpatientAdmissionRepository,
                outpatientAdmissionRepository);
    }

    @Test
    public void whenPatientAllocated_thenAdmissionShouldBeFound() {
        InpatientAdmission inpatientAdmission = Mocks.inpatientAdmission();
        Mockito.when(inpatientAdmissionRepository.save(inpatientAdmission))
                .thenReturn(inpatientAdmission);
        OutpatientAdmission outpatientAdmission = Mocks.outpatientAdmission();
        Mockito.when(outpatientAdmissionRepository.save(outpatientAdmission))
                .thenReturn(outpatientAdmission);

        InpatientAdmission allocated = admissionService
                .allocatePatient(inpatientAdmission);
        OutpatientAdmission called = admissionService.callPatient(outpatientAdmission);

        assertThat(allocated).isNotNull();
        assertThat(called).isNotNull();
    }

    @Test
    public void whenPatientDischarged_thenAdmissionShouldNotBeFound() {
        InpatientAdmission inpatientAdmission = Mocks.inpatientAdmission();
        Mockito.when(inpatientAdmissionRepository.save(inpatientAdmission))
                .thenReturn(inpatientAdmission);
        OutpatientAdmission outpatientAdmission = Mocks.outpatientAdmission();
        Mockito.when(outpatientAdmissionRepository.save(outpatientAdmission))
                .thenReturn(outpatientAdmission);

        InpatientAdmission allocated = admissionService
                .allocatePatient(inpatientAdmission);
        OutpatientAdmission called = admissionService.callPatient(outpatientAdmission);
        admissionService.dischargePatient(allocated);
        admissionService.dischargePatient(called);

        Mockito.verify(inpatientAdmissionRepository, Mockito.times(1)).delete(allocated);
        Mockito.verify(outpatientAdmissionRepository, Mockito.times(1)).delete(called);
    }

}
