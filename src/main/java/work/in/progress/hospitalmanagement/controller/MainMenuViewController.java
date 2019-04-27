package work.in.progress.hospitalmanagement.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.factory.DialogFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main menu of the application.
 * @author Andrzej Grabowski
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class MainMenuViewController extends AbstractViewController {

    private static final String INFO_DIALOG_BODY_TEXT =
            "Hospital Management System by WorkInProgress team developed as part of 02160\n"
                    + "Agile Object-oriented Software Development course at Technical University of Denmark.\n"
                    + "Assets used with permission from pngtree.com/free-icon excluding application logo.";
    private static final String SOFTWARE_INFORMATION = "Software Information";
    @FXML
    private StackPane stackPane;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void openPatientRegistration(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(PatientRegistrationViewController.class), true);
    }

    @FXML
    private void showInfo(ActionEvent actionEvent) {
        DialogFactory.getDefaultFactory().infoTextDialog(
                SOFTWARE_INFORMATION,
                INFO_DIALOG_BODY_TEXT,
                Event::consume,
                (StackPane) getRoot()
        ).show();
    }

    @FXML
    private void exit(ActionEvent actionEvent) {
        DialogFactory.getDefaultFactory().deletionDialog(
                "Are you sure you want to quit?",
                "",
                event -> Platform.exit(),
                Event::consume,
                (StackPane) getRoot()
        ).show();
    }

    @FXML
    private void openStaffManagement(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(StaffManagementViewController.class), true);
    }

    @FXML
    private void openDepartmentManagement(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(DepartmentManagementViewController.class), true);
    }

    @FXML
    private void openAdvancedSearch(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(AdvancedSearchViewController.class), true);
    }

    @FXML
    private void openAdmissionManagement(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(AdmissionManagementViewController.class), true);
    }

    @FXML
    private void openPatientsWaiting(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(PatientsWaitingViewController.class), true);
    }

}
