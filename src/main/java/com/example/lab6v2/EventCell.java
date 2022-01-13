package com.example.lab6v2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import socialnetwork.domain.Event;
import socialnetwork.service.ServiceManager;
import java.time.format.DateTimeFormatter;

public class EventCell {
    private final ServiceManager sM = ServiceManager.getInstance();
    @FXML
    public Label eventNameLabel;

    @FXML
    public Label descriptionLabel;

    @FXML
    public Label beginDateLabel;

    @FXML
    public Label endDateLabel;

    @FXML
    public Button subscribeButton;


    public void setValues(Event e) {
        if (e == null) {
            System.out.println("null");
        } else {
            if (sM.getSrvEvents().findSubscription(e.getId()) != null) {
                this.subscribeButton.setText("UNSUBSCRIBE");
            } else {
                this.subscribeButton.setText("SUBSCRIBE");
            }
            this.eventNameLabel.setText(e.getName());
            this.descriptionLabel.setText(e.getDescription());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd](HH:mm)");
            this.beginDateLabel.setText("Begin: " + e.getStartDate().format(formatter));
            this.endDateLabel.setText("End: " + e.getEndDate().format(formatter));
            subscribeButton.setOnAction(x->{
                try {
                    if (subscribeButton.getText().equals("SUBSCRIBE")) {
                        sM.getSrvEvents().subscribeToEvent(e.getId());
                        subscribeButton.setText("UNSUBSCRIBE");
                    } else {
                        sM.getSrvEvents().unsubscribeFromEvent(e.getId());
                        subscribeButton.setText("SUBSCRIBE");
                    }

                } catch (RuntimeException ex) {
                    System.out.println("Exceptie");
                }
            });
        }
    }
}
