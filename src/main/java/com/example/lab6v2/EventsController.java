package com.example.lab6v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import socialnetwork.domain.Event;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.ServiceManager;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EventsController {
    private final ServiceManager sM=ServiceManager.getInstance();
    public ScrollPane eventsScrollPane;

    public VBox eventsVBox;

    public TextField textFieldSearchEvent;

    public TextField eventNameTextField;

    public TextArea descriptionTextArea;

    public DatePicker beginDatePicker;

    public DatePicker endDatePicker;

    public Button createEventButton;

    public ComboBox<String> beginHour;

    public ComboBox<String> beginMinute;

    public ComboBox<String> endHour;

    public ComboBox<String> endMinute;

    public void initialize(){
        ObservableList<String> hours = FXCollections.observableArrayList();
        for(int i = 0; i <= 9; i++){
            hours.add(i, "0" + i);
        }
        for(int i = 10; i <= 23; i++){
            hours.add(i, "" + i);
        }
        beginHour.setItems(hours);
        endHour.setItems(hours);

        ObservableList<String> minutes = FXCollections.observableArrayList();
        minutes.add(0,"00");
        minutes.add(1, "15");
        minutes.add(2, "30");
        minutes.add(3, "45");
        beginMinute.setItems(minutes);
        endMinute.setItems(minutes);
        textFieldSearchEvent.textProperty().addListener(o -> searchEvent());
        setDefaultEvent();
        load(sM.getSrvEvents().getAllEvents());
    }

    private void searchEvent(){
        String searchText = textFieldSearchEvent.getText();
        Predicate<Event> predicateSearch = event -> event.getName().contains(searchText);
        setupEventsVBox(sM.getSrvEvents().getAllEvents()
                .stream()
                .filter(predicateSearch)
                .collect(Collectors.toList()));
    }

    public void onCreateEventButtonClick(){
        String name = eventNameTextField.getText();
        String description = descriptionTextArea.getText();
        LocalDateTime begin = beginDatePicker.getValue().atTime(Integer.parseInt(beginHour.getValue()), Integer.parseInt(beginMinute.getValue()));
        LocalDateTime end = endDatePicker.getValue().atTime(Integer.parseInt(endHour.getValue()), Integer.parseInt(endMinute.getValue()));
        try{
            Utilizator u = sM.getSrvUtilizator().findOne(MainViewController.getIdLogin());
            String userName = u.getFirstName()+ " " + u.getLastName();
            sM.getSrvEvents().addEvent(name, description, begin, end, userName);
            setupEventsVBox(sM.getSrvEvents().getAllEvents());
            setDefaultEvent();
        } catch (RuntimeException e){
            System.out.println("Exceptie");
        }
    }

    private void setDefaultEvent(){

        eventNameTextField.clear();
        descriptionTextArea.clear();
        beginHour.getSelectionModel().select(12);
        beginMinute.getSelectionModel().selectFirst();

        endHour.getSelectionModel().selectFirst();
        endMinute.getSelectionModel().selectFirst();

        beginDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now().plusDays(1));
    }

    private void setupEventsVBox(List<Event> events) {
        eventsVBox.getChildren().clear();
        load(events);
        eventsScrollPane.setFitToWidth(true);
        eventsScrollPane.setContent(eventsVBox);
        System.out.println(eventsVBox.getWidth());
        System.out.println(eventsScrollPane.getWidth());
    }

    private void load(List<Event> events) {
        System.out.println(events);
        for(Event e:events) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("eventCellView.fxml"));
            Parent root = null;
            try {
                root =  loader.load();
                System.out.println("root loaded");
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            EventCell ctrl = loader.getController();
            ctrl.setValues(e);
            eventsVBox.getChildren().add(root);
        }
    }

}
