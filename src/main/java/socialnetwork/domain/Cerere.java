package socialnetwork.domain;

import javafx.scene.control.Button;
import socialnetwork.domain.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cerere extends Entity<Long> {
    Long id;
    Long to;
    Long from;
    String status;
    LocalDateTime timestamp;
    private Button buttonAccept;
    private Button buttonDecline;
    private Button buttonUnsend;

    public void setButtonUnsend(Button buttonUnsend) {
        this.buttonUnsend = buttonUnsend;
    }

    public Button getButtonUnsend() {
        return buttonUnsend;
    }

    public Cerere(Long from, Long to, String status, LocalDateTime timestamp) {
        this.to = to;
        this.from = from;
        this.status = status;
        this.timestamp = timestamp;
        /*this.buttonAccept= new Button("Accept");
        this.buttonDecline= new Button("Decline");*/
    }

    public Cerere(Long id, Long from, Long to, String status, LocalDateTime timestamp) {
        this.id = id;
        this.to = to;
        this.from = from;
        this.status = status;
        this.timestamp = timestamp;
    }
    public String getStatus() {
        return status;
    }

    public Long getTo() {
        return to;
    }
    public Long getFrom() {
        return from;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Cerere{" +
                "to=" + to +
                ", from=" + from +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                '}';
    }

    public Button getButtonAccept() {
        return buttonAccept;
    }

    public Button getButtonDecline() {
        return buttonDecline;
    }

    public void setButtonAccept(Button buttonAccept) {
        this.buttonAccept = buttonAccept;
    }

    public void setButtonDecline(Button buttonDecline) {
        this.buttonDecline = buttonDecline;
    }
}
