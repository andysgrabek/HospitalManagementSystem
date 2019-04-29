package work.in.progress.hospitalmanagement.factory;

import com.jfoenix.controls.JFXButton;
import javafx.scene.paint.Paint;
import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;

import static org.junit.Assert.*;

public class ButtonFactoryTest {

    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();

    @Test
    public void defaultButton() {
        JFXButton t = ButtonFactory.getDefaultFactory().defaultButton("Test");
        assertEquals("Test", t.getText());
        assertEquals(80, t.getPrefWidth(), 0.0);
        assertEquals(30, t.getPrefHeight(), 0.0);
        assertTrue(t.getStyleClass().contains("hms-button"));
        assertEquals(Paint.valueOf("#f0ab8d"), t.getRipplerFill());
        assertEquals(JFXButton.ButtonType.RAISED, t.getButtonType());
        assertFalse(t.isFocusTraversable());
    }
}