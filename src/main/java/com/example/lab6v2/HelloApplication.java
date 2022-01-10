package com.example.lab6v2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view1.fxml"));
        AnchorPane root=loader.load();

        HelloController ctrl=loader.getController();

        primaryStage.setScene(new Scene(root));
        //primaryStage.setTitle("Social Network");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeScene(String fxml, ActionEvent actionEvent,String title) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
        addScene(fxml,title);

    }

    public static void addScene(String fxml, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(HelloApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("StyleSheet.css")).toExternalForm());
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}