package work.in.progress.hospitalmanagement;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import work.in.progress.hospitalmanagement.controller.AbstractViewController;
import work.in.progress.hospitalmanagement.controller.SplashScreenViewController;

/**
 * Main application class, starting both the SpringApplication and JavaFX front end.
 *
 * @author Andrzej Grabowski
 * @author Bartłomiej Jabłoński
 */
@SuppressWarnings("HideUtilityClassConstructor")
@SpringBootApplication
public class HospitalManagementApplication extends Application {

    private static final double MIN_WINDOW_WIDTH = 1024.0;
    private static final double MIN_WINDOW_HEIGHT = 768;

    /**
     * Application entry point. Requires no arguments.
     *
     * @param args CL arguments list. Unused.
     */
    public static void main(String[] args) {
        ApplicationContextSingleton.setContext(SpringApplication.run(HospitalManagementApplication.class, args));
        launch(args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        AbstractViewController viewController =
                AbstractViewController.instantiateViewController(SplashScreenViewController.class);
        Scene s = new Scene(viewController.getRoot());
        primaryStage.setScene(s);
        primaryStage.setMinHeight(MIN_WINDOW_HEIGHT);
        primaryStage.setMinWidth(MIN_WINDOW_WIDTH);
        primaryStage.show();
        primaryStage.setTitle("Hospital Management System");
        primaryStage.getIcons().add(new Image("images/icon_transparent.png"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
        System.exit(0);
    }

}
