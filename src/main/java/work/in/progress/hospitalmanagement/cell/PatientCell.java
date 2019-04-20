package work.in.progress.hospitalmanagement.cell;


import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import work.in.progress.hospitalmanagement.model.Patient;

public class PatientCell extends ListCell<Patient> {
    @Override
    public void updateItem(Patient item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
            return;
        }
        HBox hBox = new HBox();
        Label label =
                new Label(item.getName() + " " + item.getSurname() + ", born " + item.getBirthDate().toString());
        Button button = new JFXButton("edit");
        button.setOnAction(event -> {
//            button.fireEvent();
        });
        hBox.getChildren().add(label);
        hBox.getChildren().add(button);
        HBox.setHgrow(label, Priority.ALWAYS);
        setGraphic(hBox);
    }
}
