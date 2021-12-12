package socialnetwork.service;

import socialnetwork.domain.Utilizator;
import socialnetwork.repository.database.LoginDbRepo;

public class ServiceLogin {
    private LoginDbRepo repo;

    public ServiceLogin(LoginDbRepo repo) {
        this.repo = repo;
    }

    public Long login(String email, String parola){
        return repo.login(email,parola);
    }
}
