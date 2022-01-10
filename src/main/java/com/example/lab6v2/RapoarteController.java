package com.example.lab6v2;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import socialnetwork.domain.UtilizatorRow;

import java.net.URL;
import java.util.ResourceBundle;

public class RapoarteController implements Initializable {
    public TableView table1;
    public Button exportButton1;
    public TextField dateActivity;
    public Button findButton1;
    public Button exportButton2;
    public TextField friendName;
    public TextField dateMessages;
    public Button findButton2;
    public TableView table2;
    public TableColumn tableType1;
    public TableColumn tableDate1;
    public TableColumn tableMessages2;
    public TableColumn tableDate2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableType1.setCellValueFactory(new PropertyValueFactory<UtilizatorRow, String>("type"));
        tableDate1.setCellValueFactory(new PropertyValueFactory<UtilizatorRow, String>("date1"));
        tableMessages2.setCellValueFactory(new PropertyValueFactory<UtilizatorRow, Button>("message"));
        tableDate2.setCellValueFactory(new PropertyValueFactory<UtilizatorRow, Button>("date2"));
        //modelPrieteni.setAll(load());
       // tableViewPrieteni.setItems(modelPrieteni);
    }
}
