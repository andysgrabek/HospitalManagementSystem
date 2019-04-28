package work.in.progress.hospitalmanagement.cell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.service.BedService;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class DepartmentCell extends DeleteListCell<Department> {

    private final BedService bedService;

    @Autowired
    public DepartmentCell(BedService bedService) {
        this.bedService = bedService;
    }

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
