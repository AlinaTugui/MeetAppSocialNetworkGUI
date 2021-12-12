package socialnetwork.repository.database;

import socialnetwork.domain.*;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.RepositoryException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

public class PrietenieDbRepo implements Repository0<Tuple<Long,Long>, Prietenie> {
    private String url;
    private String username;
    private String password;
    private Validator<Prietenie> validator;

    public PrietenieDbRepo(String url, String username, String password, Validator<Prietenie> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    @Override
    public Prietenie findOne(Tuple<Long, Long> t) {
        Prietenie pValidate = new Prietenie(LocalDateTime.now());

        pValidate.setId(t);
        validator.validate(pValidate);
        String sql = "select date from friends where id1=? and id2=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, t.getLeft().intValue());
            ps.setInt(2, t.getRight().intValue());
            ResultSet resultSet = ps.executeQuery();
            LocalDateTime ld = null;
            if(resultSet.getDate("date") != null &&
                    resultSet.getTime("time") != null )
                ld = LocalDateTime.of(resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime());

            Prietenie p = new Prietenie(ld);

            p.setId(new Tuple(t.getLeft(),t.getRight()));
            return p;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Prietenie> findAll() {
        Set<Prietenie> pritenii = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friends");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");

                LocalDateTime ld = null;
                if(resultSet.getDate("date") != null &&
                        resultSet.getTime("time") != null )
                    ld = LocalDateTime.of(resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime());

                Prietenie p = new Prietenie(ld);
                p.setId(new Tuple(id1,id2));
                pritenii.add(p);
            }
            return pritenii;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pritenii;
    }

    @Override
    public Prietenie save(Prietenie prietenie) {
        validator.validate(prietenie);
        String sql1 = "select * from friends where id1=? and id2=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql1)) {
            ps.setInt(1, prietenie.getId().getLeft().intValue());
            ps.setInt(2, prietenie.getId().getRight().intValue());

            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) throw new RepositoryException("Prietenie existenta!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String sql = "insert into friends (id1, id2, date, time ) values (?, ?, ?, ?)";


        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, prietenie.getId().getLeft().intValue());
            ps.setInt(2, prietenie.getId().getRight().intValue());
            ps.setDate(3, Date.valueOf(prietenie.getDateTime().toLocalDate()));
            ps.setTime(4, Time.valueOf(prietenie.getDateTime().toLocalTime()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Prietenie delete(Tuple<Long, Long> t) {
        Prietenie pValidate = new Prietenie(LocalDateTime.now());
        pValidate.setId(t);
        validator.validate(pValidate);
        String sql = "delete from friends where id1=? and id2=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, t.getLeft().intValue());
            ps.setInt(2, t.getRight().intValue());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Prietenie update(Prietenie prietenie) {
        return null;
    }
}