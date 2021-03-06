package com.example.lab6v2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import socialnetwork.domain.Grup;
import socialnetwork.domain.MesajConv;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.ServiceManager;

import javax.swing.text.StyledEditorKit;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChatMessagesPaneController implements Initializable {
    private final ServiceManager sM = ServiceManager.getInstance();
    public AnchorPane ap_main;
    public ScrollPane sp_main;
    public VBox vbox_messages;
    public TextField tf_message;
    public Button button_send;
    public Circle imagine;
    public Label nume = new Label();
    private Utilizator user2;
    private Grup grup;

    private void load_messages() {
        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double) newValue);
            }
        });
        List<MesajConv> mesaje = sM.getSrvMesaje().afisareConversatie(MainViewController.getIdLogin(), user2.getId());
        for (MesajConv mesaj : mesaje) {
            if (mesaj.getFrom().getId() == MainViewController.getIdLogin()) {
                addMsgSenderToGUI(mesaj.getMsg());
            } else {
                addMsgReceiverToGUI(mesaj);
            }
        }
    }

    private void load_messages_grup() {
        vbox_messages.heightProperty().addListener((observable, oldValue, newValue) ->
                sp_main.setVvalue((Double) newValue));
        List<MesajConv> mesaje = sM.getSrvMesaje().afisareConversatieGrup(MainViewController.getIdLogin(), grup.getId());
        for (MesajConv mesaj : mesaje) {
            if (Objects.equals(mesaj.getFrom().getId(), MainViewController.getIdLogin())) {
                addMsgSenderToGUI(mesaj.getMsg());
            } else {
                addMsgReceiverToGUI(mesaj);
            }
        }
    }

    public void setValues(Utilizator user2) {
        this.nume.setText(user2.getFirstName() + " " + user2.getLastName());
        this.imagine.setFill(MainViewController.changeImage(user2.getId()));
        this.user2 = user2;
        load_messages();
    }

    public void setValues(Grup grup) {
        this.nume.setText(grup.getNume());
        this.imagine.setFill(MainViewController.ImageGrup());
        this.grup = grup;
        load_messages_grup();
    }

    private void addMsgSenderToGUI(String mesaj) {
        if (!mesaj.isEmpty()) {
            HBox hBox = new HBox();
            hBox.onMouseEnteredProperty();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(0, 5, 5, 10));

            Text text = new Text(mesaj);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-color: rgb(239,242,255);" +
                    "-fx-background-color: rgb(15,125,242);" +
                    "-fx-background-radius: 20px;");

            textFlow.setPadding(new Insets(5, 10, 5, 10));
            text.setFill(Color.color(0.93, 0.94, 0.996));
            hBox.getChildren().add(textFlow);
            vbox_messages.getChildren().add(hBox);

            tf_message.clear();
        }
    }

    private void addMsgReceiverToGUI(MesajConv mesaj) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(mesaj.getMsg());
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-color: rgb(233,233,235);" +
                "-fx-background-color: rgb(203,206,218);" +
                "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));

        if(mesaj.getToGroup() != null) {
            Text nume = new Text(mesaj.getFrom().getFirstName() + " " + mesaj.getFrom().getLastName() + ":");
            TextFlow numeFlow = new TextFlow(nume);
            numeFlow.setPadding(new Insets(0, 0, 0, 10));
            numeFlow.setStyle("-fx-font-weight: bold");;
            vbox_messages.getChildren().add(numeFlow);
        }
        hBox.getChildren().add(textFlow);
        vbox_messages.getChildren().add(hBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    public void onSendMessage(ActionEvent actionEvent) {
        String mesaj = tf_message.getText();
        addMsgSenderToGUI(mesaj);
        if (user2 != null) sM.getSrvMesaje().adaugaMesaj(MainViewController.getIdLogin(),
                new ArrayList<Long>() {{
                    add(user2.getId());
                }}, mesaj);
        else sM.getSrvMesaje().adaugaMesajGrup(MainViewController.getIdLogin(), grup.getId(), mesaj);
    }
}


