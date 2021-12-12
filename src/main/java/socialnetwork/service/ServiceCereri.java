package socialnetwork.service;

import socialnetwork.domain.Cerere;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.database.CereriDbRepo;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServiceCereri {
    private Repository0<Long, Utilizator> repoUtilizator;
    private Repository0<Tuple<Long, Long>, Prietenie> repoPrietenie;
    private CereriDbRepo repoCereri;
    private ServiceUtilizator srvUtilizator;
    private ServicePrietenie srvPrietenie;

    public ServiceCereri(Repository0<Long, Utilizator> repoUtilizator,
                         Repository0<Tuple<Long, Long>, Prietenie> repoPrietenie,
                         CereriDbRepo repoCereri, ServiceUtilizator srvUtilizator,
                         ServicePrietenie srvPrietenie) {
        this.repoUtilizator = repoUtilizator;
        this.repoPrietenie = repoPrietenie;
        this.repoCereri = repoCereri;
        this.srvUtilizator = srvUtilizator;
        this.srvPrietenie = srvPrietenie;
    }

    public void trimiteCerereDePrietenie(Long idSender, Long idReceiver) {
        if (idSender == null || idReceiver == null)
            throw new ServiceException("Cel putin un utilizator nu exista!");
        repoCereri.save(idSender, idReceiver, LocalDateTime.now());
    }

    public void afisareCereriUtilizator(Long id) {
     /*   List<Cerere> cereri = repoCereri.findAll();
        List<Cerere> rez = new ArrayList<>();
        for (Cerere cerere : rez)
            if (cerere.getFrom() == id) {
                System.out.println(repoUtilizator.findOne(cerere.getFrom()).getFirstName() + repoUtilizator.findOne(cerere.getFrom()).getLastName());
                System.out.println(repoUtilizator.findOne(cerere.getTo()).getFirstName() + repoUtilizator.findOne(cerere.getFrom()).getLastName());
                System.out.println(cerere.getStatus());
                System.out.println(cerere.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }

*/
        repoCereri.findAllUsers(id).forEach(System.out::println);
    }

    public void procesareCerere(Long id, Long optiune) {
        Cerere c = repoCereri.findOne(id);
        if(c==null)
            throw new ServiceException("Cererea cu id-ul dat nu exista!");
        if(optiune.equals(1L)) {
            repoCereri.update(id,"approved",LocalDateTime.now());
            srvPrietenie.addPrietenie(c.getFrom(), c.getTo());
        }
        else if(optiune.equals(2L)) {
            repoCereri.update(id,"rejected",LocalDateTime.now());
        }
    }

    public void delete(Long id1, Long id2) {
        repoCereri.deleteCerere(id1,id2);
    }
}
