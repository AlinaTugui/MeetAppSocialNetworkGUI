package com.example.lab6v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class MainViewController {
    private static Long idLogin;
    public AnchorPane rightPane;

    public static Long getIdLogin() {return idLogin;}
    public static void setIdLogin(Long _idLogin) {
        idLogin = _idLogin;
    }


    private void changeRightPane(String fxmlName) throws IOException {
        Pane newLoadedPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlName)));
        rightPane.getChildren().setAll(newLoadedPane);
    }

    public void showFriends(ActionEvent actionEvent) throws IOException {
        changeRightPane("show-friends-view.fxml");
    }

    public void openAddFriends(ActionEvent actionEvent) throws IOException {
        changeRightPane("AddFriendsNewView.fxml");
    }

    public void openFriendRequests(ActionEvent actionEvent) throws IOException {
        changeRightPane("FriendRequestsNewView.fxml");
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        HelloApplication.changeScene("hello-view.fxml", actionEvent, "Main View");
    }
}
