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

/**
 * Simple splash screen controller. Does nothing except for pausing the program for 3 seconds to display the logo,
 * then continues to the {@link MainMenuViewController} screen.
 * @author Andrzej Grabowski
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class SplashScreenViewController extends AbstractViewController {

    private static final int LOADING_TRANSITION_DURATION = 3;
    @FXML
    private ImageView logoImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(LOADING_TRANSITION_DURATION));
        pauseTransition.setOnFinished(event ->
                presentViewController(instantiateViewController(MainMenuViewController.class), true));
        pauseTransition.play();
    }

}
