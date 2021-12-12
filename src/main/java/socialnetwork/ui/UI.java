package socialnetwork.ui;

import socialnetwork.domain.Grup;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.database.*;
import socialnetwork.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UI {
    private final ServiceUtilizator srvUtilizator;
    private final ServicePrietenie srvPrietenie;
    private final ServicePrietenieUtilizator srvPrietenieUtilizator;
    private final ServiceMesaje srvMesaje;
    private final ServiceCereri srvCereri;
    private final ServiceLogin srvLogin;
    private final ServiceCereriPrietenie srvCereriPrietenie;
    private final ServiceGrup srvGrup;
    Scanner sc = new Scanner(System.in);
    private Long idLogin;
    public UI(ServiceUtilizator srvUtilizator, ServicePrietenie srvPrietenie,
              ServicePrietenieUtilizator srvPrietenieUtilizator, ServiceMesaje srvMesaje,
              ServiceLogin srvLogin, ServiceCereri srvCereri, ServiceCereriPrietenie srvCereriPrietenie,
              ServiceGrup srvGrup) {
        this.srvUtilizator = srvUtilizator;
        this.srvPrietenie = srvPrietenie;
        this.srvPrietenieUtilizator = srvPrietenieUtilizator;
        this.srvMesaje = srvMesaje;
        this.srvCereri = srvCereri;
        this.srvGrup = srvGrup;

        this.srvLogin = srvLogin;
        this.srvCereriPrietenie = srvCereriPrietenie;
        idLogin=-1L;
    }

    static public void init(String[] args) {
        try {
            Repository0<Long, Utilizator> repoUtilizator = new UtilizatorDbRepo(
                    args[0], args[1], args[2],
                    new UtilizatorValidator());
            Repository0<Tuple<Long, Long>, Prietenie> repoPrietenie = new PrietenieDbRepo(
                    args[0], args[1], args[2],
                    new PrietenieValidator());
            MesajeDbRepo repoMsgCoresp = new MesajeDbRepo(
                    args[0], args[1], args[2]);
            CereriDbRepo repoCereri = new CereriDbRepo(args[0],args[1], args[2], repoUtilizator);
            LoginDbRepo repoLogin = new LoginDbRepo(
                    args[0], args[1], args[2]);
            GrupDbRepo repoGrup = new GrupDbRepo(args[0], args[1], args[2]);
            UseriGrupDbRepo repoUseriGrup = new UseriGrupDbRepo(args[0], args[1], args[2]);
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
            UI ui = new UI(srvUtilizatori, srvPrietenie, srvUtilizatoriPrieteni,
                    srvMesaje, srvLogin, srvCereri, srvCereriPrietenie, srvGrup);
            ui.run();
        } catch (ValidationException ve) {
            System.out.println(ve.getMessage());
        }
    }

    private void listaComenzi() {
        System.out.println("""
                1-Add utilizator
                2-Update utilizator
                3-Remove utilizator
                9-Afiseaza toti utilizatorii
                10-Add prietenie
                11-Remove prietenie
                19-Afiseaza toate prieteniile
                20-Numar comunitati
                21-Cea mai sociabila comunitate
                30-Relatii de prietenie utilizator
                31-Relatii de prietenie utilizator intr-o luna data
                32-Adauga mesaj
                33-Afiseaza conversatie a doi utilizatori
                41-Trimite cerere de prietenie
                42-Afiseaza cererile de prietenie pentru un utilizator dat
                43-Proceseaza cerere (accept/decline)
                50-Add grup
                51-Update grup
                52-Delete grup
                99-Afiseaza comenzi
                100-Login
                0-Iesi din program""");
    }

    private void listaComenziLogin() {
        System.out.println("""
                1-Add prietenie
                2-Remove prietenie
                3-Afiseaza toate prieteniile
                10-Adauga mesaj
                11-Afiseaza conversatiile cu un utilizator
                20-Trimite cerere
                21-Afiseaza cereri
                22-Proceseaza cerere
                99-Afiseaza comenzi
                100-Logout
                0-Iesi din program""");
    }

    private void addUtilizator() {
        System.out.println("Add utilizator");
        System.out.print("Id: ");
        Long id = sc.nextLong();
        sc.nextLine();
        System.out.print("firstName: ");
        String firstName = sc.nextLine();
        System.out.print("lastName: ");
        String lastName = sc.nextLine();
        srvUtilizator.addUtilizator(id, firstName, lastName);
        System.out.println("Utilizator adaugat!");
    }

    private void updUtilizator() {
        System.out.println("Update utilizator");
        System.out.print("Id: ");
        Long id = sc.nextLong();
        sc.nextLine();
        System.out.print("firstName: ");
        String firstName = sc.nextLine();
        System.out.print("lastName: ");
        String lastName = sc.nextLine();
        srvUtilizator.updateUtilizator(id, firstName, lastName);
        System.out.println("Utilizator modificat!");
    }

    private void remUtilizator() {
        System.out.println("Remove utilizator");
        System.out.print("Id: ");
        Long id = sc.nextLong();
        sc.nextLine();
        srvPrietenieUtilizator.deleteUtilizator(id);
        System.out.println("Utilizator sters!");
    }

    private void printUtilizatori() {
        System.out.println("Afiseaza utilizatori");
        srvUtilizator.findAll().forEach(System.out::println);
    }

    private void addPrietenie() {
        System.out.println("Add prietenie");
        System.out.print("Id1: ");
        Long id1 = sc.nextLong();
        System.out.print("Id2: ");
        Long id2 = sc.nextLong();
        srvPrietenieUtilizator.addPrietenie(id1, id2);
        System.out.println("Prietenie adaugata!");
    }

    private void remPrietenie() {
        System.out.println("Remove prietenie");
        System.out.print("Id1: ");
        Long id1 = sc.nextLong();
        System.out.print("Id2: ");
        Long id2 = sc.nextLong();
        srvCereriPrietenie.stergePrietenie(id1,id2);
        System.out.println("Prietenie stearsa!");
    }

    private void printPrietenii() {
        System.out.println("Afiseaza prietenii");
        srvPrietenie.findAll().forEach(System.out::println);
    }

    private void numarComunitati() {
        int nrC = srvPrietenieUtilizator.numarComunitati();
        System.out.println("Numar de comunitati: " + nrC);
    }

    private void ComunitateSociabila() {
        List<Utilizator> rez = new ArrayList<>();
        Integer compS = srvPrietenieUtilizator.ComunitateSociabila(rez);
        System.out.println("Lungimea comunitatii cea mai sociabile: " + compS);
        System.out.println("Componenta cea mai sociabila:");
        rez.forEach(System.out::println);
    }

    private void relatiiUtilizator() {
        System.out.println("Relatii utilizator: ");
        System.out.print("Id: ");
        Long id = sc.nextLong();
        System.out.print("Luna: ");
        Integer luna = sc.nextInt();
        srvPrietenieUtilizator.relatiiUtilizator(id, luna).forEach(System.out::println);
    }

    private void adaugaMesaj() {
        System.out.println("Adauga mesaj: ");
        System.out.print("Id sender: ");
        Long idSender = sc.nextLong();
        sc.nextLine();
        System.out.print("Ids receivers: ");
        String ids = sc.nextLine();
        List<Long> idsReceivers = Arrays.stream(ids.split(","))
                .map(x -> Long.parseLong(x.strip())).toList();
        System.out.println("Mesaj: ");
        String msg = sc.nextLine();
        srvMesaje.adaugaMesaj(idSender, idsReceivers, msg);
    }

    private void afisareConversatie() {
        System.out.println("Afisare conversatie a doi utilizatori: ");
        System.out.print("Id1: ");
        Long id1 = sc.nextLong();
        System.out.print("Id2: ");
        Long id2 = sc.nextLong();
        srvMesaje.afisareConversatie(id1,id2).forEach(System.out::println);
    }

    /**
     * functionalitatea 1
     */
    private void relatiiPrietenieUtilizator()  {
        System.out.println("Relatii de prietenie utilizator: ");
        System.out.println("Dati id-ul utililzatorului: ");
        Long id=sc.nextLong();
        List<Tuple<Utilizator, LocalDateTime>> rez=srvPrietenieUtilizator.relatiiPrietenieUtilizator(id);
        if(rez.isEmpty())
            System.out.println("Utilizatorul dat nu are prieteni!:(");
        for (Tuple<Utilizator, LocalDateTime> utilizatorDateTuple : rez) {
            System.out.println(utilizatorDateTuple.getLeft().getFirstName()+"|"
                    +utilizatorDateTuple.getLeft().getLastName()+"|"
                    +utilizatorDateTuple.getRight().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
    }
    public void afisareCereri() {
        System.out.println("Afisare cereri de prietenie pentru un utilizator dat");
        System.out.println("Dati id-ul utilizatorului: ");
        Long id= sc.nextLong();
        srvCereri.afisareCereriUtilizator(id);
    }
    public void trimiteCerere() {
        System.out.println("Trimitere cerere de prietenie: ");
        System.out.println("Dati id-ul userului care trimite cererea: ");
        Long id1=sc.nextLong();
        System.out.println("Dati id-ul userului care primeste cererea: ");
        Long id2=sc.nextLong();
        srvCereri.trimiteCerereDePrietenie(id1,id2);
    }

    private void addPrietenieLogin() {
        System.out.println("Add prietenie");
        System.out.print("Id: ");
        Long id1 = sc.nextLong();
        srvPrietenieUtilizator.addPrietenie(id1, idLogin);
        System.out.println("Prietenie adaugata!");
    }

    private void remPrietenieLogin() {
        System.out.println("Remove prietenie");
        System.out.print("Id1: ");
        Long id1 = sc.nextLong();
        srvPrietenie.deletePrietenie(id1, idLogin);
        System.out.println("Prietenie stearsa!");
    }

    private void relatiiPrietenieUtilizatorLogin()  {
        System.out.println("Relatii de prietenie utilizator: ");
        List<Tuple<Utilizator, LocalDateTime>> rez=srvPrietenieUtilizator.relatiiPrietenieUtilizator(idLogin);
        if(rez.isEmpty())
            System.out.println("Utilizatorul dat nu are prieteni!:(");
        for (Tuple<Utilizator, LocalDateTime> utilizatorDateTuple : rez) {
            System.out.println(utilizatorDateTuple.getLeft().getFirstName()+"|"+
                    utilizatorDateTuple.getLeft().getLastName()+"|"+
                    utilizatorDateTuple.getRight().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
    }

    private void adaugaMesajLogin() {
        System.out.println("Adauga mesaj: ");
        System.out.print("Ids receivers: ");
        String ids = sc.nextLine();
        List<Long> idsReceivers = Arrays.stream(ids.split(","))
                .map(x -> Long.parseLong(x.strip())).toList();
        System.out.println("Mesaj: ");
        String msg = sc.nextLine();
        srvMesaje.adaugaMesaj(idLogin, idsReceivers, msg);
    }

    private void afisareConversatieLogin() {
        System.out.println("Afisare conversatie a doi utilizatori: ");
        System.out.print("Id1: ");
        Long id1 = sc.nextLong();
        srvMesaje.afisareConversatie(id1,idLogin).forEach(System.out::println);
    }

    private void login(){
        System.out.println("Logare: ");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Parola: ");
        String parola = sc.nextLine();
        idLogin = srvLogin.login(email, parola);
        System.out.println("Logare cu succes!");
        listaComenziLogin();
    }

    private void logout(){
        idLogin = -1L;
        System.out.println("Logout cu succes!");
        listaComenzi();
    }

    private void procesareCerere() {
        System.out.println("Procesare cerere de prietenie: ");
        System.out.println("Dati id-ul cererii: ");
        Long id=sc.nextLong();
        System.out.println("Alegeti optiunea: 1 pentru accept/2 pentru decline");
        Long optiune = sc.nextLong();
        srvCereri.procesareCerere(id,optiune);
    }

    public void afisareCereriLogin() {
        System.out.println("Afisare cereri de prietenie pentru un utilizator dat");
        srvCereri.afisareCereriUtilizator(idLogin);
    }
    public void trimiteCerereLogin() {
        System.out.println("Trimitere cerere de prietenie: ");
        System.out.println("Dati id-ul userului care primeste cererea: ");
        Long id2=sc.nextLong();
        srvCereri.trimiteCerereDePrietenie(idLogin,id2);
    }

    public void addGrup(){
        System.out.println("Creaza grup");
        System.out.print("Nume: ");
        String nume = sc.nextLine();
        System.out.print("Id Admin: ");
        Long id_admin = sc.nextLong();
        List<Long> membrii = new ArrayList<>();
        for(Long id=sc.nextLong(); id != 0; id=sc.nextLong()){
            membrii.add(id);
        }
        srvGrup.addGrup(nume, id_admin, membrii);
    }

    public void updGrup(){
        System.out.println("Updateaza grup");
        System.out.print("Id: ");
        Long id = sc.nextLong();
        sc.nextLine();
        System.out.print("Nume: ");
        String nume = sc.nextLine();
        System.out.print("Id Admin: ");
        Long id_admin = sc.nextLong();
        srvGrup.updateGrup(id, nume, id_admin);
    }

    public void delGrup(){
        System.out.println("Sterge grup");
        System.out.print("Id: ");
        Long id = sc.nextLong();
        srvGrup.deleteGrup(id);
    }

    public void run() {
        int caz;
        listaComenzi();
        while (true) {
            System.out.print(">>>");
            try {
                caz = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Nu s-a introdus un intreg");
                continue;
            }
            if (caz == 0) {
                System.out.println("Iesire din program");
                break;
            }
            try {
                if(idLogin != -1) {
                    switch (caz) {
                        case 100 -> logout();
                        case 99 -> listaComenziLogin();
                        case 1 -> addPrietenieLogin();
                        case 2 -> remPrietenieLogin();
                        case 3 -> relatiiPrietenieUtilizatorLogin();
                        case 10 -> adaugaMesajLogin();
                        case 11 -> afisareConversatieLogin();
                        case 20 -> trimiteCerereLogin();
                        case 21 -> afisareCereriLogin();
                        case 22 -> procesareCerere();
                    }
                }
                else {
                    switch (caz) {
                        case 100 -> login();
                        case 99 -> listaComenzi();
                        case 1 -> addUtilizator();
                        case 2 -> updUtilizator();
                        case 3 -> remUtilizator();
                        case 9 -> printUtilizatori();
                        case 10 -> addPrietenie();
                        case 11 -> remPrietenie();
                        case 19 -> printPrietenii();
                        case 20 -> numarComunitati();
                        case 21 -> ComunitateSociabila();
                        case 30 -> relatiiPrietenieUtilizator();
                        case 31 -> relatiiUtilizator();
                        case 32 -> adaugaMesaj();
                        case 33 -> afisareConversatie();
                        case 41 -> trimiteCerere();
                        case 42 -> afisareCereri();
                        case 43 -> procesareCerere();
                        case 50 -> addGrup();
                        case 51 -> delGrup();
                        case 52 -> updGrup();
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }



}
