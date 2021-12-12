package socialnetwork.service;

import socialnetwork.domain.MesajConv;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.database.MesajeDbRepo;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceMesaje {
    private Repository0<Long, Utilizator> repoUtilizator;
    private Repository0<Tuple<Long, Long>, Prietenie> repoPrietenie;
    private MesajeDbRepo repoMsgCoresp;
    private ServiceUtilizator srvUtilizator;
    private ServicePrietenie srvPrietenie;

    public ServiceMesaje(Repository0<Long, Utilizator> repoUtilizator,
                                      Repository0<Tuple<Long, Long>, Prietenie> repoPrietenie,
                                      MesajeDbRepo repoMsgCoresp,
                                      ServiceUtilizator srvUtilizator,
                                      ServicePrietenie srvPrietenie) {
        this.repoUtilizator = repoUtilizator;
        this.repoPrietenie = repoPrietenie;
        this.srvUtilizator = srvUtilizator;
        this.srvPrietenie = srvPrietenie;
        this.repoMsgCoresp = repoMsgCoresp;
    }

    public void adaugaMesaj(Long idSender, List<Long> idReceivers, String msg){
        if (repoUtilizator.findOne(idSender) == null) throw new ServiceException("Nu exista acesti utilizatori!");
        for (Long idReceiver : idReceivers) {
            if (repoUtilizator.findOne(idReceiver) == null) throw new ServiceException("Nu exista acesti utilizatori!");
            Long idReply = repoMsgCoresp.idUltimuluiReply(idSender,idReceiver);
            repoMsgCoresp.save(idSender, idReceiver, msg, LocalDateTime.now(), idReply);
        }
    }

    public List<MesajConv> afisareConversatie(Long id1, Long id2){
        if (repoUtilizator.findOne(id1) == null || repoUtilizator.findOne(id2) == null) throw new ServiceException("Nu exista acesti utilizatori!");
        return repoMsgCoresp.afisareConversatie(repoUtilizator.findOne(id1),repoUtilizator.findOne(id2));
    }

    public void adaugaMesajGrup(Long idSender, Long idGrup, String msg){
        if (repoUtilizator.findOne(idSender) == null) throw new ServiceException("Nu exista acesti utilizatori!");
        repoMsgCoresp.saveMsgGrup(idSender,idGrup,msg,LocalDateTime.now());
    }
}