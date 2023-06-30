package dynamic.event;

public interface EventHandler<E> {
    
    void handle(E e);
}
