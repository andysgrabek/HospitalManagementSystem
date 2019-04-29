package work.in.progress.hospitalmanagement.validator;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Validator class to validate the {@link LocalDateTime}
 * of an {@link work.in.progress.hospitalmanagement.model.OutpatientAdmission}
 * @author Andrzej Grabowski
 */
public class VisitDateTimeValidator extends AbstractFieldValidator {

    private JFXDatePicker datePicker;
    private JFXTimePicker timePicker;

    /**
     * Creates the validator for the given class, checking a specific field and using a specific instance of
     * {@link Validator}
     *
     * @param c         class of the object to be validated
     * @param field     field in the object to be validated
     * @param validator validator to be used to validate the object being validated
     */
    public VisitDateTimeValidator(Class c,
                           String field,
                           Validator validator,
                           JFXDatePicker datePicker,
                           JFXTimePicker timePicker) {
        super(c, field, validator);
        this.timePicker = timePicker;
        this.datePicker = datePicker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void eval() {
        hasErrors.set(false);
        if (timePicker.getValue() == null || datePicker.getValue() == null) {
            hasErrors.set(true);
            this.setMessage("Must not be empty");
            return;
        }
        LocalDateTime field = LocalDateTime.of(datePicker.getValue(), timePicker.getValue());
        Set<ConstraintViolation> violations =
                getValidator().validateValue(getC(), getField(), field);
        if (!violations.isEmpty()) {
            hasErrors.set(true);
            this.setMessage(violations.iterator().next().getMessage());
        }
    }
}
