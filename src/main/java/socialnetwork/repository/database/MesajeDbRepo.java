package socialnetwork.repository.database;

import socialnetwork.domain.*;
import socialnetwork.domain.validators.Validator;
import socialnetwork.service.ServiceManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public List<MesajConv> ultimulMesajDeLaToateContacteleUnuiUser(Long id){
        String sql= "SELECT  * "+
        "FROM ( "+
        "SELECT  *, "+
        "ROW_NUMBER() OVER (PARTITION BY "+
        "(case when id_sender < id_receiver then id_sender else id_receiver end), "+
        "(case when id_sender < id_receiver then id_receiver else id_sender end) "+
        "ORDER BY timestamp DESC) rn "+
        "FROM  mesaje where id_sender=? or id_receiver=? "+
        ") q "+
        "WHERE   rn = 1 " +
                "order by timestamp desc";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            List<MesajConv> listMesaje = new ArrayList<>();
            ps.setLong(1, id);
            ps.setLong(2, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long idOtherUser=(rs.getLong("id_sender") == id?rs.getLong("id_receiver"):rs.getLong("id_sender"));
                listMesaje.add(new MesajConv(ServiceManager.getInstance().getSrvUtilizator().findOne(idOtherUser),
                        rs.getString("msg"), rs.getTimestamp("timestamp").toLocalDateTime() ));
            }
            return listMesaje;
        } catch (SQLException e) {
            e.printStackTrace();
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
        String sql = "select id_sender, id_grup ,msg, timestamp from mesaje_grup " +
                "where id_grup=? " +
                "order by timestamp";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            List<MesajConv> listConv = new ArrayList<>();
            ps.setInt(1, g.getId().intValue());
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                listConv.add(new MesajConv( new Utilizator(Long.valueOf(rs.getLong("id_sender")),null,null,null,null,null),
                        ServiceManager.getInstance().getSrvGrup().findOne(rs.getLong("id_grup")),
                        rs.getString("msg"),
                        rs.getTimestamp("timestamp").toLocalDateTime()));
            }
            return listConv;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MesajConv> ultimulMesajDeLaToateGrupurileUnuiUser(List<Long> idGrupuri){
        String sql= "SELECT  * "+
                "FROM ( "+
                "SELECT  *, "+
                "ROW_NUMBER() OVER (PARTITION BY "+
                "id_grup " +
                "ORDER BY timestamp DESC) rn "+
                "FROM  mesaje_grup"+
                ") q "+
                "WHERE   rn = 1 " +
                "order by timestamp desc";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            List<MesajConv> listMesaje = new ArrayList<>();
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                if(!idGrupuri.contains(rs.getLong("id_grup"))) continue;
                listMesaje.add(new MesajConv(
                        ServiceManager.getInstance().getSrvUtilizator().findOne(rs.getLong("id_sender")),
                        ServiceManager.getInstance().getSrvGrup().findOne(rs.getLong("id_grup")),
                        rs.getString("msg"), rs.getTimestamp("timestamp").toLocalDateTime() ));
            }
            return listMesaje;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Raport> afisareConversatiiUseriData(Long id, LocalDate d1, LocalDate d2){
        LocalDateTime d11 = d1.atTime(LocalTime.MIN);
        LocalDateTime d22 = d2.atTime(LocalTime.MAX);
        String sql="select id_sender, id_receiver, timestamp from mesaje where timestamp >= ? and timestamp <= ? " +
                "and (id_sender=? or id_receiver=?) " +
                "order by timestamp asc";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            List<Raport> rapoarte = new ArrayList<>();
            ps.setTimestamp(1, Timestamp.valueOf(d11));
            ps.setTimestamp(2, Timestamp.valueOf(d22));
            ps.setLong(3, id);
            ps.setLong(4, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Long idOther = (id == resultSet.getLong("id_sender"))?
                        resultSet.getLong("id_receiver"):resultSet.getLong("id_sender");
                Utilizator u = ServiceManager.getInstance().getSrvUtilizator().findOne(idOther);
                rapoarte.add(new Raport("Ti-a scris " + u.getFirstName() + " " + u.getLastName(),
                        resultSet.getDate("timestamp").toLocalDate()));
            }
            return rapoarte;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
