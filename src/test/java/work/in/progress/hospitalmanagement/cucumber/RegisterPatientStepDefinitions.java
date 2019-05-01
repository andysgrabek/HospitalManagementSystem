package work.in.progress.hospitalmanagement.cucumber;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.Ignore;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;
import work.in.progress.hospitalmanagement.model.Patient;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Ignore
public class RegisterPatientStepDefinitions extends IntegrationTest {

    @Given("^I am in the <register_patient> screen$")
    public void i_am_in_the_register_patient_screen() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        final JFXButton button = find("Registration");
        button.fire();
        TimeUnit.SECONDS.sleep(1);
        FxAssert.verifyThat(".hms-label-title", LabeledMatchers.hasText("Patient registration"));
    }

    @When("^I enter <name>$")
    public void i_enter_name() {
        final JFXTextField textField = find("#nameField");
        textField.setText("John");
    }

    @When("^I enter <surname>$")
    public void i_enter_surname() {
        final JFXTextField textField = find("#surnameField");
        textField.setText("Johny");
    }

    @When("^I enter <birth-date>$")
    public void i_enter_birth_date() {
        final JFXDatePicker datePicker = find("#birthDatePicker");
        datePicker.setValue(LocalDate.now().minusDays(1));
    }

    @When("^I enter <home-address>$")
    public void i_enter_home_address() {
        final JFXTextField addressField = find("#addressLineField");
        final JFXTextField cityField = find("#cityField");
        final JFXTextField postalCodeField = find("#postalCodeField");
        addressField.setText("ABC street");
        cityField.setText("Copenhagen");
        postalCodeField.setText("10000");
    }

    @When("^I enter <phone-number>$")
    public void i_enter_phone_number() {
        final JFXTextField textField = find("#phoneNumberField");
        textField.setText("321321321");

    }

    @When("^I click button <submit>$")
    public void i_click_button_submit() throws Throwable {
        final JFXButton button = find("#registerPatientButton");
        button.fire();
        TimeUnit.SECONDS.sleep(1);
    }

    @Then("^I can see new patient in the list$")
    public void i_am_redirected_to_clerk_panel_screen() {
        JFXListView listView = find("#registeredPatientListView");
        Patient patient = (Patient) listView.getItems().get(0);
        Assert.assertEquals(patient.getName(), "John");
    }

}
