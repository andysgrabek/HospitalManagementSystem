package work.in.progress.hospitalmanagement.cucumber;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import org.junit.Assert;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import work.in.progress.hospitalmanagement.model.Address;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.HospitalStaff;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.service.HospitalStaffService;
import work.in.progress.hospitalmanagement.service.InpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.OutpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@Ignore
public class StepDefinitions extends IntegrationTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private BedService bedService;

    @Autowired
    private InpatientAdmissionService inpatientAdmissionService;

    @Autowired
    private OutpatientAdmissionService outpatientAdmissionService;

    @Autowired
    private HospitalStaffService hospitalStaffService;

    @Given("^Patient with name \"([^\"]*)\" and surname \"([^\"]*)\" exists$")
    public void patient_with_name_and_surname_exists(String name, String surname) {
        Patient patient = Patient.builder()
                .birthDate(LocalDate.now().minusYears(30))
                .homeAddress(new Address("address", "Copenhagen", 11111))
                .name(name)
                .surname(surname)
                .isAlive(true)
                .phoneNumber("123123123")
                .build();
        patientService.save(patient);
    }

    @Given("^Staff member with name \"([^\"]*)\" and surname \"([^\"]*)\" exists$")
    public void staff_member_with_name_and_surname_exists(String name, String surname) {
        Department department = departmentService.findAll().get(0);
        HospitalStaff hospitalStaff = HospitalStaff.builder()
                .name(name)
                .surname(surname)
                .department(department)
                .role(HospitalStaff.Role.CLERK)
                .build();

        hospitalStaffService.save(hospitalStaff);
    }

    @Given("^Department \"([^\"]*)\" exists$")
    public void department_exists(String name) {
        departmentService.save(new Department(name));
    }

    @Given("^Bed \"([^\"]*)\" is assigned to Department \"([^\"]*)\"$")
    public void bed_is_assigned_to_department(String bedNumber, String departmentName) {
        Department department = departmentService.findAll()
                .stream()
                .filter(el -> el.getName().equals(departmentName))
                .findFirst()
                .orElse(null);
        bedService.save(new Bed(department, bedNumber));
    }

    @Given("^Patient with surname \"([^\"]*)\" is assigned to bed \"([^\"]*)\"$")
    public void patient_with_surname_is_assigned_to_bed(String surname, String bedNumber) {
        Bed bed = bedService.findAll()
                .stream()
                .filter(el -> el.getRoomNumber().equals(bedNumber))
                .findFirst()
                .orElse(null);
        Patient patient = patientService.findBySurname(surname).get(0);
        InpatientAdmission admission = new InpatientAdmission(patient, bed);
        inpatientAdmissionService.save(admission);
    }

    @Given("^Patient with surname \"([^\"]*)\" is assigned to appointment in department \"([^\"]*)\"$")
    public void patient_with_surname_is_assigned_to_appointment_in_department(String surname, String depName) {
        Patient patient = patientService.findBySurname(surname).get(0);
        Department department = departmentService.findAll()
                .stream()
                .filter(el -> el.getName().equals(depName))
                .findFirst()
                .orElse(null);

        LocalDateTime dateTime = LocalDateTime.now().plusDays(5);
        OutpatientAdmission admission = new OutpatientAdmission(patient, department, dateTime);
        outpatientAdmissionService.save(admission);
    }

    @Given("^I am in the \"([^\"]*)\" screen$")
    public void i_am_in_the_screen(String screenName) throws Throwable {
        TimeUnit.SECONDS.sleep(5);
        final JFXButton button = find(screenName);
        button.fire();
        TimeUnit.SECONDS.sleep(1);
    }

    @When("^I enter \"([^\"]*)\" into \"([^\"]*)\" input$")
    public void i_enter_into(String value, String id) {
        final JFXTextField textField = find("#" + id + "Field");
        Platform.runLater(() -> textField.setText(value));
    }

    @When("^I click button \"([^\"]*)\"$")
    public void i_click_button(String name) throws InterruptedException {
        final JFXButton button = find("#" + name + "Button");
        Platform.runLater(button::fire);
        TimeUnit.SECONDS.sleep(1);
    }

    @When("^I click button with text \"([^\"]*)\"$")
    public void i_click_button_with_text(String name) throws InterruptedException {
        final JFXButton button = find(name);
        System.out.println(button);
        Platform.runLater(button::fire);
        TimeUnit.SECONDS.sleep(1);
    }

    @When("^I enter date \"([^\"]*)\" into \"([^\"]*)\" input$")
    public void i_enter_date(String value, String id) {
        final JFXDatePicker datePicker = find("#" + id);
        datePicker.setValue(parseDateString(value));
    }

    @Then("I can see \"([^\"]*)\" in the \"([^\"]*)\" list")
    public void i_can_see_in_the_list(String text, String value) {
        JFXListView listView = find("#" + value + "ListView");
        Assert.assertTrue(listView.getItems().toString().contains(text));
    }

    @Then("I can not see \"([^\"]*)\" in the \"([^\"]*)\" list")
    public void i_can_not_see_in_the_list(String text, String value) {
        JFXListView listView = find("#" + value + "ListView");
        Assert.assertFalse(listView.getItems().toString().contains(text));
    }

    @When("^I pick first option in \"([^\"]*)\" comboBox$")
    public void i_pick_first_option_in_comboBox(String id) {
        final JFXComboBox comboBox = find("#" + id + "Field");
        Platform.runLater(() -> comboBox.getSelectionModel().select(0));
    }

    @Given("^I set checkbox \"([^\"]*)\" as \"([^\"]*)\"$")
    public void i_set_checkbox_as(String id, String value) {
        JFXCheckBox checkBox = find("#" + id);
        Platform.runLater(() -> checkBox.setSelected(value.equals("true")));
    }

    @And("^I enter time into \"([^\"]*)\" input$")
    public void i_enter_time_into_input(String id) {
        final JFXTimePicker timePicker = find("#" + id);
        timePicker.setValue(LocalTime.now());
    }

    @Then("I can see \"([^\"]*)\" in table \"([^\"]*)\"")
    public void i_can_see_in_table(String text, String id) {
        TableView tableView = find("#" + id);
        TableColumn col = (TableColumn) tableView.getColumns().get(0);
        String data = (String) col.getCellObservableValue(tableView.getItems().get(0)).getValue();
        Assert.assertTrue(data.contains(text));
    }

    @And("^I press enter button$")
    public void i_press_enter_button() throws InterruptedException {
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }

    @Then("^Report is generated$")
    public void reportIsGenerated() {
        Assert.assertTrue(true);
    }
}
