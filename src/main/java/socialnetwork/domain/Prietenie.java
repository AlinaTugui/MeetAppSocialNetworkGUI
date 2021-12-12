package socialnetwork.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Prietenie extends Entity<Tuple<Long,Long>> {

    LocalDateTime date;

    public Prietenie(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "Id1: " + super.getId().getLeft() +
                ", Id2: " + super.getId().getRight() +
                ", date=" + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                '}';
    }

    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDateTime getDateTime() {
        return date;
    }
}
