package memento;

import entity.Paket;
import entity.UredDostave;
import implementation.Vozilo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Memento {
    private String nazivStanja;

    private List<Vozilo> vozila = new ArrayList<>();
    private List<Paket> paketi = new ArrayList<>();
    //private final UredDostave spremljenoStanjeUreda;


    private Timestamp virtualnoVrijeme;

    public Memento(String nazivStanja, List<Vozilo> spremljenaVozila, List<Paket> spremljeniPaketi, Timestamp virtualnoVrijeme){
        this.nazivStanja = nazivStanja;
        this.virtualnoVrijeme = virtualnoVrijeme;
        this.vozila = spremljenaVozila;
        this.paketi = spremljeniPaketi;
    }


    public String getNazivStanja() {
        return nazivStanja;
    }
/*
    public UredDostave vratiSliku(){
        return spremljenoStanjeUreda;
    }
*/
    public List<Vozilo> vratiVozilo(){
        List<Vozilo> lista;

        return this.vozila;}
    public List<Paket> vratiPaket(){return this.paketi;}
    public Timestamp vratiVrijeme(){
        return virtualnoVrijeme;
    }
}
