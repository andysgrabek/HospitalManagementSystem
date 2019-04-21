package work.in.progress.hospitalmanagement.event;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.Setter;
import work.in.progress.hospitalmanagement.model.Patient;

public class PatientEditEvent extends Event {

    public static final EventType<PatientEditEvent> EDIT_EVENT = new EventType<>(ANY, "EDIT_EVENT");
    public static final EventType<PatientEditEvent> DELETE_EVENT = new EventType<>(ANY, "DELETE_EVENT");

    @Getter
    @Setter
    private Patient patient;

    public PatientEditEvent(EventType<? extends Event> eventType, Patient p) {
        super(eventType);
        patient = p;
    }

}
