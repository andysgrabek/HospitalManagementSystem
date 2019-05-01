package work.in.progress.hospitalmanagement.validator;

import com.jfoenix.controls.JFXTextField;
import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TextFieldValidatorTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void evalCorrect() {
        TextFieldValidator validator = new TextFieldValidator(Class.class, "field", Mocks.validatorAlwaysCorrect());
        JFXTextField picker = new JFXTextField();
        picker.setText(String.valueOf(Mocks.patient().getHomeAddress().getAddressLine()));
        validator.setSrcControl(picker);
        validator.eval();
        assertFalse(validator.getHasErrors());
    }

    @Test
    public void evalIncorrect() {
        TextFieldValidator validator = new TextFieldValidator(Class.class, "field", Mocks.validatorAlwaysIncorrect());
        JFXTextField picker = new JFXTextField();
        picker.setText(String.valueOf(Mocks.patient().getHomeAddress().getAddressLine()));
        validator.setSrcControl(picker);
        validator.eval();
        assertTrue(validator.getHasErrors());
    }

}