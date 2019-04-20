package work.in.progress.hospitalmanagement.validator;

import com.jfoenix.controls.JFXTextField;
import de.saxsys.javafx.test.JfxRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class TextFieldValidatorTest {

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