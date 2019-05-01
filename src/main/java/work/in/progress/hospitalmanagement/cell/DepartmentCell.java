package work.in.progress.hospitalmanagement.cell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.service.BedService;

/**
 * Class representing the cell displayed in a {@link javafx.scene.control.ListView} in e.g.
 * {@link work.in.progress.hospitalmanagement.controller.DepartmentManagementViewController}
 * showing how many beds are available in a single department
 * @author Andrzej Grabowski
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class DepartmentCell extends DeleteListCell<Department> {

    private final BedService bedService;

    @Autowired
    public DepartmentCell(BedService bedService) {
        this.bedService = bedService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateItem(Department item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
            return;
        }
        getLabel().setText(item.getName()
                + ", free beds: "
                + bedService.freeBeds(item).size()
                + "/"
                + bedService.findByDepartment(item).size());
        setGraphic(getHBox());
    }
}
