package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.base.IFXValidatableControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.event.ListCellEvent;
import work.in.progress.hospitalmanagement.factory.DialogFactory;
import work.in.progress.hospitalmanagement.factory.PersonCellFactory;
import work.in.progress.hospitalmanagement.model.Address;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.service.PatientService;
import work.in.progress.hospitalmanagement.validator.BirthDateValidator;
import work.in.progress.hospitalmanagement.validator.NumberFieldValidator;
import work.in.progress.hospitalmanagement.validator.TextFieldValidator;

import javax.validation.Validator;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static work.in.progress.hospitalmanagement.event.ListCellEvent.DELETE_EVENT;
import static work.in.progress.hospitalmanagement.event.ListCellEvent.EDIT_EVENT;

/**
 * Controller for the view responsible for controlling patient registration.
 * @author Andrzej Grabowski
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class PatientRegistrationViewController extends AbstractViewController {

    @FXML
    private Label formLabel;
    private Patient editedPatient;
    @FXML
    private JFXTextField nameSearchField;
    @FXML
    private JFXTextField surnameSearchField;
    @FXML
    private JFXDatePicker birthDateSearchPicker;
    @FXML
    private HBox formButtonsParent;
    @FXML
    private JFXButton cancelEditPatientButton;
    @FXML
    private JFXButton confirmEditPatientButton;
    @FXML
    private JFXButton registerPatientButton;
    @FXML
    private JFXListView<Patient> registeredPatientListView;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField surnameField;
    @FXML
    private JFXDatePicker birthDatePicker;
    @FXML
    private JFXTextField phoneNumberField;
    @FXML
    private JFXCheckBox deceasedCheckbox;
    @FXML
    private JFXTextField addressLineField;
    @FXML
    private JFXTextField cityField;
    @FXML
    private JFXTextField postalCodeField;
    private List<Node> formFields;
    private ObservableList<Patient> patientObservableList;
    private final PatientService patientService;
    private final Validator validator;

    @Autowired
    public PatientRegistrationViewController(PatientService patientService, Validator validator) {
        this.patientService = patientService;
        this.validator = validator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        patientObservableList = FXCollections.observableArrayList();
        formButtonsParent.getChildren().remove(confirmEditPatientButton);
        formButtonsParent.getChildren().remove(cancelEditPatientButton);
        formFields = Arrays.asList(
                nameField,
                surnameField,
                birthDatePicker,
                phoneNumberField,
                addressLineField,
                cityField,
                postalCodeField);
        PersonCellFactory<Patient> personCellFactory = new PersonCellFactory<>();
        registeredPatientListView.setCellFactory(personCellFactory);
        patientObservableList.addAll(patientService.findAll());
        registeredPatientListView.addEventHandler(EDIT_EVENT, handlePatientEditPressed());
        registeredPatientListView.addEventHandler(DELETE_EVENT, handlePatientDeletePressed());
        Label placeholder = new Label("No patients matching search criteria.");
        placeholder.getStyleClass().add("hms-text");
        registeredPatientListView.setPlaceholder(placeholder);
        initFormValidation();
        initListFiltering();
    }

    /**
     * Method setting up listeners on list filtering search boxes and date picker to filter the results using predicates
     */
    private void initListFiltering() {
        FilteredList<Patient> filteredList = new FilteredList<>(patientObservableList, patient -> true);
        nameSearchField.textProperty().addListener((o, oV, newValue)
                -> filteredList.setPredicate(composePatientPredicate(
                        newValue, surnameSearchField.getText(), birthDateSearchPicker.getValue())));
        surnameSearchField.textProperty().addListener((o, oV, newValue)
                -> filteredList.setPredicate(composePatientPredicate(
                        nameSearchField.getText(), newValue, birthDateSearchPicker.getValue())));
        birthDateSearchPicker.valueProperty().addListener((o, oV, newValue)
                -> filteredList.setPredicate(composePatientPredicate(
                        nameSearchField.getText(), surnameSearchField.getText(), newValue)));
        registeredPatientListView.setItems(filteredList);
    }

    /**
     * Method setting up validation of patient edit/register form using {@link Validator}
     */
    private void initFormValidation() {
        nameField.getValidators().add(
                new TextFieldValidator(Patient.class, "name", validator));
        surnameField.getValidators().add(
                new TextFieldValidator(Patient.class, "surname", validator));
        phoneNumberField.getValidators().add(
                new TextFieldValidator(Patient.class, "phoneNumber", validator));
        addressLineField.getValidators().add(
                new TextFieldValidator(Address.class, "addressLine", validator));
        cityField.getValidators().add(
                new TextFieldValidator(Address.class, "city", validator));
        postalCodeField.getValidators().add(
                new NumberFieldValidator(Address.class, "zipCode", validator));
        birthDatePicker.getValidators().add(
                new BirthDateValidator(Patient.class, "birthDate", validator));
    }

    /**
     * Method to return the event handler of pressing the delete button next to a patient entry in the list
     * @return event handler deleting the patient from the database
     */
    private EventHandler<ListCellEvent> handlePatientDeletePressed() {
        return this::removePatientOnDelete;
    }

    /**
     * Method handling the deletion of a patient from the database and the list itself
     * @param event the received deletion event
     */
    private void removePatientOnDelete(ListCellEvent<Patient> event) {
        DialogFactory.getDefaultFactory().deletionDialog(
                "Are you sure you want to delete the patient?",
                event.getSubject().getName() + " " + event.getSubject().getSurname(),
                event1 -> {
                    if (editedPatient != null) {
                        cancelEditPatient(null);
                    }
                    patientService.delete(event.getSubject());
                    patientObservableList.remove(event.getSubject());
                },
                Event::consume,
                (StackPane) getRoot()
        );
    }

    /**
     * Method to return the event handler of pressing the edit button next to a patient entry in the list
     * @return event handler editing the patient in the database
     */
    private EventHandler<ListCellEvent> handlePatientEditPressed() {
        return this::updatePatientOnEdit;
    }

    /**
     * Method handling the edition of a patient in the database and in the list itself
     * @param event the received edition event
     */
    private void updatePatientOnEdit(ListCellEvent<Patient> event) {
        if (editedPatient != null) {
            cancelEditPatient(null);
        }
        lockEditableFormFields(true);
        resetFormFields();
        Patient p = event.getSubject();
        nameField.setText(p.getName());
        surnameField.setText(p.getSurname());
        birthDatePicker.setValue(p.getBirthDate());
        phoneNumberField.setText(p.getPhoneNumber());
        deceasedCheckbox.setSelected(!p.isAlive());
        addressLineField.setText(p.getHomeAddress().getAddressLine());
        cityField.setText(p.getHomeAddress().getCity());
        postalCodeField.setText(String.valueOf(p.getHomeAddress().getZipCode()));
        formButtonsParent.getChildren().remove(registerPatientButton);
        formButtonsParent.getChildren().add(cancelEditPatientButton);
        formButtonsParent.getChildren().add(confirmEditPatientButton);
        editedPatient = p;
        formLabel.setText("EDITING PATIENT");
    }

    /**
     * Locks or unlocks fields that should not be available for edit then editing a {@link Patient}
     * @param lock indicates if the fields should be locked
     */
    private void lockEditableFormFields(boolean lock) {
        nameField.setDisable(lock);
        surnameField.setDisable(lock);
        birthDatePicker.setDisable(lock);
    }

    /**
     * Method composing a complex predicate used to filter the results in the list of patients.
     * @param name name of the patient in the search field
     * @param surname surname of the patient in the search field
     * @param date birth date of the patient in the search field
     * @return the composed filtering predicate
     */
    private Predicate<Patient> composePatientPredicate(String name, String surname, LocalDate date) {
        Predicate<Patient> namePredicate =
                (name == null || name.length() == 0)
                ? patient -> true
                : patient -> patient.getName().toLowerCase().contains(name.toLowerCase());
        Predicate<Patient> surnamePredicate =
                (surname == null || surname.length() == 0)
                ? patient -> true
                : patient -> patient.getSurname().toLowerCase().contains(surname.toLowerCase());
        Predicate<Patient> datePredicate =
                (date == null)
                ? patient -> true
                : patient -> patient.getBirthDate().isEqual(date);
        return namePredicate.and(surnamePredicate).and(datePredicate);
    }

    /**
     * Method clearing the form data provided in the edit/register form
     */
    private void resetFormFields() {
        nameField.setText("");
        surnameField.setText("");
        birthDatePicker.setValue(null);
        phoneNumberField.setText("");
        deceasedCheckbox.setSelected(false);
        addressLineField.setText("");
        cityField.setText("");
        postalCodeField.setText("");
        editedPatient = null;
    }

    @FXML
    private void registerPatient(ActionEvent actionEvent) {
        if (validatePatientForm()) {
            patientObservableList.add(patientService.save(getPatientFromForm()));
            resetFormFields();
            formFields.forEach(field -> ((IFXValidatableControl) field).resetValidation());
        }
    }

    /**
     * Method validating the correctness of data provided in the patient edit/register form.
     * @return true if all fields were correctly filled
     */
    private boolean validatePatientForm() {
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
     * Creates a {@link Patient} object from data provided by the user in the edit/register form.
     * @return the newly created patient
     */
    private Patient getPatientFromForm() {
        Address address = new Address(
                addressLineField.getText(),
                cityField.getText(),
                Integer.parseInt(postalCodeField.getText()));
        return Patient.builder()
                .name(nameField.getText())
                .surname(surnameField.getText())
                .birthDate(birthDatePicker.getValue())
                .homeAddress(address)
                .isAlive(!deceasedCheckbox.isSelected())
                .phoneNumber(phoneNumberField.getText())
                .build();
    }

    @FXML
    private void confirmEditPatient(ActionEvent actionEvent) {
        if (validatePatientForm()) {
            Patient p = getPatientFromForm();
            editedPatient.setAlive(p.isAlive());
            editedPatient.setPhoneNumber(p.getPhoneNumber());
            editedPatient.getHomeAddress().setAddressLine(p.getHomeAddress().getAddressLine());
            editedPatient.getHomeAddress().setCity(p.getHomeAddress().getCity());
            editedPatient.getHomeAddress().setZipCode(p.getHomeAddress().getZipCode());
            patientObservableList.remove(editedPatient);
            patientObservableList.add(patientService.save(editedPatient));
            resetFormFields();
            formButtonsParent.getChildren().add(registerPatientButton);
            formButtonsParent.getChildren().remove(cancelEditPatientButton);
            formButtonsParent.getChildren().remove(confirmEditPatientButton);
            editedPatient = null;
            formFields.forEach(field -> ((IFXValidatableControl) field).resetValidation());
            lockEditableFormFields(false);
            formLabel.setText("REGISTER NEW PATIENT");
        }
    }

    @FXML
    private void cancelEditPatient(ActionEvent actionEvent) {
        resetFormFields();
        formFields.forEach(field -> ((IFXValidatableControl) field).resetValidation());
        formButtonsParent.getChildren().add(registerPatientButton);
        formButtonsParent.getChildren().remove(cancelEditPatientButton);
        formButtonsParent.getChildren().remove(confirmEditPatientButton);
        lockEditableFormFields(false);
        editedPatient = null;
        formLabel.setText("REGISTER NEW PATIENT");
    }

    @FXML
    private void clearSearchFields(ActionEvent actionEvent) {
        nameSearchField.setText("");
        birthDateSearchPicker.setValue(null);
        surnameSearchField.setText("");
    }
}
