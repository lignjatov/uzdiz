package memento;

import entity.Paket;
import entity.UredDostave;
import implementation.Vozilo;
import singleton.VirtualnoVrijeme;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Memento {
    private String nazivStanja;

    private List<Vozilo> vozila = new ArrayList<>();
    private List<Paket> paketi = new ArrayList<>();
    //private final UredDostave spremljenoStanjeUreda;


    private Timestamp virtualnoVrijeme;
    private Timestamp zadnjeVrijeme;
    private LocalTime vrijemeRadaSustava;

    public Memento(String nazivStanja, List<Vozilo> spremljenaVozila, List<Paket> spremljeniPaketi, Timestamp virtualnoVrijeme){
        this.nazivStanja = nazivStanja;
        this.virtualnoVrijeme = virtualnoVrijeme;
        this.vozila = spremljenaVozila;
        this.paketi = spremljeniPaketi;
        this.vrijemeRadaSustava = VirtualnoVrijeme.getInstance().vratiPomicnoVrijeme();
        if(paketi.isEmpty()){
            this.zadnjeVrijeme=new Timestamp(0);
        }
        else {
            this.zadnjeVrijeme = paketi.get(paketi.size()-1).getVrijemePrijema();
        }

    }


    public String getNazivStanja() {
        return nazivStanja;
    }
    public List<Vozilo> vratiVozilo(){
        List<Vozilo> lista = new ArrayList<>();
        for(var a : vozila){
            lista.add(a.kloniraj());
        }
        return lista;
    }
    public List<Paket> vratiPaket(){
        List<Paket> lista = new ArrayList<>();
        for(var a : paketi){
            lista.add(a.kloniraj());
        }
        return lista;}
    public Timestamp vratiVrijeme(){
        return virtualnoVrijeme;
    }
    public Timestamp vratiZadnjeVrijeme(){
        return this.zadnjeVrijeme;
    }

    public LocalTime vratiRadnoVrijeme(){
        return this.vrijemeRadaSustava;
    }
}
