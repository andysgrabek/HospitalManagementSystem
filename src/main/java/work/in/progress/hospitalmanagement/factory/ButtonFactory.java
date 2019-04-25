package work.in.progress.hospitalmanagement.factory;

import com.jfoenix.controls.JFXButton;
import javafx.scene.paint.Paint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ButtonFactory {

    private static final double WIDTH = 130.0;
    private static final double HEIGHT = 30.0;
    private static final Paint PAINT = Paint.valueOf("#f0ab8d");
    private static final JFXButton.ButtonType TYPE = JFXButton.ButtonType.RAISED;
    private static final boolean FOCUS_TRAVERSABLE = false;
    private static final String STYLE = "hms-button";
    @Getter
    private static ButtonFactory defaultFactory = new ButtonFactory();

    /**
     * Creates a button styled with HMS style rules
     * @return a new button with HMS style attached
     */
    public JFXButton defaultButton(String title) {
        return customButton(title, HEIGHT, WIDTH, STYLE, PAINT, TYPE, FOCUS_TRAVERSABLE);
    }

    public JFXButton customButton(String title,
                                  double height,
                                  double width,
                                  String styleClass,
                                  Paint ripplerFill,
                                  JFXButton.ButtonType type,
                                  boolean focusTraversable) {
        JFXButton button = new JFXButton(title);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.getStyleClass().add(styleClass);
        button.setRipplerFill(ripplerFill);
        button.setButtonType(type);
        button.setFocusTraversable(focusTraversable);
        return button;
    }

}