package com.example.lab6v2;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import socialnetwork.domain.Grup;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.ServiceManager;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ChatPaneController implements Initializable {
    private ServiceManager sM=ServiceManager.getInstance();

    public VBox chatLeftUpperVbox;
    public VBox chatLeftBottomVbox;
    public AnchorPane chatRightPane;

    public void changeRightPane(Utilizator u){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatMessagesPane.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChatMessagesPaneController ctrl = loader.getController();
        ctrl.setValues(u);
        chatRightPane.getChildren().setAll(root);
    }

    public void chatRightPane(Grup grup) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatMessagesPane.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChatMessagesPaneController ctrl = loader.getController();
        ctrl.setValues(grup);
        chatRightPane.getChildren().setAll(root);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        loadContacteSiGrupuriCuCareAiMesaje();
    }

    private void loadContacteSiGrupuriCuCareAiMesaje() {
        List<Utilizator> listaUseriCuMesaje = sM.getSrvMesaje().ultimulMesajDeLaToateContacteleUnuiUser(MainViewController.getIdLogin());
        List<Utilizator> listUseriFaraMesaje = new ArrayList<>();
        Iterable<Long> l =sM.getSrvPrietenie().findAllUser(MainViewController.getIdLogin());
        l.forEach(x -> listUseriFaraMesaje.add(sM.getSrvUtilizator().findOne(x)));
        List<Utilizator> listUseriFaraMesaje2 = listUseriFaraMesaje.stream().filter(val -> !listaUseriCuMesaje.contains(val)).toList();
        List<Utilizator> listaToti = new ArrayList<>(){{addAll(listaUseriCuMesaje);
            addAll(listUseriFaraMesaje2);
        }};
        for(Utilizator u : listaToti){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chatUserPane.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChatUserPaneController ctrl = loader.getController();
            ctrl.setValues(u,this);

            chatLeftUpperVbox.getChildren().add(root);
        }
        Utilizator user = sM.getSrvUtilizator().findOne(MainViewController.getIdLogin());
        for(Long idGrup : user.getGrupuri()){
            Grup grup = sM.getSrvGrup().findOne(idGrup);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chatUserPane.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChatUserPaneController ctrl = loader.getController();
            ctrl.setValues(grup,this);

            chatLeftBottomVbox.getChildren().add(root);
        }
    }


}
