package com.example.lab6v2;

import javafx.event.ActionEvent;

import java.io.IOException;

public class MainViewController {
    private static Long idLogin;
    public static Long getIdLogin() {return idLogin;}
    public static void setIdLogin(Long _idLogin) {
        idLogin = _idLogin;
    }

    public void test(ActionEvent actionEvent) throws IOException {
        HelloApplication.addScene("requests-view.fxml","Requests");
    }

    public void showFriends(ActionEvent actionEvent) throws IOException {
        HelloApplication.addScene("show-friends-view.fxml", "Friends");
    }

    public void openAddFriends(ActionEvent actionEvent) throws IOException {
        HelloApplication.addScene("openAddFriendView.fxml", "Add Friends");
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        HelloApplication.changeScene("hello-view.fxml", actionEvent, "Main View");
    }
}
