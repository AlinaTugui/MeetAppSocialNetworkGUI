package socialnetwork.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Raport {
    private String mesaj;
    private LocalDate date;

    public Raport(String mesaj, LocalDate date) {
        this.mesaj = mesaj;
        this.date = date;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
