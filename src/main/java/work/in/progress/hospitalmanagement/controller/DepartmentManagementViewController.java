package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.event.ListCellEvent;
import work.in.progress.hospitalmanagement.factory.BedCellFactory;
import work.in.progress.hospitalmanagement.factory.ButtonFactory;
import work.in.progress.hospitalmanagement.factory.DepartmentCellFactory;
import work.in.progress.hospitalmanagement.factory.DialogFactory;
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
    @FXML
    private JFXButton addBedButton;
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
        bedsListView.addEventHandler(EDIT_EVENT, handleBedEditPressed());
        bedsListView.addEventHandler(DELETE_EVENT, handleBedDeletePressed());
        formButtonsParent.getChildren().remove(cancelEditDepartmentButton);
        formButtonsParent.getChildren().remove(addBedButton);
        initFormValidation();
    }

    /**
     * Method setting up validation of patient edit/register form using {@link Validator}
     */
    private void initFormValidation() {
        nameField.getValidators().add(
                new TextFieldValidator(Patient.class, "name", validator));
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
        createDepartmentDeleteDialog(event.getSubject()).show();
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
        editedDepartment = null;
        Department p = event.getSubject();
        nameField.setText(p.getName());
        formButtonsParent.getChildren().remove(createDepartmentButton);
        formButtonsParent.getChildren().add(cancelEditDepartmentButton);
        formButtonsParent.getChildren().add(addBedButton);
        editedDepartment = p;
        bedsListView.setItems(FXCollections.observableArrayList(bedService.findByDepartment(editedDepartment)));
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
     * Method to return the event handler of pressing the delete button next to a patient entry in the list
     * @return event handler deleting the patient from the database
     */
    private EventHandler<ListCellEvent> handleBedEditPressed() {
        return this::updateBedOnEdit;
    }

    /**
     * Method handling the deletion of a patient from the database and the list itself
     * @param event the received deletion event
     */
    private void updateBedOnEdit(ListCellEvent<Bed> event) {
        editBedDialog(event.getSubject()).show();
    }

    /**
     * Method handling the deletion of a patient from the database and the list itself
     * @param event the received deletion event
     */
    private void removeBedOnDelete(ListCellEvent<Bed> event) {
        createBedDeleteDialog(event.getSubject()).show();

    }

    /**
     * Locks or unlocks fields that should not be available for edit when editing a {@link Department}
     * A department has no editable fields - one can only add or remove beds in the department.
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
        return new Department(nameField.getText());
    }

    @FXML
    private void cancelEditDepartment(ActionEvent actionEvent) {
        nameField.setText("");
        editedDepartment = null;
        nameField.resetValidation();
        bedsListView.setItems(FXCollections.emptyObservableList());
        formButtonsParent.getChildren().add(createDepartmentButton);
        formButtonsParent.getChildren().remove(cancelEditDepartmentButton);
        formButtonsParent.getChildren().remove(addBedButton);
        lockEditableFormFields(false);
        editedDepartment = null;
        formLabel.setText("CREATE NEW DEPARTMENT");
    }

    @FXML
    public void addBed(ActionEvent actionEvent) {
        createBedDialog().show();
    }

    private JFXDialog createBedDialog() {
        return createBedRoomNumberDialog("Add new bed", null);
    }

    private JFXDialog editBedDialog(Bed bed) {
        return createBedRoomNumberDialog("Edit bed", bed);
    }

    private JFXDialog createBedRoomNumberDialog(String heading, Bed bed) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(heading));
        VBox vBox = new VBox();
        JFXTextField jfxTextField = new JFXTextField();
        jfxTextField.getValidators().add(new TextFieldValidator(Bed.class, "roomNumber", validator));
        vBox.getChildren().add(new Text("Please provide room number of the bed."));
        vBox.getChildren().add(jfxTextField);
        content.setBody(vBox);
        JFXDialog dialog = new JFXDialog((StackPane) getRoot(), content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = ButtonFactory.getDefaultFactory().defaultButton("SAVE");
        JFXButton cancel = ButtonFactory.getDefaultFactory().defaultButton("CANCEL");
        content.getStyleClass().add("hms-text");
        if (bed == null) {
            button.setOnAction(event -> {
                if (jfxTextField.validate()) {
                    bedsListView.getItems().add(bedService.save(new Bed(editedDepartment, jfxTextField.getText())));
                    departmentsListView.setItems(FXCollections.observableArrayList(departmentService.findAll()));
                    dialog.close();
                }
            });
        } else {
            button.setOnAction(event -> {
                if (jfxTextField.validate()) {
                    bedsListView.getItems().remove(bed);
                    bed.setRoomNumber(jfxTextField.getText());
                    bedsListView.getItems().add(bedService.save(bed));
                    departmentsListView.setItems(FXCollections.observableArrayList(departmentService.findAll()));
                    dialog.close();
                }
            });
        }
        cancel.setOnAction(event -> dialog.close());
        content.setActions(button, cancel);
        return dialog;
    }

    private JFXDialog createDepartmentDeleteDialog(Department department) {
        return DialogFactory
                .getDefaultFactory()
                .deletionDialog(
                        "Department deletion",
                        "You are about to delete a department. It will result in discharging\n"
                                + "all patients belonging to this department, as well as removing all\n"
                                + "beds belonging to this department from the database.\n"
                                + "Are you sure you want to continue?",
                        event -> {
                            departmentService.delete(department);
                            departmentObservableList.remove(department);
                        },
                        event -> { },
                        (StackPane) getRoot());
    }

    private JFXDialog createBedDeleteDialog(Bed bed) {
        return DialogFactory
                .getDefaultFactory()
                .deletionDialog(
                        "Bed deletion",
                        "You are about to remove a bed.\n"
                                + "Removing it will also discharge\n"
                                + "the associated patient currently admitted (if any).",
                        event -> {
                            bedService.delete(bed);
                            bedsListView.getItems().remove(bed);
                            departmentsListView
                                    .setItems(FXCollections.observableArrayList(departmentService.findAll()));
                        },
                        event -> { },
                        (StackPane) getRoot());
    }
}
