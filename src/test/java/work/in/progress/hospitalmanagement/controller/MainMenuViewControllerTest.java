package work.in.progress.hospitalmanagement.controller;

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
import org.springframework.test.util.ReflectionTestUtils;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;
import work.in.progress.hospitalmanagement.converter.DepartmentStringConverter;
import work.in.progress.hospitalmanagement.factory.DialogFactory;
import work.in.progress.hospitalmanagement.repository.PatientRepository;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.service.HospitalStaffService;
import work.in.progress.hospitalmanagement.service.InpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.OutpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.PatientService;

import javax.validation.Validator;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                PatientService.class,
                BedService.class,
                InpatientAdmissionService.class,
                OutpatientAdmissionService.class,
                DepartmentService.class,
                HospitalStaffService.class,
                MainMenuViewController.class,
                PatientRegistrationViewController.class,
                AdmissionManagementViewController.class,
                DepartmentManagementViewController.class,
                PatientsWaitingViewController.class,
                AdvancedSearchViewController.class,
                StaffManagementViewController.class,
                DepartmentStringConverter.class,
                DialogFactory.class
        }
)
public class MainMenuViewControllerTest implements ApplicationContextAware {

    @MockBean
    private BedService bedService;

    @MockBean
    private InpatientAdmissionService inpatientAdmissionService;

    @MockBean
    private OutpatientAdmissionService outpatientAdmissionService;

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
    public void showInfoTest() {
        ReflectionTestUtils.invokeMethod(vc, "showInfo", new ActionEvent());
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = (ConfigurableApplicationContext) applicationContext;
    }
}