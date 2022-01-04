package socialnetwork.domain;

import javafx.scene.control.Button;

import java.util.Objects;

public class UserRecord extends Entity<Long>{
    private String name;
    private Button btnAddFriend;

    public UserRecord(Long id, String name) {
        super.setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Button getBtnAddFriend() {
        return btnAddFriend;
    }

    public void setBtnAddFriend(Button btnAddFriend) {
        this.btnAddFriend = btnAddFriend;
    }
    @Override
    public String toString() {
        return "Utilizator{" +
                "Id=" + super.getId() +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        UserRecord that = (UserRecord) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

}
