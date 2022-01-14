package socialnetwork.domain;

import javafx.scene.control.Button;


public class UtilizatorFriends extends Entity<Long>{
    private String firstName;
    private String lastName;
        private Button btnAddFriend;

    @Override
    public String toString() {
        return "UtilizatorFriends{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", btnAddFriend=" + btnAddFriend +
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

    public Button getBtnAddFriend() {
        return btnAddFriend;
    }

    public void setBtnAddFriend(Button btnAddFriend) {
        this.btnAddFriend = btnAddFriend;
    }

    public UtilizatorFriends(Long id, String firstName, String lastName) {
        super.setId(id);
        this.firstName = firstName;
        this.lastName =  lastName;
    }

}
