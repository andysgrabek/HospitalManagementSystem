package work.in.progress.hospitalmanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class AdmissionManagementViewController extends AbstractViewController {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void backToMainMenu(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(MainMenuViewController.class), true);
    }
}
