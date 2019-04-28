package work.in.progress.hospitalmanagement.factory;

import com.jfoenix.controls.JFXButton;
import javafx.scene.paint.Paint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Factory for creating programatically buttons that conform to the Hospital Management Application's style
 * @author Andrzej Grabowski
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ButtonFactory {

    @Getter
    private static final double WIDTH = 80;
    @Getter
    private static final double LARGE_BUTTON_WIDTH = WIDTH * 1.5;
    @Getter
    private static final double HEIGHT = 30.0;
    @Getter
    private static final Paint PAINT = Paint.valueOf("#f0ab8d");
    @Getter
    private static final JFXButton.ButtonType TYPE = JFXButton.ButtonType.RAISED;
    @Getter
    private static final boolean FOCUS_TRAVERSABLE = false;
    @Getter
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

    /**
     * Creates a custom button
     * @param title the title of the button
     * @param height the height of the button
     * @param width the width of the button
     * @param styleClass the style class of the button to be applied
     * @param ripplerFill the rippler fill color to be applied
     * @param type the type of the button to be created
     * @param focusTraversable states if the button should be focus-traverable
     * @return the custom button
     */
    private JFXButton customButton(String title,
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
