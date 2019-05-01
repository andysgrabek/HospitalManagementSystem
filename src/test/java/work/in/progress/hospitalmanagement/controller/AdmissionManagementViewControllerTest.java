package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
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
import org.springframework.test.util.ReflectionTestUtils;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;
import work.in.progress.hospitalmanagement.converter.BedStringConverter;
import work.in.progress.hospitalmanagement.converter.DepartmentStringConverter;
import work.in.progress.hospitalmanagement.event.ListCellEvent;
import work.in.progress.hospitalmanagement.factory.DialogFactory;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.repository.BedRepository;
import work.in.progress.hospitalmanagement.repository.DepartmentRepository;
import work.in.progress.hospitalmanagement.repository.InpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.repository.OutpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.repository.PatientRepository;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.service.InpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.OutpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.PatientService;
import work.in.progress.hospitalmanagement.util.Mocks;

import javax.validation.Validator;

import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                AdmissionManagementViewController.class,
                InpatientAdmissionService.class,
                OutpatientAdmissionService.class,
                PatientService.class,
                DepartmentService.class,
                BedService.class,
                DepartmentStringConverter.class,
                BedStringConverter.class,
                DialogFactory.class
        }
)
public class AdmissionManagementViewControllerTest implements ApplicationContextAware {

    private ConfigurableApplicationContext context;

    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();

    @MockBean
    private Validator validator;
    @MockBean
    private InpatientAdmissionRepository inpatientAdmissionRepository;
    @MockBean
    private OutpatientAdmissionRepository outpatientAdmissionRepository;
    @MockBean
    private PatientRepository patientRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    private AdmissionManagementViewController vc;
    @MockBean
    private BedRepository bedRepository;

    @Before
    public void setUp() throws Exception {
        ApplicationContextSingleton.setContext(null);
        ApplicationContextSingleton.setContext(context);
        vc = (AdmissionManagementViewController) AbstractViewController
                .instantiateViewController(AdmissionManagementViewController.class);
    }

    @After
    public void tearDown() throws Exception {
        ApplicationContextSingleton.setContext(null);
    }

    @Test
    public void initializeTest() {
        assertNotNull(vc);
    }

    @Test
    public void handleCreateTest() {
        ListCellEvent<Patient> listCellEvent = new ListCellEvent<>(ListCellEvent.NEW_EVENT, Mocks.patient());
        StackPane stackPane =  ReflectionTestUtils.invokeMethod(vc, "getRoot");
        int childrenBefore = stackPane.getChildren().size();
        ReflectionTestUtils.invokeMethod(vc, "handleCreate", listCellEvent);
        int childrenAfter = stackPane.getChildren().size();
        assertTrue(childrenAfter > childrenBefore);
    }

    @Test
    public void handleDeleteTest() {
        ListCellEvent<Patient> listCellEvent = new ListCellEvent<>(ListCellEvent.DELETE_EVENT, Mocks.patient());
        StackPane stackPane =  ReflectionTestUtils.invokeMethod(vc, "getRoot");
        int childrenBefore = stackPane.getChildren().size();
        ReflectionTestUtils.invokeMethod(vc, "handleDelete", listCellEvent);
        int childrenAfter = stackPane.getChildren().size();
        assertTrue(childrenAfter > childrenBefore);
    }

    @Test
    public void handleEditTest() {
        Patient patient = Mocks.patient();
        patient.setAdmission(Mocks.outpatientAdmission());
        ListCellEvent<Patient> listCellEvent = new ListCellEvent<>(ListCellEvent.EDIT_EVENT, patient);
        StackPane stackPane =  ReflectionTestUtils.invokeMethod(vc, "getRoot");
        int childrenBefore = stackPane.getChildren().size();
        ReflectionTestUtils.invokeMethod(vc, "handleEdit", listCellEvent);
        int childrenAfter = stackPane.getChildren().size();
        assertTrue(childrenAfter > childrenBefore);
    }

    @Test
    public void clearSearchFieldsTest() {
        JFXTextField nameField  = (JFXTextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameSearchField");
        JFXTextField surnameField = (JFXTextField) ReflectionTestUtils.getField(vc, vc.getClass(), "surnameSearchField");
        JFXDatePicker datePicker = (JFXDatePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "birthDateSearchPicker");
        JFXComboBox<Department> departmentField = (JFXComboBox<Department>) ReflectionTestUtils.getField(vc, vc.getClass(), "departmentSearchField");
        ReflectionTestUtils.invokeMethod(vc, "clearSearchFields", new ActionEvent());
        assertEquals("", nameField.getText());
        assertEquals("", surnameField.getText());
        assertNull(datePicker.getValue());
        assertNull(departmentField.getSelectionModel().getSelectedItem());
    }

    @Test
    public void composePatientPredicateTest_nullFields() {
        Predicate<Patient> predicate = ReflectionTestUtils.invokeMethod(
                vc, "composePatientPredicate", null, null, null, null);
        assertNotNull(predicate);
        assertTrue(predicate.test(Mocks.patient()));
    }

    @Test
    public void composePatientPredicate() {
        Predicate<Patient> predicate = ReflectionTestUtils.invokeMethod(
                vc, "composePatientPredicate",
                Mocks.patient().getName(),
                Mocks.patient().getSurname(),
                Mocks.patient().getBirthDate(), null);
        assertNotNull(predicate);
        assertTrue(predicate.test(Mocks.patient()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;
    }
}