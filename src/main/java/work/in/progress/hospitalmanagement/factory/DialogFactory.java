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
import work.in.progress.hospitalmanagement.validator.ComboBoxValidator;
import work.in.progress.hospitalmanagement.validator.TextFieldValidator;
import work.in.progress.hospitalmanagement.validator.VisitDateTimeValidator;

import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Factory for creation of {@link JFXDialog}s presented to the user on various occasions like confirming
 * the deletion of an object from the database.
 * @author Andrzej Grabowski
 */
@Component
public final class DialogFactory {

    private final BedService bedService;
    private final DepartmentService departmentService;
    private final InpatientAdmissionService inpatientAdmissionService;
    private final OutpatientAdmissionService outpatientAdmissionService;
    private final Validator validator;

    /**
     * Helper class representing elements of the admission dialog.
     * @author Andrzej Grabowski
     */
    @AllArgsConstructor
    static class AdmissionDialogFormElements {
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
                         Validator validator) {
        this.bedService = bedService;
        this.departmentService = departmentService;
        this.inpatientAdmissionService = inpatientAdmissionService;
        this.outpatientAdmissionService = outpatientAdmissionService;
        this.validator = validator;
    }

    private static final Paint PAINT = Paint.valueOf("#f0ab8d");
    private static final double VBOX_SPACING = 20.0;
    private static final double ADMISSION_PREF_WIDTH = 400.0;
    private static final double MAX_HEIGHT_RATIO = 0.8;
    private static final double INFO_DIALOG_LINE_SPACING = 2.0;
    private static final int INFO_DIALOG_LOGO_SIZE = 75;
    private static final double INFO_DIALOG_HBOX_SPACING = 20.0;

    /**
     * Method retrieving the default factory
     * @return default {@link JFXDialog} factory
     */
    public static DialogFactory getDefaultFactory() {
        return ApplicationContextSingleton.getContext().getBean(DialogFactory.class);
    }

    /**
     * Creates an informative text {@link JFXDialog}
     * @param header header to de displayed
     * @param body body text to be displayed
     * @param onConfirm handler to be run upon closing the dialog with its button
     * @param root the owner object of the dialog
     * @return the newly created dialog
     */
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

    /**
     * Creates a parametrized dialog with a {@link javafx.scene.control.ComboBox}
     * @param header header text to be displayed
     * @param values list of values to be available in the {@link javafx.scene.control.ComboBox}
     * @param property a property object through which the selected value is returned
     * @param converter the {@link StringConverter} to be used for the type of values controlled by the dialog
     * @param onConfirm handler to be run upon closing the dialog with its button
     * @param root the owner of the dialog
     * @param <T> type of the objects controlled by the {@link javafx.scene.control.ComboBox}
     * @return the newly created dialog
     */
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

    /**
     * Creates a deletion dialog prompting the user during critical times in application operation.
     * @param header header text to be displayed
     * @param bodyText body text to be displayed
     * @param onConfirm handler to be run when user confirms the action
     * @param onCancel handler to be run when user cancels the action
     * @param root owner of the dialog
     * @return the newly created dialog
     */
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

    /**
     * Creates a dialog facilitating the creation of {@link Admission}
     * @param header header text to be displayed
     * @param patient the patient which is to be admitted
     * @param admissionProperty property object through which the newly created admission is returned
     * @param onComplete handler to be run when the user confirms the creation of the admission
     * @param root owner of the dialog
     * @return the newly created dialog
     */
    public JFXDialog admissionFormDialog(String header,
                                         Patient patient,
                                         Property<Admission> admissionProperty,
                                         EventHandler<ActionEvent> onComplete,
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
        setAdmissionFieldsValidators(departmentComboBox, bedComboBox, datePicker, timePicker);
        AdmissionDialogFormElements formElements = new AdmissionDialogFormElements(
                inpatientCheckbox, departmentComboBox, bedComboBox, confirmButton, dialog, datePicker, timePicker);
        setAdmissionDialogActions(patient, admissionProperty, onComplete, content, formElements);
        return dialog;
    }

