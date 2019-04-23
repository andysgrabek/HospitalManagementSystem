package work.in.progress.hospitalmanagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.HospitalStaff;
import work.in.progress.hospitalmanagement.repository.HospitalStaffRepository;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.service.HospitalStaffService;
import work.in.progress.hospitalmanagement.util.Mocks;

import javax.validation.Validator;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = { AbstractViewControllerTest.class,
                StaffManagementViewController.class,
                MainMenuViewController.class,
                HospitalStaffService.class,
                Validator.class})
public class StaffManagementViewControllerTest implements ApplicationContextAware {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @MockBean
    public Validator validator;
    @MockBean
    public HospitalStaffRepository staffRepository;
    @MockBean
    public HospitalStaffService staffService;
    @MockBean
    public DepartmentService departmentService;
    
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
                = AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        assertNotNull(vc);
    }

    @Test
    public void testPredicateAllFieldsEmpty() {
        StaffManagementViewController vc
                = (StaffManagementViewController) AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        TextField nameFieldS = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameSearchField");
        TextField surnameFieldS = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameSearchField");
        ComboBox<Department> departmentFieldS = (ComboBox<Department>) ReflectionTestUtils.getField(vc, vc.getClass(), "departmentSearchField");
        nameFieldS.setText("");
        surnameFieldS.setText("");
        Department d = Mocks.department();
        departmentFieldS.setItems(FXCollections.observableArrayList(d));
        departmentFieldS.getSelectionModel().clearSelection();
        HospitalStaff h = Mocks.hospitalStaff();
        FilteredList<HospitalStaff> filteredList = new FilteredList<>(FXCollections.observableArrayList(h));
        Predicate<HospitalStaff> predicate = ReflectionTestUtils.invokeMethod(vc, "composeHospitalStaffPredicate",
                nameFieldS.getText(),
                surnameFieldS.getText(),
                departmentFieldS.getSelectionModel().getSelectedItem());
        filteredList.setPredicate(predicate);
        assertTrue(filteredList.stream().collect(Collectors.toList()).contains(h));
    }

    @Test
    public void testPredicateFindStaffMember() {
        StaffManagementViewController vc
                = (StaffManagementViewController) AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        TextField nameFieldS = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameSearchField");
        TextField surnameFieldS = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameSearchField");
        ComboBox<Department> departmentFieldS = (ComboBox<Department>) ReflectionTestUtils.getField(vc, vc.getClass(), "departmentSearchField");
        HospitalStaff h = Mocks.hospitalStaff();
        nameFieldS.setText(h.getName());
        surnameFieldS.setText(h.getSurname());
        Department d = Mocks.department();
        departmentFieldS.setItems(FXCollections.observableArrayList(d));
        departmentFieldS.getSelectionModel().select(h.getDepartment());
        FilteredList<HospitalStaff> filteredList = new FilteredList<>(FXCollections.observableArrayList(h));
        Predicate<HospitalStaff> predicate = ReflectionTestUtils.invokeMethod(vc, "composeHospitalStaffPredicate",
                nameFieldS.getText(),
                surnameFieldS.getText(),
                departmentFieldS.getSelectionModel().getSelectedItem());
        filteredList.setPredicate(predicate);
        assertTrue(new ArrayList<>(filteredList).contains(h));
    }

    @Test
    public void removeStaffOnDeleteTest() {
        StaffManagementViewController vc
                = (StaffManagementViewController) AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        ObservableList<HospitalStaff> list =
                (ObservableList<HospitalStaff>) ReflectionTestUtils.getField(vc, vc.getClass(), "staffObservableList");
        HospitalStaff p = Mocks.hospitalStaff();
        list.add(p);
        ReflectionTestUtils.invokeMethod(vc, "removeStaffOnDelete", new ListCellEvent<>(ListCellEvent.DELETE_EVENT, p));
        assertFalse(list.contains(p));
    }

    @Test
    public void updateStaffOnEditTest_ongoingEdit() {
        StaffManagementViewController vc
                = (StaffManagementViewController) AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        HospitalStaff p = Mocks.hospitalStaff();
        ReflectionTestUtils.invokeMethod(vc, "updateStaffOnEdit", new ListCellEvent<>(ListCellEvent.EDIT_EVENT, p));
        HospitalStaff edited = HospitalStaff.builder().build();
        TextField nameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        TextField surnameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameField");
        ComboBox<HospitalStaff.Role> roleField = (ComboBox<HospitalStaff.Role>) ReflectionTestUtils.getField(vc, vc.getClass(), "roleField");
        ComboBox<Department> departmentField = (ComboBox<Department>) ReflectionTestUtils.getField(vc, vc.getClass(), "departmentField");

        HospitalStaff editedStaff = (HospitalStaff) ReflectionTestUtils.getField(vc, vc.getClass(), "editedStaff");

        assertEquals(p.getName(), nameField.getText());
        assertEquals(p.getSurname(), surnameField.getText());
        assertEquals(p.getDepartment(), departmentField.getSelectionModel().getSelectedItem());
        assertEquals(p.getRole(), roleField.getSelectionModel().getSelectedItem());

        assertNotEquals(editedStaff, edited);
    }

    @Test
    public void updateStaffOnEditTest_newEdit() {
        StaffManagementViewController vc
                = (StaffManagementViewController) AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        HospitalStaff p = Mocks.hospitalStaff();
        ReflectionTestUtils.invokeMethod(vc, "updateStaffOnEdit", new ListCellEvent<>(ListCellEvent.EDIT_EVENT, p));
        TextField nameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        TextField surnameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameField");
        ComboBox<HospitalStaff.Role> roleField = (ComboBox<HospitalStaff.Role>) ReflectionTestUtils.getField(vc, vc.getClass(), "roleField");
        ComboBox<Department> departmentField = (ComboBox<Department>) ReflectionTestUtils.getField(vc, vc.getClass(), "departmentField");

        assertEquals(p.getDepartment(), departmentField.getSelectionModel().getSelectedItem());
        assertEquals(p.getRole(), roleField.getSelectionModel().getSelectedItem());

        HospitalStaff editedStaff = (HospitalStaff) ReflectionTestUtils.getField(vc, vc.getClass(), "editedStaff");

        assertEquals(p.getName(), nameField.getText());
        assertEquals(p.getSurname(), surnameField.getText());
        assertEquals(p.getRole(), roleField.getSelectionModel().getSelectedItem());
        assertEquals(p.getDepartment(), departmentField.getSelectionModel().getSelectedItem());
        assertEquals(editedStaff, p);
    }

    @Test
    public void resetFormFieldsTest() {
        StaffManagementViewController vc
                = (StaffManagementViewController) AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        HospitalStaff p = Mocks.hospitalStaff();
        TextField nameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        TextField surnameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameField");
        ComboBox<HospitalStaff.Role> roleField = (ComboBox<HospitalStaff.Role>) ReflectionTestUtils.getField(vc, vc.getClass(), "roleField");
        ComboBox<Department> departmentField = (ComboBox<Department>) ReflectionTestUtils.getField(vc, vc.getClass(), "departmentField");
        ReflectionTestUtils.invokeMethod(vc, "updateStaffOnEdit", new ListCellEvent<>(ListCellEvent.EDIT_EVENT, p));
        ReflectionTestUtils.invokeMethod(vc, "resetFormFields");
        assertEquals("", nameField.getText());
        assertEquals("", surnameField.getText());
        assertTrue(roleField.getSelectionModel().isEmpty());
        assertTrue(departmentField.getSelectionModel().isEmpty());
    }

    @Test
    public void clearSearchFieldsTest() {
        StaffManagementViewController vc
                = (StaffManagementViewController) AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        HospitalStaff p = Mocks.hospitalStaff();
        TextField nameFieldS = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameSearchField");
        TextField surnameFieldS = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameSearchField");
        ComboBox<Department> departmentFieldS = (ComboBox<Department>) ReflectionTestUtils.getField(vc, vc.getClass(), "departmentSearchField");
        nameFieldS.setText(p.getName());
        surnameFieldS.setText(p.getSurname());
        Department d = Mocks.department();
        departmentFieldS.setItems(FXCollections.observableArrayList(d));
        departmentFieldS.getSelectionModel().select(d);
        ReflectionTestUtils.invokeMethod(vc, "clearSearchFields", new ActionEvent());
        assertEquals("", nameFieldS.getText());
        assertEquals("", surnameFieldS.getText());
        assertTrue(departmentFieldS.getSelectionModel().isEmpty());
    }

    private void fillFormValid(StaffManagementViewController vc) {
        TextField nameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        TextField surnameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameField");
        ComboBox<HospitalStaff.Role> roleField = (ComboBox<HospitalStaff.Role>) ReflectionTestUtils.getField(vc, vc.getClass(), "roleField");
        ComboBox<Department> departmentField = (ComboBox<Department>) ReflectionTestUtils.getField(vc, vc.getClass(), "departmentField");

        HospitalStaff p = Mocks.hospitalStaff();
        nameField.setText(p.getName());
        surnameField.setText(p.getSurname());
        roleField.getSelectionModel().select(p.getRole());
        departmentField.getSelectionModel().select(p.getDepartment());
        ReflectionTestUtils.setField(vc, vc.getClass(), "editedStaff", p, p.getClass());
    }

    private void fillFormInvalid(StaffManagementViewController vc) {
        TextField nameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        TextField surnameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameField");
        ComboBox<HospitalStaff.Role> roleField = (ComboBox<HospitalStaff.Role>) ReflectionTestUtils.getField(vc, vc.getClass(), "roleField");
        ComboBox<Department> departmentField = (ComboBox<Department>) ReflectionTestUtils.getField(vc, vc.getClass(), "departmentField");

        HospitalStaff p = Mocks.hospitalStaff();
        nameField.setText("");
        surnameField.setText("");
        roleField.getSelectionModel().select(null);
        departmentField.getSelectionModel().select(null);
        ReflectionTestUtils.setField(vc, vc.getClass(), "editedStaff", p, p.getClass());    }

    @Test
    public void confirmEditStaffTest_validated() {
        StaffManagementViewController vc
                = (StaffManagementViewController) AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        HBox formButtonsParent = (HBox) ReflectionTestUtils.getField(vc, vc.getClass(), "formButtonsParent");
        Button registerButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "createStaffButton");
        Button cancelEditButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "cancelEditStaffButton");
        Button confirmEditButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "confirmEditStaffButton");
        fillFormValid(vc);
        formButtonsParent.getChildren().add(confirmEditButton);
        formButtonsParent.getChildren().add(cancelEditButton);
        formButtonsParent.getChildren().remove(registerButton);
        ReflectionTestUtils.invokeMethod(vc, "confirmEditStaff", new ActionEvent());
        HospitalStaff editedStaff = (HospitalStaff) ReflectionTestUtils.getField(vc, vc.getClass(), "editedStaff");
        assertNull(editedStaff);
        assertTrue(formButtonsParent.getChildrenUnmodifiable().contains(registerButton));
        assertFalse(formButtonsParent.getChildrenUnmodifiable().contains(cancelEditButton));
        assertFalse(formButtonsParent.getChildrenUnmodifiable().contains(confirmEditButton));
    }

    @Test
    public void confirmEditStaffTest_notValidated() {
        StaffManagementViewController vc
                = (StaffManagementViewController) AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        HBox formButtonsParent = (HBox) ReflectionTestUtils.getField(vc, vc.getClass(), "formButtonsParent");
        Button registerButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "createStaffButton");
        Button cancelEditButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "cancelEditStaffButton");
        Button confirmEditButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "confirmEditStaffButton");
        fillFormInvalid(vc);
        formButtonsParent.getChildren().add(confirmEditButton);
        formButtonsParent.getChildren().add(cancelEditButton);
        formButtonsParent.getChildren().remove(registerButton);
        ReflectionTestUtils.invokeMethod(vc, "confirmEditStaff", new ActionEvent());
        HospitalStaff editedStaff = (HospitalStaff) ReflectionTestUtils.getField(vc, vc.getClass(), "editedStaff");
        assertNotNull(editedStaff);
        assertFalse(formButtonsParent.getChildrenUnmodifiable().contains(registerButton));
        assertTrue(formButtonsParent.getChildrenUnmodifiable().contains(cancelEditButton));
        assertTrue(formButtonsParent.getChildrenUnmodifiable().contains(confirmEditButton));
    }

    @Test
    public void cancelEditStaffTest() {
        StaffManagementViewController vc
                = (StaffManagementViewController) AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        HBox formButtonsParent = (HBox) ReflectionTestUtils.getField(vc, vc.getClass(), "formButtonsParent");
        Button registerButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "createStaffButton");
        Button cancelEditButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "cancelEditStaffButton");
        Button confirmEditButton = (Button) ReflectionTestUtils.getField(vc, vc.getClass(), "confirmEditStaffButton");
        HospitalStaff editedStaff = (HospitalStaff) ReflectionTestUtils.getField(vc, vc.getClass(), "editedStaff");
        formButtonsParent.getChildren().add(confirmEditButton);
        formButtonsParent.getChildren().add(cancelEditButton);
        formButtonsParent.getChildren().remove(registerButton);
        ActionEvent e = new ActionEvent();
        ReflectionTestUtils.invokeMethod(vc, "cancelEditStaff", e);
        assertNull(editedStaff);
        assertTrue(formButtonsParent.getChildrenUnmodifiable().contains(registerButton));
        assertFalse(formButtonsParent.getChildrenUnmodifiable().contains(cancelEditButton));
        assertFalse(formButtonsParent.getChildrenUnmodifiable().contains(confirmEditButton));
    }

    @Test
    public void registerStaffTest_validStaff() {
        StaffManagementViewController vc
                = (StaffManagementViewController) AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        fillFormValid(vc);
        ObservableList<HospitalStaff> list =
                (ObservableList<HospitalStaff>) ReflectionTestUtils.getField(vc, vc.getClass(), "staffObservableList");
        HospitalStaff p = ReflectionTestUtils.invokeMethod(vc, "getStaffFromForm");
        ReflectionTestUtils.invokeMethod(vc, "registerStaff", new ActionEvent());
        TextField nameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        TextField surnameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameField");
        ComboBox<HospitalStaff.Role> roleField = (ComboBox<HospitalStaff.Role>) ReflectionTestUtils.getField(vc, vc.getClass(), "roleField");
        ComboBox<Department> departmentField = (ComboBox<Department>) ReflectionTestUtils.getField(vc, vc.getClass(), "departmentField");

        assertEquals("", nameField.getText());
        assertEquals("", surnameField.getText());
        assertTrue(roleField.getSelectionModel().isEmpty());
        assertTrue(departmentField.getSelectionModel().isEmpty());

    }

    @Test
    public void registerStaffTest_invalidStaff() {
        StaffManagementViewController vc
                = (StaffManagementViewController) AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        fillFormInvalid(vc);
        ObservableList<HospitalStaff> list =
                (ObservableList<HospitalStaff>) ReflectionTestUtils.getField(vc, vc.getClass(), "staffObservableList");
        ReflectionTestUtils.invokeMethod(vc, "registerStaff", new ActionEvent());
        TextField nameField = (TextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        assertEquals(nameField.getText(), "");
    }

    @Test
    public void backToMainMenuTest() {
        StaffManagementViewController vc
                = (StaffManagementViewController) AbstractViewController.instantiateViewController(StaffManagementViewController.class);
        ReflectionTestUtils.invokeMethod(vc, "backToMainMenu", new ActionEvent());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = (ConfigurableApplicationContext) applicationContext;
    }
}