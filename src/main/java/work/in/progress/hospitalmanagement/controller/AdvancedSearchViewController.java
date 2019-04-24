package work.in.progress.hospitalmanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class AdvancedSearchViewController extends AbstractViewController {
    @FXML
    private TableView tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void backToMainMenu(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(MainMenuViewController.class), true);
    }
}
