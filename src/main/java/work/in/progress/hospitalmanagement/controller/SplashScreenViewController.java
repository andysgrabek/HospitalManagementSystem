package work.in.progress.hospitalmanagement.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class SplashScreenViewController extends AbstractViewController {

    @FXML
    private ImageView logoImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.setOnFinished(event -> presentViewController(ControllerFactory.instantiateViewController(MainMenuViewController.class), true));
        pauseTransition.play();
    }

}
