package work.in.progress.hospitalmanagement.event;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.Setter;
import work.in.progress.hospitalmanagement.model.Person;

/**
 * Custom event class for handling the deletions and updates of patients in the list available in the
 * {@link work.in.progress.hospitalmanagement.controller.PatientRegistrationViewController} screen.
 * @author Andrzej Grabowski
 */
public class PersonEvent<T extends Person> extends Event {

    public static final EventType<PersonEvent> EDIT_EVENT = new EventType<>(ANY, "EDIT_EVENT");
    public static final EventType<PersonEvent> DELETE_EVENT = new EventType<>(ANY, "DELETE_EVENT");

    @Getter
    @Setter
    private T person;

    /**
     * Creates an event with the given type and patient.
     * @param eventType selected event type. Possible event types are edit and delete events.
     * @param p the patient associated with the event
     */
    public PersonEvent(EventType<? extends Event> eventType, T p) {
        super(eventType);
        person = p;
    }

}
