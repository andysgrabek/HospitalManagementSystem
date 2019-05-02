package work.in.progress.hospitalmanagement.validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.util.Mocks;

import javax.validation.Validator;

import static org.junit.Assert.assertEquals;

public class AbstractFieldValidatorTest {

    private final Validator validatorB = Mocks.validatorAlwaysCorrect();

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    private AbstractFieldValidator validator;

    @Before
    public void setUp() {
        validator = new AbstractFieldValidator(Class.class, "field", validatorB) {
            @Override
            protected void eval() {

            }
        };
    }

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