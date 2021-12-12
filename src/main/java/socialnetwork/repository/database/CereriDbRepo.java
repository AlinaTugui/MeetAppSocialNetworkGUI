package socialnetwork.repository.database;

import socialnetwork.domain.*;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.RepositoryException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CereriDbRepo {
    private String url;
    private String username;
    private String password;

    public CereriDbRepo(String url, String username, String password, Repository0<Long, Utilizator> repoUtilizator) {
        this.url = url;
        this.username = username;
        this.password = password;
    }


    public void save(Long idSender, Long idReceiver, LocalDateTime timestamp) {
        String sql1 = "select * from cereri_de_prietenie where id_sender=? and id_receiver=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql1)) {
            ps.setInt(1, idSender.intValue());
            ps.setInt(2, idReceiver.intValue());

            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) throw new RepositoryException("Cerere existenta!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

       String sql = "insert into cereri_de_prietenie (id_sender,id_receiver,status,timestamp) values (?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setInt(1, idSender.intValue());
            ps.setInt(2, idReceiver.intValue());
            ps.setString(3,"pending");
            ps.setTimestamp(4, Timestamp.valueOf(timestamp));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   public List<Cerere> findAll() {
       List<Cerere> cereri = new ArrayList<>();
       try (Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * from cereri_de_prietenie");
            ResultSet resultSet = statement.executeQuery()) {

           while (resultSet.next()) {
               Long idSender = resultSet.getLong("id_sender");
               Long idReceiver = resultSet.getLong("id_receiver");
               String status = resultSet.getString("status");
               LocalDateTime timestamp = resultSet.getTimestamp("timestamp").toLocalDateTime();
               Cerere cerere = new Cerere(idSender,idReceiver,status,timestamp);
               cereri.add(cerere);
           }
           return cereri;
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return cereri;
   }
    public List<Cerere> findAllUsers(Long id) {
        List<Cerere> cereri = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement("SELECT * from cereri_de_prietenie " +
                    "where id_receiver=?");
            statement.setInt(1,id.intValue());
             ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long idSender = resultSet.getLong("id_sender");
                Long idReceiver = resultSet.getLong("id_receiver");
                String status = resultSet.getString("status");
                LocalDateTime timestamp = resultSet.getTimestamp("timestamp").toLocalDateTime();
                Cerere cerere = new Cerere(idSender,idReceiver,status,timestamp);
                cereri.add(cerere);
            }
            return cereri;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cereri;
    }
    public Cerere findOne(Long id) {
        String sql = "select * from cereri_de_prietenie where id=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id.intValue());
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                Long idSender = Long.valueOf(resultSet.getInt("id_sender"));
                Long idReceiver = Long.valueOf(resultSet.getInt("id_receiver"));
                String status = resultSet.getString("status");
                LocalDateTime timestamp = resultSet.getTimestamp("timestamp").toLocalDateTime();
                Cerere cerere = new Cerere(idSender, idReceiver, status, timestamp);
                cerere.setId(id);
                return cerere;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Long id,String optiune, LocalDateTime timestamp) {
        String sql = "update cereri_de_prietenie set status=?, timestamp=? where id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,optiune);
            ps.setInt(3, id.intValue());
            ps.setTimestamp(2, Timestamp.valueOf(timestamp));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(Long id) {
        String sql = "delete from cereri_de_prietenie where id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id.intValue());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void deleteCerere(Long idSender, Long idReceiver) {
        String sql = "delete from cereri_de_prietenie where id_sender=? and id_receiver=? or id_sender=? and id_receiver=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idSender.intValue());
            ps.setInt(2, idReceiver.intValue());
            ps.setInt(4, idSender.intValue());
            ps.setInt(3, idReceiver.intValue());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

