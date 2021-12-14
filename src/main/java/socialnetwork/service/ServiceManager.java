package socialnetwork.service;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.database.*;

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

    public ServiceManager() {
        String url="jdbc:postgresql://localhost:5432/postgres";
        String user="postgres";
        String password="postgres";
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
