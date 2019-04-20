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
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.factory.PatientCellFactory;
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

import static work.in.progress.hospitalmanagement.event.PatientEditEvent.PATIENT_EDIT_EVENT_EVENT_TYPE;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class PatientRegistrationViewController extends AbstractViewController {

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
    private ObservableList<Patient> patientObservableList = FXCollections.observableArrayList();
    private final PatientService patientService;
    private final Validator validator;

    @Autowired
    public PatientRegistrationViewController(PatientService patientService, Validator validator) {
        this.patientService = patientService;
        this.validator = validator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        PatientCellFactory patientCellFactory = new PatientCellFactory();
        registeredPatientListView.setCellFactory(patientCellFactory);
        patientObservableList.addAll(patientService.findAll());
        //item editing
        registeredPatientListView.addEventHandler(PATIENT_EDIT_EVENT_EVENT_TYPE, event -> {
            if (editedPatient != null) {
                cancelEditPatient(null);
            }
            resetFormFields();
            Patient p = event.getPatient();
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
        });
        //form validation
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
        formFields.forEach(field -> field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                ((IFXValidatableControl) field).validate();
            }
        }));
        //filtering
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
    private void backToMainMenu(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(MainMenuViewController.class), true);
    }

    @FXML
    private void registerPatient(ActionEvent actionEvent) {
        if (validatePatientForm()) {
            patientObservableList.add(patientService.save(getPatientFromForm()));
            resetFormFields();
            formFields.forEach(field -> ((IFXValidatableControl) field).resetValidation());
        }
    }

    private boolean validatePatientForm() {
        final boolean[] isCorrect = {true};
        formFields.forEach(field -> {
            boolean p = ((IFXValidatableControl) field).validate();
            if (isCorrect[0]) {
                isCorrect[0] = p;
            }
        });
        return isCorrect[0];
    }

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
            patientObservableList.remove(editedPatient);
            patientObservableList.add(patientService.save(p)); //TODO: make sure it updates not adds a new patient
            resetFormFields();
            formButtonsParent.getChildren().add(registerPatientButton);
            formButtonsParent.getChildren().remove(cancelEditPatientButton);
            formButtonsParent.getChildren().remove(confirmEditPatientButton);
            editedPatient = null;
            formFields.forEach(field -> ((IFXValidatableControl) field).resetValidation());
        }
    }

    @FXML
    private void cancelEditPatient(ActionEvent actionEvent) {
        resetFormFields();
        formFields.forEach(field -> ((IFXValidatableControl) field).resetValidation());
        formButtonsParent.getChildren().add(registerPatientButton);
        formButtonsParent.getChildren().remove(cancelEditPatientButton);
        formButtonsParent.getChildren().remove(confirmEditPatientButton);
        editedPatient = null;
    }

    @FXML
    private void clearSearchFields(ActionEvent actionEvent) {
        nameSearchField.setText("");
        birthDateSearchPicker.setValue(null);
        surnameSearchField.setText("");
    }
}
