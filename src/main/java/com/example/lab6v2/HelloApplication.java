package com.example.lab6v2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
//    private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        VBox root=loader.load();

        HelloController ctrl=loader.getController();
        ctrl.initialize();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Social Network");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeScene(String fxml, ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(HelloApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Login window");
        stage.setScene(scene);
        stage.show();
    }
}