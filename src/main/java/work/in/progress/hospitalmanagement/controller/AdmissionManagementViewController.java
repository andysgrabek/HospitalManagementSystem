package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.converter.BedStringConverter;
import work.in.progress.hospitalmanagement.event.ListCellEvent;
import work.in.progress.hospitalmanagement.factory.DialogFactory;
import work.in.progress.hospitalmanagement.factory.PatientAdmissionCellFactory;
import work.in.progress.hospitalmanagement.factory.PatientPredicateFactory;
import work.in.progress.hospitalmanagement.model.Admission;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.service.InpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.OutpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.PatientService;

import javax.validation.Validator;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static work.in.progress.hospitalmanagement.event.ListCellEvent.DELETE_EVENT;
import static work.in.progress.hospitalmanagement.event.ListCellEvent.EDIT_EVENT;
import static work.in.progress.hospitalmanagement.event.ListCellEvent.NEW_EVENT;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class AdmissionManagementViewController extends AbstractViewController {

    private final PatientService patientService;
    private final InpatientAdmissionService inpatientAdmissionService;
    private final OutpatientAdmissionService outpatientAdmissionService;
    private final DepartmentService departmentService;
    private final BedService bedService;
    private final Validator validator;
    @FXML
    private JFXTextField nameSearchField;
    @FXML
    private JFXTextField surnameSearchField;
    @FXML
    private JFXDatePicker birthDateSearchPicker;
    @FXML
    private JFXComboBox<Department> departmentSearchField;
    @FXML
    private JFXListView<Patient> registeredPatientListView;
    private ObservableList<Patient> patientObservableList = FXCollections.observableArrayList();
    private List<Node> formFields;
    private Patient editedPatient;

    @Autowired
    public AdmissionManagementViewController(PatientService patientService,
                                             InpatientAdmissionService inpatientAdmissionService,
                                             OutpatientAdmissionService outpatientAdmissionService,
                                             DepartmentService departmentService,
                                             BedService bedService,
                                             Validator validator) {
        this.patientService = patientService;
        this.inpatientAdmissionService = inpatientAdmissionService;
        this.outpatientAdmissionService = outpatientAdmissionService;
        this.departmentService = departmentService;
        this.bedService = bedService;
        this.validator = validator;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registeredPatientListView.setCellFactory(new PatientAdmissionCellFactory());
        patientObservableList.addAll(patientService.findAll());
        registeredPatientListView.addEventHandler(EDIT_EVENT, handlePatientEditPressed());
        registeredPatientListView.addEventHandler(DELETE_EVENT, handlePatientDeletePressed());
        registeredPatientListView.addEventHandler(NEW_EVENT, handlePatientCreatePressed());
        departmentSearchField.setItems(FXCollections.observableArrayList(departmentService.findAll()));
        initListFiltering();
    }

    private EventHandler<ListCellEvent> handlePatientEditPressed() {
        return this::handleEdit;
    }
    private EventHandler<ListCellEvent> handlePatientDeletePressed() {
        return this::handleDelete;
    }
    private EventHandler<ListCellEvent> handlePatientCreatePressed() {
        return this::handleCreate;
    }

    private void handleCreate(ListCellEvent<Patient> event) {
        Property<Admission> admissionProperty = new SimpleObjectProperty<>();
        JFXDialog dialog = DialogFactory.getDefaultFactory().admissionFormDialog(
                "Create a new admission",
                event.getSubject(),
                admissionProperty,
                bedService,
                departmentService,
                onCompleteEvent -> {
                    patientObservableList.remove(event.getSubject());
                    event.getSubject().setCurrentAdmission(admissionProperty.getValue());
                    patientObservableList.add(patientService.save(event.getSubject()));
                },
                validator,
                (StackPane) getRoot());
        dialog.show();
    }

    private void handleDelete(ListCellEvent<Patient> event) {
        JFXDialog dialog = DialogFactory.getDefaultFactory().deletionDialog(
                "Are you sure you want to discharge the patient?",
                "The patient will be immediately discharged from their department.",
                onCompleteEvent -> {
//                    patientObservableList.remove(event.getSubject());
                    if (event.getSubject().getCurrentAdmission().get() instanceof InpatientAdmission) {
                        inpatientAdmissionService
                                .delete((InpatientAdmission) event.getSubject().getCurrentAdmission().get());
                    } else {
                        outpatientAdmissionService
                                .delete((OutpatientAdmission) event.getSubject().getCurrentAdmission().get());
                    }
//                    event.getSubject().setCurrentAdmission(null);
//                    patientObservableList.remove(event.getSubject());
//                    patientObservableList.add(patientService.save(event.getSubject()));
                },
                Event::consume,
                (StackPane) getRoot());
        dialog.show();
    }

    private void handleEdit(ListCellEvent<Patient> event) {
        Property<Bed> selectedBedProperty = new SimpleObjectProperty<>();
        Collection<Bed> beds =
                bedService.findByDepartment(event.getSubject().getCurrentAdmission().get().getDepartment());
        JFXDialog dialog = DialogFactory.getDefaultFactory().comboBoxDialog(
                "Editing admission",
                FXCollections.observableArrayList(beds),
                selectedBedProperty,
                new BedStringConverter(bedService.findAll()),
                onConfirmEvent -> {
                    patientObservableList.remove(event.getSubject());
                    InpatientAdmission admission =
                            (InpatientAdmission) event.getSubject().getCurrentAdmission().get();
                    admission.getBed().setAdmission(null);
                    selectedBedProperty.getValue().setAdmission(admission);
                    event.getSubject().setCurrentAdmission(admission);
                    patientObservableList.remove(event.getSubject());
                    patientObservableList.add(patientService.save(event.getSubject()));
                },
                (StackPane) getRoot());
        dialog.show();
    }

    /**
     * Method setting up listeners on list filtering search boxes and date picker to filter the results using predicates
     */
    private void initListFiltering() {
        FilteredList<Patient> filteredList = new FilteredList<>(patientObservableList, patient -> true);

        nameSearchField.textProperty().addListener((o, oV, newValue)
                -> filteredList.setPredicate(composePatientPredicate(
                newValue,
                surnameSearchField.getText(),
                birthDateSearchPicker.getValue(),
                departmentSearchField.getSelectionModel().getSelectedItem())));

        surnameSearchField.textProperty().addListener((o, oV, newValue)
                -> filteredList.setPredicate(composePatientPredicate(
                nameSearchField.getText(),
                newValue,
                birthDateSearchPicker.getValue(),
                departmentSearchField.getSelectionModel().getSelectedItem())));

        birthDateSearchPicker.valueProperty().addListener((o, oV, newValue)
                -> filteredList.setPredicate(composePatientPredicate(
                nameSearchField.getText(),
                surnameSearchField.getText(),
                newValue,
                departmentSearchField.getSelectionModel().getSelectedItem())));

        departmentSearchField.valueProperty().addListener((o, oV, newValue)
                -> filteredList.setPredicate(composePatientPredicate(
                nameSearchField.getText(),
                surnameSearchField.getText(),
                birthDateSearchPicker.getValue(),
                newValue)));

        registeredPatientListView.setItems(filteredList);
    }

    /**
     * Method composing a complex predicate used to filter the results in the list of patients.
     * @param name name of the patient in the search field
     * @param surname surname of the patient in the search field
     * @param date birth date of the patient in the search field
     * @param department department in the search field
     * @return the composed filtering predicate
     */
    private Predicate<? super Patient> composePatientPredicate(String name,
                                                               String surname,
                                                               LocalDate date,
                                                               Department department) {
        PatientPredicateFactory factory = PatientPredicateFactory.getDefaultFactory();
        return factory.namePredicate(name)
                .and(factory.surnamePredicate(surname))
                .and(factory.datePredicate(date))
                .and(factory.departmentPredicate(department));
    }

    @FXML
    private void clearSearchFields(ActionEvent actionEvent) {
        nameSearchField.setText("");
        birthDateSearchPicker.setValue(null);
        surnameSearchField.setText("");
        departmentSearchField.getSelectionModel().clearSelection();
    }
}
