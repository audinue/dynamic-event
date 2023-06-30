package dynamic.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class EventListener {

    private Map<EventTarget, Map<EventType, List<EventHandler>>> records;

    public <E> void listen(EventTarget target, EventType<E> type, EventHandler<E> handler) {
        if (records == null) {
            records = new HashMap<EventTarget, Map<EventType, List<EventHandler>>>();
        }
        Map<EventType, List<EventHandler>> types = records.get(target);
        if (types == null) {
            types = new HashMap<EventType, List<EventHandler>>();
            records.put(target, types);
        }
        List<EventHandler> handlers = types.get(type);
        if (handlers == null) {
            handlers = new ArrayList<EventHandler>();
            types.put(type, handlers);
        }
        target.on(type, handler);
        handlers.add(handler);
    }

    public <E> void unlisten(EventTarget target, EventType<E> type, EventHandler<E> handler) {
        List<EventHandler> handlers = records.get(target).get(type);
        target.off(type, handler);
        handlers.remove(handler);
    }

    public <E> void unlisten(EventTarget target, EventType<E> type) {
        List<EventHandler> handlers = records.get(target).get(type);
        for (EventHandler handler : handlers) {
            target.off(type, handler);
        }
        handlers.clear();
    }

    public <E> void unlisten(EventTarget target) {
        for (Map.Entry<EventType, List<EventHandler>> entry : records.get(target).entrySet()) {
            for (EventHandler handler : entry.getValue()) {
                target.off(entry.getKey(), handler);
            }
            entry.getValue().clear();
        }
    }

    public <E> void unlisten() {
        for (Map.Entry<EventTarget, Map<EventType, List<EventHandler>>> target : records.entrySet()) {
            for (Map.Entry<EventType, List<EventHandler>> type : target.getValue().entrySet()) {
                for (EventHandler handler : type.getValue()) {
                    target.getKey().off(type.getKey(), handler);
                }
                type.getValue().clear();
            }
        }
    }

    public <E> void listenOnce(final EventTarget target, final EventType<E> type, final EventHandler<E> handler) {
        listen(target, type, new EventHandler<E>() {
            public void handle(E e) {
                unlisten(target, type, this);
                handler.handle(e);
            }
        });
    }
}
