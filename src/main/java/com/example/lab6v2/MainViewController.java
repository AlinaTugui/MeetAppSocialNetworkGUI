package com.example.lab6v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.ServiceManager;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class MainViewController {

    private ServiceManager sM=ServiceManager.getInstance();
    private static Long idLogin;
    public Circle pozaLogat;
    public Label numeLogat = new Label();
    public AnchorPane rightPane;

    private void changeImage(){
        String imgPath = sM.getSrvUtilizator().findOne(idLogin).getImage_path();
        if(imgPath == null) return;
        String path = "file:///" + imgPath;
        Image img = new Image(path,false);
        pozaLogat.setFill(new ImagePattern(img));
    }

    public void setValues() {
        Utilizator u = sM.getSrvUtilizator().findOne(idLogin);
        numeLogat.setText(u.getFirstName() + " " + u.getLastName());
        changeImage();
    }

    public static Long getIdLogin() {
        return idLogin;
    }

    public static void setIdLogin(Long _idLogin) {
        idLogin = _idLogin;
    }

    private void changeRightPane(String fxmlName) throws IOException {
        Pane newLoadedPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlName)));
        rightPane.getChildren().setAll(newLoadedPane);
    }

    public void openChat(ActionEvent actionEvent) throws IOException {
        changeRightPane("chatPane.fxml");
    }

    public void showFriends(ActionEvent actionEvent) throws IOException {
        changeRightPane("show-friends-view.fxml");
    }

    public void openAddFriends(ActionEvent actionEvent) throws IOException {
        changeRightPane("AddFriendsNewView.fxml");
    }

    public void openFriendRequests(ActionEvent actionEvent) throws IOException {
        changeRightPane("FriendRequestsNewView.fxml");
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        HelloApplication.changeScene("hello-view.fxml", actionEvent, "Main View");
    }


    public void changeImage(ActionEvent actionEvent) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("imaginiUseri/ImagineNr.txt"));
        Integer imgNr = Integer.parseInt(bufferedReader.readLine());
        bufferedReader.close();
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        String path = "C:\\Users\\turtu\\Desktop\\Lucrari\\IntelliGay\\Lab6v2\\imaginiUseri\\Imagini\\";
        String newPath = path + imgNr + ".png";
        Files.copy(file.toPath(),
                (new File(newPath)).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("imaginiUseri/ImagineNr.txt"));
        bufferedWriter.write(String.valueOf(imgNr+1));
        bufferedWriter.close();
        Utilizator u = sM.getSrvUtilizator().findOne(idLogin);
        u.setImage_path(newPath);
        sM.getSrvUtilizator().updateUtilizator(u);
        changeImage();
    }
}
