package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.event.ListCellEvent;
import work.in.progress.hospitalmanagement.factory.BedCellFactory;
import work.in.progress.hospitalmanagement.factory.DepartmentCellFactory;
import work.in.progress.hospitalmanagement.model.Address;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.validator.TextFieldValidator;

import javax.validation.Validator;
import java.net.URL;
import java.util.ResourceBundle;

import static work.in.progress.hospitalmanagement.event.ListCellEvent.DELETE_EVENT;
import static work.in.progress.hospitalmanagement.event.ListCellEvent.EDIT_EVENT;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class DepartmentManagementViewController extends AbstractViewController {

    private final DepartmentService departmentService;
    private final BedService bedService;
    private Validator validator;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXListView<Bed> bedsListView;
    @FXML
    private HBox formButtonsParent;
    @FXML
    private JFXButton cancelEditDepartmentButton;
    @FXML
    private JFXButton createDepartmentButton;
    @FXML
    private Label formLabel;
    @FXML
    private JFXListView<Department> departmentsListView;
    private Department editedDepartment;
    private ObservableList<Department> departmentObservableList;

    public DepartmentManagementViewController(DepartmentService departmentService,
                                              BedService bedService,
                                              Validator validator) {
        this.departmentService = departmentService;
        this.bedService = bedService;
        this.validator = validator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bedsListView.setCellFactory(new BedCellFactory());
        departmentsListView.setCellFactory(new DepartmentCellFactory());
        departmentObservableList = FXCollections.observableArrayList(departmentService.findAll());
        departmentsListView.setItems(departmentObservableList);
        departmentsListView.addEventHandler(EDIT_EVENT, handleDepartmentEditPressed());
        departmentsListView.addEventHandler(DELETE_EVENT, handleDepartmentDeletePressed());
        bedsListView.addEventHandler(DELETE_EVENT, handleBedDeletePressed());
        formButtonsParent.getChildren().remove(cancelEditDepartmentButton);
        initFormValidation();
    }

    /**
     * Method setting up validation of patient edit/register form using {@link Validator}
     */
    private void initFormValidation() {
        nameField.getValidators().add(
                new TextFieldValidator(Patient.class, "name", validator));
        nameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                nameField.validate();
            }
        });
    }

    /**
     * Method to return the event handler of pressing the delete button next to a patient entry in the list
     * @return event handler deleting the patient from the database
     */
    private EventHandler<ListCellEvent> handleDepartmentDeletePressed() {
        return this::removeDepartmentOnDelete;
    }

    /**
     * Method handling the deletion of a patient from the database and the list itself
     * @param event the received deletion event
     */
    private void removeDepartmentOnDelete(ListCellEvent<Department> event) {
        if (editedDepartment != null) {
            cancelEditDepartment(null);
        }
        //display a dialog to confirm? remove all patients from department as discharged?
        departmentService.delete(event.getSubject());
        departmentObservableList.remove(event.getSubject());
    }

    /**
     * Method to return the event handler of pressing the edit button next to a patient entry in the list
     * @return event handler editing the patient in the database
     */
    private EventHandler<ListCellEvent> handleDepartmentEditPressed() {
        return this::updateDepartmentOnEditPressed;
    }

    /**
     * Method handling the edition of a patient in the database and in the list itself
     * @param event the received edition event
     */
    private void updateDepartmentOnEditPressed(ListCellEvent<Department> event) {
        if (editedDepartment != null) {
            cancelEditDepartment(null);
        }
        lockEditableFormFields(true);
        nameField.setText("");
        editedDepartment = null;
        Department p = event.getSubject();
        nameField.setText(p.getName());
        bedsListView.setItems(FXCollections.observableArrayList(bedService.occupiedBeds(event.getSubject())));//TODO: change to getting all beds
        formButtonsParent.getChildren().remove(createDepartmentButton);
        formButtonsParent.getChildren().add(cancelEditDepartmentButton);
        editedDepartment = p;
        formLabel.setText("EDITING DEPARTMENT");
    }

    /**
     * Method to return the event handler of pressing the delete button next to a patient entry in the list
     * @return event handler deleting the patient from the database
     */
    private EventHandler<ListCellEvent> handleBedDeletePressed() {
        return this::removeBedOnDelete;
    }

    /**
     * Method handling the deletion of a patient from the database and the list itself
     * @param event the received deletion event
     */
    private void removeBedOnDelete(ListCellEvent<Bed> event) {
        if (editedDepartment != null) {
            cancelEditDepartment(null);
        }
        //display a dialog to confirm? discharge the patient lying on the bed?
        bedService.delete(event.getSubject());
        bedsListView.getItems().remove(event.getSubject());
    }

    /**
     * Locks or unlocks fields that should not be available for edit then editing a {@link Patient}
     * @param lock indicates if the fields should be locked
     */
    private void lockEditableFormFields(boolean lock) {
        nameField.setDisable(lock);
    }

    @FXML
    private void backToMainMenu(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(MainMenuViewController.class), true);
    }

    @FXML
    private void createDepartment(ActionEvent actionEvent) {
        if (nameField.validate()) {
            departmentObservableList.add(departmentService.save(getDepartmentFromForm()));
            nameField.setText("");
            editedDepartment = null;
            nameField.resetValidation();
        }
    }

    /**
     * Creates a {@link Department} object from data provided by the user in the edit/register form.
     * @return the newly created department
     */
    private Department getDepartmentFromForm() {
        return new Department(nameField.getText(), new Address("a", "b", 44444));
    }

    @FXML
    private void cancelEditDepartment(ActionEvent actionEvent) {
        nameField.setText("");
        editedDepartment = null;
        nameField.resetValidation();
        bedsListView.setItems(FXCollections.emptyObservableList());
        formButtonsParent.getChildren().add(createDepartmentButton);
        formButtonsParent.getChildren().remove(cancelEditDepartmentButton);
        lockEditableFormFields(false);
        editedDepartment = null;
        formLabel.setText("CREATE NEW DEPARTMENT");
    }
}
