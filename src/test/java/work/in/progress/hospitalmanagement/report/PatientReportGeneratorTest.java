package work.in.progress.hospitalmanagement.report;

import com.itextpdf.text.DocumentException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PatientReportGenerator.class)
public class PatientReportGeneratorTest {

    @Rule
    public final TemporaryFolder TEST_FOLDER = new TemporaryFolder();

    @Autowired
    private ReportGenerator<Patient> patientReportGenerator;

    @Test
    public void whenCustomPathReportGenerated_thenFileShouldBeCreated() throws IOException, DocumentException {
        List<Patient> patients = Arrays.asList(Mocks.patient(), Mocks.patient(), Mocks.patient());
        String path = patientReportGenerator.createDefaultPath();
        String reportPath = String.format("%s/%s", TEST_FOLDER.newFolder().getPath(), path);

        File file = patientReportGenerator.generate(patients, reportPath);
        assertThat(file.exists()).isTrue();
    }

    @Test
    public void whenDefaultReportGenerated_thenFileShouldBeCreated() throws IOException, DocumentException {
        File file = patientReportGenerator.generate(Arrays.asList(Mocks.patient(), Mocks.patient(), Mocks.patient()));
        file.deleteOnExit();

        assertThat(file.exists()).isTrue();
    }

}
