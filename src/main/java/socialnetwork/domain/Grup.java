package socialnetwork.domain;

public class Grup extends Entity<Long> {
    private Long id;
    private String nume;
    private Long idAdmin;

    public Grup(Long id, String nume, Long idAdmin) {
        this.id = id;
        this.nume = nume;
        this.idAdmin = idAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    @Override
    public String toString() {
        return "Grup{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", idAdmin=" + idAdmin +
                '}';
    }
}
