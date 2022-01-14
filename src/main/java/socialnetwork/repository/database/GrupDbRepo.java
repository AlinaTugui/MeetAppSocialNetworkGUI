package socialnetwork.repository.database;

import socialnetwork.domain.Grup;
import socialnetwork.repository.Repository0;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class GrupDbRepo implements Repository0<Long, Grup> {
    private String url;
    private String username;
    private String password;

    public GrupDbRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    public Grup findOne(Long id) {
        String sql = "select name, id_admin from groups where id=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next()) return null;
            return new Grup(id, resultSet.getString("name"), resultSet.getLong("id_admin"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Grup> findAll() {
        Set<Grup> groups = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from groups");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("first_name");
                Long id_admin = Long.parseLong(resultSet.getString("id_admin"));

                Grup grup = new Grup(id, name, id_admin);
                groups.add(grup);
            }
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    public Grup save(Grup entity) {
        Integer id=-1;
        String sql = "insert into groups (name,id_admin) values (?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getNume());
            ps.setInt(2, entity.getIdAdmin().intValue());
            ps.executeUpdate();
            ResultSet rs= ps.getGeneratedKeys();
            rs.next();
            id=rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Acesta este id ul grupului"+id);
        return new Grup(Long.valueOf(id), entity.getNume(), entity.getId());
    }

    @Override
    public Grup delete(Long aLong) {
        String sql = "delete from groups where id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, aLong);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Grup update(Grup entity) {
        String sql = "update groups set name=?, id_admin=? where id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getNume());
            ps.setLong(2, entity.getIdAdmin());
            ps.setLong(3, entity.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}

