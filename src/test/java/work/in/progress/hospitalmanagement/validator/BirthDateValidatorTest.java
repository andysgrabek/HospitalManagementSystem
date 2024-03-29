package work.in.progress.hospitalmanagement.validator;

import javafx.scene.control.DatePicker;
import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BirthDateValidatorTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void evalCorrect() {
        BirthDateValidator validator = new BirthDateValidator(Class.class, "field", Mocks.validatorAlwaysCorrect());
        DatePicker picker = new DatePicker();
        picker.setValue(LocalDate.now());
        validator.setSrcControl(picker);
        validator.eval();
        assertFalse(validator.getHasErrors());
    }

    @Test
    public void evalIncorrect() {
        BirthDateValidator validator = new BirthDateValidator(Class.class, "field", Mocks.validatorAlwaysIncorrect());
        DatePicker picker = new DatePicker();
        picker.setValue(LocalDate.now());
        validator.setSrcControl(picker);
        validator.eval();
        assertTrue(validator.getHasErrors());
    }
}