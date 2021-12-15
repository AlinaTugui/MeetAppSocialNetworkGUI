package com.example.lab6v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import socialnetwork.domain.Cerere;
import socialnetwork.domain.UtilizatorRow;
import socialnetwork.service.ServiceManager;

import java.io.IOException;
import java.util.List;

public class AddFriendsController {
    private ServiceManager sM=ServiceManager.getInstance();

    ObservableList<UtilizatorRow> modelPrieteni = FXCollections.observableArrayList();

    @FXML
    TableColumn<UtilizatorRow, String> tableColumnName = new TableColumn<>("nume");
    @FXML
    TableColumn<UtilizatorRow, Button> tableColumnButton = new TableColumn<>("bDel");
    @FXML
    TableView<UtilizatorRow> tableViewPrieteni= new TableView<>();

    public AddFriendsController() throws IOException {
    }

    private Button createDeleteButton(Long id) {
        Button deleteButton=new Button("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sM.getSrvPrietenie().deletePrietenie(id, MainViewController.getIdLogin());
            }
        });
        return deleteButton;
    }

}
