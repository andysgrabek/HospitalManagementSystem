package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.factory.AppointmentCellFactory;
import work.in.progress.hospitalmanagement.factory.AppointmentPredicateFactory;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.service.OutpatientAdmissionService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class PatientsWaitingViewController extends AbstractViewController {

    @FXML
    private JFXDatePicker appointmentDatePicker;
    @FXML
    private JFXTimePicker appointmentTimePicker;
    @FXML
    private JFXComboBox<Department> departmentField;
    @FXML
    private JFXListView<OutpatientAdmission> appointmentListView;

    private final OutpatientAdmissionService outpatientAdmissionService;
    private final DepartmentService departmentService;
    private ObservableList<OutpatientAdmission> list;

    @Autowired
    public PatientsWaitingViewController(OutpatientAdmissionService outpatientAdmissionService,
                                         DepartmentService departmentService) {
        this.outpatientAdmissionService = outpatientAdmissionService;
        this.departmentService = departmentService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList(outpatientAdmissionService.findAll());
        appointmentListView.setCellFactory(new AppointmentCellFactory());
        departmentField.setItems(FXCollections.observableArrayList(departmentService.findAll()));
        Label label = new Label("No outpatient admissions matching search criteria");
        label.getStyleClass().add("hms-text");
        appointmentListView.setPlaceholder(label);
        initListFiltering();
    }

    /**
     * Method setting up listeners on list filtering search boxes and date picker to filter the results using predicates
     */
    private void initListFiltering() {
        FilteredList<OutpatientAdmission> filteredList = new FilteredList<>(list, patient -> true);

        departmentField.getSelectionModel().selectedItemProperty().addListener((o, oV, newValue)
                -> filteredList.setPredicate(composeAdmissionPredicate()));
        appointmentTimePicker.valueProperty().addListener((o, oV, newValue)
                -> filteredList.setPredicate(composeAdmissionPredicate()));
        appointmentDatePicker.valueProperty().addListener((o, oV, newValue)
                -> filteredList.setPredicate(composeAdmissionPredicate()));
        appointmentListView.setItems(filteredList);
    }

    private Predicate<OutpatientAdmission> composeAdmissionPredicate() {
        AppointmentPredicateFactory factory = AppointmentPredicateFactory.getDefaultFactory();
        return factory.datePredicate(appointmentDatePicker.getValue())
                .and(factory.timePredicate(appointmentTimePicker.getValue()))
                .and(factory.departmentPredicate(departmentField.getSelectionModel().getSelectedItem()));
    }

    @FXML
    private void clearSearchFields(ActionEvent actionEvent) {
        appointmentDatePicker.setValue(null);
        appointmentTimePicker.setValue(null);
        departmentField.getSelectionModel().clearSelection();
    }
}
