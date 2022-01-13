package socialnetwork.repository.database;
import socialnetwork.domain.Event;
import socialnetwork.repository.RepositoryException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventsDbRepo {

    private final String url;
    private final String username;
    private final String password;

    public EventsDbRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    protected Event extractEntityFromResultSetEntry(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        LocalDateTime beginDate = resultSet.getTimestamp("start_date").toLocalDateTime();
        LocalDateTime endDate = resultSet.getTimestamp("end_date").toLocalDateTime();
        String creator = resultSet.getString("creator");
        Event event = new Event(name, description, beginDate, endDate, creator);
        event.setId(id);
        return event;
    }

    public void save(Event event) {
        String sql1 = "select * from events where name=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql1)) {
            ps.setString(1, event.getName());

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) throw new RepositoryException("Event existent!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String sql = "insert into events (name, description, start_date, end_date, creator ) values (?, ?, ?, ?,?)";

        Integer id;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, event.getName());
            ps.setString(2, event.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(event.getStartDate()));
            ps.setTimestamp(4, Timestamp.valueOf(event.getEndDate()));
            ps.setString(5, event.getCreator());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
            event.setId(id.longValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Long findUserEvent(Long idUser, Long idEvent) {
        String sql = "select * from events_users where id_event=? and id_user=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idEvent.intValue());
            ps.setInt(2, idUser.intValue());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return idUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUserToEvent(Long idUser, Long idEvent,LocalDateTime timestamp) {
        String sql = "insert into events_users (id_event,id_user,subscription_date) values (?, ?,?)";


        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idEvent.intValue());
            ps.setInt(2, idUser.intValue());
            ps.setTimestamp(3,Timestamp.valueOf(timestamp));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserFromEvent(Long idUser, Long idEvent) {
        String sql = "delete from events_users where id_event=? and id_user=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(2, idUser.intValue());
            ps.setInt(1, idEvent.intValue());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from events");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long idEvent = resultSet.getLong("id");
                events.add(0, extractEvent(idEvent));
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    private Event extractEvent(Long idEvent) {
        Event e = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from events where id=?")) {
            statement.setInt(1, idEvent.intValue());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name=resultSet.getString("name");
                String description=resultSet.getString("description");
                String creator=resultSet.getString("creator");
                LocalDateTime startDate = resultSet.getTimestamp("start_date").toLocalDateTime();
                LocalDateTime endDate = resultSet.getTimestamp("end_date").toLocalDateTime();
                e = new Event(name,description,startDate,endDate,creator);
                e.setId(idEvent);
            }
            return e;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return e;
    }

    public List<Event> getAllEventsUser(Long idUser) {
        List<Event> res = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from events_users where id_user=?")) {
            statement.setInt(1, idUser.intValue());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long idEvent = resultSet.getLong("id_event");
                res.add(0,extractEvent(idEvent));
            }
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public LocalDateTime findSubscriptionDate(Long idEvent, Long idUser) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from events_users where id_event=? and id_user=?")) {
            statement.setInt(2, idUser.intValue());
            statement.setInt(1, idEvent.intValue());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LocalDateTime timestamp = resultSet.getTimestamp("subscription_date").toLocalDateTime();
                return timestamp;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
