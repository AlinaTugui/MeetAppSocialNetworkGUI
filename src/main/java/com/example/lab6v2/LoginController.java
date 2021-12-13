package com.example.lab6v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import socialnetwork.repository.RepositoryException;
import socialnetwork.service.ServiceManager;

import java.io.IOException;

public class LoginController {
    private static ServiceManager sM = ServiceManager.getInstance();
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
                Long idLogin = sM.getSrvLogin().login(emailS, passwordS);
                MainViewController.setIdLogin(idLogin);
                HelloApplication.changeScene("mainView.fxml", actionEvent,"Main View");
            }catch(RepositoryException re){
                loginFailed.setText(re.getMessage());
            }
        }
    }
}
