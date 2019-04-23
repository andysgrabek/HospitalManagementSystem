package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.base.IFXValidatableControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.converter.DepartmentStringConverter;
import work.in.progress.hospitalmanagement.converter.RoleStringConverter;
import work.in.progress.hospitalmanagement.event.ListCellEvent;
import work.in.progress.hospitalmanagement.factory.PersonCellFactory;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.HospitalStaff;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.service.HospitalStaffService;
import work.in.progress.hospitalmanagement.validator.ComboBoxValidator;
import work.in.progress.hospitalmanagement.validator.TextFieldValidator;

import javax.validation.Validator;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static work.in.progress.hospitalmanagement.event.ListCellEvent.DELETE_EVENT;
import static work.in.progress.hospitalmanagement.event.ListCellEvent.EDIT_EVENT;

/**
 * Controller for the view responsible for controlling staff registration
 * @author Andrzej Grabowski
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class StaffManagementViewController extends AbstractViewController {

    private final Validator validator;
    private final HospitalStaffService hospitalStaffService;
    private final DepartmentService departmentService;
    @FXML
    private JFXComboBox<HospitalStaff.Role> roleField;
    @FXML
    private JFXTextField nameSearchField;
    @FXML
    private JFXTextField surnameSearchField;
    @FXML
    private JFXListView<HospitalStaff> staffListView;
    @FXML
    private Label formLabel;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField surnameField;
    @FXML
    private AnchorPane emailPane;
    @FXML
    private HBox formButtonsParent;
    @FXML
    private JFXButton cancelEditStaffButton;
    @FXML
    private JFXButton confirmEditStaffButton;
    @FXML
    private JFXButton createStaffButton;
    @FXML
    private JFXComboBox<Department> departmentField;
    @FXML
    private JFXComboBox<Department> departmentSearchField;
    private ObservableList<HospitalStaff> staffObservableList;
    private List<Node> formFields;
    private HospitalStaff editedStaff;

    @Autowired
    public StaffManagementViewController(HospitalStaffService hospitalStaffService,
                                         DepartmentService departmentService,
                                         Validator validator) {
        this.hospitalStaffService = hospitalStaffService;
        this.validator = validator;
        this.departmentService = departmentService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staffObservableList = FXCollections.observableArrayList();
        formButtonsParent.getChildren().remove(confirmEditStaffButton);
        formButtonsParent.getChildren().remove(cancelEditStaffButton);
        formFields = Arrays.asList(
                nameField,
                surnameField,
                departmentField);
        PersonCellFactory<HospitalStaff> personCellFactory = new PersonCellFactory<>();
        staffListView.setCellFactory(personCellFactory);
        staffObservableList.addAll(hospitalStaffService.findAll());
        staffListView.addEventHandler(EDIT_EVENT, handleStaffEditPressed());
        staffListView.addEventHandler(DELETE_EVENT, handleStaffDeletedEvent());
        initFormValidation();
        initListFiltering();
        departmentSearchField.setConverter(new DepartmentStringConverter(departmentService.findAll()));
        roleField.setConverter(new RoleStringConverter());
        departmentField.setConverter(new DepartmentStringConverter(departmentService.findAll()));
        departmentField.setItems(FXCollections.observableArrayList(departmentService.findAll()));
        if (HospitalStaff.Role.values().length != 0) {
            roleField.setItems(FXCollections.observableArrayList(HospitalStaff.Role.values()));
            roleField.getSelectionModel().selectFirst();
        }
    }

    /**
     * Method setting up listeners on list filtering search boxes to filter the results using predicates
     */
    private void initListFiltering() {
        FilteredList<HospitalStaff> filteredList = new FilteredList<>(staffObservableList, staff -> true);
        nameSearchField.textProperty().addListener((o, oV, newValue)
                -> filteredList.setPredicate(composeHospitalStaffPredicate(
                newValue, surnameSearchField.getText(), departmentSearchField.getValue())));
        surnameSearchField.textProperty().addListener((o, oV, newValue)
                -> filteredList.setPredicate(composeHospitalStaffPredicate(
                nameSearchField.getText(), newValue, departmentSearchField.getValue())));
        departmentSearchField.valueProperty().addListener((o, oV, newValue)
                -> filteredList.setPredicate(composeHospitalStaffPredicate(
                nameSearchField.getText(), surnameSearchField.getText(), newValue)));
        staffListView.setItems(filteredList);
    }

    /**
     * Method composing a complex predicate used to filter the results in the list of staff.
     * @param name name of the staff in the search field
     * @param surname surname of the staff in the search field
     * @param department department of the staff in the search field
     * @return the composed filtering predicate
     */
    private Predicate<HospitalStaff> composeHospitalStaffPredicate(String name, String surname, Department department) {
        Predicate<HospitalStaff> namePredicate =
                (name == null || name.length() == 0)
                        ? staff -> true
                        : staff -> staff.getName().toLowerCase().contains(name.toLowerCase());
        Predicate<HospitalStaff> surnamePredicate =
                (surname == null || surname.length() == 0)
                        ? staff -> true
                        : staff -> staff.getSurname().toLowerCase().contains(surname.toLowerCase());
        Predicate<HospitalStaff> departmentPredicate =
                (department == null)
                        ? staff -> true
                        : staff -> staff.getDepartment().getName().equals(department.getName());
        return namePredicate.and(surnamePredicate).and(departmentPredicate);
    }

    /**
     * Method setting up validation of hospital staff edit/register form using {@link Validator}
     */
    private void initFormValidation() {
        nameField.getValidators().add(
                new TextFieldValidator(HospitalStaff.class, "name", validator));
        surnameField.getValidators().add(
                new TextFieldValidator(HospitalStaff.class, "surname", validator));
        departmentField.getValidators().add(
                new ComboBoxValidator(HospitalStaff.class, "department", validator));
        roleField.getValidators().add(
                new ComboBoxValidator(HospitalStaff.class, "role", validator));
        formFields.forEach(field -> field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                ((IFXValidatableControl) field).validate();
            }
        }));
    }

    @FXML
    private void backToMainMenu(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(MainMenuViewController.class), true);
    }

    @FXML
    private void clearSearchFields(ActionEvent actionEvent) {
        nameSearchField.setText("");
        departmentSearchField.getSelectionModel().clearSelection();
        surnameSearchField.setText("");
    }

    @FXML
    private void cancelEditStaff(ActionEvent actionEvent) {
        resetFormFields();
        formFields.forEach(field -> ((IFXValidatableControl) field).resetValidation());
        formButtonsParent.getChildren().add(createStaffButton);
        formButtonsParent.getChildren().remove(cancelEditStaffButton);
        formButtonsParent.getChildren().remove(confirmEditStaffButton);
        lockEditableFormFields(false);
        editedStaff = null;
        formLabel.setText("CREATE NEW STAFF MEMBER");
    }

    @FXML
    private void confirmEditStaff(ActionEvent actionEvent) {
        if (validateStaffForm()) {
            HospitalStaff p = getStaffFromForm();
            editedStaff.setDepartment(p.getDepartment());
            staffObservableList.remove(editedStaff);
            staffObservableList.add(hospitalStaffService.save(editedStaff));
            resetFormFields();
            formButtonsParent.getChildren().add(createStaffButton);
            formButtonsParent.getChildren().remove(cancelEditStaffButton);
            formButtonsParent.getChildren().remove(confirmEditStaffButton);
            editedStaff = null;
            formFields.forEach(field -> ((IFXValidatableControl) field).resetValidation());
            lockEditableFormFields(false);
            formLabel.setText("CREATE NEW STAFF MEMBER");
        }
    }

    @FXML
    private void registerStaff(ActionEvent actionEvent) {
        if (validateStaffForm()) {
            staffObservableList.add(hospitalStaffService.save(getStaffFromForm()));
            resetFormFields();
            formFields.forEach(field -> ((IFXValidatableControl) field).resetValidation());
        }
    }

    /**
     * Creates a {@link HospitalStaff} object from data provided by the user in the edit/register form.
     * @return the newly created staff member
     */
    private HospitalStaff getStaffFromForm() {
        return HospitalStaff.builder()
                .name(nameField.getText())
                .surname(surnameField.getText())
                .department(departmentField.getSelectionModel().getSelectedItem())
                .role(roleField.getSelectionModel().getSelectedItem())
                .build();
    }

    /**
     * Method validating the correctness of data provided in the staff edit/create form.
     * @return true if all fields were correctly filled
     */
    private boolean validateStaffForm() {
        boolean isCorrect = true;
        for (Node field : formFields) {
            boolean p = ((IFXValidatableControl) field).validate();
            if (isCorrect) {
                isCorrect = p;
            }
        }
        return isCorrect;
    }

    /**
     * Method to return the event handler of pressing the delete button next to a staff entry in the list
     * @return event handler deleting the staff from the database
     */
    private EventHandler<ListCellEvent> handleStaffDeletedEvent() {
        return this::removeStaffOnDelete;
    }

    /**
     * Method handling the deletion of a staff member from the database and the list itself
     * @param event the received deletion event
     */
    private void removeStaffOnDelete(ListCellEvent<HospitalStaff> event) {
        if (editedStaff != null) {
            cancelEditStaff(null);
        }
        hospitalStaffService.delete(event.getSubject());
        staffObservableList.remove(event.getSubject());
    }

    /**
     * Method to return the event handler of pressing the edit button next to a staff entry in the list
     * @return event handler editing the staff in the database
     */
    private EventHandler<ListCellEvent> handleStaffEditPressed() {
        return this::updateStaffOnEdit;
    }

    /**
     * Method handling the edition of a staff member in the database and in the list itself
     * @param event the received edition event
     */
    private void updateStaffOnEdit(ListCellEvent<HospitalStaff> event) {
        if (editedStaff != null) {
            cancelEditStaff(null);
        }
        lockEditableFormFields(true);
        resetFormFields();
        HospitalStaff p = event.getSubject();
        nameField.setText(p.getName());
        surnameField.setText(p.getSurname());
        roleField.getSelectionModel().select(p.getRole());
        departmentField.getSelectionModel().select(p.getDepartment());
        formButtonsParent.getChildren().remove(createStaffButton);
        formButtonsParent.getChildren().add(cancelEditStaffButton);
        formButtonsParent.getChildren().add(confirmEditStaffButton);
        editedStaff = p;
        formLabel.setText("EDITING STAFF MEMBER");
    }

    /**
     * Locks or unlocks fields that should not be available for edit then editing a {@link HospitalStaff}
     * @param lock indicates if the fields should be locked
     */
    private void lockEditableFormFields(boolean lock) {
        nameField.setDisable(lock);
        surnameField.setDisable(lock);
        roleField.setDisable(lock);
    }

    /**
     * Method clearing the form data provided in the edit/register form
     */
    private void resetFormFields() {
        nameField.setText("");
        surnameField.setText("");
        departmentField.getSelectionModel().clearSelection();
        roleField.getSelectionModel().clearSelection();
        editedStaff = null;
    }

}
