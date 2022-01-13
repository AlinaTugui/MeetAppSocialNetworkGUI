package socialnetwork.service;

import com.example.lab6v2.MainViewController;
import socialnetwork.domain.Event;
import socialnetwork.repository.database.EventsDbRepo;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceEvents {
    private EventsDbRepo repo;
    public ServiceEvents(EventsDbRepo repoEvents) {
        this.repo = repoEvents;
    }
    public Long findSubscription(Long idEvent) {

        return repo.findUserEvent(MainViewController.getIdLogin(),idEvent);
    }
    public void subscribeToEvent(Long idEvent) {
        repo.addUserToEvent(MainViewController.getIdLogin(),idEvent);
    }
    public void unsubscribeFromEvent(Long idEvent) {
        repo.removeUserFromEvent(MainViewController.getIdLogin(),idEvent);
    }
    public void addEvent(String name, String description, LocalDateTime beginTime,LocalDateTime endTime, String creator) {
            repo.save(new Event(name,description,beginTime,endTime,creator));
    }
    public List<Event> getAllEvents() {
        return repo.getAll();
    }
}
