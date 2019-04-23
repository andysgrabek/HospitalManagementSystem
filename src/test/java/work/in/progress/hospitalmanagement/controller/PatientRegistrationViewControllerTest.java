package work.in.progress.hospitalmanagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.util.ReflectionTestUtils;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;
import work.in.progress.hospitalmanagement.event.ListCellEvent;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.repository.PatientRepository;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.service.PatientService;
import work.in.progress.hospitalmanagement.util.Mocks;

import javax.validation.Validator;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = { AbstractViewControllerTest.class,
                PatientRegistrationViewController.class,
                MainMenuViewController.class,
                PatientService.class,
                Validator.class})
public class PatientRegistrationViewControllerTest implements ApplicationContextAware {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @MockBean
    public Validator validator;
    @MockBean
    public PatientRepository patientRepository;
    @MockBean
    public PatientService patientService;

    private ConfigurableApplicationContext context;

    @Before
    public void setUp() {
        ApplicationContextSingleton.setContext(null);
        ApplicationContextSingleton.setContext(context);
    }

    @After
    public void tearDown() {
        ApplicationContextSingleton.setContext(null);
    }

    @Test
    public void initializeTest() {
        AbstractViewController vc
                = AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        assertNotNull(vc);
    }

    @Test
    public void testPredicateAllFieldsEmpty() {
        PatientRegistrationViewController vc
                = (PatientRegistrationViewController) AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        Patient p = Mocks.patient();
        TextField nameFieldS = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameSearchField");
        TextField surnameFieldS = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameSearchField");
        DatePicker birthDateSearchPicker = (DatePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "birthDateSearchPicker");
        nameFieldS.setText("");
        surnameFieldS.setText("");
        birthDateSearchPicker.setValue(null);
        FilteredList<Patient> filteredList = new FilteredList<>(FXCollections.observableArrayList(p));
        Predicate<Patient> predicate = ReflectionTestUtils.invokeMethod(vc, "composePatientPredicate",
                nameFieldS.getText(),
                surnameFieldS.getText(),
                birthDateSearchPicker.getValue());
        filteredList.setPredicate(predicate);
        assertTrue(filteredList.stream().collect(Collectors.toList()).contains(p));
    }

    @Test
    public void testPredicateFindStaffMember() {
        PatientRegistrationViewController vc
                = (PatientRegistrationViewController) AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        Patient p = Mocks.patient();
        TextField nameFieldS = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameSearchField");
        TextField surnameFieldS = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameSearchField");
        DatePicker birthDateSearchPicker = (DatePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "birthDateSearchPicker");
        nameFieldS.setText(p.getName());
        surnameFieldS.setText(p.getSurname());
        birthDateSearchPicker.setValue(p.getBirthDate());
        FilteredList<Patient> filteredList = new FilteredList<>(FXCollections.observableArrayList(p));
        Predicate<Patient> predicate = ReflectionTestUtils.invokeMethod(vc, "composePatientPredicate",
                nameFieldS.getText(),
                surnameFieldS.getText(),
                birthDateSearchPicker.getValue());
        filteredList.setPredicate(predicate);
        assertTrue(filteredList.stream().collect(Collectors.toList()).contains(p));
    }


    @Test
    public void removePatientOnDeleteTest() {
        PatientRegistrationViewController vc
                = (PatientRegistrationViewController) AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        ObservableList<Patient> list =
                (ObservableList<Patient>) ReflectionTestUtils.getField(vc, vc.getClass(), "patientObservableList");
        Patient p = Mocks.patient();
        list.add(p);
        ReflectionTestUtils.invokeMethod(vc, "removePatientOnDelete", new ListCellEvent<>(ListCellEvent.DELETE_EVENT, p));
        assertFalse(list.contains(p));
    }

