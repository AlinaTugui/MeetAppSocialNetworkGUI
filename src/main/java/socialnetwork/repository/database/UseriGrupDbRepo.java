package socialnetwork.repository.database;

import socialnetwork.domain.Grup;
import socialnetwork.domain.Tuple;
import socialnetwork.repository.Repository0;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UseriGrupDbRepo {

    private String url;
    private String username;
    private String password;

    public UseriGrupDbRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }



    public void delete(Tuple<Long,Long> aLong) {
        String sql = "delete from groups where id_grup=? and id_user=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, aLong.getLeft());
            ps.setLong(2, aLong.getRight());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Long idUser, Long idGrup){
        String sql = "insert into groups (id_grup, id_user) values (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, idGrup);
            ps.setLong(2, idUser);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
