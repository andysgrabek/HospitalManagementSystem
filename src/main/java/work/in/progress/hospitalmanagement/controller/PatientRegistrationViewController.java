package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.factory.PatientCellFactory;
import work.in.progress.hospitalmanagement.model.Address;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.service.PatientService;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class PatientRegistrationViewController extends AbstractViewController {

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

    private final PatientService patientService;

    public PatientRegistrationViewController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PatientCellFactory patientCellFactory = new PatientCellFactory();
        registeredPatientListView.setCellFactory(patientCellFactory);
        ObservableList<Patient> patientObservableList = FXCollections.observableArrayList();
        //patientObservableList.addAll(get data from patient service);
        registeredPatientListView.setItems(patientObservableList);
//        getRoot().addEventHandler();
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
    }

    @FXML
    private void backToMainMenu(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(MainMenuViewController.class), true);
    }

    @FXML
    private void registerPatient(ActionEvent actionEvent) {
        Address address = new Address(addressLineField.getText(), cityField.getText(), Integer.parseInt(postalCodeField.getText()));
        Patient patient = Patient.builder()
                .name(nameField.getText())
                .surname(surnameField.getText())
                .birthDate(birthDatePicker.getValue())
                .homeAddress(address)
                .isAlive(!deceasedCheckbox.isSelected())
                .phoneNumber(phoneNumberField.getText())
                .build();
        patientService.registerPatient(patient);
        resetFormFields();
    }

    @FXML
    private void confirmEditPatient(ActionEvent actionEvent) {

    }

    @FXML
    private void cancelEditPatient(ActionEvent actionEvent) {

    }
}
