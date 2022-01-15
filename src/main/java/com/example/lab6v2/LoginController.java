package com.example.lab6v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import socialnetwork.config.Encryptor;
import socialnetwork.repository.RepositoryException;
import socialnetwork.service.ServiceManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class LoginController {
    private ServiceManager sM = ServiceManager.getInstance();
    @FXML
    public Label loginFailed;
    @FXML
    public TextField email;
    @FXML
    public PasswordField password;


    public void login(ActionEvent actionEvent) throws IOException {
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();
        if(emailS.equals("") && passwordS.equals(""))
            loginFailed.setText("Email and password are missing!");
        else if(emailS.equals(""))
            loginFailed.setText("Email is missing!");
        else if(passwordS.equals(""))
            loginFailed.setText("Password is missing!");
        else{
            try {
                Encryptor encryptor = new Encryptor();
                passwordS = encryptor.encryptString(passwordS);
                Long idLogin = sM.getSrvLogin().login(emailS, passwordS);
                MainViewController.setIdLogin(idLogin);

                ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(HelloApplication.class.getResource("mainView.fxml"));
                AnchorPane root = fxmlLoader.load();

                MainViewController ctrl=fxmlLoader.getController();
                ctrl.setValues();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("StyleSheet.css")).toExternalForm());
                Stage stage = new Stage();
                stage.setTitle("Main View");
                stage.setScene(scene);
                stage.show();
            }catch(RepositoryException | NoSuchAlgorithmException re){
                loginFailed.setText(re.getMessage());
            }
        }
    }

    public void onBackBtn(ActionEvent actionEvent) throws IOException {
        HelloApplication.changeScene("hello-view1.fxml", actionEvent, "Main View");
    }
}
