package com.example.lab6v2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import socialnetwork.domain.Cerere;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.UtilizatorRow;
import socialnetwork.service.ServiceManager;

import java.util.ArrayList;
import java.util.List;

public class ShowFriendsController {
    private ServiceManager sM=ServiceManager.getInstance();

    ObservableList<UtilizatorRow> modelPrieteni = FXCollections.observableArrayList();

    @FXML
    public TextField textFieldSearch;
    @FXML
    TableColumn<UtilizatorRow, String> tableColumnFirstName = new TableColumn<>("firstName");
    @FXML
    TableColumn<UtilizatorRow, String> tableColumnLastName = new TableColumn<>("lastName");
    @FXML
    TableColumn<UtilizatorRow, Button> tableColumnButton = new TableColumn<>("bDel");
    @FXML
    TableView<UtilizatorRow> tableViewPrieteni= new TableView<>();

    private Button createDeleteButton(Long id) {
        Button deleteButton=new Button("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sM.getSrvPrietenie().deletePrietenie(id, MainViewController.getIdLogin());
                modelPrieteni.setAll(load());
            }
        });
        return deleteButton;
    }

    private List<UtilizatorRow> load(){
        List<UtilizatorRow> listaPrieteni = new ArrayList<>();
        Iterable<Long> prieteniID = sM.getSrvPrietenie().findAllUser(MainViewController.getIdLogin());
        prieteniID.forEach(x -> {
            UtilizatorRow u = new UtilizatorRow(sM.getSrvUtilizator().findOne(x));
            u.setbDel(createDeleteButton(x));
            listaPrieteni.add(u);
        });
        return listaPrieteni;
    }

    public void initialize() {
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<UtilizatorRow, String>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<UtilizatorRow, String>("lastName"));
        tableColumnButton.setCellValueFactory(new PropertyValueFactory<UtilizatorRow, Button>("bDel"));
        modelPrieteni.setAll(load());
        tableViewPrieteni.setItems(modelPrieteni);
    }
}
