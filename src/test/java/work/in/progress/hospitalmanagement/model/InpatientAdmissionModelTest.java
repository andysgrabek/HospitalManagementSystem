package work.in.progress.hospitalmanagement.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class InpatientAdmissionModelTest {

    @Test
    public void whenToStringCalled_thenProperStringShouldBeReturned() {
        InpatientAdmission admission = Mocks.inpatientAdmission();
        assertThat(admission.toString()).isEqualTo("InpatientAdmission(" +
                "super=Admission(admissionDate=" + admission.getAdmissionDate() + ", " +
                "patient=" + admission.getPatient() + "), " +
                "bed=" + admission.getBed() + ")");
    }

}
