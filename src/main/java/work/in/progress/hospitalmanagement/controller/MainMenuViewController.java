package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main menu of the application.
 * @author Andrzej Grabowski
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class MainMenuViewController extends AbstractViewController {

    private static final int INFO_DIALOG_BUTTON_PREF_WIDTH = 110;
    private static final double INFO_DIALOG_LINE_SPACING = 2.0;
    private static final int INFO_DIALOG_LOGO_SIZE = 75;
    private static final double INFO_DIALOG_HBOX_SPACING = 20.0;
    private static final String INFO_DIALOG_BODY_TEXT =
            "Hospital Management System by WorkInProgress team developed as part of 02160\n"
                    + "Agile Object-oriented Software Development course at Technical University of Denmark.\n"
                    + "Assets used with permission from pngtree.com/free-icon excluding application logo.";
    @FXML
    private StackPane stackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void openPatientRegistration(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(PatientRegistrationViewController.class), true);
    }

    @FXML
    private void showInfo(ActionEvent actionEvent) {
        createInfoDialog().show();
    }

    private JFXDialog createInfoDialog() {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        Text headingText = new Text("Software Information");
        Text bodyText = new Text(INFO_DIALOG_BODY_TEXT);
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
        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton closeButton = createDialogActionButton("Close", event -> jfxDialog.close());
        stackPane.setOnMouseClicked(event -> { });
        jfxDialogLayout.setActions(closeButton);
        return jfxDialog;
    }

    private JFXButton createDialogActionButton(String text, EventHandler<ActionEvent> buttonHandler) {
        JFXButton button = new JFXButton(text);
        button.setPrefWidth(INFO_DIALOG_BUTTON_PREF_WIDTH);
        button.getStyleClass().add("hms-button");
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setOnAction(buttonHandler);
        button.setFocusTraversable(false);
        return button;
    }

    private JFXDialog createExitDialog() {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        Text headingText = new Text("Are you sure you want to quit?");
        jfxDialogLayout.setHeading(headingText);
        jfxDialogLayout.getStyleClass().add("hms-text");
        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton yesButton = createDialogActionButton("Yes", event -> Platform.exit());
        JFXButton noButton = createDialogActionButton("No", event -> jfxDialog.close());
        stackPane.setOnMouseClicked(event -> { });
        jfxDialogLayout.setActions(yesButton, noButton);
        return jfxDialog;
    }

    @FXML
    private void exit(ActionEvent actionEvent) {
        createExitDialog().show();
    }
}
