package dynamic.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class EventTarget {

    private Map<EventType, List<EventHandler>> events;

    public <E> void on(EventType<E> type, EventHandler<E> handler) {
        if (events == null) {
            events = new HashMap<EventType, List<EventHandler>>();
        }
        List<EventHandler> handlers = events.get(type);
        if (handlers == null) {
            handlers = new ArrayList<EventHandler>();
            events.put(type, handlers);
        }
        handlers.add(handler);
    }

    public <E> void off(EventType<E> type, EventHandler<E> handler) {
        events.get(type).remove(handler);
    }

    public <E> void trigger(EventType<E> type, E e) {
        if (events == null) {
            return;
        }
        List<EventHandler> handlers = events.get(type);
        if (handlers == null) {
            return;
        }
        for (int i = handlers.size() - 1; i > -1; i--) {
            handlers.get(i).handle(e);
        }
    }

    public <E> void once(final EventType<E> type, final EventHandler<E> handler) {
        on(type, new EventHandler<E>() {
            public void handle(E e) {
                off(type, this);
                handler.handle(e);
            }
        });
    }
}
