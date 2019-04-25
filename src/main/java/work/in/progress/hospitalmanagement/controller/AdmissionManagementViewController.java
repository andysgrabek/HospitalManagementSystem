package work.in.progress.hospitalmanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class AdmissionManagementViewController extends AbstractViewController {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void backToMainMenu(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(MainMenuViewController.class), true);
    }
}
