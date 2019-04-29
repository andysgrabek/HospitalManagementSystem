package work.in.progress.hospitalmanagement.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/feature/CreateStaff.feature")
public class CreateStaffCucumberIntegrationTest {
}
