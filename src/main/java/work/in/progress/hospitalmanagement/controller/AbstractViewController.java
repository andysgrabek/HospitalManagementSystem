package work.in.progress.hospitalmanagement.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;

import java.io.IOException;

/**
 * Abstract class to be used as a base class for all view controllers.
 *
 * @author Andrzej Grabowski
 */
public abstract class AbstractViewController implements Initializable {

    private static final double TRANSITION_CROSS_DISSOLVE_DURATION = 100.0f;
    private Parent root;

    /**
     * Retrieves the root {@link Parent} node of the controller view
     * @return root node of the controlled view
     */
    public Parent getRoot() {
        if (root == null) {
            throw new IllegalStateException("Root node cannot be null");
        }
        return root;
    }

    /**
     * Retrives the {@link Scene} of the view connected with the controller.
     * @return scene of the related view
     */
    public Scene getScene() {
        if (root == null) {
            throw new IllegalStateException("Parent node cannot be null");
        } else {
            return root.getScene();
        }
    }

    /**
     * Retrives the {@link Stage} of the view connected with the controller.
     * @return stage of the related view
     */
    public Stage getStage() {
        return (Stage) getScene().getWindow();
    }

    /**
     * Swaps the currently displayed view controller to a new one
     * @param viewController view controller to be displayed
     * @param animated use to make the change animated using a fading transition
     */
    public void presentViewController(AbstractViewController viewController, boolean animated) {
        if (viewController == null) {
            throw new IllegalStateException("Segue target view controller cannot be null");
        } else if (animated) {
            final DoubleProperty opacity = viewController.getRoot().opacityProperty();
            Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                    new KeyFrame(new Duration(TRANSITION_CROSS_DISSOLVE_DURATION), transition -> {
                        this.getStage().setScene(new Scene(viewController.getRoot()));
                        Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                new KeyFrame(new Duration(TRANSITION_CROSS_DISSOLVE_DURATION), arg0 -> { },
                                        new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }, new KeyValue(opacity, 0.0)));
            fade.play();
        } else {
            this.getStage().setScene(new Scene(viewController.getRoot()));
        }
    }

    /**
     * Sets the root {@link Parent} node for the view being controlled
     * @param parent node to be set as root
     */
    public void setRoot(Parent parent) {
        if (root == null) {
            root = parent;
        } else {
            throw new IllegalStateException("Changing a scene's root node is not allowed.");
        }
    }

    /**
     * Method to instantiate a {@link AbstractViewController} using an fxml file. The fxml file links the particular
     * view to a controller, an instance of which is returned so the next view can be presented.
     * @see AbstractViewController
     * @param identifier view controller type to be presented
     * @return instantiated view controller
     */
    public static AbstractViewController instantiateViewController(Class<? extends AbstractViewController> identifier) {
        AbstractViewController viewController = null;
        try {
            String resourceString = "view/" + identifier.getSimpleName() + ".fxml";
            FXMLLoader viewControllerLoader =
                    new FXMLLoader(AbstractViewController.class.getClassLoader().getResource(resourceString));
            viewControllerLoader.setControllerFactory(ApplicationContextSingleton.getContext()::getBean);
            Parent parent = viewControllerLoader.load();
            parent.setId(resourceString);
            viewController = viewControllerLoader.getController();
            viewController.setRoot(parent);
        } catch (IOException e) {
            System.err.println("Failed to instantiate view controller with identifier " + identifier.getName());
            System.err.println("Was " + identifier.getName() + " annotated with @Component?");
            Platform.exit();
        }
        return viewController;
    }
}
