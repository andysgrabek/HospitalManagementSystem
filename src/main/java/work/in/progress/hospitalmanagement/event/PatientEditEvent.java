package work.in.progress.hospitalmanagement.event;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.Setter;
import work.in.progress.hospitalmanagement.model.Patient;

public class PatientEditEvent extends Event {

    public static final EventType<PatientEditEvent> PATIENT_EDIT_EVENT_EVENT_TYPE = new EventType<>(ANY);

    @Getter
    @Setter
    private Patient patient;

    public PatientEditEvent(EventType<? extends Event> eventType, Patient p) {
        super(eventType);
        patient = p;
    }

}
