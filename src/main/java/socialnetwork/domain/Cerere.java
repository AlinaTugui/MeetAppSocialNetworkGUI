package socialnetwork.domain;

import socialnetwork.domain.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cerere extends Entity<Long> {
    Long to;
    Long from;
    String status;
    LocalDateTime timestamp;

    public Cerere(Long from, Long to, String status, LocalDateTime timestamp) {
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
    public String toString() {
        return "Cerere{" +
                "to=" + to +
                ", from=" + from +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                '}';
    }
}
