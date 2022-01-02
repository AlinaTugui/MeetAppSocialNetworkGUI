package com.example.lab6v2;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.ServiceManager;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

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



    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        loadContacteSiGrupuriCuCareAiMesaje();
    }

    private void loadContacteSiGrupuriCuCareAiMesaje() {
        List<Utilizator> listaUseri = sM.getSrvMesaje().ultimulMesajDeLaToateContacteleUnuiUser(MainViewController.getIdLogin());
        for(Utilizator u : listaUseri){
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
    }
}
