package com.example.lab6v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import socialnetwork.domain.Cerere;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.database.*;
import socialnetwork.service.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class HelloController {
    private ServiceUtilizator srvUtilizator;
    private ServicePrietenie srvPrietenie;
    private ServicePrietenieUtilizator srvPrietenieUtilizator;
    private ServiceMesaje srvMesaje;
    private ServiceCereri srvCereri;
    private ServiceLogin srvLogin;
    private ServiceCereriPrietenie srvCereriPrietenie;
    private ServiceGrup srvGrup;

    @FXML
    private Label welcomeText;

    public void init(String url, String user, String password){
        Repository0<Long, Utilizator> repoUtilizator = new UtilizatorDbRepo(url, user, password,
                new UtilizatorValidator());
        Repository0<Tuple<Long, Long>, Prietenie> repoPrietenie = new PrietenieDbRepo(
                url, user, password,
                new PrietenieValidator());
        MesajeDbRepo repoMsgCoresp = new MesajeDbRepo(url, user, password);
        CereriDbRepo repoCereri = new CereriDbRepo(url, user, password, repoUtilizator);
        LoginDbRepo repoLogin = new LoginDbRepo(url, user, password);
        GrupDbRepo repoGrup = new GrupDbRepo(url, user, password);
        UseriGrupDbRepo repoUseriGrup = new UseriGrupDbRepo(url, user, password);
        ServiceUtilizator srvUtilizatori = new ServiceUtilizator(repoUtilizator);
        ServicePrietenie srvPrietenie = new ServicePrietenie(repoPrietenie);
        ServiceMesaje srvMesaje = new ServiceMesaje(repoUtilizator, repoPrietenie,
                repoMsgCoresp, srvUtilizatori, srvPrietenie);
        ServiceLogin srvLogin = new ServiceLogin(repoLogin);
        ServicePrietenieUtilizator srvUtilizatoriPrieteni =
                new ServicePrietenieUtilizator(repoUtilizator, repoPrietenie, repoMsgCoresp, srvUtilizatori, srvPrietenie);
        ServiceCereri srvCereri = new ServiceCereri(repoUtilizator,repoPrietenie,repoCereri,srvUtilizatori,srvPrietenie);
        ServiceCereriPrietenie srvCereriPrietenie = new ServiceCereriPrietenie(srvCereri,srvPrietenie);
        ServiceGrup srvGrup = new ServiceGrup(repoUtilizator, repoGrup, repoUseriGrup);
        this.srvUtilizator = srvUtilizatori;
        this.srvPrietenie = srvPrietenie;
        this.srvPrietenieUtilizator = srvUtilizatoriPrieteni;
        this.srvMesaje = srvMesaje;
        this.srvCereri = srvCereri;
        this.srvGrup = srvGrup;
        this.srvLogin = srvLogin;
        this.srvCereriPrietenie = srvCereriPrietenie;
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(srvPrietenieUtilizator.numarComunitati().toString());
    }

}