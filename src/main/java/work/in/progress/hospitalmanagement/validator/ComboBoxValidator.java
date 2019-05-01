package work.in.progress.hospitalmanagement.validator;

import com.jfoenix.controls.JFXComboBox;

import javax.validation.Validator;

/**
 * Validator class to validate a selection in a {@link javafx.scene.control.ComboBox}.
 * @author Andrzej Grabowski
 */
public class ComboBoxValidator extends AbstractFieldValidator {

    /**
     * {@inheritDoc}
     */
    public ComboBoxValidator(Class c, String field, Validator validator) {
        super(c, field, validator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void eval() {
        hasErrors.set(false);
        JFXComboBox field = (JFXComboBox) srcControl.get();
        if (field.getSelectionModel().getSelectedItem() == null) {
            hasErrors.set(true);
            this.setMessage("Cannot be empty");
        }
    }
}
