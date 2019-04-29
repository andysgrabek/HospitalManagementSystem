package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
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
import work.in.progress.hospitalmanagement.model.Admission;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.repository.DepartmentRepository;
import work.in.progress.hospitalmanagement.repository.OutpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.service.OutpatientAdmissionService;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.util.function.Predicate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                PatientsWaitingViewController.class,
                OutpatientAdmissionService.class,
                DepartmentStringConverter.class,
                DepartmentService.class
        }
)
public class PatientsWaitingViewControllerTest implements ApplicationContextAware {

    @MockBean
    private OutpatientAdmissionRepository outpatientAdmissionRepository;
    @MockBean
    private DepartmentRepository departmentRepository;

    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();
    private PatientsWaitingViewController vc;
    private ConfigurableApplicationContext context;

    @Before
    public void setUp() throws Exception {
        ApplicationContextSingleton.setContext(null);
        ApplicationContextSingleton.setContext(context);
        vc = (PatientsWaitingViewController) AbstractViewController.instantiateViewController(PatientsWaitingViewController.class);
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
    public void composeAdmissionPredicateTest_null() {
        Predicate<Admission> predicate = ReflectionTestUtils.invokeMethod(vc, "composeAdmissionPredicate");
        assertTrue(predicate.test(Mocks.outpatientAdmission()));
    }

    @Test
    public void composeAdmissionPredicateTest() {
        JFXDatePicker appointmentDatePicker = (JFXDatePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "appointmentDatePicker");
        JFXTimePicker appointmentTimePicker = (JFXTimePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "appointmentTimePicker");
        JFXComboBox<Department> departmentField = (JFXComboBox<Department>) ReflectionTestUtils.getField(vc, vc.getClass(), "departmentField");
        OutpatientAdmission outpatientAdmission = Mocks.outpatientAdmission();
        appointmentDatePicker.setValue(outpatientAdmission.getVisitDate().toLocalDate());
        appointmentTimePicker.setValue(outpatientAdmission.getVisitDate().toLocalTime());
        departmentField.setItems(FXCollections.observableArrayList(outpatientAdmission.getDepartment()));
        departmentField.getSelectionModel().select(outpatientAdmission.getDepartment());
        Predicate<Admission> predicate = ReflectionTestUtils.invokeMethod(vc, "composeAdmissionPredicate");
        assertTrue(predicate.test(outpatientAdmission));
    }

    @Test
    public void clearSearchFieldsTest() {
        JFXDatePicker appointmentDatePicker = (JFXDatePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "appointmentDatePicker");
        JFXTimePicker appointmentTimePicker = (JFXTimePicker) ReflectionTestUtils.getField(vc, vc.getClass(), "appointmentTimePicker");
        JFXComboBox<Department> departmentField = (JFXComboBox<Department>) ReflectionTestUtils.getField(vc, vc.getClass(), "departmentField");
        ReflectionTestUtils.invokeMethod(vc, "clearSearchFields", new ActionEvent());
        assertNull(appointmentDatePicker.getValue());
        assertNull(appointmentTimePicker.getValue());
        assertNull(departmentField.getSelectionModel().getSelectedItem());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;
    }
}