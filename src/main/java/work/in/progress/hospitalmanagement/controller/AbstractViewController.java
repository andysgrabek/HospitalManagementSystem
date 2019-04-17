package work.in.progress.hospitalmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Abstract class to be used as a base class for all view controllers. A root object must be given the id "root" in the
 * corresponding fxml file.
 *
 * @author Andrzej Grabowski
 */
public abstract class AbstractViewController implements Initializable {

    @FXML
    private Parent root;

    public Scene getScene() {
        if (root == null) {
            throw new IllegalStateException("Parent node cannot be null");
        } else {
            return root.getScene();
        }
    }

    private Stage getStage() {
        return (Stage) getScene().getWindow();
    }

    public void presentViewController(AbstractViewController viewController) {
        if (viewController == null) {
            throw new IllegalStateException("Segue target view controller cannot be null");
        } else {
            this.getStage().setScene(viewController.getScene());
        }
    }

}
