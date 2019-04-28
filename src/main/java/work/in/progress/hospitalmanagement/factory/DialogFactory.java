package work.in.progress.hospitalmanagement.factory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;
import work.in.progress.hospitalmanagement.converter.BedStringConverter;
import work.in.progress.hospitalmanagement.converter.DepartmentStringConverter;
import work.in.progress.hospitalmanagement.model.Admission;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.service.InpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.OutpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.PatientService;
import work.in.progress.hospitalmanagement.validator.ComboBoxValidator;
import work.in.progress.hospitalmanagement.validator.TextFieldValidator;
import work.in.progress.hospitalmanagement.validator.VisitDateTimeValidator;

import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.List;

@Component
public final class DialogFactory {

    private final BedService bedService;
    private final DepartmentService departmentService;
    private final InpatientAdmissionService inpatientAdmissionService;
    private final OutpatientAdmissionService outpatientAdmissionService;

    @AllArgsConstructor
    private class AdmissionDialogFormElements {
        @Getter
        private JFXCheckBox inpatientCheckbox;
        @Getter
        private JFXComboBox<Department> departmentComboBox;
        @Getter
        private JFXComboBox<Bed> bedComboBox;
        @Getter
        private JFXButton confirmButton;
        @Getter
        private JFXDialog dialog;
        @Getter
        private JFXDatePicker datePicker;
        @Getter
        private JFXTimePicker timePicker;
    };

    @Autowired
    public DialogFactory(BedService bedService,
                         DepartmentService departmentService,
                         InpatientAdmissionService inpatientAdmissionService,
                         OutpatientAdmissionService outpatientAdmissionService,
                         PatientService patientService) {
        this.bedService = bedService;
        this.departmentService = departmentService;
        this.inpatientAdmissionService = inpatientAdmissionService;
        this.outpatientAdmissionService = outpatientAdmissionService;
    }

    private static final Paint PAINT = Paint.valueOf("#f0ab8d");
    private static final double VBOX_SPACING = 20.0;
    private static final double ADMISSION_PREF_WIDTH = 400.0;
    private static final double MAX_HEIGHT_RATIO = 0.8;
    private static final double INFO_DIALOG_LINE_SPACING = 2.0;
    private static final int INFO_DIALOG_LOGO_SIZE = 75;
    private static final double INFO_DIALOG_HBOX_SPACING = 20.0;

    public static DialogFactory getDefaultFactory() {
        return ApplicationContextSingleton.getContext().getBean(DialogFactory.class);
    }

