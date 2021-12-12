package com.example.lab6v2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.database.*;
import socialnetwork.service.*;
import socialnetwork.ui.UI;

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

    public void initialize(){
        Repository0<Long, Utilizator> repoUtilizator = new UtilizatorDbRepo(
                "jdbc:postgresql://localhost:13030/postgres", "postgres",
                "postgres",
                new UtilizatorValidator());
        Repository0<Tuple<Long, Long>, Prietenie> repoPrietenie = new PrietenieDbRepo(
                "jdbc:postgresql://localhost:13030/postgres", "postgres", "postgres",
                new PrietenieValidator());
        MesajeDbRepo repoMsgCoresp = new MesajeDbRepo(
                "jdbc:postgresql://localhost:13030/postgres", "postgres", "postgres");
        CereriDbRepo repoCereri = new CereriDbRepo("jdbc:postgresql://localhost:13030/postgres",
                "postgres", "postgres", repoUtilizator);
        LoginDbRepo repoLogin = new LoginDbRepo(
                "jdbc:postgresql://localhost:13030/postgres", "postgres", "postgres");
        GrupDbRepo repoGrup = new GrupDbRepo("jdbc:postgresql://localhost:13030/postgres",
                "postgres", "postgres");
        UseriGrupDbRepo repoUseriGrup = new UseriGrupDbRepo("jdbc:postgresql://localhost:13030/postgres",
                "postgres", "postgres");
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