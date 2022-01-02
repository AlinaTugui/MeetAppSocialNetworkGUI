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

    public List<Long> findGrupuriUser(Long idUser) {
        List<Long> idGrupuri = new ArrayList<>();
        String sql = "select id_grup from useri_grup where id_user=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, idUser);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                idGrupuri.add(rs.getLong("id_grup"));
            }
            return idGrupuri;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Utilizator findOne(Long id) {
        String sql = "select first_name, last_name, email, parola from users where id=?";

        List<Long> idGrupuri = findGrupuriUser(id);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id.intValue());
            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) return null;
            return new Utilizator(id, resultSet.getString("first_name"),
                    resultSet.getString("last_name"), resultSet.getString("email"),
                    resultSet.getString("parola"), idGrupuri);

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
                String email = resultSet.getString("email");
                String parola = resultSet.getString("parola");
                List<Long> idGrupuri = findGrupuriUser(id);

                Utilizator utilizator = new Utilizator(id, firstName, lastName, email, parola, idGrupuri);
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

        String sql = "insert into users (first_name, last_name, email, parola ) values (?, ?, ?, ?)";
        String sql2 = "select id from users where email=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql);
             PreparedStatement ps2 = connection.prepareStatement(sql2);) {


            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getPassword());
            ps.executeUpdate();

            ps2.setString(1, entity.getEmail());
            ResultSet resultSet = ps2.executeQuery();
            resultSet.next();
            entity.setId(resultSet.getLong("id"));
            return entity;
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
        String sql = "update users set first_name=?, last_name=?, email=?, parola=? where id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {


            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getPassword());
            ps.setLong(5, entity.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
