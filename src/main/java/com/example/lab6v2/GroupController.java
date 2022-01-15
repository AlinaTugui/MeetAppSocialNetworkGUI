package com.example.lab6v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.UtilizatorFriends;
import socialnetwork.service.ServiceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupController {
    public  TableView tableView = new TableView();
    public  TableColumn<UtilizatorFriends,String> columnFirstName = new TableColumn<>();
    public TableColumn<UtilizatorFriends,String> columnLastName = new TableColumn<>();
    public TableColumn<UtilizatorFriends,Button> columnAddBtn = new TableColumn<>();
    private final ObservableList userList = FXCollections.observableArrayList();
    public final TextField textField = new TextField();
    public TextField groupNameField = new TextField();
    ServiceManager sM = ServiceManager.getInstance();
    private final List<Long> friendsGroup =  new ArrayList();
    public Label label = new Label();
    @FXML
    public void initialize() {
        tableView.setItems(userList);
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnAddBtn.setCellValueFactory(new PropertyValueFactory<>("btnAddFriend"));
        userList.setAll(loadFriends());
        tableView.setItems(userList);
    }

    private List<UtilizatorFriends> loadFriends() {
        List<UtilizatorFriends> listaPrieteni = new ArrayList<>();
        Iterable<Long> prieteniID = sM.getSrvPrietenie().findAllUser(MainViewController.getIdLogin());
        if(prieteniID == null) return userList;

        prieteniID.forEach(x -> {
            Utilizator user = sM.getSrvUtilizator().findOne(x);
            UtilizatorFriends u = new UtilizatorFriends(user.getId(), user.getFirstName(), user.getLastName());
            u.setBtnAddFriend(createAddButton(x));
            listaPrieteni.add(u);
        });
        return listaPrieteni.stream().filter(
                x->{
                     for(Long id:friendsGroup)
                         if(id.equals(x.getId()))
                             return false;
                     return true;
                }
        ).collect(Collectors.toList());

    }

    private Button createAddButton(Long x) {
        Button addButton = new Button("Add to Group");
        addButton.setStyle("-fx-background-color: #ffffff");
        addButton.setStyle("-fx-border-color: #2e107a");
        addButton.setStyle("-fx-border-radius: 20px");
        addButton.setStyle("-fx-background-radius: 20px");
        addButton.setOnAction(event -> {
                    friendsGroup.add(x);
                    userList.remove(sM.getSrvUtilizator().findOne(x));
                    System.out.println(userList);
                    userList.setAll(loadFriends());
                    System.out.println("am apasat un buton");
                    System.out.println(friendsGroup);
                }
        );
        return addButton;
    }

    public void createGroup(ActionEvent actionEvent) {
        friendsGroup.add(MainViewController.getIdLogin());
        String name = groupNameField.getText();
        sM.getSrvGrup().addGrup(name, MainViewController.getIdLogin(),friendsGroup);
        String msg="A group named "+name+" with the users ";
        for(Long id:friendsGroup){
            Utilizator u = sM.getSrvUtilizator().findOne(id);
            msg+=u.getFirstName()+" "+u.getLastName()+", ";
        }

        msg+=" was successfully created";
        label.setText(msg);
    }
}
