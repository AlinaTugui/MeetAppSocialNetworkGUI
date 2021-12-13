package socialnetwork.repository.database;

import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.RepositoryException;

import java.sql.*;

public class LoginDbRepo {
    private String url;
    private String username;
    private String password;
    private Validator<Utilizator> validator;

    public LoginDbRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Long login(String email, String parola){
        String sql = "select id from users where email=? and parola=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, parola);
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next()) throw new RepositoryException("User not found!");
            return resultSet.getLong("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
