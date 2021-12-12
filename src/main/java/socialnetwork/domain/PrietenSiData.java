package socialnetwork.domain;

import java.time.LocalDate;

public class PrietenSiData {
    private String firstName;
    private String lastName;
    private LocalDate data;

    @Override
    public String toString() {
        return "PrietenSiData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", data=" + data +
                '}';
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public PrietenSiData(String firstName, String lastName, LocalDate data) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.data = data;
    }
}
