package work.in.progress.hospitalmanagement.factory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.in.progress.hospitalmanagement.validator.TextFieldValidator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DialogFactory {

    private static final Paint PAINT = Paint.valueOf("#f0ab8d");
    private static final double IMAGE_DIALOG_IMAGE_HEIGHT = 0.8;
    @Getter
    private static DialogFactory defaultFactory = new DialogFactory();

    public JFXDialog deletionDialog(String header,
                                    String bodyText,
                                    EventHandler<ActionEvent> onConfirm,
                                    EventHandler<ActionEvent> onCancel,
                                    StackPane root) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(header));
        content.setBody(new Text(bodyText));
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        JFXButton yesButton = ButtonFactory.getDefaultFactory().defaultButton("YES");
        JFXButton noButton = ButtonFactory.getDefaultFactory().defaultButton("NO");
        content.getStyleClass().add("hms-text");
        yesButton.setOnAction(event -> {
            onConfirm.handle(event);
            dialog.close();
        });
        noButton.setOnAction(event -> {
            onCancel.handle(event);
            dialog.close();
        });
        content.setActions(yesButton, noButton);
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
        imageView.setFitHeight(IMAGE_DIALOG_IMAGE_HEIGHT * root.getHeight());
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
