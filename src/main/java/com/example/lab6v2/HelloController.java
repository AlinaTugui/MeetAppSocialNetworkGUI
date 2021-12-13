package com.example.lab6v2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import socialnetwork.service.*;
import java.io.IOException;

public class HelloController {

    private static ServiceManager sM = ServiceManager.getInstance();

    @FXML
    private Label welcomeText;

    public void openLogin(ActionEvent actionEvent) throws IOException {
        HelloApplication.changeScene("login.fxml", actionEvent,"Login");
    }

}