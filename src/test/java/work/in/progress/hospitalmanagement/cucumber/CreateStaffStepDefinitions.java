package work.in.progress.hospitalmanagement.cucumber;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javafx.application.Platform;
import org.junit.Assert;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.HospitalStaff;
import work.in.progress.hospitalmanagement.service.DepartmentService;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@Ignore
public class CreateStaffStepDefinitions extends IntegrationTest {

    @Autowired
    private DepartmentService departmentService;

    @Given("Department A exists")
    public void department_exists() {
        departmentService.save(new Department("A"));
    }

    @Given("I am in the <staff> screen")
    public void i_am_in_the_staff_screen() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        final JFXButton button = find("Staff");
        button.fire();
        TimeUnit.SECONDS.sleep(1);
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
        Platform.runLater(() -> comboBox.getSelectionModel().select(0));
    }

    @When("I choose <staff_member_specialisation>")
    public void i_choose_staff_member_specialisation() {
        final JFXComboBox comboBox = find("#roleField");
        Platform.runLater(() -> comboBox.getSelectionModel().select(0));
    }

    @When("I click button <create_staff_member>")
    public void i_click_button_create_staff_member() throws InterruptedException {
        final JFXButton button = find("#createStaffButton");
        Platform.runLater(button::fire);
        TimeUnit.SECONDS.sleep(1);
    }

    @Then("I can see new staff_member in the list")
    public void i_can_see_new_staff_member_in_the_list() {
        JFXListView listView = find("#staffListView");
        HospitalStaff staff = (HospitalStaff) listView.getItems().get(0);
        Assert.assertEquals(staff.getSurname(), "House");
        Assert.assertEquals(staff.getDepartment().getName(), "A");
    }

}
