package work.in.progress.hospitalmanagement.cucumber;

import com.jfoenix.controls.JFXButton;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;
import work.in.progress.hospitalmanagement.service.PatientService;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

@Ignore
public class RegisterPatientStepDefinitions extends IntegrationTest {

    @Autowired
    private PatientService patientService;

    @Given("^I am in the <register_patient> screen$")
    public void i_am_in_the_register_patient_screen() throws InterruptedException {
        /* TODO: Sample test - refactor it later */
        TimeUnit.SECONDS.sleep(3);

        final JFXButton button = find("Registration");
        button.fire();
        TimeUnit.SECONDS.sleep(1);

        assertNotNull(patientService);
        FxAssert.verifyThat(".hms-label-title", LabeledMatchers.hasText("Patient registration"));
    }

    @When("^I enter <name>$")
    public void i_enter_name() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I enter <surname>$")
    public void i_enter_surname() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I enter <birth-date>$")
    public void i_enter_birth_date() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I enter <home-address>$")
    public void i_enter_home_address() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I enter <phone-number>$")
    public void i_enter_phone_number() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I enter <tribe>$")
    public void i_enter_tribe() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I enter <alive/dead>$")
    public void i_enter_alive_dead() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I click button <submit>$")
    public void i_click_button_submit() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^new patient is created$")
    public void new_patient_is_created() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I am redirected to <clerk_panel> screen$")
    public void i_am_redirected_to_clerk_panel_screen() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
