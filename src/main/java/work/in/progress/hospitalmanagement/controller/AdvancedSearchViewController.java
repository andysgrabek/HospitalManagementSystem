package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleObjectProperty;
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
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.converter.SearchQueryStringConverter;
import work.in.progress.hospitalmanagement.factory.DialogFactory;
import work.in.progress.hospitalmanagement.model.SearchQuery;
import work.in.progress.hospitalmanagement.service.SearchQueryService;
import work.in.progress.hospitalmanagement.validator.TextFieldValidator;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.validation.Validator;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Class serving as controller for the view in which the user can perform an advanced search through the hospital's
 * database using a custom SQL-like query.
 * @author Andrzej Grabowski
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class AdvancedSearchViewController extends AbstractViewController {
    private final Validator validator;
    private final SearchQueryService searchQueryService;
    @FXML
    private JFXCheckBox saveCheckBox;
    @FXML
    private JFXButton deleteQueryButton;
    @FXML
    private ComboBox<SearchQuery> predefinedQueriesComboBox;
    @FXML
    private JFXTextField queryTextField;
    @FXML
    private TableView<Tuple> tableView;

    @Autowired
    public AdvancedSearchViewController(Validator validator, SearchQueryService searchQueryService) {
        this.validator = validator;
        this.searchQueryService = searchQueryService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        predefinedQueriesComboBox.setItems(FXCollections.observableArrayList(searchQueryService.findAll()));
        predefinedQueriesComboBox.getSelectionModel().selectedItemProperty().addListener(
                (o, oV, newValue) -> deleteQueryButton.setDisable(newValue == null));
        queryTextField.getValidators().add(new TextFieldValidator(SearchQuery.class, "expression", validator));
        queryTextField.textProperty().addListener((o, oV, nV) -> {
            saveCheckBox.setDisable(false);
            saveCheckBox.setSelected(true);
        });
        predefinedQueriesComboBox.setConverter(new SearchQueryStringConverter(searchQueryService));
    }

    /**
     * Handler for the event of selecting a new query in the {@link ComboBox containing predefined queries}
     * @param actionEvent event that triggered the action
     */
    @FXML
    private void selectedQuery(ActionEvent actionEvent) {
        if (predefinedQueriesComboBox.getSelectionModel().getSelectedItem() != null) {
            queryTextField.setText(predefinedQueriesComboBox.getSelectionModel().getSelectedItem().getExpression());
            saveCheckBox.setDisable(true);
            saveCheckBox.setSelected(false);
        }
    }

    /**
     * Handler for the event of pressing the button to execute the typed query
     * @param actionEvent event that triggered the action
     */
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
                            SearchQuery searchQuery = new SearchQuery(stringProperty.get(), queryTextField.getText());
                            predefinedQueriesComboBox.getItems().add(searchQueryService.save(searchQuery));
                            execute(searchQuery);
                        },
                        (StackPane) getRoot(),
                        new TextFieldValidator(SearchQuery.class, "label", validator)).show();
            } else {
                execute(new SearchQuery("CustomQuery", queryTextField.getText()));
            }
        }
    }

    /**
     * Method executing the query typed in by the user
     * @param searchQuery the query typed in by the user
     */
    private void execute(SearchQuery searchQuery) {
        tableView.getColumns().clear();
        List<Tuple> result = searchQueryService.execute(searchQuery);

        if (result.size() > 0) {
            int i = 0;
            List<TupleElement<?>> columns = result.get(0).getElements();
            for (TupleElement column : columns) {
                TableColumn<Tuple, Object> tableColumn = new TableColumn<>(
                        ObjectUtils.defaultIfNull(column.getAlias(), "Unnamed"));

                final int index = i++;
                tableColumn.setCellValueFactory(param ->
                        new SimpleObjectProperty<Object>(param.getValue().get(index)) {
                        });

                tableView.getColumns().add(tableColumn);
            }
        }

        tableView.setItems(FXCollections.observableArrayList(result));
    }

    /**
     * Handler for the event of clicking the button to view the database schema
     * @param actionEvent event that triggered the action
     */
    @FXML
    private void showSchema(ActionEvent actionEvent) {
        DialogFactory.getDefaultFactory().imageDialog(
                "Hospital Management System Schema",
                new Image("images/schema.png"),
                event -> {
                },
                (StackPane) getRoot()
        ).show();
    }

    /**
     * Handler for the event of clicking the button to delete the currently selected predefined query
     * @param actionEvent event that triggered the action
     */
    @FXML
    private void deleteQuery(ActionEvent actionEvent) {
        if (!predefinedQueriesComboBox.getSelectionModel().isEmpty()) {
            searchQueryService.delete(predefinedQueriesComboBox.getSelectionModel().getSelectedItem());
            predefinedQueriesComboBox.getItems()
                    .remove(predefinedQueriesComboBox.getSelectionModel().getSelectedItem());
            predefinedQueriesComboBox.getSelectionModel().clearSelection();
        }
    }
}
