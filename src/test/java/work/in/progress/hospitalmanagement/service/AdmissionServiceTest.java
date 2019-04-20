package work.in.progress.hospitalmanagement.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.repository.InpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.repository.OutpatientAdmissionRepository;

@RunWith(SpringRunner.class)
public class AdmissionServiceTest {

    private InpatientAdmissionService inpatientAdmissionService;
    private OutpatientAdmissionService outpatientAdmissionService;

    @MockBean
    private InpatientAdmissionRepository inpatientAdmissionRepository;

    @MockBean
    private OutpatientAdmissionRepository outpatientAdmissionRepository;

    @Before
    public void setUp() {
        inpatientAdmissionService = new InpatientAdmissionService(inpatientAdmissionRepository);
        outpatientAdmissionService = new OutpatientAdmissionService(outpatientAdmissionRepository);
    }

    @Test
    public void emptyTest() {
    }

}
