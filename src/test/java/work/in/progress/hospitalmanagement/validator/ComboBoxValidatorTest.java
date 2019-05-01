package work.in.progress.hospitalmanagement.validator;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ComboBoxValidatorTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void evalCorrect() {
        ComboBoxValidator validator = new ComboBoxValidator(Class.class, "field", Mocks.validatorAlwaysCorrect());
        JFXComboBox<Department> picker = new JFXComboBox<>();
        picker.setItems(FXCollections.observableArrayList(Mocks.department()));
        picker.getSelectionModel().selectFirst();
        validator.setSrcControl(picker);
        validator.eval();
        assertFalse(validator.getHasErrors());
    }

    @Test
    public void evalIncorrect() {
        ComboBoxValidator validator = new ComboBoxValidator(Class.class, "field", Mocks.validatorAlwaysIncorrect());
        JFXComboBox<Department> picker = new JFXComboBox<>();
        picker.setItems(FXCollections.observableArrayList(Mocks.department()));
        picker.getSelectionModel().select(null);
        validator.setSrcControl(picker);
        validator.eval();
        assertTrue(validator.getHasErrors());
    }
}