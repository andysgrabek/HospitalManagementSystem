package work.in.progress.hospitalmanagement.converter;

import javafx.util.StringConverter;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.service.BedService;

/**
 * Concretization of a {@link StringConverter} to convert a {@link Bed} to a {@link String} and back
 * to be used with a {@link javafx.scene.control.ComboBox}
 * @author Andrzej Grabowski
 */
@Component
public class BedStringConverter extends StringConverter<Bed> {

    private final BedService bedService;

    public BedStringConverter(BedService bedService) {
        this.bedService = bedService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(Bed object) {
        return object.getDepartment().getName() + " " + object.getRoomNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bed fromString(String string) {
        return bedService.findAll().stream().filter(object ->
                (object.getDepartment().getName() + " " + object.getRoomNumber())
                        .equals(string)).findFirst().orElse(null);
    }
}
