package work.in.progress.hospitalmanagement.cucumber;

import com.jfoenix.controls.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.Ignore;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.HospitalStaff;
import work.in.progress.hospitalmanagement.model.Patient;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

@Ignore
public class RegisterPatientStepDefinitions extends IntegrationTest {

    @Given("^I am in the <register_patient> screen$")
    public void i_am_in_the_register_patient_screen() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        final JFXButton button = find("Registration");
        button.fire();
        TimeUnit.SECONDS.sleep(1);
        assertNotNull(patientService);
        FxAssert.verifyThat(".hms-label-title", LabeledMatchers.hasText("Patient registration"));
    }

    @When("^I enter <name>$")
    public void i_enter_name() throws Throwable {
        final JFXTextField textField = find("#nameField");
        textField.setText("John");
    }

    @When("^I enter <surname>$")
    public void i_enter_surname() throws Throwable {
        final JFXTextField textField = find("#surnameField");
        textField.setText("Johny");
    }

    @When("^I enter <birth-date>$")
    public void i_enter_birth_date() throws Throwable {
        final JFXDatePicker datePicker = find("#birthDatePicker");
        datePicker.setValue(LOCAL_DATE("01-05-2016"));
    }

    @When("^I enter <home-address>$")
    public void i_enter_home_address() throws Throwable {
        final JFXTextField addressField = find("#addressLineField");
        final JFXTextField cityField = find("#cityField");
        final JFXTextField postalCodeField = find("#postalCodeField");
        addressField.setText("ABC street");
        cityField.setText("Copenhagen");
        postalCodeField.setText("10000");
    }

    @When("^I enter <phone-number>$")
    public void i_enter_phone_number() throws Throwable {
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


    @Given("I am in the <department> screen")
    public void i_am_in_the_department_screen() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        final JFXButton button = find("Departments");
        button.fire();
        TimeUnit.SECONDS.sleep(1);
        assertNotNull(patientService);
        FxAssert.verifyThat(".hms-label-title", LabeledMatchers.hasText("Department management"));
    }

    @When("I enter <department_name>")
    public void i_enter_department_name() {
        final JFXTextField textField = find("#nameField");
        textField.setText("A");
    }

    @When("I click button <create_department>")
    public void i_click_button_create_department() throws InterruptedException {
        final JFXButton button = find("#createDepartmentButton");
        button.fire();
        TimeUnit.SECONDS.sleep(1);
    }

    @Then("I can see new department in the list")
    public void i_can_see_new_department_in_the_list() {
        JFXListView listView = find("#departmentsListView");
        Department department = (Department) listView.getItems().get(0);
        Assert.assertEquals(department.getName(), "A");
    }


    @Given("Department A exists")
    public void department_exists() {
        departmentService.save(new Department("A"));
    }

    @Given("I am in the <staff> screen")
    public void i_am_in_the_staff_screen() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        final JFXButton button = find("Staff");
        button.fire();
        TimeUnit.SECONDS.sleep(1);
        assertNotNull(patientService);
        FxAssert.verifyThat(".hms-label-title", LabeledMatchers.hasText("Staff management"));
    }

    @When("I enter <staff_member_name>")
    public void i_enter_staff_member_name() {
        final JFXTextField textField = find("#nameField");
        textField.setText("John");
    }

    @When("I enter <staff_member_surname>")
    public void i_enter_staff_member_surname() {
        final JFXTextField textField = find("#surnameField");
        textField.setText("House");
    }

    @When("I choose <staff_member_department>")
    public void i_choose_staff_member_department() {
        final JFXComboBox comboBox = find("#departmentField");
        comboBox.getSelectionModel().select(0);
    }

    @When("I choose <staff_member_specialisation>")
    public void i_choose_staff_member_specialisation() {
        final JFXComboBox comboBox = find("#roleField");
        comboBox.getSelectionModel().select(0);
    }

    @When("I click button <create_staff_member>")
    public void i_click_button_create_staff_member() throws InterruptedException {
        final JFXButton button = find("#createStaffButton");
        button.fire();
        TimeUnit.SECONDS.sleep(1);
    }

    @Then("I can see new staff_member in the list")
    public void i_can_see_new_staff_member_in_the_list() throws InterruptedException {
        JFXListView listView = find("#staffListView");
        HospitalStaff staff = (HospitalStaff) listView.getItems().get(0);
        Assert.assertEquals(staff.getSurname(), "House");
    }
}
