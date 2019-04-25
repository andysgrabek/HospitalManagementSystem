package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.factory.DialogFactory;
import work.in.progress.hospitalmanagement.validator.TextFieldValidator;

import javax.validation.Validator;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class AdvancedSearchViewController extends AbstractViewController {
    @FXML
    private JFXCheckBox saveCheckBox;
    private final Validator validator;
    @FXML
    private JFXButton deleteQueryButton;
    @FXML
    private ComboBox<Query> predefinedQueriesComboBox;
    @FXML
    private JFXTextField queryTextField;
    @FXML
    private JFXButton executeQueryButton;
    @FXML
    private TableView<List<String>> tableView;

    @Autowired
    public AdvancedSearchViewController(Validator validator) {
        this.validator = validator;
    }

    public class Query {
        public String getQueryString() { return "SELECT * FROM ..."; }
        public String getQueryLabel() { return "Select everything from ellipsis"; }
    } //TODO: remove after true query type is added

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        predefinedQueriesComboBox.setItems(queryService.findAll());//TODO uncomment
        predefinedQueriesComboBox.getSelectionModel().selectedItemProperty().addListener(
                (o, oV, newValue) -> deleteQueryButton.setDisable(newValue == null));
        queryTextField.getValidators().add(new TextFieldValidator(Query.class, "queryString", validator));//TODO change field name
        queryTextField.textProperty().addListener((o, oV, nV) -> {
            saveCheckBox.setDisable(false);
            saveCheckBox.setSelected(true);
        });
    }

    @FXML
    private void backToMainMenu(ActionEvent actionEvent) {
        presentViewController(instantiateViewController(MainMenuViewController.class), true);
    }

    @FXML
    private void selectedQuery(ActionEvent actionEvent) {
        queryTextField.setText(predefinedQueriesComboBox.getSelectionModel().getSelectedItem().getQueryString());
        saveCheckBox.setDisable(true);
        saveCheckBox.setSelected(false);
    }

    @FXML
    private void executeQuery(ActionEvent actionEvent) {
       if (queryTextField.validate()) {
           if (saveCheckBox.isSelected()) {
               StringProperty stringProperty = new SimpleStringProperty();
               DialogFactory.getDefaultFactory().textFieldDialog(
                       "Enter query name",
                       "Query name",
                       stringProperty,
                       event -> {
                           Query query = new Query();
//                           queryService.save(query);//TODO use value from stringProperty as the name
                           execute(query);
                       },
                       (StackPane) getRoot()).show();
           } else {
               execute(new Query());
           }
       }
    }

    private void execute(Query query) {
//        queryService.execute?//TODO uncomment
        //TODO execute query
        List<List<String>> result = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("Aaaaaaaaaa", "Bbbbbbbbb", "Ccccccccc")),
                new ArrayList<>(Arrays.asList("Pppppppppp", "Qqqqqqqqq", "Fffffffff")),
                new ArrayList<>(Arrays.asList("D", "E", "F"))));
        for (int i = 0; i < result.get(0).size(); ++i) {
            List<String> header = result.get(0);
            TableColumn<List<String>, String> column = new TableColumn<>(header.get(i) + " ");
            int finalI = i;
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(finalI)) {
            });
            tableView.getColumns().add(column);
        }
        tableView.setItems(FXCollections.observableArrayList(
                result.stream().filter(p -> result.indexOf(p) != 0).collect(Collectors.toList())));
        predefinedQueriesComboBox.setItems(FXCollections.emptyObservableList());//TODO: retrieve list of queries from query service
    }

    @FXML
    private void showSchema(ActionEvent actionEvent) {
        DialogFactory.getDefaultFactory().imageDialog(
                "Hospital Management System Schema",
                new Image("images/admission.png"),
                event -> { },
                (StackPane) getRoot()
        ).show();
    }

    @FXML
    private void deleteQuery(ActionEvent actionEvent) {
//        queryService.delete(predefinedQueriesComboBox.getSelectionModel().getSelectedItem());//TODO uncomment
        predefinedQueriesComboBox.getItems().remove(predefinedQueriesComboBox.getSelectionModel().getSelectedItem());
    }
}
