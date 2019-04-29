package work.in.progress.hospitalmanagement.factory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
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
import work.in.progress.hospitalmanagement.converter.RoleStringConverter;
import work.in.progress.hospitalmanagement.model.Admission;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.HospitalStaff;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.model.Patient;
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
import work.in.progress.hospitalmanagement.validator.TextFieldValidator;

import javax.validation.Validator;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        DialogFactory.class,
        BedService.class,
        DepartmentService.class,
//        InpatientAdmissionService.class,
//        OutpatientAdmissionService.class,
        PatientService.class,
        HospitalStaffService.class,
        DepartmentStringConverter.class,
        BedStringConverter.class
})
public class DialogFactoryTest implements ApplicationContextAware {

    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();
    @MockBean
    private BedService bedService;
    @MockBean
    private DepartmentService departmentService;
    @MockBean
    private InpatientAdmissionService inpatientAdmissionService;
    @MockBean
    private OutpatientAdmissionService outpatientAdmissionService;
    @MockBean
    private Validator validator;
    @MockBean
    private BedRepository bedRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private InpatientAdmissionRepository inpatientAdmissionRepository;
    @MockBean
    private OutpatientAdmissionRepository outpatientAdmissionRepository;
    @MockBean
    private PatientRepository patientRepository;
    @MockBean
    private HospitalStaffRepository hospitalStaffRepository;
    private ApplicationContext context;

    @Before
    public void setUp() {
        ApplicationContextSingleton.setContext(null);
        ApplicationContextSingleton.setContext((ConfigurableApplicationContext) context);
    }

    @Test
    public void infoTextDialog() {
        assertNotNull(DialogFactory.getDefaultFactory().infoTextDialog("Test", "testbody", Event::consume,
                new StackPane()));
    }

    @Test
    public void comboBoxDialog() {
        assertNotNull(DialogFactory.getDefaultFactory().comboBoxDialog("Test",
                FXCollections.observableArrayList(HospitalStaff.Role.values()),
                new SimpleObjectProperty<>(), new RoleStringConverter(), Event::consume, new StackPane()));
    }

    @Test
    public void deletionDialog() {
        assertNotNull(DialogFactory.getDefaultFactory().deletionDialog("Test", "body",
                Event::consume, Event::consume, new StackPane()));
    }

    @Test
    public void admissionFormDialog() {
        assertNotNull(DialogFactory.getDefaultFactory().admissionFormDialog("Test", Mocks.patient(),
                new SimpleObjectProperty<>(), Event::consume, new StackPane()));
    }

    @Test
    public void textFieldDialog() {
        assertNotNull(DialogFactory.getDefaultFactory().textFieldDialog("Test", "prompt",
                new SimpleStringProperty(), Event::consume, new StackPane(), new TextFieldValidator(
                        Patient.class, "name", validator)));
    }

    @Test
    public void imageDialog() {
        assertNotNull(DialogFactory.getDefaultFactory().imageDialog("Test",
                new Image("images/icon_transparent.png"), Event::consume, new StackPane()));
    }

