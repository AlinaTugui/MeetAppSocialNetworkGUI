package com.example.lab6v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

import java.net.URL;
import java.time.LocalDateTime;
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
    TableView<Cerere> tableViewCereri= new TableView<>();

    @FXML
    protected void onFriendRequestsButtonClick() {
        System.out.println(sM.getSrvCereri().cereriUtilizator(1L));
        modelCereri.setAll(sM.getSrvCereri().cereriUtilizator(1L));
    }

    public void initialize() {
        tableColumnIdSender.setCellValueFactory(new PropertyValueFactory<Cerere, Long>("from"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<Cerere, String>("status"));
        tableColumnTimeStamp.setCellValueFactory(new PropertyValueFactory<Cerere, LocalDateTime>("timestamp"));
        tableViewCereri.setItems(modelCereri);
    }
}
