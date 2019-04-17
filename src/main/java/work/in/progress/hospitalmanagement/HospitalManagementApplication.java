package work.in.progress.hospitalmanagement;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import work.in.progress.hospitalmanagement.controller.AbstractViewController;
import work.in.progress.hospitalmanagement.controller.ControllerFactory;
import work.in.progress.hospitalmanagement.controller.SplashScreenViewController;

@SpringBootApplication
public class HospitalManagementApplication extends Application {

	public static void main(String[] args) {
		ApplicationContextSingleton.setContext(SpringApplication.run(HospitalManagementApplication.class, args));
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AbstractViewController viewController = ControllerFactory.instantiateViewController(SplashScreenViewController.class);
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
