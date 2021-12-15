package socialnetwork.service;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.repository.Repository0;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServicePrietenie {
    private Repository0<Tuple<Long,Long>, Prietenie> repoPrietenie;

    public ServicePrietenie(Repository0<Tuple<Long,Long>, Prietenie> repoPrietenie){
        this.repoPrietenie = repoPrietenie;
    }

    public void stergePrieteniiUtilizator(Long id){
        List<Tuple<Long,Boolean>> prieteniiDeSters = new ArrayList<>();
        for(Prietenie p : repoPrietenie.findAll()){
            if(p.getId().getLeft().equals(id)) prieteniiDeSters.add(new Tuple(p.getId().getRight(),false));
            else if(p.getId().getRight().equals(id)) prieteniiDeSters.add(new Tuple(p.getId().getRight(),true));
        }
        for(Tuple<Long,Boolean> e : prieteniiDeSters){
            if(e.getRight().equals(false)) repoPrietenie.delete(new Tuple(id, e.getLeft()));
            else repoPrietenie.delete(new Tuple(e.getLeft(), id));
        }
    }

    /**
     * Adauga prietenie intre 2 utilizatori
     * @param id1 - id al unui utilizator
     * @param id2 -idul altui utilizator
     * @throws ServiceException- daca idurile sunt egale
     */
    public void addPrietenie(Long id1, Long id2) {
        if(id1 > id2) id1 += (id2 - (id2 = id1)); //swap
        Prietenie p = new Prietenie(LocalDateTime.now());
        p.setId(new Tuple(id1,id2));
        repoPrietenie.save(p);
//        Utilizator u1 = repoPrietenie.findOne(id1);
//        Utilizator u2 = repoPrietenie.findOne(id2);
//        if(id1 == id2) throw new ServiceException("Idurile nu au voie sa fie identice!");
//        u1.addFriend(id2);
//        u2.addFriend(id1);
//        repoPrietenie.update(u1);
//        repoPrietenie.update(u2);
    }

    /**
     * Sterge prietenie intre 2 utilizatori
     * @param id1 - id al unui utilizator
     * @param id2 -idul altui utilizator
     * @throws ServiceException- daca idurile sunt egale
     */
    public void deletePrietenie(Long id1, Long id2) {
        if(id1 > id2) id1 += (id2 - (id2 = id1));
        repoPrietenie.delete(new Tuple(id1, id2));
//        Utilizator u1 = repoPrietenie.findOne(id1);
//        Utilizator u2 = repoPrietenie.findOne(id2);
//        if(id1 == id2) throw new ServiceException("Idurile nu au voie sa fie identice!");
//        u1.deleteFriend(id2);
//        u2.deleteFriend(id1);
//        repoPrietenie.update(u1);
//        repoPrietenie.update(u2);
    }

    public Iterable<Prietenie> findAll() {
        return repoPrietenie.findAll();
    }

    public Prietenie findOne(Tuple<Long, Long> t) {
        return repoPrietenie.findOne(t);
    }
}