    /**
     * Method to set up appointment time and picker controls for the dialog responsible for creating an
     * {@link OutpatientAdmission}
     * @param datePicker date picker control to be set up
     * @param timePicker time picker control to be set up
     */
    private void setUpAppointmentTimePickers(JFXDatePicker datePicker, JFXTimePicker timePicker) {
        datePicker.setVisible(false);
        timePicker.setVisible(false);
        datePicker.setPromptText("Appointment date");
        timePicker.setPromptText("Appointment time");
        datePicker.setDefaultColor(PAINT);
        timePicker.setDefaultColor(PAINT);
    }

    /**
     * Method to set up the combo boxes for the dialog responsible for creating an {@link Admission}
     * @param departmentComboBox combo box representing available {@link Department}s
     * @param bedComboBox combo box representing available {@link Bed}s in the selected department
     */
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

    /**
     * Method styling the children of the admission dialog form
     * @param vBox the vbox container of the child elements
     */
    private void styleAdmissionFormChildren(VBox vBox) {
        vBox.getChildren().forEach(child -> {
            child.getStyleClass().add("hms-form-text");
            ((Control) child).setPrefWidth(ADMISSION_PREF_WIDTH);
        });
    }

    /**
     * Method to set up the switching of admission dialog versions between {@link InpatientAdmission} and
     * {@link OutpatientAdmission} variants by clicking the available checkbox
     * @param inpatientCheckbox the checkbox on which the change is to be registered
     * @param bedComboBox bed combo box to be shown when the checkbox is selected
     * @param datePicker date picker to be shown when the checkbox is unselected
     * @param timePicker time picker to be shown when the checkbox is unselected
     */
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

    /**
     * Method to set up validation of the admission dialog form fields
     * @param departmentComboBox combo box containing available department values to be validated
     * @param bedComboBox combo box containing available bed values to be validated
     * @param datePicker visit date picker to be validated
     * @param timePicker visit time picker to be validated
     */
    private void setAdmissionFieldsValidators(JFXComboBox<Department> departmentComboBox,
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

    /**
     * Method to set up the actions of the admission dialog
     * @param patient the {@link Patient} for which the admission is to be made
     * @param admissionProperty the property through which the created {@link Admission} is returned
     * @param onComplete the handler to be run when the user confirms the creation of the {@link Admission}
     * @param content the content of the {@link JFXDialog} to be shown
     * @param admissionDialogFormElements the form elements of the dialog e.g. combo box with available departments
     */
    private void setAdmissionDialogActions(Patient patient,
                                           Property<Admission> admissionProperty,
                                           EventHandler<ActionEvent> onComplete,
                                           JFXDialogLayout content,
                                           AdmissionDialogFormElements admissionDialogFormElements) {
        admissionDialogFormElements.getConfirmButton().setOnAction(event -> {
            saveAdmissionForm(patient, admissionProperty, onComplete, admissionDialogFormElements, event);
        });
        content.setActions(admissionDialogFormElements.getConfirmButton());
    }

    private void saveAdmissionForm(Patient patient,
                                   Property<Admission> admissionProperty,
                                   EventHandler<ActionEvent> onComplete,
                                   AdmissionDialogFormElements admissionDialogFormElements,
                                   ActionEvent event) {
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
    }

    /**
     * Creates a {@link JFXDialog} with a {@link JFXTextField} input control.
     * @param header header text to be displayed
     * @param prompt prompt text to be dispalyed
     * @param stringProperty string property through which the provided text is returned
     * @param onConfirm the handler to be run when user confirms the input with a button click
     * @param root owner of the dialog
     * @param validator the validator to be used on the text field
     * @return the newly created dialog
     */
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

    /**
     * Creates a {@link JFXDialog} that simply displays an image to the user
     * @param header header text to be displayed
     * @param image image to be displayed
     * @param onClose handler to be run when the user closes the dialog with a button click
     * @param root owner of the dialog
     * @return the newly created dialog
     */
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
