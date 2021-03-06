package socialnetwork.service;

import com.example.lab6v2.MainViewController;
import socialnetwork.domain.*;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.database.GrupDbRepo;
import socialnetwork.repository.database.MesajeDbRepo;
import socialnetwork.repository.database.PrietenieDbRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceMesaje {
    private Repository0<Long, Utilizator> repoUtilizator;
    private GrupDbRepo repoGrup;
    private PrietenieDbRepo repoPrietenie;
    private MesajeDbRepo repoMsgCoresp;
    private ServiceUtilizator srvUtilizator;
    private ServicePrietenie srvPrietenie;

    public ServiceMesaje(Repository0<Long, Utilizator> repoUtilizator,
                         GrupDbRepo repoGrup,
                         PrietenieDbRepo repoPrietenie,
                         MesajeDbRepo repoMsgCoresp,
                         ServiceUtilizator srvUtilizator,
                         ServicePrietenie srvPrietenie
    ) {
        this.repoUtilizator = repoUtilizator;
        this.repoPrietenie = repoPrietenie;
        this.srvUtilizator = srvUtilizator;
        this.srvPrietenie = srvPrietenie;
        this.repoMsgCoresp = repoMsgCoresp;
        this.repoGrup = repoGrup;
    }

    public List<MesajConv> ultimulMesajDeLaToateContacteleUnuiUser(Long id) {
        return repoMsgCoresp.ultimulMesajDeLaToateContacteleUnuiUser(id);
    }

    public List<MesajConv> ultimulMesajDeLaToateGrupurileUnuiUser(List<Long> idGrupuri){
        return repoMsgCoresp.ultimulMesajDeLaToateGrupurileUnuiUser(idGrupuri);
    }


    public void adaugaMesaj(Long idSender, List<Long> idReceivers, String msg) {
        if (repoUtilizator.findOne(idSender) == null) throw new ServiceException("Nu exista acesti utilizatori!");
        for (Long idReceiver : idReceivers) {
            if (repoUtilizator.findOne(idReceiver) == null) throw new ServiceException("Nu exista acesti utilizatori!");
            Long idReply = repoMsgCoresp.idUltimuluiReply(idSender, idReceiver);
            repoMsgCoresp.save(idSender, idReceiver, msg, LocalDateTime.now(), idReply);
        }
    }

    public List<MesajConv> afisareConversatie(Long id1, Long id2) {
        if (repoUtilizator.findOne(id1) == null || repoUtilizator.findOne(id2) == null)
            throw new ServiceException("Nu exista acesti utilizatori!");
        return repoMsgCoresp.afisareConversatie(repoUtilizator.findOne(id1), repoUtilizator.findOne(id2));
    }

    public void adaugaMesajGrup(Long idSender, Long idGrup, String msg) {
        if (repoUtilizator.findOne(idSender) == null) throw new ServiceException("Nu exista acesti utilizatori!");
        repoMsgCoresp.saveMsgGrup(idSender, idGrup, msg, LocalDateTime.now());
    }

    public List<MesajConv> afisareConversatieGrup(Long id1, Long id2) {
        if (repoUtilizator.findOne(id1) == null || repoGrup.findOne(id2) == null)
            throw new ServiceException("Nu exista utilizator sau grup!");
        List<MesajConv> mesaje = repoMsgCoresp.afisareConversatieGrup(repoGrup.findOne(id2));
        mesaje.forEach(x -> x.setFrom(repoUtilizator.findOne(x.getFrom().getId())));
        return mesaje;
    }

    public List<Raport> afisareConversatiiUseriData(Long id, LocalDate d1, LocalDate d2) {
        return repoMsgCoresp.afisareConversatiiUseriData(id, d1, d2);
    }
}
