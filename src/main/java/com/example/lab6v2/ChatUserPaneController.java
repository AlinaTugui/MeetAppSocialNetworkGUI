package com.example.lab6v2;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import socialnetwork.Main;
import socialnetwork.domain.Grup;
import socialnetwork.domain.MesajConv;
import socialnetwork.domain.Utilizator;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChatUserPaneController implements Initializable{


    private ChatPaneController chatPaneController;
    public Circle poza;
    public Label nume = new Label();
    public Label mesaj;
    public Label ora;
    private Utilizator user;
    private Grup grup;

    public void setValues(Utilizator u, ChatPaneController chatPaneController){
        this.nume.setText(u.getFirstName() + " " + u.getLastName());
        this.user = u;
        this.poza.setFill(MainViewController.changeImage(u.getId()));
        this.mesaj.setVisible(false);
        this.ora.setVisible(false);
        this.chatPaneController = chatPaneController;
    }

    public void setValues(MesajConv mesajConv, ChatPaneController chatPaneController){
        if(mesajConv.getToGroup() == null) this.nume.setText(mesajConv.getFrom().getFirstName() + " " + mesajConv.getFrom().getLastName());
        else this.nume.setText(mesajConv.getToGroup().getNume());
        this.mesaj.setText(mesajConv.getMsg());
        if(mesajConv.getDateTime().toLocalDate().compareTo(LocalDate.now()) != 0 )
            this.ora.setText(DateTimeFormatter.ofPattern("dd/MM")
                    .format(mesajConv.getDateTime().toLocalDate()).toString());
        else
            this.ora.setText(DateTimeFormatter.ofPattern("hh:mm")
                    .format(mesajConv.getDateTime().toLocalTime()).toString());
        if(mesajConv.getToGroup() == null) this.poza.setFill(MainViewController.changeImage(mesajConv.getFrom().getId()));
        else this.poza.setFill(MainViewController.ImageGrup());
        this.user= mesajConv.getFrom();
        this.grup = mesajConv.getToGroup();
        this.chatPaneController = chatPaneController;
    }

    public void setValues(Grup g, ChatPaneController chatPaneController){
        this.nume.setText(g.getNume());
        this.grup = g;
        this.poza.setFill(MainViewController.ImageGrup());
        this.mesaj.setVisible(false);
        this.ora.setVisible(false);
        this.chatPaneController = chatPaneController;
    }


    public void openUserChat(MouseEvent mouseEvent) {
        if(grup == null) chatPaneController.changeRightPane(user);
        else chatPaneController.chatRightPane(grup);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
