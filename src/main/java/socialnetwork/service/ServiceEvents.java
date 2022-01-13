package socialnetwork.service;

import com.example.lab6v2.MainViewController;
import socialnetwork.domain.Event;
import socialnetwork.domain.Notification;
import socialnetwork.domain.NotificationTimer;
import socialnetwork.repository.database.EventsDbRepo;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
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
        repo.addUserToEvent(MainViewController.getIdLogin(),idEvent,LocalDateTime.now());
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

    public List<Notification> getNotifications(Long idUser) {
        List<Notification> notifications = new ArrayList<>();
        List<Event> events = repo.getAllEventsUser(idUser);
        for(Event e:events) {
            /*LocalDateTime time = e.getStartDate().minusDays(3);
            System.out.println(e.getName());
            System.out.println(time);
            boolean ok = time.isBefore((LocalDateTime.now()));
            System.out.println(ok);
            System.out.println('\n');
            if(ok)
                notifications.add(new Notification("3 zile",LocalDateTime.now(),e.getName()));*/
            for(var notificationTimer:NotificationTimer.values()) {
                if(notificationTimer.equals(NotificationTimer.SUBSCRIBED))
                    notifications.add(new Notification(notificationTimer.getMessage(),findSubscriptionDate(e.getId()),e.getName()));
                LocalDateTime notifyTime = e.getStartDate().minus(notificationTimer.getDifferenceInTime());
                if(notifyTime.isBefore(findSubscriptionDate(e.getId()))) continue;
                if(notifyTime.isBefore(LocalDateTime.now()))
                    notifications.add(new Notification(notificationTimer.getMessage(),notifyTime,e.getName()));
            }
        }
        return notifications;
    }

    private LocalDateTime findSubscriptionDate(Long idEvent) {

        return repo.findSubscriptionDate(idEvent,MainViewController.getIdLogin());
    }
}
