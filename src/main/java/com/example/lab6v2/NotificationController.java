package com.example.lab6v2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import socialnetwork.domain.Notification;
import socialnetwork.service.ServiceManager;

import java.time.format.DateTimeFormatter;

public class NotificationController {
    private final ServiceManager sM = ServiceManager.getInstance();
    @FXML
    private Label msgLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label eventNameLabel;

    public void setValues(Notification notification) {
        if(notification == null) {
            System.out.println("null");
        }
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd](HH:mm)");
            msgLabel.setText(notification.getMessage());
            dateLabel.setText(notification.getTime().format(formatter));
            eventNameLabel.setText(notification.getEventName());
        }
    }
}
