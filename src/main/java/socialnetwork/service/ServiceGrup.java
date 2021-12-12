package socialnetwork.service;

import socialnetwork.domain.Grup;
import socialnetwork.domain.Grup;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.RepositoryException;
import socialnetwork.repository.database.GrupDbRepo;
import socialnetwork.repository.database.UseriGrupDbRepo;

import java.util.List;

public class ServiceGrup {
    private Repository0<Long, Utilizator> repoUtilizator;
    private GrupDbRepo repoGrup;
    private UseriGrupDbRepo repoUseriGrup;
    private Long idUltimuluiGrup;

    public ServiceGrup(Repository0<Long, Utilizator> repoUtilizator, GrupDbRepo repoGrup,
                       UseriGrupDbRepo repoUseriGrup) {
        this.repoUtilizator = repoUtilizator;
        this.repoGrup = repoGrup;
        this.repoUseriGrup = repoUseriGrup;
    }
    
    public void addGrup(String nume, Long id_admin, List<Long> membrii) {
        if(repoUtilizator.findOne(id_admin) == null) throw new RepositoryException("Id admin invalid!");
        membrii.forEach(idUser -> {if(repoUtilizator.findOne(idUser) == null) throw new RepositoryException("Id membrii invalid!");});
        Long idGrup = repoGrup.save(new Grup(1L, nume, id_admin)).getId();
        membrii.forEach(idUser -> repoUseriGrup.save(idGrup, idUser));
    }

    public void addMembruInGrup(){}

    public void stergeMembruDinGrup(){}

    public Iterable<Grup> findAll() {
        return repoGrup.findAll();
    }

    public Grup deleteGrup(Long id) {
        Grup g = repoGrup.delete(id);
        return g;
    }

    public Grup updateGrup(Long id, String nume, Long id_admin) {
        Grup g = new Grup( id, nume, id_admin);
        return repoGrup.update(g);
    }
}