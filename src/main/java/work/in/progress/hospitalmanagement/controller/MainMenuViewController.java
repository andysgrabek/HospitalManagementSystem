package work.in.progress.hospitalmanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void openPatientRegistration(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(PatientRegistrationViewController.class), true);
    }
}
