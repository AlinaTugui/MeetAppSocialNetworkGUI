package com.example.lab6v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import socialnetwork.domain.Cerere;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.database.*;
import socialnetwork.service.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class FriendRequestsController  {
    private ServiceManager sM=ServiceManager.getInstance();

    ObservableList<Cerere> modelCereri = FXCollections.observableArrayList();
    @FXML
    TableColumn<Cerere, Long> tableColumnIdSender= new TableColumn<>("Id sender");

    @FXML
    TableColumn<Cerere, String> tableColumnStatus=new TableColumn<>("Status");

    @FXML
    TableColumn<Cerere, LocalDateTime> tableColumnTimeStamp=new TableColumn<>("Timestamp");

    @FXML
    TableColumn<Cerere, Button> tableColumnAccept=new TableColumn<>("Accept Request");

    @FXML
    TableColumn<Cerere, Button> tableColumnDecline=new TableColumn<>("Decline Request");

    @FXML
    TableView<Cerere> tableViewCereri= new TableView<>();
    TableColumn actionCol = new TableColumn("Action");

    public FriendRequestsController() throws IOException {
    }

    private Button createAcceptButton(Long id) {
        Button acceptButton=new Button("Accept");
        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sM.getSrvCereri().acceptCerere(id);
                modelCereri.remove(sM.getSrvCereri().findOne(id));
            }
        }
        );
        return acceptButton;
    }

    private Button createDeclineButton(Long id) {
        Button declineButton=new Button("Decline");
        declineButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sM.getSrvCereri().declineCerere(id);
                modelCereri.remove(sM.getSrvCereri().findOne(id));
            }
        });
        return declineButton;
    }

    @FXML
    protected void onFriendRequestsButtonClick() {
        System.out.println(sM.getSrvCereri().cereriUtilizator(MainViewController.getIdLogin()));
        List<Cerere> res=sM.getSrvCereri().cereriUtilizator(MainViewController.getIdLogin());
        for(Cerere c : res) {
            c.setButtonAccept(createAcceptButton(c.getId()));
            c.setButtonDecline(createDeclineButton(c.getId()));

        }
        modelCereri.setAll(res);
    }

    public void initialize() {
        tableColumnIdSender.setCellValueFactory(new PropertyValueFactory<Cerere, Long>("from"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<Cerere, String>("status"));
        tableColumnTimeStamp.setCellValueFactory(new PropertyValueFactory<Cerere, LocalDateTime>("timestamp"));
        tableColumnAccept.setCellValueFactory(new PropertyValueFactory<Cerere, Button>("buttonAccept"));
        tableColumnDecline.setCellValueFactory(new PropertyValueFactory<Cerere, Button>("buttonDecline"));
        tableViewCereri.setItems(modelCereri);
    }
}
