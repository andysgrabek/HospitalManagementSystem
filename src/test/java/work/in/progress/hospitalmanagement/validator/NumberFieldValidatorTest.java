package work.in.progress.hospitalmanagement.validator;

import com.jfoenix.controls.JFXTextField;
import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.control.DatePicker;
import org.junit.Test;
import org.junit.runner.RunWith;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class NumberFieldValidatorTest {

    @Test
    public void evalCorrect() {
        NumberFieldValidator validator = new NumberFieldValidator(Class.class, "field", Mocks.validatorAlwaysCorrect());
        JFXTextField picker = new JFXTextField();
        picker.setText(String.valueOf(Mocks.patient().getHomeAddress().getZipCode()));
        validator.setSrcControl(picker);
        validator.eval();
        assertFalse(validator.getHasErrors());
    }

    @Test
    public void evalIncorrectValue() {
        NumberFieldValidator validator = new NumberFieldValidator(Class.class, "field", Mocks.validatorAlwaysIncorrect());
        JFXTextField picker = new JFXTextField();
        picker.setText(String.valueOf(Mocks.patient().getHomeAddress().getZipCode()));
        validator.setSrcControl(picker);
        validator.eval();
        assertTrue(validator.getHasErrors());
    }

    @Test
    public void evalIncorrectTextInField() {
        NumberFieldValidator validator = new NumberFieldValidator(Class.class, "field", Mocks.validatorAlwaysIncorrect());
        JFXTextField picker = new JFXTextField();
        picker.setText(String.valueOf(Mocks.patient().getHomeAddress().getAddressLine()));
        validator.setSrcControl(picker);
        validator.eval();
        assertTrue(validator.getHasErrors());
    }

}