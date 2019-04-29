package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.report.ReportGenerator;
import work.in.progress.hospitalmanagement.repository.BedRepository;
import work.in.progress.hospitalmanagement.repository.DepartmentRepository;
import work.in.progress.hospitalmanagement.repository.HospitalStaffRepository;
import work.in.progress.hospitalmanagement.repository.InpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.repository.OutpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.repository.PatientRepository;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.service.HospitalStaffService;
import work.in.progress.hospitalmanagement.service.InpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.OutpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.PatientService;
import work.in.progress.hospitalmanagement.util.Mocks;

import javax.validation.Validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        DepartmentManagementViewController.class,
        BedService.class,
        BedStringConverter.class,
        DepartmentStringConverter.class,
        InpatientAdmissionService.class,
        OutpatientAdmissionService.class,
        DialogFactory.class,
        PatientService.class,
        HospitalStaffService.class
})
public class DepartmentManagementViewControllerTest implements ApplicationContextAware {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @MockBean
    private PatientRepository patientRepository;
    @MockBean
    private HospitalStaffRepository hospitalStaffRepository;
    @MockBean
    private Validator validator;
    @MockBean
    private BedRepository bedRepository;
    @MockBean
    private DepartmentService departmentService;
    @MockBean
    private BedService bedService;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private OutpatientAdmissionService outpatientAdmissionService;
    @MockBean
    private InpatientAdmissionService inpatientAdmissionService;
    @MockBean
    ReportGenerator<Patient> reportGenerator;
    @MockBean
    private OutpatientAdmissionRepository outpatientAdmissionRepository;
    @MockBean
    private InpatientAdmissionRepository inpatientAdmissionRepository;
    private ConfigurableApplicationContext context;
    private DepartmentManagementViewController vc;

    @Before
    public void setUp() {
        ApplicationContextSingleton.setContext(null);
        ApplicationContextSingleton.setContext(context);
        vc = (DepartmentManagementViewController) AbstractViewController
                .instantiateViewController(DepartmentManagementViewController.class);
    }

    @After
    public void tearDown() {
        ApplicationContextSingleton.setContext(null);
    }

    @Test
    public void createBedDeleteDialogTest() {
        Bed bed = Mocks.bed();
        bed.setAdmission(Mocks.inpatientAdmission());
        JFXDialog dialog = ReflectionTestUtils.invokeMethod(vc, "createBedDeleteDialog", bed);
        assertNotNull(dialog.getDialogContainer());
        assertNotNull(dialog);
    }

    @Test
    public void createBedDeleteDialogTest_noAdmissionOnBed() {
        Bed bed = Mocks.bed();
        JFXDialog dialog = ReflectionTestUtils.invokeMethod(vc, "createBedDeleteDialog", bed);
        assertNotNull(dialog.getDialogContainer());
        assertNotNull(dialog);
    }

