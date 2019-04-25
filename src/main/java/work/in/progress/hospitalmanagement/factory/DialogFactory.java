package work.in.progress.hospitalmanagement.factory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DialogFactory {

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
                                     StackPane root) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(header));
        JFXTextField jfxTextField = new JFXTextField();
        jfxTextField.setPromptText(prompt);
        content.setBody(jfxTextField);
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        JFXButton yesButton = ButtonFactory.getDefaultFactory().defaultButton("YES");
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
}
