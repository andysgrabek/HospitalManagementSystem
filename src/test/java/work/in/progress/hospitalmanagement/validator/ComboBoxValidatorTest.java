package work.in.progress.hospitalmanagement.validator;

import com.jfoenix.controls.JFXComboBox;
import de.saxsys.javafx.test.JfxRunner;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class ComboBoxValidatorTest {

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