    public JFXDialog infoTextDialog(String header, String body, EventHandler<ActionEvent> onConfirm, StackPane root) {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        Text headingText = new Text(header);
        Text bodyText = new Text(body);
        bodyText.setLineSpacing(INFO_DIALOG_LINE_SPACING);
        jfxDialogLayout.setHeading(headingText);
        HBox hBox = new HBox();
        hBox.setSpacing(INFO_DIALOG_HBOX_SPACING);
        hBox.setAlignment(Pos.CENTER);
        ImageView logo = new ImageView(new Image("/images/icon_transparent.png"));
        logo.setFitWidth(INFO_DIALOG_LOGO_SIZE);
        logo.setPreserveRatio(true);
        hBox.getChildren().add(logo);
        hBox.getChildren().add(bodyText);
        jfxDialogLayout.setBody(hBox);
        jfxDialogLayout.getStyleClass().add("hms-text");
        JFXDialog jfxDialog = new JFXDialog(root, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton closeButton = ButtonFactory.getDefaultFactory().defaultButton("Close");
        closeButton.setOnAction(event -> {
            jfxDialog.close();
            onConfirm.handle(event);
        });
        jfxDialogLayout.setActions(closeButton);
        return jfxDialog;
    }

    public <T> JFXDialog comboBoxDialog(String header,
                                        ObservableList<T> values,
                                        Property<T> property,
                                        StringConverter<T> converter,
                                        EventHandler<ActionEvent> onConfirm,
                                        StackPane root) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(header));
        JFXComboBox<T> comboBox = new JFXComboBox<>();
        comboBox.setFocusColor(PAINT);
        content.setBody(comboBox);
        comboBox.setItems(values);
        comboBox.setConverter(converter);
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        JFXButton confirmButton = ButtonFactory.getDefaultFactory().defaultButton("Confirm");
        content.getStyleClass().add("hms-form-text");
        confirmButton.setOnAction(event -> {
            property.setValue(comboBox.getSelectionModel().getSelectedItem());
            dialog.close();
            onConfirm.handle(event);
        });
        content.setActions(confirmButton);
        return dialog;
    }

    public JFXDialog deletionDialog(String header,
                                    String bodyText,
                                    EventHandler<ActionEvent> onConfirm,
                                    EventHandler<ActionEvent> onCancel,
                                    StackPane root) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(header));
        content.setBody(new Text(bodyText));
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        JFXButton yesButton = ButtonFactory.getDefaultFactory().defaultButton("Yes");
        JFXButton noButton = ButtonFactory.getDefaultFactory().defaultButton("No");
        content.getStyleClass().add("hms-text");
        yesButton.setOnAction(event -> {
            dialog.close();
            onConfirm.handle(event);
        });
        noButton.setOnAction(event -> {
            dialog.close();
            onCancel.handle(event);
        });
        content.setActions(yesButton, noButton);
        return dialog;
    }

    public JFXDialog admissionFormDialog(String header,
                                         Patient patient,
                                         Property<Admission> admissionProperty,
                                         EventHandler<ActionEvent> onComplete,
                                         Validator validator,
                                         StackPane root) {
        JFXDialogLayout content = new JFXDialogLayout();
        List<Department> departmentList = departmentService.findAll();
        content.setHeading(new Text(header));
        VBox vBox = new VBox();
        vBox.setMaxHeight(MAX_HEIGHT_RATIO * root.getHeight());
        JFXCheckBox inpatientCheckbox = new JFXCheckBox("Admit as inpatient?");
        inpatientCheckbox.setSelected(true);
        inpatientCheckbox.setCheckedColor(PAINT);
        JFXComboBox<Department> departmentComboBox =
                new JFXComboBox<>(FXCollections.observableArrayList(departmentList));
        JFXComboBox<Bed> bedComboBox = new JFXComboBox<>();
        JFXDatePicker datePicker = new JFXDatePicker();
        JFXTimePicker timePicker = new JFXTimePicker();
        setUpAdmissionFormComboBoxes(departmentComboBox, bedComboBox);
        setUpAppointmentTimePickers(datePicker, timePicker);
        vBox.setSpacing(VBOX_SPACING);
        setUpAdmissionFormSwitching(inpatientCheckbox, bedComboBox, datePicker, timePicker);
        JFXButton confirmButton = ButtonFactory.getDefaultFactory().defaultButton("Admit");
        content.setBody(vBox);
        bedComboBox.setPromptText("Bed");
        departmentComboBox.setPromptText("Department");
        vBox.getChildren().addAll(inpatientCheckbox, departmentComboBox, bedComboBox, datePicker, timePicker);
        styleAdmissionFormChildren(vBox);
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        setAdmissionFieldsValidators(validator, departmentComboBox, bedComboBox, datePicker, timePicker);
        AdmissionDialogFormElements formElements = new AdmissionDialogFormElements(
                inpatientCheckbox, departmentComboBox, bedComboBox, confirmButton, dialog, datePicker, timePicker);
        setAdmissionDialogActions(patient, admissionProperty, onComplete, content, formElements);
        return dialog;
    }

    private void setUpAppointmentTimePickers(JFXDatePicker datePicker, JFXTimePicker timePicker) {
        datePicker.setVisible(false);
        timePicker.setVisible(false);
        datePicker.setPromptText("Appointment date");
        timePicker.setPromptText("Appointment time");
        datePicker.setDefaultColor(PAINT);
        timePicker.setDefaultColor(PAINT);
    }

    private void setUpAdmissionFormComboBoxes(JFXComboBox<Department> departmentComboBox,
                                              JFXComboBox<Bed> bedComboBox) {
        departmentComboBox.setFocusColor(PAINT);
        departmentComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            bedComboBox.getSelectionModel().clearSelection();
            bedComboBox.setItems(FXCollections.observableArrayList(bedService.freeBeds(newValue)));
        });
        departmentComboBox
                .setConverter(ApplicationContextSingleton.getContext().getBean(DepartmentStringConverter.class));
        bedComboBox
                .setConverter(ApplicationContextSingleton.getContext().getBean(BedStringConverter.class));
        bedComboBox.setFocusColor(PAINT);
    }

    private void styleAdmissionFormChildren(VBox vBox) {
        vBox.getChildren().forEach(child -> {
            child.getStyleClass().add("hms-form-text");
            ((Control) child).setPrefWidth(ADMISSION_PREF_WIDTH);
        });
    }

    private void setUpAdmissionFormSwitching(JFXCheckBox inpatientCheckbox,
                                             JFXComboBox<Bed> bedComboBox,
                                             JFXDatePicker datePicker,
                                             JFXTimePicker timePicker) {
        inpatientCheckbox.selectedProperty().addListener((obs, o, newValue) -> {
            bedComboBox.setVisible(newValue);
            datePicker.setVisible(!newValue);
            timePicker.setVisible(!newValue);
        });
    }

    private void setAdmissionFieldsValidators(Validator validator,
                                              JFXComboBox<Department> departmentComboBox,
                                              JFXComboBox<Bed> bedComboBox,
                                              JFXDatePicker datePicker,
                                              JFXTimePicker timePicker) {
        VisitDateTimeValidator visitDateTimeValidator = new VisitDateTimeValidator(
                OutpatientAdmission.class,
                "visitDate",
                validator,
                datePicker, timePicker);
        datePicker.getValidators().add(visitDateTimeValidator);
        timePicker.getValidators().add(visitDateTimeValidator);
        bedComboBox.getValidators().add(
                new ComboBoxValidator(InpatientAdmission.class, "bed", validator));
        departmentComboBox.getValidators().add(
                new ComboBoxValidator(OutpatientAdmission.class, "department", validator));
    }

    private void setAdmissionDialogActions(Patient patient,
                                           Property<Admission> admissionProperty,
                                           EventHandler<ActionEvent> onComplete,
                                           JFXDialogLayout content,
                                           AdmissionDialogFormElements admissionDialogFormElements) {
        admissionDialogFormElements.getConfirmButton().setOnAction(event -> {
            admissionDialogFormElements.getBedComboBox().resetValidation();
            admissionDialogFormElements.getDepartmentComboBox().resetValidation();
            admissionDialogFormElements.getDatePicker().resetValidation();
            admissionDialogFormElements.getTimePicker().resetValidation();
            if (admissionDialogFormElements.getInpatientCheckbox().isSelected()) {
                if (admissionDialogFormElements.getBedComboBox().validate()) {
                    admissionProperty.setValue(
                            inpatientAdmissionService.save(
                                    new InpatientAdmission(patient, admissionDialogFormElements
                                                    .getBedComboBox()
                                                    .getSelectionModel()
                                                    .getSelectedItem())));
                    onComplete.handle(event);
                    admissionDialogFormElements.dialog.close();
                }
            } else {
                if (admissionDialogFormElements.getDepartmentComboBox().validate()
                        & admissionDialogFormElements.getDatePicker().validate()
                        & admissionDialogFormElements.getDatePicker().validate()) {
                    admissionProperty.setValue(outpatientAdmissionService.save(
                            new OutpatientAdmission(patient,
                                    admissionDialogFormElements
                                            .getDepartmentComboBox()
                                            .getSelectionModel()
                                            .getSelectedItem(),
                                    LocalDateTime.of(admissionDialogFormElements.getDatePicker().getValue(),
                                            admissionDialogFormElements.getTimePicker().getValue()))));
                    onComplete.handle(event);
                    admissionDialogFormElements.getDialog().close();
                }
            }
        });
        content.setActions(admissionDialogFormElements.getConfirmButton());
    }

    public JFXDialog textFieldDialog(String header,
                                     String prompt,
                                     StringProperty stringProperty,
                                     EventHandler<ActionEvent> onConfirm,
                                     StackPane root,
                                     TextFieldValidator validator) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(header));
        JFXTextField jfxTextField = new JFXTextField();
        jfxTextField.getValidators().add(validator);
        jfxTextField.setFocusColor(PAINT);
        jfxTextField.setPromptText(prompt);
        content.setBody(jfxTextField);
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        JFXButton yesButton = ButtonFactory.getDefaultFactory().defaultButton("Confirm");
        content.getStyleClass().add("hms-text");
        yesButton.setOnAction(event -> {
            if (jfxTextField.validate()) {
                stringProperty.setValue(jfxTextField.getText());
                onConfirm.handle(event);
                dialog.close();
            }
        });
        content.setActions(yesButton);
        return dialog;
    }

    public JFXDialog imageDialog(String header,
                                 Image image,
                                 EventHandler<ActionEvent> onClose,
                                 StackPane root) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(header));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(MAX_HEIGHT_RATIO * root.getHeight());
        content.setBody(imageView);
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        JFXButton closeButton = ButtonFactory.getDefaultFactory().defaultButton("CLOSE");
        content.getStyleClass().add("hms-text");
        closeButton.setOnAction(event -> {
            onClose.handle(event);
            dialog.close();
        });
        content.setActions(closeButton);
        return dialog;
    }
}
