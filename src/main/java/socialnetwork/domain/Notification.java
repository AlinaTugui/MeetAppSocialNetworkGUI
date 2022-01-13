package socialnetwork.domain;

import java.time.LocalDateTime;

public class Notification {
    String message;
    LocalDateTime time;
    String eventName;
    public Notification(String message, LocalDateTime time, String eventName) {
        this.message = message;
        this.time = time;
        this.eventName = eventName;
    }

    public Notification(String message, LocalDateTime time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getEventName() {
        return eventName;
    }
}
