package work.in.progress.hospitalmanagement.validator;

import javafx.scene.control.DatePicker;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Validator class to validate a birth date.
 * @author Andrzej Grabowski
 */
public class BirthDateValidator extends AbstractFieldValidator {

    /**
     * {@inheritDoc}
     */
    public BirthDateValidator(Class c, String field, Validator validator) {
        super(c, field, validator);
    }

    @Override
    protected void eval() {
        hasErrors.set(false);
        DatePicker field = (DatePicker) srcControl.get();
        Set<ConstraintViolation> violations =
                getValidator().validateValue(getC(), getField(), field.getValue());
        if (!violations.isEmpty()) {
            hasErrors.set(true);
            this.setMessage(violations.iterator().next().getMessage());
        }
    }
}
