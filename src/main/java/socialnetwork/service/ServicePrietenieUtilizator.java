package socialnetwork.service;

import socialnetwork.domain.PrietenSiData;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.database.MesajeDbRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.StreamSupport;

public class ServicePrietenieUtilizator {
    private Repository0<Long, Utilizator> repoUtilizator;
    private Repository0<Tuple<Long, Long>, Prietenie> repoPrietenie;
    private MesajeDbRepo repoMsgCoresp;
    private ServiceUtilizator srvUtilizator;
    private ServicePrietenie srvPrietenie;
    HashMap<Long, List<Long>> prietenii;

    public ServicePrietenieUtilizator(Repository0<Long, Utilizator> repoUtilizator,
                                      Repository0<Tuple<Long, Long>, Prietenie> repoPrietenie,
                                      MesajeDbRepo repoMsgCoresp,
                                      ServiceUtilizator srvUtilizator,
                                      ServicePrietenie srvPrietenie) {
        this.repoUtilizator = repoUtilizator;
        this.repoPrietenie = repoPrietenie;
        this.srvUtilizator = srvUtilizator;
        this.srvPrietenie = srvPrietenie;
    }

    public void addPrietenie(Long id1, Long id2) {
        if (repoUtilizator.findOne(id1) == null || repoUtilizator.findOne(id2) == null)
            throw new ServiceException("Nu exista acesti utilizatori!");
        srvPrietenie.addPrietenie(id1, id2);
    }

    private void updateListaPrietenii() {
        HashMap<Long, List<Long>> prietenii = new HashMap<>();
        for (Prietenie prietenie : repoPrietenie.findAll()) {
            if (!prietenii.containsKey(prietenie.getId().getLeft()))
                prietenii.put(prietenie.getId().getLeft(), new ArrayList(Arrays.asList(prietenie.getId().getRight())));
            else
                prietenii.get(prietenie.getId().getLeft()).add(prietenie.getId().getRight());
            if (!prietenii.containsKey(prietenie.getId().getRight()))
                prietenii.put(prietenie.getId().getRight(), new ArrayList(Arrays.asList(prietenie.getId().getLeft())));
            else
                prietenii.get(prietenie.getId().getRight()).add(prietenie.getId().getLeft());
        }
        this.prietenii = prietenii;
    }

    public Utilizator deleteUtilizator(Long id) {
        Utilizator u = repoUtilizator.delete(id);
        srvPrietenie.stergePrieteniiUtilizator(id);
        return u;
    }

    private void DFS(List<Utilizator> utilizatori, boolean[] viz, Long idU) {
        viz[idU.intValue()] = true;
        if (prietenii.get(idU) == null) return;
        for (Long idNou : prietenii.get(idU)) {
            if (viz[idNou.intValue()] == false) {
                DFS(utilizatori, viz, idNou);
            }
        }
    }

    /**
     * Afla numarul comunitatilor pe baza prieteniilor
     *
     * @return numarul comunitatilor - int
     */
    public int numarComunitati() {
        updateListaPrietenii();
        int rez = 0;
        List<Utilizator> utilizatori = new ArrayList<>();
        repoUtilizator.findAll().forEach(utilizatori::add);
        boolean[] viz = new boolean[1000];
        //List<Boolean> viz = new ArrayList<>(Collections.nCopies(utilizatori.size(), false));
        for (Utilizator u : utilizatori) {
            if (viz[u.getId().intValue()] == true) continue;
            rez++;
            DFS(utilizatori, viz, u.getId());
        }
        return rez;
    }

    public int BFS(List<Utilizator> utilizatori, boolean[] viz, boolean[] viz2, Long idU, List<Utilizator> compSocCurent) {
        Queue<Long> q = new LinkedList<>();
        q.add(idU);
        Long nc = idU, capat;
        viz2[nc.intValue()] = true;
        if (prietenii.get(idU) == null) return 0;
        while (!q.isEmpty()) {
            nc = q.remove();
            for (Long idNou : prietenii.get(nc)) {
                if (!viz2[idNou.intValue()]) {
                    viz2[idNou.intValue()] = true;
                    q.add(idNou);
                }
            }
        }
        int[] dist = new int[1000];
        capat = nc;
        q.add(capat);
        dist[capat.intValue()] = 0;
        viz[nc.intValue()] = true;
        while (!q.isEmpty()) {
            nc = q.remove();
            compSocCurent.add(repoUtilizator.findOne(nc));
            for (Long idNou : prietenii.get(nc)) {
                if (!viz[idNou.intValue()]) {
                    viz[idNou.intValue()] = true;
                    dist[idNou.intValue()] = dist[nc.intValue()] + 1;
                    q.add(idNou);
                }
            }
        }
        return dist[nc.intValue()];
    }

    /**
     * Afiseaza cea mai sociabila comunitatea si lungimea ei
     *
     * @return lungimea drumului din cea mai sociabila comunitate
     */
    public Integer ComunitateSociabila(List<Utilizator> l) {
        updateListaPrietenii();
        Integer curent = 0, MAX = 0;
        List<Utilizator> compSocCurent = new ArrayList<>();
        List<Utilizator> utilizatori = new ArrayList<>();
        repoUtilizator.findAll().forEach(utilizatori::add);
        boolean[] viz1 = new boolean[1000];
        boolean[] viz2 = new boolean[1000];
        for (Utilizator u : utilizatori) {
            if (viz2[u.getId().intValue()]) continue;
            compSocCurent.clear();
            curent = BFS(utilizatori, viz1, viz2, u.getId(), compSocCurent);
            if (curent > MAX) {
                MAX = curent;
                l.clear();
                compSocCurent.forEach(l::add);
            }
        }
        return MAX;
    }

    public List<PrietenSiData> relatiiUtilizator(Long id, Integer luna) {
        return StreamSupport.stream(srvPrietenie.findAll().spliterator(), false)
                .filter(prietenie -> {
                    return (prietenie.getId().getLeft() == id || prietenie.getId().getRight() == id) &&
                            prietenie.getDateTime().getMonth().equals(Month.of(luna));
                })
                .map(prietenie -> {
                    Long id2 = prietenie.getId().getLeft() == id ?
                            prietenie.getId().getRight() : prietenie.getId().getLeft();
                    Utilizator u = repoUtilizator.findOne(id2);
                    return new PrietenSiData(u.getFirstName(), u.getLastName(),
                            prietenie.getDateTime().toLocalDate());
                })
                .toList();
    }
    public List<Tuple<Utilizator, LocalDateTime>> relatiiPrietenieUtilizator(Long id) {
        Utilizator u=repoUtilizator.findOne(id);
        if(u==null)
            throw new ServiceException("Utilizatorul cu id ul "+id+ " nu exista!");

        return StreamSupport.stream(repoPrietenie.findAll().spliterator(),false)
                .filter(prietenie -> {
                    return (prietenie.getId().getLeft()==id || prietenie.getId().getRight()==id);
                })
                .map(prietenie -> {
                    Long id1=-1L;
                    if(prietenie.getId().getLeft().equals(id))
                        id1=prietenie.getId().getRight();
                    else if(prietenie.getId().getRight().equals(id))
                        id1=prietenie.getId().getLeft();
                    Utilizator user=repoUtilizator.findOne(id1);
                    return new Tuple<Utilizator, LocalDateTime>(user,prietenie.getDateTime());
                })
                .toList();
    }
}
