package com.example.lab6v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import socialnetwork.domain.Cerere;
import socialnetwork.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FriendRequestsController {
    private final ServiceManager sM = ServiceManager.getInstance();

    ObservableList<Cerere> modelCereri = FXCollections.observableArrayList();
    @FXML
    TableColumn<Cerere, Long> tableColumnIdSender = new TableColumn<>("Id sender");

    @FXML
    TableColumn<Cerere, String> tableColumnStatus = new TableColumn<>("Status");

    @FXML
    TableColumn<Cerere, LocalDateTime> tableColumnTimeStamp = new TableColumn<>("Timestamp");

    @FXML
    TableColumn<Cerere, Button> tableColumnAccept = new TableColumn<>("Accept Request");

    @FXML
    TableColumn<Cerere, Button> tableColumnDecline = new TableColumn<>("Decline Request");
    @FXML
    TableColumn<Cerere, Button> tableColumnUnsend = new TableColumn<>("Unsend Request");

    @FXML
    TableView<Cerere> tableViewCereri = new TableView<>();


    private Button createAcceptButton(Long id) {
        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(event -> {
                    sM.getSrvCereri().acceptCerere(id);
                    modelCereri.remove(sM.getSrvCereri().findOne(id));
                }
        );
        return acceptButton;
    }

    private Button createDeclineButton(Long id) {
        Button declineButton = new Button("Decline");
        declineButton.setOnAction(event -> {
            sM.getSrvCereri().declineCerere(id);
            modelCereri.remove(sM.getSrvCereri().findOne(id));
        });
        return declineButton;
    }

    @FXML
    protected void onFriendRequestsButtonClick() {
        System.out.println(sM.getSrvCereri().cereriUtilizator(MainViewController.getIdLogin()));
        List<Cerere> res = sM.getSrvCereri().cereriUtilizator(MainViewController.getIdLogin());
        for (Cerere c : res) {
            c.setButtonAccept(createAcceptButton(c.getId()));
            c.setButtonDecline(createDeclineButton(c.getId()));

        }
        modelCereri.setAll(res);
    }

    @FXML
    protected void onSentFriendRequestsButtonClick() {
        List<Cerere> res = sM.getSrvCereri().findAll().stream()
                .filter(cerere -> (cerere.getFrom().equals(MainViewController.getIdLogin())
                        && cerere.getStatus().equals("pending")))
                .collect(Collectors.toList());
        System.out.println(res);
        for (Cerere c : res)
            c.setButtonUnsend(createUnsendButton(c.getFrom(), c.getTo(), c.getTimestamp()));
        modelCereri.setAll(res);
        tableViewCereri.setItems(modelCereri);
    }

    private Button createUnsendButton(Long idSender, Long idReceiver, LocalDateTime timestamp ) {
        Button unsendButton = new Button("Unsend");
        unsendButton.setOnAction(event -> {
                    sM.getSrvCereri().delete(idSender, idReceiver,timestamp);
                    modelCereri.remove(sM.getSrvCereri().findOne(idSender,idReceiver));
                }
        );
        return unsendButton;
    }

    @FXML
    public void initialize() {
        tableColumnIdSender.prefWidthProperty().bind(tableViewCereri.widthProperty().multiply(0.166));
        tableColumnStatus.prefWidthProperty().bind(tableViewCereri.widthProperty().multiply(0.166));
        tableColumnTimeStamp.prefWidthProperty().bind(tableViewCereri.widthProperty().multiply(0.166));
        tableColumnAccept.prefWidthProperty().bind(tableViewCereri.widthProperty().multiply(0.166));
        tableColumnDecline.prefWidthProperty().bind(tableViewCereri.widthProperty().multiply(0.166));
        tableColumnUnsend.prefWidthProperty().bind(tableViewCereri.widthProperty().multiply(0.166));
        tableColumnIdSender.setCellValueFactory(new PropertyValueFactory<>("from"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableColumnTimeStamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        tableColumnAccept.setCellValueFactory(new PropertyValueFactory<>("buttonAccept"));
        tableColumnDecline.setCellValueFactory(new PropertyValueFactory<>("buttonDecline"));
        tableColumnUnsend.setCellValueFactory(new PropertyValueFactory<>("buttonUnsend"));
        tableViewCereri.setItems(modelCereri);
    }
}
