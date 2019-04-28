package work.in.progress.hospitalmanagement.factory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import work.in.progress.hospitalmanagement.ApplicationContextSingleton;
import work.in.progress.hospitalmanagement.cell.DepartmentCell;
import work.in.progress.hospitalmanagement.model.Department;

/**
 * Factory for creating {@link DepartmentCell} objects to be used in {@link ListView} where
 * {@link Department} objects are presented
 * @author Andrzej Grabowski
 */
public class DepartmentCellFactory implements Callback<ListView<Department>, ListCell<Department>> {
    @Override
    public ListCell<Department> call(ListView<Department> param) {
        return ApplicationContextSingleton.getContext().getBean(DepartmentCell.class);
    }
}
