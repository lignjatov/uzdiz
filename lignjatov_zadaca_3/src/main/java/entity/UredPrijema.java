package entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import memento.Memento;
import singleton.DataRepository;
import singleton.VirtualnoVrijeme;


public class UredPrijema {
  List<Paket> listaPrimljenihPaketa = new ArrayList<Paket>();

  Timestamp zadnjeVrijeme;

  public UredPrijema() {
    zadnjeVrijeme = new Timestamp(0);
  }

  public void radi() {
    provjeriNovePakete();
  };

  public List<Paket> prenesiListuZaDostavu() {
    List<Paket> listaZaDostavu = new ArrayList<Paket>();
    for (var e : listaPrimljenihPaketa) {
      listaZaDostavu.add(e);
    }
    listaPrimljenihPaketa.clear();
    return listaZaDostavu;
  }



  private boolean provjeriNovePakete() {
    var trenutnoVrijeme = VirtualnoVrijeme.getInstance().vratiVrijeme();
    if (zadnjeVrijeme.compareTo(trenutnoVrijeme) < 0) {
      zapisiPaket(trenutnoVrijeme);
      return true;
    }
    return false;
  }

  private void zapisiPaket(Timestamp trenutnoVrijeme) {
    List<Paket> paketi = DataRepository.getInstance().vratiListaPaketa();
    for (Paket p : paketi) {
      
      if (p.getVrijemePrijema().compareTo(trenutnoVrijeme) < 0
          && p.getVrijemePrijema().compareTo(zadnjeVrijeme) > 0) {
        listaPrimljenihPaketa.add(p);
        naplatiIznosDostave(p);
        postaviZadnjeVrijeme(p.getVrijemePrijema());
      }
    }
  }

  private void naplatiIznosDostave(Paket paket) {
    List<VrstaPaketa> vrste = DataRepository.getInstance().vratiVrstaPaketa();
    VrstaPaketa vrsta = null;
    for (var e : vrste) {
      if (paket.vrstaPaketa.compareTo(e.oznaka) == 0) {
        vrsta = e;
        break;
      }
    }

    if (paket.getUslugaDostave().compareTo("P") == 0) {
      return;
    }

    if (paket.getVrstaPaketa().compareTo("X") == 0) {
      Float volumen = paket.duzina * paket.sirina * paket.visina;
      Float vrijednostP = vrsta.cijenaP * volumen;
      Float vrijednostT = vrsta.cijenaT * paket.tezina;
      paket.setIznosPouzeca(vrijednostT + vrijednostP);
    } else {
      if (paket.uslugaDostave.compareTo("H") == 0) {
        paket.setIznosPouzeca(vrsta.cijenaHitno);
      } else {
        paket.setIznosPouzeca(vrsta.cijena);
      }
    }

    dodajPretplatnike(paket);
    String poruka = "Prijem paketa " + paket.getOznaka() + " se naplaćuje: " + paket.getIznosPouzeca() + "kn";
    paket.posaljiPorukuPretplatnicima(poruka);
  }

  private void dodajPretplatnike(Paket posiljatelj) {
    Osoba osobaPosiljatelj = vratiOsobu(posiljatelj.getPosiljatelj());
    Osoba osobaPrimatelj = vratiOsobu(posiljatelj.getPrimatelj());
    if(osobaPosiljatelj!=null){
      posiljatelj.dodajPretplatnika(osobaPosiljatelj);
    }
    if(osobaPrimatelj!=null){
      posiljatelj.dodajPretplatnika(osobaPrimatelj);
    }
  }

  private Osoba vratiOsobu(String posiljatelj) {
    for(var osoba : DataRepository.getInstance().vratiListaOsoba()){
      if(posiljatelj.compareTo(osoba.vratiIme())==0){
        return osoba;
      }
    }
    return null;
  }


  public void postaviZadnjeVrijeme(Timestamp vrijeme){
    this.zadnjeVrijeme = vrijeme;
  }

  public void vratiSliku(Memento naziv){
    this.zadnjeVrijeme=naziv.vratiZadnjeVrijeme();
  }
}
