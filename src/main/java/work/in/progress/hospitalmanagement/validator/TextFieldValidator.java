package work.in.progress.hospitalmanagement.validator;

import javafx.scene.control.TextInputControl;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Validator class to validate a piece of text.
 * @author Andrzej Grabowski
 */
public class TextFieldValidator extends AbstractFieldValidator {

    /**
     * {@inheritDoc}
     */
    public TextFieldValidator(Class c, String field, Validator validator) {
        super(c, field, validator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void eval() {
        hasErrors.set(false);
        TextInputControl textField = (TextInputControl) srcControl.get();
        Set<ConstraintViolation> violations =
                getValidator().validateValue(getC(), getField(), textField.getText());
        if (!violations.isEmpty()) {
            hasErrors.set(true);
            this.setMessage(violations.iterator().next().getMessage());
        }
    }
}
