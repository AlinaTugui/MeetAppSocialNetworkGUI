package socialnetwork.domain;

import javafx.scene.control.Button;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CererePrimita extends Entity<Long>{
    Long id;
    Long to;
    Long from;
    String senderName;
    LocalDateTime timestamp;
    private Button buttonAccept;
    private Button buttonDecline;

    public CererePrimita(Long from, Long to, String senderName, LocalDateTime timestamp) {
        this.to = to;
        this.from = from;
        this.senderName = senderName;
        this.timestamp = timestamp;
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
                ", senderName='" + senderName + '\'' +
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

    public String getSenderName() {
        return senderName;
    }

    public void setButtonDecline(Button buttonDecline) {
        this.buttonDecline = buttonDecline;
    }
}
