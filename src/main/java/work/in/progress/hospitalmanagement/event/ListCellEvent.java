package work.in.progress.hospitalmanagement.event;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import lombok.Setter;

/**
 * Custom event class for handling the deletions and updates of subjects in various list views
 * @author Andrzej Grabowski
 */
public class ListCellEvent<T> extends Event {

    public static final EventType<ListCellEvent> EDIT_EVENT = new EventType<>(ANY, "EDIT_EVENT");
    public static final EventType<ListCellEvent> DELETE_EVENT = new EventType<>(ANY, "DELETE_EVENT");
    public static final EventType<ListCellEvent> NEW_EVENT = new EventType<>(ANY, "NEW_EVENT");

    @Getter
    @Setter
    private T subject;

    /**
     * Creates an event with the given type and subject.
     * @param eventType selected event type. Possible event types are edit and delete events.
     * @param p the patient associated with the event
     */
    public ListCellEvent(EventType<? extends Event> eventType, T p) {
        super(eventType);
        subject = p;
    }

}
