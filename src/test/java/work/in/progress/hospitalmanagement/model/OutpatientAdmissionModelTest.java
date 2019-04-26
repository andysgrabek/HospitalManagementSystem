package work.in.progress.hospitalmanagement.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class OutpatientAdmissionModelTest {

    @Test
    public void whenToStringCalled_thenProperStringShouldBeReturned() {
        OutpatientAdmission admission = Mocks.outpatientAdmission();
        assertThat(admission.toString()).isEqualTo("OutpatientAdmission(" +
                "super=Admission(admissionDate=" + admission.getAdmissionDate() + ", " +
                "patient=" + admission.getPatient() + "), " +
                "department=" + admission.getDepartment() + ", " +
                "visitDate=" + admission.getVisitDate() + ")");
    }

}
