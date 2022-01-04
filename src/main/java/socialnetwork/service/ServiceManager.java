package socialnetwork.service;

import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.database.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ServiceManager {
    private static ServiceManager instance;

    private final ServiceUtilizator srvUtilizator;
    private final ServicePrietenie srvPrietenie;
    private final ServicePrietenieUtilizator srvPrietenieUtilizator;
    private final ServiceMesaje srvMesaje;
    private final ServiceCereri srvCereri;
    private final ServiceLogin srvLogin;
    private final ServiceCereriPrietenie srvCereriPrietenie;
    private final ServiceGrup srvGrup;

    public static ServiceManager getInstance() {
        if(instance == null)  instance = new ServiceManager();
        return instance;
    }

    public ServiceManager()  {
        BufferedReader in = null;
        try { in = new BufferedReader( new FileReader("Data/DbConnectData.txt"));
        } catch (FileNotFoundException e) { e.printStackTrace();}
        String url = null;
        try { url = in.readLine();
        } catch (IOException e) { e.printStackTrace();}
        String user= null;
        try { user = in.readLine();
        } catch (IOException e) { e.printStackTrace();}
        String password= null;
        try { password = in.readLine();
        } catch (IOException e) { e.printStackTrace();}
        Repository0<Long, Utilizator> repoUtilizator = new UtilizatorDbRepo(url, user, password,
                new UtilizatorValidator());
        PrietenieDbRepo repoPrietenie = new PrietenieDbRepo(
                url, user, password,
                new PrietenieValidator());
        MesajeDbRepo repoMsgCoresp = new MesajeDbRepo(url, user, password);
        CereriDbRepo repoCereri = new CereriDbRepo(url, user, password, repoUtilizator);
        LoginDbRepo repoLogin = new LoginDbRepo(url, user, password);
        GrupDbRepo repoGrup = new GrupDbRepo(url, user, password);
        UseriGrupDbRepo repoUseriGrup = new UseriGrupDbRepo(url, user, password);
        ServiceUtilizator srvUtilizatori = new ServiceUtilizator(repoUtilizator);
        ServicePrietenie srvPrietenie = new ServicePrietenie(repoPrietenie);
        ServiceMesaje srvMesaje = new ServiceMesaje(repoUtilizator, repoGrup, repoPrietenie,
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

    public ServiceUtilizator getSrvUtilizator() {
        return srvUtilizator;
    }

    public ServicePrietenie getSrvPrietenie() {
        return srvPrietenie;
    }

    public ServicePrietenieUtilizator getSrvPrietenieUtilizator() {
        return srvPrietenieUtilizator;
    }

    public ServiceMesaje getSrvMesaje() {
        return srvMesaje;
    }

    public ServiceCereri getSrvCereri() {
        return srvCereri;
    }

    public ServiceLogin getSrvLogin() {
        return srvLogin;
    }

    public ServiceCereriPrietenie getSrvCereriPrietenie() {
        return srvCereriPrietenie;
    }

    public ServiceGrup getSrvGrup() {
        return srvGrup;
    }
}
