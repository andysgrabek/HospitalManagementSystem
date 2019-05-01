package work.in.progress.hospitalmanagement.cucumber;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.Ignore;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;
import work.in.progress.hospitalmanagement.model.Department;

import java.util.concurrent.TimeUnit;

@Ignore
public class CreateDepartmentStepDefinitions extends IntegrationTest {

    @Given("I am in the <department> screen")
    public void i_am_in_the_department_screen() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        final JFXButton button = find("Departments");
        button.fire();
        TimeUnit.SECONDS.sleep(1);
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

}
