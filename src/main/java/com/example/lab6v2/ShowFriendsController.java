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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import socialnetwork.domain.UtilizatorRow;
import socialnetwork.service.ServiceManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowFriendsController implements Initializable {
    private ServiceManager sM=ServiceManager.getInstance();

    public ObservableList<UtilizatorRow> modelPrieteni = FXCollections.observableArrayList();

    public TableColumn<UtilizatorRow, String> columnFirstName;
    public TableColumn<UtilizatorRow, String> columnLastName;
    public TableColumn<UtilizatorRow, Button> columnButton;
    public TableView<UtilizatorRow> tableViewPrieteni;

    public ShowFriendsController() throws IOException {
    }

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
        if(prieteniID == null) return modelPrieteni;
        prieteniID.forEach(x -> {
            UtilizatorRow u = new UtilizatorRow(sM.getSrvUtilizator().findOne(x));
            u.setBDel(createDeleteButton(x));
            listaPrieteni.add(u);
        });
        return listaPrieteni;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnFirstName.setCellValueFactory(new PropertyValueFactory<UtilizatorRow, String>("firstName"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<UtilizatorRow, String>("lastName"));
        columnButton.setCellValueFactory(new PropertyValueFactory<UtilizatorRow, Button>("bDel"));
        modelPrieteni.setAll(load());
        tableViewPrieteni.setItems(modelPrieteni);
    }
}