    @Test
    public void saveAdmissionTest_Inpatient() {
        JFXCheckBox checkBox = new JFXCheckBox();
        JFXComboBox<Department> departmentJFXComboBox
                = new JFXComboBox<>();
        Department department = Mocks.department();
        departmentJFXComboBox.setItems(FXCollections.observableArrayList(department));
        departmentJFXComboBox.getSelectionModel().select(department);
        JFXComboBox<Bed> bedJFXComboBox = new JFXComboBox<>();
        Bed bed = Mocks.bed();
        bedJFXComboBox.setItems(FXCollections.observableArrayList(bed));
        bedJFXComboBox.getSelectionModel().select(bed);
        JFXDatePicker datePicker = new JFXDatePicker();
        datePicker.setValue(Mocks.outpatientAdmission().getVisitDate().toLocalDate().plusDays(1));
        JFXTimePicker timePicker = new JFXTimePicker();
        timePicker.setValue(Mocks.outpatientAdmission().getVisitDate().toLocalTime());
        checkBox.setSelected(true);
        JFXDialog jfxDialog = new JFXDialog(new StackPane(), new JFXDialogLayout(), JFXDialog.DialogTransition.CENTER);
        jfxDialog.show();
        Patient p = Mocks.patient();
        DialogFactory.AdmissionDialogFormElements admissionDialogFormElements
                = new DialogFactory.AdmissionDialogFormElements(
                        checkBox,
                        departmentJFXComboBox,
                bedJFXComboBox,
                new JFXButton(),
                jfxDialog,
                datePicker,
                timePicker);
        SimpleObjectProperty<Admission> admissionSimpleObjectProperty = new SimpleObjectProperty<>();
        InpatientAdmission inpatientAdmission
                = new InpatientAdmission(p, admissionDialogFormElements
                                            .getBedComboBox()
                                            .getSelectionModel()
                                            .getSelectedItem());
        Mockito.when(inpatientAdmissionService.save(Mockito.any(InpatientAdmission.class)))
                .thenReturn(inpatientAdmission);
        ReflectionTestUtils.invokeMethod(context.getBean(DialogFactory.class),
                "saveAdmissionForm",
                p, admissionSimpleObjectProperty,
                (EventHandler) event -> { },
                admissionDialogFormElements,
                new ActionEvent());
        Mockito.verify(inpatientAdmissionService, Mockito.times(1))
                .save(Mockito.any(InpatientAdmission.class));
        assertEquals(admissionSimpleObjectProperty.getValue().getPatient().getName(), p.getName());
    }

    @Test
    public void saveAdmissionTest_Outpatient() {
        JFXCheckBox checkBox = new JFXCheckBox();
        JFXComboBox<Department> departmentJFXComboBox
                = new JFXComboBox<>();
        Department department = Mocks.department();
        departmentJFXComboBox.setItems(FXCollections.observableArrayList(department));
        departmentJFXComboBox.getSelectionModel().select(department);
        JFXComboBox<Bed> bedJFXComboBox = new JFXComboBox<>();
        Bed bed = Mocks.bed();
        bedJFXComboBox.setItems(FXCollections.observableArrayList(bed));
        bedJFXComboBox.getSelectionModel().select(bed);
        JFXDatePicker datePicker = new JFXDatePicker();
        datePicker.setValue(Mocks.outpatientAdmission().getVisitDate().toLocalDate().plusDays(1));
        JFXTimePicker timePicker = new JFXTimePicker();
        timePicker.setValue(Mocks.outpatientAdmission().getVisitDate().toLocalTime());
        JFXDialog jfxDialog = new JFXDialog(new StackPane(), new JFXDialogLayout(), JFXDialog.DialogTransition.CENTER);
        checkBox.setSelected(false);
        jfxDialog.show();
        Patient p = Mocks.patient();
        DialogFactory.AdmissionDialogFormElements admissionDialogFormElements
                = new DialogFactory.AdmissionDialogFormElements(
                checkBox,
                departmentJFXComboBox,
                bedJFXComboBox,
                new JFXButton(),
                jfxDialog,
                datePicker,
                timePicker);
        SimpleObjectProperty<Admission> admissionSimpleObjectProperty = new SimpleObjectProperty<>();
        OutpatientAdmission outpatientAdmission
                = new OutpatientAdmission(p,
                admissionDialogFormElements
                        .getDepartmentComboBox()
                        .getSelectionModel()
                        .getSelectedItem(),
                LocalDateTime.of(admissionDialogFormElements.getDatePicker().getValue(),
                        admissionDialogFormElements.getTimePicker().getValue()));
        Mockito.when(outpatientAdmissionService.save(Mockito.any(OutpatientAdmission.class)))
                .thenReturn(outpatientAdmission);
        ReflectionTestUtils.invokeMethod(context.getBean(DialogFactory.class),
                "saveAdmissionForm",
                p, admissionSimpleObjectProperty,
                (EventHandler) event -> { },
                admissionDialogFormElements,
                new ActionEvent());
        Mockito.verify(outpatientAdmissionService, Mockito.times(1))
                .save(Mockito.any(OutpatientAdmission.class));
        assertEquals(admissionSimpleObjectProperty.getValue().getPatient().getName(), p.getName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}