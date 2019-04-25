package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
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
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.repository.BedRepository;
import work.in.progress.hospitalmanagement.repository.DepartmentRepository;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.util.Mocks;

import javax.validation.Validator;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = { AbstractViewControllerTest.class,
                DepartmentManagementViewController.class,
                MainMenuViewController.class,
                DepartmentService.class,
                BedService.class,
                Validator.class})
public class DepartmentManagementViewControllerTest implements ApplicationContextAware {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

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
    public void createBedDeleteDialogTest_noAdmisstionOnBed() {
        Bed bed = Mocks.bed();
        JFXDialog dialog = ReflectionTestUtils.invokeMethod(vc, "createBedDeleteDialog", bed);
        assertNotNull(dialog.getDialogContainer());
        assertNotNull(dialog);
    }

    @Test
    public void removeDepartmentOnDeleteTest() {
        Department d = Mocks.department();
        ListCellEvent<Department> event = new ListCellEvent<>(ListCellEvent.DELETE_EVENT, d);
        ReflectionTestUtils.invokeMethod(vc, "removeDepartmentOnDelete", event);
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
    public void backToMainMenuTest() {
        ReflectionTestUtils.invokeMethod(AbstractViewController.instantiateViewController(vc.getClass()), "backToMainMenu", new ActionEvent());
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