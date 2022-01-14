package com.example.lab6v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.UserRecord;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.ServiceManager;

import java.util.List;
import java.util.stream.Collectors;

public class AddFriendsControllerNew {
    @FXML
    private TextField textField = new TextField();
    @FXML
    private TableView<UserRecord> tableView =new TableView();
    @FXML
    private TableColumn<UserRecord, String> nameColumn = new TableColumn<>("Name");
    @FXML
    private TableColumn<UserRecord, Button> addFriendColumn = new TableColumn<>("Add Friend");
    private final ObservableList<UserRecord> userRecordList = FXCollections.observableArrayList();
    private final ServiceManager sM = ServiceManager.getInstance();

    @FXML
    public void initialize() {
        nameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));
        addFriendColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addFriendColumn.setCellValueFactory(new PropertyValueFactory<>("btnAddFriend"));
        textField.textProperty().addListener((obs,oldTxt,newTxt)->findUserByName());
        tableView.setItems(userRecordList);
        updateTableWithUsersAtSearch("");
    }

    private void updateTableWithUsersAtSearch(String searchName) {
        tableView.getItems().clear();
        userRecordList.setAll(getUserRecordList(searchName));
    }

    private ObservableList<UserRecord> getUserRecordList(String searchName) {
        List<Utilizator> users = sM.getSrvUtilizator().findAllByNameTest(searchName)
                .stream().filter(u->(
                        sM.getSrvPrietenie().findOne(new Tuple<>(MainViewController.getIdLogin(),u.getId()))==null
                                && sM.getSrvPrietenie().findOne(new Tuple<>(u.getId(),MainViewController.getIdLogin()))==null))
                .filter(u->(
                        sM.getSrvCereri().findOneStatus(MainViewController.getIdLogin(),u.getId(),"pending")==null
                                && sM.getSrvCereri().findOneStatus(u.getId(),MainViewController.getIdLogin(),"pending")==null))
                .collect(Collectors.toList());
        ObservableList<UserRecord> userRecordObservableList = FXCollections.observableArrayList();
        for (Utilizator u : users) {
            String name = u.getFirstName()+" "+u.getLastName();
            Button addFriendButton = createAddFriendButton(u.getId());
            UserRecord userRecord = new UserRecord(u.getId(), name);
            userRecord.setBtnAddFriend(addFriendButton);
            userRecordObservableList.add(userRecord);
        }
        return userRecordObservableList;
    }

    private Button createAddFriendButton(Long id) {
        Button addFriendButton = new Button("Add friend");
        addFriendButton.setStyle("-fx-background-color: #ffffff");
        addFriendButton.setStyle("-fx-border-color: #2e107a");
        /*addFriendButton.setStyle("-fx-border-radius: 20px");
        addFriendButton.setStyle("-fx-background-radius: 20px");
        addFriendButton.setStyle("-fx-font-style: bold italic");
        addFriendButton.setStyle("-fx-font-size: 12px");*/
        //addFriendButton.getStyleClass().add("D:\\faculta\\An2\\sem1\\map\\lab6_4\\lab6v2\\src\\main\\resources\\com\\example\\lab6v2\\css\\addFriendsButton.css");

        addFriendButton.setOnAction(event -> {
            sM.getSrvCereri().trimiteCerereDePrietenie(MainViewController.getIdLogin(), id);
            findUserByName();
        });
        return addFriendButton;
    }

    private void findUserByName() {
        updateTableWithUsersAtSearch(textField.getText());
    }

    public void onRefreshBtn(ActionEvent actionEvent) {
        findUserByName();
    }

    public void onRefreshBtn1(MouseEvent mouseEvent) {
        findUserByName();
    }
}
