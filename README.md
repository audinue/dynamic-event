# dynamic-event

Java 5+ dynamic event library.

```java
class KeyEvent {

  int button;

  KeyEvent(int button) {
    this.button = button;
  }
}

static final EventType<KeyEvent> KEY_DOWN = new EventType<KeyEvent>();

EventTarget game = new EventTarget();

game.on(KEY_DOWN, new EventHandler<KeyEvent>() {
  public void handle(KeyEvent e) {
    System.out.println("Key down: " + e.button);
  }
});

game.trigger(KEY_DOWN, new KeyEvent(65));
```
