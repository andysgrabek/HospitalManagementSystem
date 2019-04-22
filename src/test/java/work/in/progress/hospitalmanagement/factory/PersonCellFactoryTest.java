package work.in.progress.hospitalmanagement.factory;

import javafx.scene.control.ListView;
import org.junit.Rule;
import org.junit.Test;
import work.in.progress.hospitalmanagement.cell.PersonCell;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;

import static org.junit.Assert.assertNotNull;

public class PersonCellFactoryTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void call() {
        ListView<Patient> listView = new ListView<>();
        PersonCellFactory personCellFactory = new PersonCellFactory();
        PersonCell cell = (PersonCell) personCellFactory.call(listView);
        assertNotNull(cell);
    }
}