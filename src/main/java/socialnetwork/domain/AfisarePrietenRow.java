package socialnetwork.domain;

import javafx.scene.control.Button;

public class AfisarePrietenRow {
    private String nume;
    private Button bDel;

    public AfisarePrietenRow(String nume, Button bDel) {
        this.nume = nume;
        this.bDel = bDel;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Button getbDel() {
        return bDel;
    }

    public void setbDel(Button bDel) {
        this.bDel = bDel;
    }
}
