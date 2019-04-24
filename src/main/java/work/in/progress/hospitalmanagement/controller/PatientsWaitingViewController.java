package work.in.progress.hospitalmanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientsWaitingViewController extends AbstractViewController {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void backToMainMenu(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(MainMenuViewController.class), true);
    }
}
