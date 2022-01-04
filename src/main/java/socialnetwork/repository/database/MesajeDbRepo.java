package socialnetwork.repository.database;

import socialnetwork.domain.*;
import socialnetwork.domain.validators.Validator;
import socialnetwork.service.ServiceManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MesajeDbRepo {
    private final String url;
    private final String username;
    private final String password;

    public MesajeDbRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Long idUltimuluiReply(Long idSender, Long idsReceiver){
        String sql = "select id from mesaje " +
                "where id_sender=? and id_receiver=? or id_sender=? and id_receiver=?" +
                "order by timestamp desc " +
                "limit 1";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setInt(1, idSender.intValue());
            ps.setInt(2, idsReceiver.intValue());
            ps.setInt(3, idSender.intValue());
            ps.setInt(4, idsReceiver.intValue());

            ResultSet rs = ps.executeQuery();
            if(!rs.next()) return null;
            return (long) rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Long idSender, Long idReceiver, String msg, LocalDateTime timestamp, Long idReply){
        String sql = idReply == null ?
                "insert into mesaje (id_sender,id_receiver,msg,timestamp) values (?,?,?,?)":
                "insert into mesaje (id_sender,id_receiver,msg,timestamp,reply) values (?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idSender.intValue());
            ps.setInt(2, idReceiver.intValue());
            ps.setString(3, msg);
            ps.setTimestamp(4, Timestamp.valueOf(timestamp));
            if(idReply != null) ps.setInt(5, idReply.intValue());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MesajConv> afisareConversatie(Utilizator u1, Utilizator u2){
        String sql = "select id_sender, msg, timestamp from mesaje " +
                "where id_sender=? and id_receiver=? or id_sender=? and id_receiver=?" +
                "order by timestamp";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            List<MesajConv> listConv = new ArrayList<>();
            ps.setInt(1, u1.getId().intValue());
            ps.setInt(2, u2.getId().intValue());
            ps.setInt(3, u2.getId().intValue());
            ps.setInt(4, u1.getId().intValue());
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Utilizator u = u1.getId().equals((long) rs.getInt("id_sender")) ? u1:u2;
                listConv.add(new MesajConv( u,
                        rs.getString("msg"),
                        rs.getTimestamp("timestamp").toLocalDateTime()));
            }
            return listConv;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Long> ultimulMesajDeLaToateContacteleUnuiUser(Long id){
        String sql = "select (case when id_sender < id_receiver then id_sender else id_receiver end) as id_sender, "+
                "(case when id_sender < id_receiver then id_receiver else id_sender end) as id_receiver "+
                "from mesaje " +
                "where id_sender=? or id_receiver=? " +
                "group by (case when id_sender < id_receiver then id_sender else id_receiver end), " +
                "(case when id_sender < id_receiver then id_receiver else id_sender end) " +
                "order by MAX(TIMESTAMP);";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            List<Long> listUseri = new ArrayList<>();
            ps.setLong(1, id);
            ps.setLong(2, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long idOtherUser=(rs.getLong("id_sender") == id?rs.getLong("id_receiver"):rs.getLong("id_sender"));
                listUseri.add(idOtherUser);
            }
            return listUseri;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("mda");
        }
        return null;
    }

    public void saveMsgGrup(Long idSender, Long idGrup, String msg, LocalDateTime timestamp) {
        String sql = "insert into mesaje_grup (id_sender,id_grup,msg,timestamp) values (?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idSender.intValue());
            ps.setInt(2, idGrup.intValue());
            ps.setString(3, msg);
            ps.setTimestamp(4, Timestamp.valueOf(timestamp));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MesajConv> afisareConversatieGrup(Grup g){
        String sql = "select id_sender ,msg, timestamp from mesaje_grup " +
                "where id_grup=? " +
                "order by timestamp";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            List<MesajConv> listConv = new ArrayList<>();
            ps.setInt(1, g.getId().intValue());
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                listConv.add(new MesajConv( new Utilizator(Long.valueOf(rs.getLong("id_sender")),null,null,null,null,null),
                        rs.getString("msg"),
                        rs.getTimestamp("timestamp").toLocalDateTime()));
            }
            return listConv;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