    @Test
    public void createDepartmentTest() {
        JFXTextField nameField = (JFXTextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        nameField.setText("DepartamentTest");
        Department d = Mocks.department();
        Mockito.when(departmentService.save(Mockito.any(Department.class))).thenReturn(d);
        ReflectionTestUtils.invokeMethod(vc, "createDepartment", new ActionEvent());
        ObservableList<Department> departmentObservableList
                = (ObservableList<Department>) ReflectionTestUtils.getField(vc, vc.getClass(), "departmentObservableList");
        Department editedDepartment = (Department) ReflectionTestUtils.getField(vc, vc.getClass(), "editedDepartment");
        assertTrue(departmentObservableList.contains(d));
        assertEquals("", nameField.getText());
        assertNull(editedDepartment);
    }

    @Test
    public void cancelEditDepartmentTest() {
        ReflectionTestUtils.invokeMethod(vc, "updateDepartmentOnEditPressed",
                new ListCellEvent<>(ListCellEvent.EDIT_EVENT, Mocks.department()));
        ReflectionTestUtils.invokeMethod(vc, "cancelEditDepartment", new ActionEvent());
        Department editedDepartment = (Department) ReflectionTestUtils.getField(vc, vc.getClass(), "editedDepartment");
        JFXTextField nameField = (JFXTextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        JFXListView<Bed> bedsListView = (JFXListView<Bed>) ReflectionTestUtils.getField(vc, vc.getClass(), "bedsListView");
        HBox formButtonsParent = (HBox) ReflectionTestUtils.getField(vc, vc.getClass(), "formButtonsParent");
        Label label = (Label) ReflectionTestUtils.getField(vc, vc.getClass(), "formLabel");
        assertEquals("", nameField.getText());
        assertNull(editedDepartment);
        assertTrue(bedsListView.getItems().isEmpty());
        assertEquals(1, formButtonsParent.getChildren().size());
        assertEquals("CREATE NEW DEPARTMENT", label.getText());
    }

    @Test
    public void addBedTest() {
        StackPane stackPane =  ReflectionTestUtils.invokeMethod(vc, "getRoot");
        int childrenBefore = stackPane.getChildren().size();
        ReflectionTestUtils.invokeMethod(vc, "addBed", new ActionEvent());
        int childrenAfter = stackPane.getChildren().size();
        assertTrue(childrenAfter > childrenBefore);
    }

    @Test
    public void showReportErrorDialogTest() {
        StackPane stackPane =  ReflectionTestUtils.invokeMethod(vc, "getRoot");
        int childrenBefore = stackPane.getChildren().size();
        ReflectionTestUtils.invokeMethod(vc, "showReportErrorDialog", "test", "body");
        int childrenAfter = stackPane.getChildren().size();
        assertTrue(childrenAfter > childrenBefore);
    }

    @Test
    public void findPatientsForReport_noDepartments() {

    }

    @Test
    public void removeDepartmentOnDeleteTest() {
        Department d = Mocks.department();
        StackPane stackPane =  ReflectionTestUtils.invokeMethod(vc, "getRoot");
        ListCellEvent<Department> event = new ListCellEvent<>(ListCellEvent.DELETE_EVENT, d);
        int childrenBefore = stackPane.getChildren().size();
        ReflectionTestUtils.invokeMethod(vc, "removeDepartmentOnDelete", event);
        int childrenAfter = stackPane.getChildren().size();
        assertTrue(childrenAfter > childrenBefore);
    }

    @Test
    public void updateDepartmentOnEditPressedTest() {
        Department d = Mocks.department();
        ListCellEvent<Department> event = new ListCellEvent<>(ListCellEvent.EDIT_EVENT, d);
        ReflectionTestUtils.invokeMethod(vc, "updateDepartmentOnEditPressed", event);
        assertEquals(d.getName(), ((JFXTextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField")).getText());
        assertEquals(d, ReflectionTestUtils.getField(vc, vc.getClass(), "editedDepartment"));
    }

    @Test
    public void updateBedOnEditTest() {
        ListCellEvent<Bed> event = new ListCellEvent<>(ListCellEvent.EDIT_EVENT, Mocks.bed());
        ReflectionTestUtils.invokeMethod(vc, "updateBedOnEdit", event);
    }

    @Test
    public void removeBedOnDeleteTest() {
        ListCellEvent<Bed> event = new ListCellEvent<>(ListCellEvent.DELETE_EVENT, Mocks.bed());
        ReflectionTestUtils.invokeMethod(vc, "removeBedOnDelete", event);
    }

    @Test
    public void initializeTest() {
        assertNotNull(vc);
    }

    @Test
    public void initFormValidationTest() {
        JFXTextField textField = (JFXTextField) ReflectionTestUtils.getField(vc, vc.getClass(), "nameField");
        assertFalse(textField.getValidators().isEmpty());
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = (ConfigurableApplicationContext) applicationContext;
    }
}