    @Test
    public void updatePatientOnEditTest_ongoingEdit() {
        PatientRegistrationViewController vc
                = (PatientRegistrationViewController) AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        Patient p = Mocks.patient();
        ReflectionTestUtils.invokeMethod(vc, "updatePatientOnEdit", new ListCellEvent<>(ListCellEvent.EDIT_EVENT, p));
        Patient edited = Patient.builder().build();
        TextField nameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        TextField surnameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameField");
        DatePicker birthDatePicker = (DatePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "birthDatePicker");
        TextField phoneNumberField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "phoneNumberField");
        CheckBox deceasedCheckbox = (CheckBox) ReflectionTestUtils.getField(vc, vc.getClass(), "deceasedCheckbox");
        TextField addressLineField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "addressLineField");
        TextField cityField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "cityField");
        TextField postalCodeField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "postalCodeField");
        Patient editedPatient = (Patient) ReflectionTestUtils.getField(vc, vc.getClass(), "editedPatient");

        assertEquals(p.getName(), nameField.getText());
        assertEquals(p.getSurname(), surnameField.getText());
        assertEquals(p.getBirthDate(), birthDatePicker.getValue());
        assertEquals(p.getPhoneNumber(), phoneNumberField.getText());
        assertEquals(p.isAlive(), !deceasedCheckbox.isSelected());
        assertEquals(p.getHomeAddress().getAddressLine(), addressLineField.getText());
        assertEquals(p.getHomeAddress().getCity(), cityField.getText());
        assertEquals(p.getHomeAddress().getZipCode(), Integer.parseInt(postalCodeField.getText()));
        assertNotEquals(editedPatient, edited);
    }

    @Test
    public void updatePatientOnEditTest_newEdit() {
        PatientRegistrationViewController vc
                = (PatientRegistrationViewController) AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        Patient p = Mocks.patient();
        ReflectionTestUtils.invokeMethod(vc, "updatePatientOnEdit", new ListCellEvent<>(ListCellEvent.EDIT_EVENT, p));
        TextField nameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        TextField surnameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameField");
        DatePicker birthDatePicker = (DatePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "birthDatePicker");
        TextField phoneNumberField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "phoneNumberField");
        CheckBox deceasedCheckbox = (CheckBox) ReflectionTestUtils.getField(vc, vc.getClass(), "deceasedCheckbox");
        TextField addressLineField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "addressLineField");
        TextField cityField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "cityField");
        TextField postalCodeField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "postalCodeField");
        Patient editedPatient = (Patient) ReflectionTestUtils.getField(vc, vc.getClass(), "editedPatient");

        assertEquals(p.getName(), nameField.getText());
        assertEquals(p.getSurname(), surnameField.getText());
        assertEquals(p.getBirthDate(), birthDatePicker.getValue());
        assertEquals(p.getPhoneNumber(), phoneNumberField.getText());
        assertEquals(p.isAlive(), !deceasedCheckbox.isSelected());
        assertEquals(p.getHomeAddress().getAddressLine(), addressLineField.getText());
        assertEquals(p.getHomeAddress().getCity(), cityField.getText());
        assertEquals(p.getHomeAddress().getZipCode(), Integer.parseInt(postalCodeField.getText()));
        assertEquals(editedPatient, p);
    }

    @Test
    public void resetFormFieldsTest() {
        PatientRegistrationViewController vc
                = (PatientRegistrationViewController) AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        Patient p = Mocks.patient();
        TextField nameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        TextField surnameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameField");
        DatePicker birthDatePicker = (DatePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "birthDatePicker");
        TextField phoneNumberField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "phoneNumberField");
        CheckBox deceasedCheckbox = (CheckBox) ReflectionTestUtils.getField(vc, vc.getClass(), "deceasedCheckbox");
        TextField addressLineField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "addressLineField");
        TextField cityField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "cityField");
        TextField postalCodeField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "postalCodeField");
        ReflectionTestUtils.invokeMethod(vc, "updatePatientOnEdit", new ListCellEvent<>(ListCellEvent.EDIT_EVENT, p));
        ReflectionTestUtils.invokeMethod(vc, "resetFormFields");
        assertEquals("", nameField.getText());
        assertEquals("", surnameField.getText());
        assertNull(birthDatePicker.getValue());
        assertEquals("", phoneNumberField.getText());
        assertTrue(!deceasedCheckbox.isSelected());
        assertEquals("", addressLineField.getText());
        assertEquals("", cityField.getText());
        assertEquals("", postalCodeField.getText());
    }

    @Test
    public void clearSearchFieldsTest() {
        PatientRegistrationViewController vc
                = (PatientRegistrationViewController) AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        Patient p = Mocks.patient();
        TextField nameFieldS = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameSearchField");
        TextField surnameFieldS = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameSearchField");
        DatePicker birthDatePickerS = (DatePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "birthDateSearchPicker");
        nameFieldS.setText(p.getName());
        surnameFieldS.setText(p.getSurname());
        birthDatePickerS.setValue(p.getBirthDate());
        ActionEvent e = new ActionEvent();
        ReflectionTestUtils.invokeMethod(vc, "clearSearchFields", e);
        assertEquals("", nameFieldS.getText());
        assertEquals("", surnameFieldS.getText());
        assertNull(birthDatePickerS.getValue());
    }

    private void fillFormValid(PatientRegistrationViewController vc) {
        TextField nameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        TextField surnameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameField");
        DatePicker birthDatePicker = (DatePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "birthDatePicker");
        TextField phoneNumberField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "phoneNumberField");
        CheckBox deceasedCheckbox = (CheckBox) ReflectionTestUtils.getField(vc, vc.getClass(), "deceasedCheckbox");
        TextField addressLineField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "addressLineField");
        TextField cityField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "cityField");
        TextField postalCodeField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "postalCodeField");
        Patient p = Mocks.patient();
        nameField.setText(p.getName());
        surnameField.setText(p.getSurname());
        birthDatePicker.setValue(p.getBirthDate());
        phoneNumberField.setText(p.getPhoneNumber());
        deceasedCheckbox.setSelected(!p.isAlive());
        addressLineField.setText(p.getHomeAddress().getAddressLine());
        cityField.setText(p.getHomeAddress().getCity());
        postalCodeField.setText(String.valueOf(p.getHomeAddress().getZipCode()));
        ReflectionTestUtils.setField(vc, vc.getClass(), "editedPatient", p, p.getClass());
    }

    private void fillFormInvalid(PatientRegistrationViewController vc) {
        TextField nameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        TextField surnameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameField");
        DatePicker birthDatePicker = (DatePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "birthDatePicker");
        TextField phoneNumberField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "phoneNumberField");
        CheckBox deceasedCheckbox = (CheckBox) ReflectionTestUtils.getField(vc, vc.getClass(), "deceasedCheckbox");
        TextField addressLineField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "addressLineField");
        TextField cityField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "cityField");
        TextField postalCodeField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "postalCodeField");
        Patient p = Mocks.patient();
        nameField.setText("");
        surnameField.setText("");
        birthDatePicker.setValue(p.getBirthDate());
        phoneNumberField.setText(p.getPhoneNumber());
        deceasedCheckbox.setSelected(!p.isAlive());
        addressLineField.setText(p.getHomeAddress().getAddressLine());
        cityField.setText(p.getHomeAddress().getCity());
        postalCodeField.setText("XD");
        ReflectionTestUtils.setField(vc, vc.getClass(), "editedPatient", p, p.getClass());
    }

    @Test
    public void confirmEditPatientTest_validated() {
        PatientRegistrationViewController vc
                = (PatientRegistrationViewController) AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        HBox formButtonsParent = (HBox) ReflectionTestUtils.getField(vc, vc.getClass(), "formButtonsParent");
        Button registerPatientButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "registerPatientButton");
        Button cancelEditPatientButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "cancelEditPatientButton");
        Button confirmEditPatientButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "confirmEditPatientButton");
        fillFormValid(vc);
        formButtonsParent.getChildren().add(confirmEditPatientButton);
        formButtonsParent.getChildren().add(cancelEditPatientButton);
        formButtonsParent.getChildren().remove(registerPatientButton);
        ActionEvent e = new ActionEvent();
        ReflectionTestUtils.invokeMethod(vc, "confirmEditPatient", e);
        Patient editedPatient = (Patient) ReflectionTestUtils.getField(vc, vc.getClass(), "editedPatient");
        assertNull(editedPatient);
        assertTrue(formButtonsParent.getChildrenUnmodifiable().contains(registerPatientButton));
        assertFalse(formButtonsParent.getChildrenUnmodifiable().contains(cancelEditPatientButton));
        assertFalse(formButtonsParent.getChildrenUnmodifiable().contains(confirmEditPatientButton));
    }

    @Test
    public void confirmEditPatientTest_notValidated() {
        PatientRegistrationViewController vc
                = (PatientRegistrationViewController) AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        HBox formButtonsParent = (HBox) ReflectionTestUtils.getField(vc, vc.getClass(), "formButtonsParent");
        Button registerPatientButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "registerPatientButton");
        Button cancelEditPatientButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "cancelEditPatientButton");
        Button confirmEditPatientButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "confirmEditPatientButton");
        fillFormInvalid(vc);
        formButtonsParent.getChildren().add(confirmEditPatientButton);
        formButtonsParent.getChildren().add(cancelEditPatientButton);
        formButtonsParent.getChildren().remove(registerPatientButton);
        ActionEvent e = new ActionEvent();
        ReflectionTestUtils.invokeMethod(vc, "confirmEditPatient", e);
        Patient editedPatient = (Patient) ReflectionTestUtils.getField(vc, vc.getClass(), "editedPatient");
        assertNotNull(editedPatient);
        assertFalse(formButtonsParent.getChildrenUnmodifiable().contains(registerPatientButton));
        assertTrue(formButtonsParent.getChildrenUnmodifiable().contains(cancelEditPatientButton));
        assertTrue(formButtonsParent.getChildrenUnmodifiable().contains(confirmEditPatientButton));
    }

    @Test
    public void cancelEditPatientTest() {
        PatientRegistrationViewController vc
                = (PatientRegistrationViewController) AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        HBox formButtonsParent = (HBox) ReflectionTestUtils.getField(vc, vc.getClass(), "formButtonsParent");
        Button registerPatientButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "registerPatientButton");
        Button cancelEditPatientButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "cancelEditPatientButton");
        Button confirmEditPatientButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "confirmEditPatientButton");
        Patient editedPatient = (Patient) ReflectionTestUtils.getField(vc, vc.getClass(), "editedPatient");
        formButtonsParent.getChildren().add(confirmEditPatientButton);
        formButtonsParent.getChildren().add(cancelEditPatientButton);
        formButtonsParent.getChildren().remove(registerPatientButton);
        ActionEvent e = new ActionEvent();
        ReflectionTestUtils.invokeMethod(vc, "cancelEditPatient", e);
        assertNull(editedPatient);
        assertTrue(formButtonsParent.getChildrenUnmodifiable().contains(registerPatientButton));
        assertFalse(formButtonsParent.getChildrenUnmodifiable().contains(cancelEditPatientButton));
        assertFalse(formButtonsParent.getChildrenUnmodifiable().contains(confirmEditPatientButton));
    }

    @Test
    public void registerPatientTest_validPatient() {
        PatientRegistrationViewController vc
                = (PatientRegistrationViewController) AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        fillFormValid(vc);
        ObservableList<Patient> list =
                (ObservableList<Patient>) ReflectionTestUtils.getField(vc, vc.getClass(), "patientObservableList");
        Patient p = ReflectionTestUtils.invokeMethod(vc, "getPatientFromForm");
        ReflectionTestUtils.invokeMethod(vc, "createDepartment", new ActionEvent());
        TextField nameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        TextField surnameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameField");
        DatePicker birthDatePicker = (DatePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "birthDatePicker");
        TextField phoneNumberField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "phoneNumberField");
        CheckBox deceasedCheckbox = (CheckBox) ReflectionTestUtils.getField(vc, vc.getClass(), "deceasedCheckbox");
        TextField addressLineField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "addressLineField");
        TextField cityField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "cityField");
        TextField postalCodeField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "postalCodeField");
        assertEquals("", nameField.getText());
        assertEquals("", surnameField.getText());
        assertNull(birthDatePicker.getValue());
        assertEquals("", phoneNumberField.getText());
        assertTrue(!deceasedCheckbox.isSelected());
        assertEquals("", addressLineField.getText());
        assertEquals("", cityField.getText());
        assertEquals("", postalCodeField.getText());
    }

    @Test
    public void registerPatientTest_invalidPatient() {
        PatientRegistrationViewController vc
                = (PatientRegistrationViewController) AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        fillFormInvalid(vc);
        ObservableList<Patient> list =
                (ObservableList<Patient>) ReflectionTestUtils.getField(vc, vc.getClass(), "patientObservableList");
        ReflectionTestUtils.invokeMethod(vc, "createDepartment", new ActionEvent());
        TextField postalCodeField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "postalCodeField");
        assertEquals(postalCodeField.getText(), "XD");
    }

    @Test
    public void backToMainMenuTest() {
        PatientRegistrationViewController vc
                = (PatientRegistrationViewController) AbstractViewController.instantiateViewController(PatientRegistrationViewController.class);
        ReflectionTestUtils.invokeMethod(vc, "backToMainMenu", new ActionEvent());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = (ConfigurableApplicationContext) applicationContext;
    }
}