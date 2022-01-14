package com.example.lab6v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import socialnetwork.domain.Notification;
import socialnetwork.service.ServiceManager;

import java.io.IOException;
import java.util.List;

public class AllNotificationsController {
    private final ServiceManager sM = ServiceManager.getInstance();

    public VBox vBox;


    @FXML
    public void initialize() {
        load();
    }

    private void load() {
        List<Notification> notificationList = sM.getSrvEvents().getNotifications(MainViewController.getIdLogin());
        for(Notification n : notificationList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("notificationView.fxml"));
            Parent root = null;
            try {
                root =  loader.load();
                System.out.println("root loaded");
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            NotificationController ctrl = loader.getController();
            ctrl.setValues(n);
            vBox.getChildren().add(root);
            }

        }

    public void onRefreshButton(ActionEvent actionEvent) {
        vBox.getChildren().clear();
        load();
    }
}


