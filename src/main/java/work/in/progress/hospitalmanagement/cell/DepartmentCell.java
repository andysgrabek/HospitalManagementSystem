package work.in.progress.hospitalmanagement.cell;

import work.in.progress.hospitalmanagement.model.Department;

public class DepartmentCell extends DeleteListCell<Department> {
    @Override
    protected void updateItem(Department item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
            return;
        }
        getLabel().setText(item.getName() + ", beds: " + 1);
        setGraphic(getHBox());
    }
}
