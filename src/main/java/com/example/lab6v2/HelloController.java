package com.example.lab6v2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import socialnetwork.service.*;


import java.io.IOException;

public class HelloController {

    private ServiceManager sM = ServiceManager.getInstance();

    public Label welcomeText;
    public Circle btnClose;

    public void openLogin(ActionEvent actionEvent) throws IOException {
        HelloApplication.changeScene("login.fxml", actionEvent,"Login");
    }

    public void fastLogin(ActionEvent actionEvent) throws IOException {
        MainViewController.setIdLogin(2L);
        HelloApplication.changeScene("mainView.fxml", actionEvent,"Main View");
    }

    public void openRegister(ActionEvent actionEvent) throws IOException {
        HelloApplication.changeScene("register.fxml", actionEvent,"Login");
    }

    public void handleMouseEvent(MouseEvent mouseEvent) {
        if(mouseEvent.getSource()==btnClose)
            System.exit(0);
    }
}