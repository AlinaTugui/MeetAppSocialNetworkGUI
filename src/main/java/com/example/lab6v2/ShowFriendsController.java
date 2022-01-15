package com.example.lab6v2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.UtilizatorRow;
import socialnetwork.service.ServiceManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.StreamSupport;

public class ShowFriendsController implements Initializable {
    private ServiceManager sM=ServiceManager.getInstance();

    public ObservableList<UtilizatorRow> modelPrieteni = FXCollections.observableArrayList();

    public TableColumn<UtilizatorRow, String> columnFirstName;
    public TableColumn<UtilizatorRow, String> columnLastName;
    public TableColumn<UtilizatorRow, Button> columnButton;
    public TableView<UtilizatorRow> tableViewPrieteni;


    private Button createDeleteButton(Long id) {
        Button deleteButton=new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #ffffff");
        deleteButton.setStyle("-fx-border-color: #2e107a");
        deleteButton.setStyle("-fx-border-radius: 20px");
        deleteButton.setStyle("-fx-background-radius: 20px");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sM.getSrvPrietenie().deletePrietenie(id, MainViewController.getIdLogin());
                modelPrieteni.setAll(load2());
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
        columnFirstName.prefWidthProperty().bind(tableViewPrieteni.widthProperty().multiply(0.4));
        columnLastName.prefWidthProperty().bind(tableViewPrieteni.widthProperty().multiply(0.4));
        columnButton.prefWidthProperty().bind(tableViewPrieteni.widthProperty().multiply(0.2));
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnButton.setCellValueFactory(new PropertyValueFactory<>("bDel"));
        modelPrieteni.setAll(load2());
        tableViewPrieteni.setItems(modelPrieteni);
    }

    private List<UtilizatorRow> load2(){
        List<UtilizatorRow> listaPrieteni = new ArrayList<>();
        Iterable<Prietenie> prieteniID = sM.getSrvPrietenie().getPrieteniiOnPage();
        if(prieteniID == null) return modelPrieteni;
        prieteniID.forEach(x -> {
            UtilizatorRow u = new UtilizatorRow(sM.getSrvUtilizator().findOne(x.getId().getLeft()));
            u.setBDel(createDeleteButton(x.getId().getLeft()));
            listaPrieteni.add(u);
        });
        return listaPrieteni;
    }

    public void handlePreviousPageButton() {
        Set<Prietenie> prietenii = sM.getSrvPrietenie().getPreviousPrietenii();
        if(prietenii != null && !prietenii.isEmpty()){
            modelPrieteni.setAll(load2());
        }
    }

    public void handleNextPageButton() {
        Set<Prietenie> prietenii = sM.getSrvPrietenie().getNextPrietenii();
        if(prietenii != null && !prietenii.isEmpty()){
            modelPrieteni.setAll(load2());
        }
    }
}
