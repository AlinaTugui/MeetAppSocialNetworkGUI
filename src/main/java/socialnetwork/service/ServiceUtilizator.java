package socialnetwork.service;

import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository0;

import java.util.*;

/**
 * Service for managing Utilizatori, Prietenii and other operations with those 2
 */
public class ServiceUtilizator {
    private Repository0<Long, Utilizator> repo;

    public ServiceUtilizator(Repository0<Long, Utilizator> repo) {
        this.repo = repo;
    }

    public Utilizator addUtilizator(Long id, String firstName, String lastName) {
        Utilizator u = new Utilizator(id, firstName, lastName);
        return repo.save(u);
    }

    public Iterable<Utilizator> findAll() {
        return repo.findAll();
    }

    public Utilizator deleteUtilizator(Long id) {
        Utilizator u = repo.delete(id);
        return u;
    }

    public Utilizator updateUtilizator(Long id, String firstName, String lastName) {
        Utilizator u = new Utilizator(id, firstName, lastName);
        return repo.update(u);
    }

}

