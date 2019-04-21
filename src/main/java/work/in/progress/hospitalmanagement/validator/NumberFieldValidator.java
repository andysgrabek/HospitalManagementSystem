package work.in.progress.hospitalmanagement.validator;

import javafx.scene.control.TextInputControl;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Validator class to validate a whole number.
 * @author Andrzej Grabowski
 */
public class NumberFieldValidator extends AbstractFieldValidator {

    /**
     * {@inheritDoc}
     */
    public NumberFieldValidator(Class c, String field, Validator validator) {
        super(c, field, validator);
    }

    @Override
    protected void eval() {
        hasErrors.set(false);
        TextInputControl textField = (TextInputControl) srcControl.get();
        if (!textField.getText().matches("-?\\d+(\\.\\d+)?")) {
            hasErrors.set(true);
            this.setMessage("Must be a number");
        } else {
            Set<ConstraintViolation> violations =
                    getValidator().validateValue(getC(), getField(), Integer.parseInt(textField.getText()));
            if (!violations.isEmpty()) {
                hasErrors.set(true);
                this.setMessage(violations.iterator().next().getMessage());
            }
        }
    }
}
