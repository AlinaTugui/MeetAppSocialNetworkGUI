package com.example.lab6v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import socialnetwork.Main;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.database.*;
import socialnetwork.service.*;
import socialnetwork.ui.UI;

import java.io.IOException;

public class HelloController {

    private ServiceManager sM;

    @FXML
    private Label welcomeText;

    public void initialize(){
       sM = ServiceManager.getInstance();
    }

    public void openLogin(ActionEvent actionEvent) throws IOException {
        HelloApplication.changeScene("login.fxml", actionEvent);
    }

}