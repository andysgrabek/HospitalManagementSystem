package work.in.progress.hospitalmanagement.validator;

import de.saxsys.javafx.test.JfxRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import work.in.progress.hospitalmanagement.util.Mocks;

import javax.validation.Validator;

import static org.junit.Assert.assertEquals;

@RunWith(JfxRunner.class)
public class AbstractFieldValidatorTest {

    private Validator validatorB = Mocks.validatorAlwaysCorrect();
    private AbstractFieldValidator validator = new AbstractFieldValidator(Class.class, "field", validatorB) {
        @Override
        protected void eval() {

        }
    };

    @Test
    public void getValidator() {
        assertEquals(validatorB, validator.getValidator());
    }

    @Test
    public void getField() {
        assertEquals("field", validator.getField());
    }

    @Test
    public void getC() {
        assertEquals(Class.class, validator.getC());
    }
}