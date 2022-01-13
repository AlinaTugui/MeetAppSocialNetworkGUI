package socialnetwork.repository.database;

import socialnetwork.domain.Cerere;
import socialnetwork.domain.Event;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.RepositoryException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventsDbRepo {

    private String url;
    private String username;
    private String password;

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
            if(resultSet.next()) throw new RepositoryException("Event existent!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String sql = "insert into events (name, description, start_date, end_date, creator ) values (?, ?, ?, ?,?)";

        Integer id;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, event.getName());
            ps.setString(2, event.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(event.getStartDate()));
            ps.setTimestamp(4, Timestamp.valueOf(event.getEndDate()));
            ps.setString(5, event.getCreator());
            ps.executeUpdate();
            ResultSet rs= ps.getGeneratedKeys();
            rs.next();
            id=rs.getInt(1);
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
            if(resultSet.next()) {
                return idUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUserToEvent(Long idUser, Long idEvent) {
        String sql = "insert into events_users (id_event,id_user) values (?, ?)";


        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1,idEvent.intValue());
            ps.setInt(2,idUser.intValue());

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
               /* String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String creator = resultSet.getString("creator");
                LocalDateTime startDate = resultSet.getTimestamp("start_date").toLocalDateTime();
                LocalDateTime endDate = resultSet.getTimestamp("end_date").toLocalDateTime();*/
                /*Event event = new Event(name, description, startDate, endDate, creator);
                events.add(event);*/
                events.add(0,extractEntityFromResultSetEntry(resultSet));
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}
