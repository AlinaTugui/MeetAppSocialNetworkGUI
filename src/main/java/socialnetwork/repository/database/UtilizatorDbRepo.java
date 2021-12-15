package socialnetwork.repository.database;

import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.RepositoryException;

import java.sql.*;
import java.util.*;

public class UtilizatorDbRepo implements Repository0<Long, Utilizator> {
    private String url;
    private String username;
    private String password;
    private Validator<Utilizator> validator;

    public UtilizatorDbRepo(String url, String username, String password, Validator<Utilizator> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    @Override
    public Utilizator findOne(Long id) {
        String sql = "select first_name, last_name from users where id=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id.intValue());
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next()) return null;
            return new Utilizator(id, resultSet.getString("first_name"), resultSet.getString("last_name"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Utilizator> findAll() {
        Set<Utilizator> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                Utilizator utilizator = new Utilizator(id, firstName, lastName);
                users.add(utilizator);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Utilizator save(Utilizator entity) {
        validator.validate(entity);

        String sql = "insert into users (id, first_name, last_name ) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getId().intValue());
            ps.setString(2, entity.getFirstName());
            ps.setString(3, entity.getLastName());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utilizator delete(Long aLong) {
        String sql = "delete from users where id=?";
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
    public Utilizator update(Utilizator entity) {
        validator.validate(entity);
        String sql = "update users set first_name=?, last_name=? where id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setLong(3, entity.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
