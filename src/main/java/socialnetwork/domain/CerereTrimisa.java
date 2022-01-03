package socialnetwork.domain;

import javafx.scene.control.Button;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CerereTrimisa extends Entity<Long>{
    Long id;
    Long to;
    Long from;
    String receiverName;
    LocalDateTime timestamp;
    private Button buttonUnsend;

    public void setButtonUnsend(Button buttonUnsend) {
        this.buttonUnsend = buttonUnsend;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public Button getButtonUnsend() {
        return buttonUnsend;
    }

    public CerereTrimisa(Long from, Long to, String receiverName, LocalDateTime timestamp) {
        this.to = to;
        this.from = from;
        this.receiverName = receiverName;
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
                ", receiverName='" + receiverName + '\'' +
                ", timestamp=" + timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                '}';
    }

}
