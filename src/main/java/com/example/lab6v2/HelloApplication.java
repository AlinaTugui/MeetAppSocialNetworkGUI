package com.example.lab6v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        VBox root=loader.load();

        HelloController ctrl=loader.getController();
        ctrl.initialize();

        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.setTitle("Social Network");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}