package work.in.progress.hospitalmanagement.validator;

import com.jfoenix.controls.JFXComboBox;

import javax.validation.Validator;

public class ComboBoxValidator extends AbstractFieldValidator {

    public ComboBoxValidator(Class c, String field, Validator validator) {
        super(c, field, validator);
    }

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
