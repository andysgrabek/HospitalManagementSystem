package work.in.progress.hospitalmanagement.factory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.in.progress.hospitalmanagement.model.Admission;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.validator.ComboBoxValidator;
import work.in.progress.hospitalmanagement.validator.TextFieldValidator;

import javax.validation.Validator;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DialogFactory {

    private static final Paint PAINT = Paint.valueOf("#f0ab8d");
    private static final double VBOX_SPACING = 20.0;
    @Getter
    private static DialogFactory defaultFactory = new DialogFactory();
    private static final double ADMISSION_PREF_WIDTH = 400.0;
    private static final double MAX_HEIGHT_RATIO = 0.8;

    public <T> JFXDialog comboBoxDialog(String header,
                                        ObservableList<T> values,
                                        Property<T> property,
                                        EventHandler<ActionEvent> onConfirm,
                                        StackPane root) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(header));
        JFXComboBox<T> comboBox = new JFXComboBox<>();
        content.setBody(comboBox);
        comboBox.setItems(values);
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
                                         BedService bedService,
                                         List<Department> departmentList,
                                         EventHandler<ActionEvent> onComplete,
                                         Validator validator,
                                         StackPane root) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(header));
        VBox vBox = new VBox();
        vBox.setMaxHeight(MAX_HEIGHT_RATIO * root.getHeight());
        JFXCheckBox inpatientCheckbox = new JFXCheckBox("Admit as inpatient?");
        inpatientCheckbox.setSelected(true);
        inpatientCheckbox.setCheckedColor(PAINT);
        JFXComboBox<Department> departmentComboBox =
                new JFXComboBox<>(FXCollections.observableArrayList(departmentList));
        departmentComboBox.setFocusColor(PAINT);
        JFXComboBox<Bed> bedComboBox = new JFXComboBox<>();
        departmentComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            bedComboBox.getSelectionModel().clearSelection();
            bedComboBox.setItems(FXCollections.observableArrayList(bedService.freeBeds(newValue)));
        });
        bedComboBox.setFocusColor(PAINT);
        vBox.getChildren().addAll(inpatientCheckbox, departmentComboBox, bedComboBox);
        vBox.getChildren().forEach(child -> {
            child.getStyleClass().add("hms-form-text");
            ((Control) child).setPrefWidth(ADMISSION_PREF_WIDTH);
        });
        vBox.setSpacing(VBOX_SPACING);
        inpatientCheckbox.selectedProperty().addListener((obs, o, newValue) -> bedComboBox.setVisible(newValue));
        JFXButton confirmButton = ButtonFactory.getDefaultFactory().defaultButton("Admit");
        content.setBody(vBox);
        bedComboBox.setPromptText("Bed");
        departmentComboBox.setPromptText("Department");
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        bedComboBox.getValidators().add(
                new ComboBoxValidator(InpatientAdmission.class, "bed", validator));
        departmentComboBox.getValidators().add(
                new ComboBoxValidator(OutpatientAdmission.class, "department", validator));
        confirmButton.setOnAction(event -> {
            if (inpatientCheckbox.isSelected()) {
                if (bedComboBox.validate()) {
                    admissionProperty.setValue(
                            new InpatientAdmission(patient, bedComboBox.getSelectionModel().getSelectedItem()));
                    onComplete.handle(event);
                    dialog.close();
                }
            } else {
                if (departmentComboBox.validate()) {
                    admissionProperty.setValue(
                            new OutpatientAdmission(patient, departmentComboBox.getSelectionModel().getSelectedItem()));
                    onComplete.handle(event);
                    dialog.close();
                }
            }
        });
        content.setActions(confirmButton);
        return dialog;
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
        JFXButton yesButton = ButtonFactory.getDefaultFactory().defaultButton("CONFIRM");
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
