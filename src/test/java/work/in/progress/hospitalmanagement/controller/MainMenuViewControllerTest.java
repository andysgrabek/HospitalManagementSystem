package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.util.ReflectionTestUtils;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;
import work.in.progress.hospitalmanagement.repository.PatientRepository;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.service.HospitalStaffService;
import work.in.progress.hospitalmanagement.service.PatientService;

import javax.validation.Validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = {
                AbstractViewControllerTest.class,
                PatientRegistrationViewController.class,
                StaffManagementViewController.class,
                MainMenuViewController.class,
                PatientService.class,
                PatientRepository.class,
                DepartmentService.class,
                HospitalStaffService.class,
                Validator.class
        })
public class MainMenuViewControllerTest implements ApplicationContextAware {

    @MockBean
    private PatientService patientService;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private DepartmentService departmentService;

    @MockBean
    private HospitalStaffService hospitalStaffService;

    @MockBean
    private Validator validator;

    private ConfigurableApplicationContext context;
    private MainMenuViewController vc;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Before
    public void setUp() {
        ApplicationContextSingleton.setContext(null);
        ApplicationContextSingleton.setContext(context);
        vc = (MainMenuViewController) AbstractViewController.instantiateViewController(MainMenuViewController.class);
    }

    @After
    public void tearDown() {
        ApplicationContextSingleton.setContext(null);
        vc = null;
    }

    @Test
    public void createActionDialogButtonTest() {
        EventHandler<ActionEvent> eh = event -> {
            System.out.println("Action event fired");
        };
        JFXButton button = ReflectionTestUtils.invokeMethod(
                vc,
                "createDialogActionButton",
                "buttonText",
                eh);
        assertEquals("buttonText", button.getText());
        assertEquals(eh, button.getOnAction());
    }

    @Test
    public void createInfoDialogTest() {
        JFXDialog dialog = ReflectionTestUtils.invokeMethod(vc, "createInfoDialog");
        StackPane stackPane = (StackPane) ReflectionTestUtils.getField(vc, vc.getClass(), "stackPane");
        assertEquals(stackPane, dialog.getDialogContainer());
        assertNotNull(dialog);
    }

    @Test
    public void createExitDialogTest() {
        JFXDialog dialog = ReflectionTestUtils.invokeMethod(vc, "createExitDialog");
        StackPane stackPane = (StackPane) ReflectionTestUtils.getField(vc, vc.getClass(), "stackPane");
        assertEquals(stackPane, dialog.getDialogContainer());
        assertNotNull(dialog);
    }

    @Test
    public void openPatientRegistrationTest() {
        ReflectionTestUtils.invokeMethod(vc, "openPatientRegistration", new ActionEvent());
    }

    @Test
    public void openStaffManagementTest() {
        ReflectionTestUtils.invokeMethod(vc, "openStaffManagement", new ActionEvent());
    }

    @Test
    public void exitTest() {
        ReflectionTestUtils.invokeMethod(vc, "exit", new ActionEvent());
    }

    @Test
    public void showInfoTest() {
        ReflectionTestUtils.invokeMethod(vc, "showInfo", new ActionEvent());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = (ConfigurableApplicationContext) applicationContext;
    }
}