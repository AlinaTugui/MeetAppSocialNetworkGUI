package com.example.lab6v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import socialnetwork.domain.Cerere;
import socialnetwork.domain.CererePrimita;
import socialnetwork.domain.CerereTrimisa;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.ServiceManager;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FriendRequestsControllerNew {

    private final ServiceManager sM = ServiceManager.getInstance();
    @FXML
    private TableView<CerereTrimisa> sentReqTableView = new TableView<>();
    @FXML
    private TableView<CererePrimita> recReqTableView = new TableView<>();
    @FXML
    private TableColumn<CerereTrimisa, String> receiverNameTableColumn = new TableColumn<>("Receiver Name");
    @FXML
    private TableColumn<CerereTrimisa, Timestamp> timestampTableColumn = new TableColumn<>("TimeStamp");
    @FXML
    private TableColumn<CerereTrimisa, Button> unsendTableColumn = new TableColumn<>("Unsend");

    @FXML
    private TableColumn<CererePrimita, String> senderNameTableColumn = new TableColumn<>("Sender Name");
    @FXML
    private TableColumn<CererePrimita, Timestamp> timestampTableColumn1 = new TableColumn<>("TimeStamp");
    @FXML
    private TableColumn<CerereTrimisa, Button> acceptTableColumn = new TableColumn<>("Accept");
    @FXML
    private TableColumn<CerereTrimisa, Button> declineTableColumn = new TableColumn<>("Decline");
    ObservableList<CerereTrimisa> sentReqObservableList = FXCollections.observableArrayList();
    ObservableList<CererePrimita> recReqObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        receiverNameTableColumn.prefWidthProperty().bind(sentReqTableView.widthProperty().multiply(0.42));
        timestampTableColumn.prefWidthProperty().bind(sentReqTableView.widthProperty().multiply(0.333));
        unsendTableColumn.prefWidthProperty().bind(sentReqTableView.widthProperty().multiply(0.333));
        senderNameTableColumn.prefWidthProperty().bind(recReqTableView.widthProperty().multiply(0.38));
        timestampTableColumn.prefWidthProperty().bind(recReqTableView.widthProperty().multiply(0.3));
        acceptTableColumn.prefWidthProperty().bind(recReqTableView.widthProperty().multiply(0.2));
        declineTableColumn.prefWidthProperty().bind(recReqTableView.widthProperty().multiply(0.2));
        receiverNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("receiverName"));
        timestampTableColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        unsendTableColumn.setCellValueFactory(new PropertyValueFactory<>("buttonUnsend"));
        senderNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("senderName"));
        timestampTableColumn1.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        acceptTableColumn.setCellValueFactory(new PropertyValueFactory<>("buttonAccept"));
        declineTableColumn.setCellValueFactory(new PropertyValueFactory<>("buttonDecline"));
        sentReqObservableList.setAll(loadSentRequests());
        recReqObservableList.setAll(loadReceivedRequests());
        sentReqTableView.setItems(sentReqObservableList);
        recReqTableView.setItems(recReqObservableList);
    }

    private List<CererePrimita> loadReceivedRequests() {
        List<CererePrimita> cereri = new ArrayList<>();
        List<Cerere> res = sM.getSrvCereri().cereriUtilizator(MainViewController.getIdLogin());
        res.forEach(x-> {
            Utilizator u = sM.getSrvUtilizator().findOne(x.getFrom());
            String name = u.getFirstName()+" "+u.getLastName();
            CererePrimita c = new CererePrimita(x.getFrom(),x.getTo(),name,x.getTimestamp());
            c.setButtonAccept(createAcceptButton(x.getId()));
            c.setButtonDecline(createDeclineButton(x.getId()));
            cereri.add(c);
        });
        return cereri;
    }

    private List<CerereTrimisa> loadSentRequests() {
        List<CerereTrimisa> cereri = new ArrayList<>();
        List<Cerere> res = sM.getSrvCereri().findAll().stream()
                .filter(cerere -> (cerere.getFrom().equals(MainViewController.getIdLogin())
                        && cerere.getStatus().equals("pending")))
                .collect(Collectors.toList());
        res.forEach(x->{
            Utilizator u = sM.getSrvUtilizator().findOne(x.getTo());
            String name = u.getFirstName()+" "+u.getLastName();
            CerereTrimisa c = new CerereTrimisa(x.getFrom(),x.getTo(),name,x.getTimestamp());
            c.setButtonUnsend(createUnsendButton(x.getFrom(),x.getTo(),x.getTimestamp()));
            cereri.add(c);
        });
        return cereri;
    }

    private Button createAcceptButton(Long id) {
        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(event -> {
                    sM.getSrvCereri().acceptCerere(id);
                    recReqObservableList.remove(sM.getSrvCereri().findOne(id));
                    recReqObservableList.setAll(loadReceivedRequests());
                }
        );
        return acceptButton;
    }

    private Button createDeclineButton(Long id) {
        Button declineButton = new Button("Decline");
        declineButton.setOnAction(event -> {
            sM.getSrvCereri().declineCerere(id);
            recReqObservableList.remove(sM.getSrvCereri().findOne(id));
            recReqObservableList.setAll(loadReceivedRequests());
        });
        return declineButton;
    }


    private Button createUnsendButton(Long idSender, Long idReceiver, LocalDateTime timestamp ) {
        Button unsendButton = new Button("Unsend");
        unsendButton.setOnAction(event -> {
                    sM.getSrvCereri().delete(idSender, idReceiver,timestamp);
                    sentReqObservableList.remove(sM.getSrvCereri().findOne(idSender,idReceiver));
                    sentReqObservableList.setAll(loadSentRequests());
                }
        );
        return unsendButton;
    }

    public void onBtnRefresh(ActionEvent actionEvent) {
        sentReqObservableList.setAll(loadSentRequests());
        recReqObservableList.setAll(loadReceivedRequests());
    }

    public void onBtnRefresh1(MouseEvent mouseEvent) {
        sentReqObservableList.setAll(loadSentRequests());
        recReqObservableList.setAll(loadReceivedRequests());
    }
}
