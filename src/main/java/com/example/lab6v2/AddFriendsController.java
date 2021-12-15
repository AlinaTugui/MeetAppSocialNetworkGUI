package com.example.lab6v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.RepositoryException;
import socialnetwork.service.ServiceManager;

import java.io.IOException;
import java.util.List;

public class AddFriendsController {
    private final ServiceManager sM = ServiceManager.getInstance();


    ObservableList<Utilizator> modelPrieteni = FXCollections.observableArrayList();
    @FXML
    ListView<Utilizator> listView = new ListView<>();
    @FXML
    TextField textField;

    @FXML
    TextField textFieldLastName;

    @FXML
    protected void initialize() {
    }

    static class CustomCell extends ListCell<Utilizator> {
        HBox hbox= new HBox();
        Label label = new Label();
        Button button = new Button("Add friend");
        Utilizator u;
        Label exceptionLabel = new Label();
        private ServiceManager sM = ServiceManager.getInstance();
        public CustomCell() {
            super();
            button.setVisible(false);
            hbox.getChildren().addAll(label, button, exceptionLabel);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        sM.getSrvCereri().trimiteCerereDePrietenie(MainViewController.getIdLogin(), u.getId());
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                        exceptionLabel.setText(exception.getMessage());
                        button.setVisible(false);
                    }
                };
            });
        }
        @Override
        protected void updateItem(Utilizator item, boolean empty) {
            super.updateItem(item, empty);
            if(empty) {
                u=null;
                setGraphic(null);
            } else {

                u = item;
                System.out.println(u);
                if (sM.getSrvPrietenie().findOne(new Tuple<Long, Long>(item.getId(),
                        MainViewController.getIdLogin())) == null && sM.getSrvPrietenie().findOne(new Tuple<>(MainViewController.getIdLogin(),
                        item.getId())) == null) {
                    label.setText(u.getFirstName() + " " + u.getLastName());
                    button.setVisible(true);

                }
                else {
                    label.setText(u.getFirstName() + " " + u.getLastName()+" "+"Already friends!");
                }
            }
            setGraphic(hbox);
        }
    }

    @FXML
    protected void onSearchButtonClick() {

        String firstName = textField.getText();
        System.out.println(firstName);
        String lastName = textFieldLastName.getText();
        System.out.println(lastName);
        List<Utilizator> users = sM.getSrvUtilizator().findAllByName(firstName, lastName);
        modelPrieteni.setAll(users);
        listView.setItems(modelPrieteni);

        listView.setCellFactory(new Callback<ListView<Utilizator>, ListCell<Utilizator>>() {
            @Override
            public ListCell<Utilizator> call(ListView<Utilizator> param) {
                return new CustomCell();
            }
    });
    }

    private Button createAddFriendBtn(Long idReceiver) {
        Button btn = new Button("Add friend");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sM.getSrvCereri().trimiteCerereDePrietenie(MainViewController.getIdLogin(),idReceiver);
            }
        });
        return btn;
    }

}
