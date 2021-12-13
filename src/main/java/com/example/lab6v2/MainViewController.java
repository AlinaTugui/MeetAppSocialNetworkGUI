package com.example.lab6v2;

import javafx.event.ActionEvent;

import java.io.IOException;

public class MainViewController {
    private static Long idLogin;

    public static void setIdLogin(Long _idLogin) {
        idLogin = _idLogin;
    }

    public void test(ActionEvent actionEvent) throws IOException {
        HelloApplication.addScene("requests-view.fxml","Requests");
    }
}
