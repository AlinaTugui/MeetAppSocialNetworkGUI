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
import socialnetwork.service.ServiceManager;

import java.util.List;

public class AddFriendsController {
    private ServiceManager sM=ServiceManager.getInstance();

    ObservableList<AfisarePrietenRow> modelPrieteni = FXCollections.observableArrayList();

    @FXML
    TableColumn<AfisarePrietenRow, String> tableColumnName = new TableColumn<>("nume");
    @FXML
    TableColumn<AfisarePrietenRow, Button> tableColumnButton = new TableColumn<>("bDel");
    @FXML
    TableView<AfisarePrietenRow> tableViewPrieteni= new TableView<>();

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

    @FXML
    protected void onFriendRequestsButtonClick() {
        System.out.println(sM.getSrvCereri().cereriUtilizator(1L));
        List<Cerere> res=sM.getSrvCereri().cereriUtilizator(1L);
        for(Cerere c : res) {
            c.setButtonAccept(createAcceptButton(c.getId()));
            c.setButtonDecline(createDeclineButton(c.getId()));
        }
        modelCereri.setAll(res);
    }

    public void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<AfisarePrietenRow, String>("nume"));
        tableColumnButton.setCellValueFactory(new PropertyValueFactory<AfisarePrietenRow, Button>("bDel"));
        tableViewPrieteni.setItems(modelPrieteni);

        textFieldName
    }
}
