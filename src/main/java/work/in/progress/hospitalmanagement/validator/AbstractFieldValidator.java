package work.in.progress.hospitalmanagement.validator;

import com.jfoenix.validation.base.ValidatorBase;
import lombok.Getter;

import javax.validation.Validator;

/**
 * Abstract validator class for {@link com.jfoenix.controls.JFXTextField} to validate various data types.
 * @author Andrzej Grabowski
 */
abstract class AbstractFieldValidator extends ValidatorBase {
    @Getter
    private Validator validator;
    @Getter
    private String field;
    @Getter
    private Class c;

    /**
     * Creates the validator for the given class, checking a specific field and using a specific instance of
     * {@link Validator}
     * @param c class of the object to be validated
     * @param field field in the object to be validated
     * @param validator validator to be used to validate the object being validated
     */
    AbstractFieldValidator(Class c, String field, Validator validator) {
        this.c = c;
        this.field = field;
        this.validator = validator;
    }

}
