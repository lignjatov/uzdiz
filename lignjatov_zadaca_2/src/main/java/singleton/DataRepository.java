package singleton;

import Composite.Mjesto;
import Composite.Spremiste;
import Composite.Ulica;
import entity.Osoba;
import entity.Paket;
import entity.VrstaPaketa;
import interfaces.Vozilo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataRepository {
    private static volatile DataRepository instance;
    private static List<Paket> listaPaketa = new ArrayList<Paket>();
    private static List<Vozilo> listaVozila = new ArrayList<Vozilo>();
    private static List<VrstaPaketa> vrstaPaketa = new ArrayList<VrstaPaketa>();
    private static List<Osoba> listaOsoba = new ArrayList<Osoba>();
    private static List<Mjesto> listaMjesta = new ArrayList<Mjesto>();
    private static List<Ulica> listaUlica = new ArrayList<Ulica>();
    private static Properties postavke = new Properties();
    private static Spremiste spremiste = new Spremiste();

    static {
        instance = new DataRepository();
    }

    public static DataRepository getInstance(){
        return instance;
    }

    public void postaviListuVozila(List<Vozilo> svaVozila){
        listaVozila = svaVozila;
    }

    public void postaviListuPaketa(List<Paket> sviPaketi){
        listaPaketa=sviPaketi;
    }

    public void postaviListuVrstaPaketa(List<VrstaPaketa> sveVrstePaketa){
        vrstaPaketa=sveVrstePaketa;
    }

    public void postaviPostavke(Properties properties) {
        postavke = properties;
    }

    public void postaviSpremiste(Spremiste compositeSpremiste){
        spremiste = compositeSpremiste;
    }

    public void postaviListuOsoba(List<Osoba> sveOsobe){
        listaOsoba=sveOsobe;
    }

    public void postaviListuMjesta(List<Mjesto> mjesto){
        listaMjesta = mjesto;
    }
    public void postaviListuUlica(List<Ulica> ulica){
        listaUlica = ulica;
    }

    public  List<Paket> vratiListaPaketa() {
        return listaPaketa;
    }

    public  List<Vozilo> vratiListaVozila() {
        return listaVozila;
    }

    public  List<VrstaPaketa> vratiVrstaPaketa() {
        return vrstaPaketa;
    }

    public  List<Osoba> vratiListaOsoba() {
        return listaOsoba;
    }

    public  List<Mjesto> vratiListaMjesta() {
        return listaMjesta;
    }

    public  List<Ulica> vratiListaUlica() {
        return listaUlica;
    }

    public  Properties vratiPostavke() {
        return postavke;
    }

    public Spremiste vratiSpremiste() {
        return spremiste;
    }
}
