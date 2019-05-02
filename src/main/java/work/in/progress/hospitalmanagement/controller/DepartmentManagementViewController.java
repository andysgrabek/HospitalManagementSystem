package work.in.progress.hospitalmanagement.controller;

import com.itextpdf.text.DocumentException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.event.ListCellEvent;
import work.in.progress.hospitalmanagement.factory.BedCellFactory;
import work.in.progress.hospitalmanagement.factory.DepartmentCellFactory;
import work.in.progress.hospitalmanagement.factory.DialogFactory;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.report.ReportGenerator;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.service.InpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.OutpatientAdmissionService;
import work.in.progress.hospitalmanagement.validator.TextFieldValidator;

import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import static work.in.progress.hospitalmanagement.event.ListCellEvent.DELETE_EVENT;
import static work.in.progress.hospitalmanagement.event.ListCellEvent.EDIT_EVENT;

/**
 * Class serving as the controller for the view in which the user is able to manage the deparments and beds
 * available in the hospital
 *
 * @author Andrzej Grabowski
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class DepartmentManagementViewController extends AbstractViewController {

    private final DepartmentService departmentService;
    private final BedService bedService;
    private final ReportGenerator<Patient> reportGenerator;
    private final OutpatientAdmissionService outpatientAdmissionService;
    private final InpatientAdmissionService inpatientAdmissionService;
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
                                              ReportGenerator<Patient> reportGenerator,
                                              OutpatientAdmissionService outpatientAdmissionService,
                                              InpatientAdmissionService inpatientAdmissionService,
                                              Validator validator) {
        this.departmentService = departmentService;
        this.bedService = bedService;
        this.validator = validator;
        this.reportGenerator = reportGenerator;
        this.inpatientAdmissionService = inpatientAdmissionService;
        this.outpatientAdmissionService = outpatientAdmissionService;
    }

    /**
     * {@inheritDoc}
     */
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
        Label bedPlaceholder = new Label("List of beds in an edited department will appear here");
        bedPlaceholder.getStyleClass().add("hms-text");
        bedsListView.setPlaceholder(bedPlaceholder);
        Label departmentPlaceholder = new Label("No departments. Added departments will appear here.");
        departmentPlaceholder.getStyleClass().add("hms-text");
        departmentsListView.setPlaceholder(departmentPlaceholder);
        nameField.getValidators().add(new TextFieldValidator(Patient.class, "name", validator));
    }

    /**
     * Method to return the event handler of pressing the delete button next to a department entry in the list
     *
     * @return event handler deleting the department from the database
     */
    private EventHandler<ListCellEvent> handleDepartmentDeletePressed() {
        return this::removeDepartmentOnDelete;
    }

    /**
     * Method handling the deletion of a department from the database and the list itself
     *
     * @param event the received deletion event
     */
    private void removeDepartmentOnDelete(ListCellEvent<Department> event) {
        if (editedDepartment != null) {
            cancelEditDepartment(null);
        }
        createDepartmentDeleteDialog(event.getSubject()).show();
    }

    /**
     * Method to return the event handler of pressing the edit button next to a department entry in the list
     *
     * @return event handler editing the department in the database
     */
    private EventHandler<ListCellEvent> handleDepartmentEditPressed() {
        return this::updateDepartmentOnEditPressed;
    }

    /**
     * Method handling the edition of a department in the database and in the list itself
     *
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
     * Method to return the event handler of pressing the delete button next to a bed entry in the list
     *
     * @return event handler deleting the bed from the database
     */
    private EventHandler<ListCellEvent> handleBedDeletePressed() {
        return this::removeBedOnDelete;
    }

    /**
     * Method to return the event handler of pressing the bed button next to a patient entry in the list
     *
     * @return event handler deleting the bed from the database
     */
    private EventHandler<ListCellEvent> handleBedEditPressed() {
        return this::updateBedOnEdit;
    }

    /**
     * Method handling the deletion of a bed from the database and the list itself
     *
     * @param event the received deletion event
     */
    private void updateBedOnEdit(ListCellEvent<Bed> event) {
        editBedDialog(event.getSubject()).show();
    }

    /**
     * Method handling the deletion of a bed from the database and the list itself
     *
     * @param event the received deletion event
     */
    private void removeBedOnDelete(ListCellEvent<Bed> event) {
        createBedDeleteDialog(event.getSubject()).show();
    }

    /**
     * Locks or unlocks fields that should not be available for edit when editing a {@link Department}
     * A department has no editable fields - one can only add or remove beds in the department.
     *
     * @param lock indicates if the fields should be locked
     */
    private void lockEditableFormFields(boolean lock) {
        nameField.setDisable(lock);
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
     *
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

    /**
     * Handler for the event of pressing the button to add more beds to the edited department
     *
     * @param actionEvent event triggered by pressing the button
     */
    @FXML
    public void addBed(ActionEvent actionEvent) {
        createBedDialog().show();
    }

    /**
     * Method creating a {@link JFXDialog} with a form to create a new bed
     *
     * @return the dialog to be presented to the user
     */
    private JFXDialog createBedDialog() {
        return createBedRoomNumberDialog("Add new bed", null);
    }

    /**
     * Method creating a {@link JFXDialog} with a form to edit a bed
     *
     * @return the dialog to be presented to the user
     */
    private JFXDialog editBedDialog(Bed bed) {
        return createBedRoomNumberDialog("Edit bed", bed);
    }

    /**
     * Method creating a general {@link JFXDialog} e.g. for bed creation or edit.
     *
     * @param heading the heading to be displayed in the dialog
     * @param bed     the bed to be described in the dialog if any
     * @return the dialog to be presented to the user
     */
    private JFXDialog createBedRoomNumberDialog(String heading, Bed bed) {
        SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
        EventHandler<ActionEvent> handler;
        if (bed == null) {
            handler = event -> {
                bedsListView.getItems()
                        .add(bedService.save(new Bed(editedDepartment, simpleStringProperty.getValue())));
                departmentsListView.setItems(FXCollections.observableArrayList(departmentService.findAll()));
            };
        } else {
            handler = event -> {
                bedsListView.getItems().remove(bed);
                bed.setRoomNumber(simpleStringProperty.getValue());
                bedsListView.getItems().add(bedService.save(bed));
                departmentsListView.setItems(FXCollections.observableArrayList(departmentService.findAll()));
            };
        }
        return DialogFactory.getDefaultFactory().textFieldDialog(
                heading,
                "Please provide room number of the bed.",
                simpleStringProperty,
                handler,
                (StackPane) getRoot(),
                new TextFieldValidator(Bed.class, "roomNumber", validator));
    }

    /**
     * Method creating a {@link JFXDialog} responsible for confirming the deletion of a department
     *
     * @param department the department to be deleted
     * @return the dialog to be presented to the user
     */
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
                        event -> {
                        },
                        (StackPane) getRoot());
    }

    /**
     * Method creating a {@link JFXDialog} responsible for confirming the deletion of a bed
     *
     * @param bed the bed to be deleted
     * @return the dialog to be presented to the user
     */
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
                        event -> {
                        },
                        (StackPane) getRoot());
    }

    /**
     * Handler to save an {@link work.in.progress.hospitalmanagement.model.Admission} report for all departments in PDF.
     *
     * @param actionEvent event that triggered the action
     */
    @FXML
    public void generateReportAllDepartments(ActionEvent actionEvent) {
        ObservableList<Department> departments = departmentsListView.getItems();
        findPatientsForReport(departments);
    }

    /**
     * Method searches the database for patients admitted to the selected departments and proceeds with generating
     * the report for them.
     *
     * @param departments selected departments
     */
    private void findPatientsForReport(ObservableList<Department> departments) {
        if (departments.isEmpty()) {
            showReportErrorDialog("No departments selected",
                    "Please select at least one department from the list above.");
        } else {
            List<Patient> patients = new ArrayList<>();
            inpatientAdmissionService.findAll().forEach(inpatientAdmission -> {
                if (departments.contains(inpatientAdmission.getDepartment())) {
                    patients.add(inpatientAdmission.getPatient());
                }
            });
            outpatientAdmissionService.findAll().forEach(outpatientAdmission -> {
                if (departments.contains(outpatientAdmission.getDepartment())) {
                    patients.add(outpatientAdmission.getPatient());
                }
            });
            saveParticipationReport(patients);
        }
    }

    /**
     * Handler to save an {@link work.in.progress.hospitalmanagement.model.Admission} report to a file in PDF format
     * for the selected departments.
     *
     * @param actionEvent event that triggered the action
     */
    @FXML
    public void generateReportSelectedDepartments(ActionEvent actionEvent) {
        ObservableList<Department> departments = departmentsListView.getSelectionModel().getSelectedItems();
        findPatientsForReport(departments);
    }

    /**
     * Method to save participation lists for a given set of .
     */
    private void saveParticipationReport(Collection<Patient> patients) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName(reportGenerator.createDefaultPath());
        try {
            File file = fileChooser.showSaveDialog(getStage());
            if (file != null) {
                try {
                    reportGenerator.generate(patients, file.getPath());
                } catch (IOException | DocumentException e) {
                    showReportErrorDialog("Error saving report!",
                            "An unexpected error has occurred when saving the report. Please try again.");
                }
            }
        } catch (UnsupportedOperationException e) {
            showReportErrorDialog("File chooser error!",
                    "File chooser is not supported. Unable to save the report.");
        }
    }

    /**
     * Utility method to create a {@link JFXDialog} signaling that a user must choose e.g. save directory
     * when creating a participation report.
     *
     * @param header dialog header text
     * @param body   dialog body text
     */
    private void showReportErrorDialog(String header, String body) {
        DialogFactory.getDefaultFactory().infoTextDialog(header, body, Event::consume, (StackPane) getRoot()).show();
    }
}
