package socialnetwork.domain;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UtilizatorRow extends Entity<Long>{
    private String firstName;
    private String lastName;
    private Button bDel;

    public UtilizatorRow(Long id, String firstName, String lastName) {
        super.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UtilizatorRow(Utilizator u) {
        super.setId(u.getId());
        this.firstName = u.getFirstName();
        this.lastName = u.getLastName();
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "Id=" + super.getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }

    public Button getbDel() {
        return bDel;
    }

    public void setbDel(Button bDel) {
        this.bDel = bDel;
    }


}