package com.example.lab6v2;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChatUserPaneController implements Initializable{

    public Circle poza;
    public Label nume = new Label();


    public void setNume(String nume){
        this.nume.setText(nume);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
