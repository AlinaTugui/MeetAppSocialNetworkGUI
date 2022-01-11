package socialnetwork.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MesajConv extends Entity<Long> {
    Utilizator from;
    Grup toGroup;
    String msg;
    LocalDateTime dateTime;

    public MesajConv(Utilizator from, String msg, LocalDateTime dateTime) {
        this.from = from;
        this.msg = msg;
        this.dateTime = dateTime;
    }

    public MesajConv(Utilizator from, Grup toGroup, String msg, LocalDateTime dateTime) {
        this.from = from;
        this.toGroup = toGroup;
        this.msg = msg;
        this.dateTime = dateTime;
    }

    public Grup getToGroup() {
        return toGroup;
    }

    public void setToGroup(Grup toGroup) {
        this.toGroup = toGroup;
    }

    public Utilizator getFrom() {
        return from;
    }

    @Override
    public String toString() {
        return  "de la=" + from +
                ", ora=" + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+
                ", msg='" + msg + '\'';

    }

    public void setFrom(Utilizator from) {
        this.from = from;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


}
