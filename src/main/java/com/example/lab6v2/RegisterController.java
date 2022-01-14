package com.example.lab6v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import socialnetwork.config.Encryptor;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.RepositoryException;
import socialnetwork.service.ServiceManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class RegisterController {
    private ServiceManager sM = ServiceManager.getInstance();

    public Label registerFailed;
    public TextField reg_lastName;
    public TextField reg_firstName;
    public TextField reg_email;
    public PasswordField reg_password;
    public PasswordField reg_password1;

    public void register(ActionEvent actionEvent) {
        String firstNameS = reg_firstName.getText().toString();
        String lastNameS = reg_lastName.getText().toString();
        String emailS = reg_email.getText().toString();
        String passwordS = reg_password.getText().toString();
        String passwordS1 = reg_password1.getText().toString();
        if(lastNameS.equals("") || firstNameS.equals("") || emailS.equals("") || passwordS.equals("") || passwordS1.equals("") )
            registerFailed.setText("Fields not completed!");
        else if(!passwordS.equals(passwordS1))
            registerFailed.setText("Passwords don't match");
        else{
            try {
                sM.getSrvLogin().verifyExistingEmail(emailS);
                Encryptor encryptor = new Encryptor();
                passwordS = encryptor.encryptString(passwordS);
                Utilizator u = sM.getSrvUtilizator().addUtilizator(firstNameS, lastNameS, emailS, passwordS);
                MainViewController.setIdLogin(u.getId());
                HelloApplication.changeScene("mainView.fxml", actionEvent,"Main View");
            }catch(RepositoryException | IOException | ValidationException | NoSuchAlgorithmException re){
                registerFailed.setText(re.getMessage());
            }
        }
    }

    public void openHello(ActionEvent actionEvent) throws IOException {
        HelloApplication.changeScene("hello-view1.fxml", actionEvent, "Main View");
    }

}
