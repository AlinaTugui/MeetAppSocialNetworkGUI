package com.example.lab6v2;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import socialnetwork.domain.Utilizator;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChatUserPaneController implements Initializable{

    private ChatPaneController chatPaneController;
    public Circle poza;
    public Label nume = new Label();
    private Utilizator user;

    public void setValues(Utilizator u, ChatPaneController chatPaneController){
        this.nume.setText(u.getFirstName() + " " + u.getLastName());
        this.user = u;
        this.chatPaneController = chatPaneController;
    }

    public void openUserChat(MouseEvent mouseEvent) {
        chatPaneController.changeRightPane(user);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
