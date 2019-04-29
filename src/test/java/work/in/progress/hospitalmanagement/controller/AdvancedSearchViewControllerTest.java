package work.in.progress.hospitalmanagement.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;
import work.in.progress.hospitalmanagement.converter.BedStringConverter;
import work.in.progress.hospitalmanagement.converter.DepartmentStringConverter;
import work.in.progress.hospitalmanagement.converter.SearchQueryStringConverter;
import work.in.progress.hospitalmanagement.factory.DialogFactory;
import work.in.progress.hospitalmanagement.model.SearchQuery;
import work.in.progress.hospitalmanagement.repository.BedRepository;
import work.in.progress.hospitalmanagement.repository.DepartmentRepository;
import work.in.progress.hospitalmanagement.repository.InpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.repository.OutpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.repository.PatientRepository;
import work.in.progress.hospitalmanagement.repository.SearchQueryRepository;
import work.in.progress.hospitalmanagement.rule.JavaFXThreadingRule;
import work.in.progress.hospitalmanagement.service.BedService;
import work.in.progress.hospitalmanagement.service.DepartmentService;
import work.in.progress.hospitalmanagement.service.InpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.OutpatientAdmissionService;
import work.in.progress.hospitalmanagement.service.PatientService;
import work.in.progress.hospitalmanagement.service.SearchQueryService;
import work.in.progress.hospitalmanagement.util.Mocks;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        AdvancedSearchViewController.class,
        SearchQueryStringConverter.class,
        InpatientAdmissionService.class,
        OutpatientAdmissionService.class,
        PatientService.class,
        DepartmentService.class,
        BedService.class,
        DepartmentStringConverter.class,
        BedStringConverter.class,
        DialogFactory.class
})
public class AdvancedSearchViewControllerTest implements ApplicationContextAware {

    private ConfigurableApplicationContext context;

    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();


    @Mock
    private TupleElement<?> tupleElement;
    @Mock
    private Tuple tuple;
    @MockBean
    private SearchQueryService searchQueryService;
    @MockBean
    private SearchQueryRepository searchQueryRepository;
    @MockBean
    private Validator validator;
    @MockBean
    private EntityManager entityManager;
    @MockBean
    private InpatientAdmissionRepository inpatientAdmissionRepository;
    @MockBean
    private OutpatientAdmissionRepository outpatientAdmissionRepository;
    @MockBean
    private PatientRepository patientRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    private AdvancedSearchViewController vc;
    @MockBean
    private BedRepository bedRepository;

    @Before
    public void setUp() throws Exception {
        ApplicationContextSingleton.setContext(null);
        ApplicationContextSingleton.setContext(context);
        vc = (AdvancedSearchViewController) AbstractViewController.instantiateViewController(AdvancedSearchViewController.class);
    }

    @After
    public void tearDown() throws Exception {
        ApplicationContextSingleton.setContext(null);
    }

    @Test
    public void initializeTest() {
        assertNotNull(vc);
    }

    @Test
    public void selectedQueryTest() {
        ComboBox<SearchQuery> searchQueryComboBox = (ComboBox<SearchQuery>) ReflectionTestUtils.getField(vc, vc.getClass(), "predefinedQueriesComboBox");
        SearchQuery query = Mocks.searchQuery();
        searchQueryComboBox.setItems(FXCollections.observableArrayList(query));
        searchQueryComboBox.getSelectionModel().select(query);
        ReflectionTestUtils.invokeMethod(vc, "selectedQuery", new ActionEvent());
        JFXTextField queryF = (JFXTextField) ReflectionTestUtils.getField(vc, vc.getClass(), "queryTextField");
        JFXCheckBox checkBox = (JFXCheckBox) ReflectionTestUtils.getField(vc, vc.getClass(), "saveCheckBox");
        assertTrue(checkBox.isDisable());
        assertFalse(checkBox.isSelected());
        assertEquals(Mocks.searchQuery().getExpression(), queryF.getText());
    }

    @Test
    public void executeQueryTest_save() {
        JFXCheckBox checkBox = (JFXCheckBox) ReflectionTestUtils.getField(vc, vc.getClass(), "saveCheckBox");
        checkBox.setSelected(true);
        JFXTextField queryF = (JFXTextField) ReflectionTestUtils.getField(vc, vc.getClass(), "queryTextField");
        queryF.setText(Mocks.searchQuery().getExpression());
        StackPane stackPane =  ReflectionTestUtils.invokeMethod(vc, "getRoot");
        int childrenBefore = stackPane.getChildren().size();
        ReflectionTestUtils.invokeMethod(vc, "executeQuery", new ActionEvent());
        int childrenAfter = stackPane.getChildren().size();
        assertTrue(childrenAfter > childrenBefore);
    }

    @Test
    public void executeQueryTest_noSave() throws InterruptedException {
        JFXTextField queryF = (JFXTextField) ReflectionTestUtils.getField(vc, vc.getClass(), "queryTextField");
        queryF.setText(Mocks.searchQuery().getExpression());
        Mockito.when(searchQueryService.execute(Mockito.any(SearchQuery.class)))
                .thenReturn(new ArrayList<>());
        JFXCheckBox checkBox = (JFXCheckBox) ReflectionTestUtils.getField(vc, vc.getClass(), "saveCheckBox");
        checkBox.setSelected(false);
        ReflectionTestUtils.invokeMethod(vc, "executeQuery", new ActionEvent());
        Mockito.verify(searchQueryService, Mockito.times(1)).execute(Mockito.any(SearchQuery.class));
    }

    @Test
    public void executeTest() {
        TableView tableView = (TableView) ReflectionTestUtils.getField(vc, vc.getClass(), "tableView");
        Mockito.when(searchQueryService.execute(Mockito.any(SearchQuery.class)))
                .thenReturn(Arrays.asList(tuple, tuple, tuple));
        Mockito.when(tuple.getElements()).thenReturn(Arrays.asList(tupleElement, tupleElement));
        searchQueryService.execute(Mocks.searchQuery());
        ReflectionTestUtils.invokeMethod(vc, "execute", Mocks.searchQuery());
        assertEquals(3, tableView.getItems().size());
    }

    @Test
    public void showSchemaTest() {
        StackPane stackPane =  ReflectionTestUtils.invokeMethod(vc, "getRoot");
        int childrenBefore = stackPane.getChildren().size();
        ReflectionTestUtils.invokeMethod(vc, "showSchema", new ActionEvent());
        int childrenAfter = stackPane.getChildren().size();
        assertTrue(childrenAfter > childrenBefore);
    }

    @Test
    public void deleteQueryTest() {
        StackPane stackPane =  ReflectionTestUtils.invokeMethod(vc, "getRoot");
        ComboBox<SearchQuery> searchQueryComboBox = (ComboBox<SearchQuery>) ReflectionTestUtils.getField(vc, vc.getClass(), "predefinedQueriesComboBox");
        SearchQuery query = Mocks.searchQuery();
        searchQueryComboBox.setItems(FXCollections.observableArrayList(query));
        searchQueryComboBox.getSelectionModel().select(query);
        int childrenBefore = stackPane.getChildren().size();
        ReflectionTestUtils.invokeMethod(vc, "deleteQuery", new ActionEvent());
        int childrenAfter = stackPane.getChildren().size();
        assertTrue(childrenAfter > childrenBefore);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;
    }
}