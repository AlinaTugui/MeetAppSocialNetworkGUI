package com.example.lab6v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import socialnetwork.domain.Raport;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.UtilizatorRow;
import socialnetwork.service.ServiceManager;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class RapoarteController implements Initializable {
    private ServiceManager sM = ServiceManager.getInstance();
    public Button exportButton1;
    public TextField dateActivity;
    public Button exportButton2;
    public TextField friendName;
    public TextField dateMessages;
    public ObservableList<Raport> modelRapoarte1 = FXCollections.observableArrayList();
    public ObservableList<Raport> modelRapoarte2 = FXCollections.observableArrayList();
    public TableView<Raport> table1;
    public TableView<Raport> table2;
    public TableColumn<Raport, String> tableActivity1;
    public TableColumn<Raport, LocalDate> tableDate1;
    public TableColumn<Raport, String> tableMessages2;
    public TableColumn<Raport, LocalDate> tableDate2;

    private List<Raport> load(LocalDate d1, LocalDate d2){
        Iterable<Raport>  i = sM.getSrvPrietenie().findAllUserDate(MainViewController.getIdLogin(), d1, d2);
        List<Raport> l1 = StreamSupport.stream(i.spliterator(), false).toList();
        Iterable<Raport> i2 = sM.getSrvMesaje().afisareConversatiiUseriData(MainViewController.getIdLogin(), d1,d2);
        List<Raport> l2 = StreamSupport.stream(i2.spliterator(),false).toList();
        return Stream.concat(l1.parallelStream(), l2.parallelStream()).toList();
    }

    private List<Raport> load2(String firstName, String lastName, LocalDate d1, LocalDate d2){
        Utilizator u2 = sM.getSrvUtilizator().findAllByName(firstName, lastName).stream().limit(1).toList().get(0);
        List<Raport> rapoarte = new ArrayList<>();
        sM.getSrvMesaje().afisareConversatie(MainViewController.getIdLogin(),u2.getId())
                .forEach(mesajConv -> {
                    if(mesajConv.getDateTime().toLocalDate().compareTo(d1) >= 0 &&
                            mesajConv.getDateTime().toLocalDate().compareTo(d2) <= 0
                     && mesajConv.getFrom().getId().equals(u2.getId()))
                        rapoarte.add(new Raport(mesajConv.getMsg(), mesajConv.getDateTime().toLocalDate()));
                });
        return rapoarte;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableActivity1.setCellValueFactory(new PropertyValueFactory<Raport, String>("mesaj"));
        tableDate1.setCellValueFactory(new PropertyValueFactory<Raport, LocalDate>("date"));
        tableMessages2.setCellValueFactory(new PropertyValueFactory<Raport, String>("mesaj"));
        tableDate2.setCellValueFactory(new PropertyValueFactory<Raport, LocalDate>("date"));
        table1.setItems(modelRapoarte1);
        table2.setItems(modelRapoarte2);
    }

    public void onFindButton1(ActionEvent actionEvent) {
        //10/01/2022 - 13/01/2022
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String[] dates = dateActivity.getText().split("-");
        modelRapoarte1.setAll(
                load(LocalDate.parse(dates[0].strip(),formatter),LocalDate.parse(dates[1].strip(),formatter)));
    }

    public void onFindButton2(ActionEvent actionEvent) {
        //10/01/2022 - 13/01/2022
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String[] dates = dateMessages.getText().split("-");
        String[] nume = friendName.getText().split(" ");
        modelRapoarte2.setAll(
                load2(nume[0].strip(), nume[1].strip()
                        ,LocalDate.parse(dates[0].strip(),formatter),
                        LocalDate.parse(dates[1].strip(),formatter)));
    }
}
