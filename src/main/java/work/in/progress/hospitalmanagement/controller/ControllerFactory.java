package work.in.progress.hospitalmanagement.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;

import java.io.IOException;

public class ControllerFactory {

    private ControllerFactory() {

    }

    /**
     * Method to instantiate a {@link AbstractViewController} using an fxml file. The fxml file links the particular view
     * to a controller, an instance of which is returned so the next view can be presented.
     * @see AbstractViewController
     * @param identifier view controller type to be presented
     * @return instantiated view controller
     */
    public static AbstractViewController instantiateViewController(Class<? extends AbstractViewController> identifier) {
        AbstractViewController viewController = null;
        try {
            String resourceString = "view/" + identifier.getSimpleName() + ".fxml";
            FXMLLoader viewControllerLoader = new FXMLLoader(ControllerFactory.class.getClassLoader().getResource(resourceString));
            viewControllerLoader.setControllerFactory(ApplicationContextSingleton.getContext()::getBean);
            Parent parent = viewControllerLoader.load();
            parent.setId(resourceString);
            viewController = viewControllerLoader.getController();
        } catch (IOException e) {
            System.err.println("Failed to instantiate view controller with identifier " + identifier.getName());
            Platform.exit();
        }
        return viewController;
    }

}
