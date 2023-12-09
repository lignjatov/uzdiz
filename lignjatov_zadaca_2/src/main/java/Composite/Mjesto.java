package Composite;

import java.util.ArrayList;
import java.util.List;

public class Mjesto implements Kutija {
    int id;
    String naziv;
    List<Kutija> ulice = new ArrayList<Kutija>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public List<Kutija> getUlice() {
        return ulice;
    }

    public void setUlice(List<Kutija> ulice) {
        this.ulice = ulice;
    }

    public void dodajUlicu(Ulica ulica){
       ulice.add(ulica);
    }

    @Override
    public void ispisiText() {
        System.out.println("    Mjesto je " + naziv);
        for(Kutija ulica : ulice){
            ulica.ispisiText();
        }
    }
}
