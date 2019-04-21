package work.in.progress.hospitalmanagement;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
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

    /**
     * Application entry point. Requires no arguments.
     * @param args CL arguments list. Unused.
     */
    public static void main(String[] args) {
        ApplicationContextSingleton.setContext(SpringApplication.run(HospitalManagementApplication.class, args));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AbstractViewController viewController =
                AbstractViewController.instantiateViewController(SplashScreenViewController.class);
        Scene s = new Scene(viewController.getRoot());
        primaryStage.setScene(s);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
        System.exit(0);
    }
}
