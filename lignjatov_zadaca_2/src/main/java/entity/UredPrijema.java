package entity;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import observer.Subscriber;
import singleton.PaketiRepository;
import singleton.VirtualnoVrijeme;
import singleton.VrsteRepository;

public class UredPrijema {
  List<Paket> listaPrimljenihPaketa = new ArrayList<Paket>();
  List<Subscriber> listaOsobaSubscriber = new ArrayList<Subscriber>();

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
    List<Paket> paketi = PaketiRepository.getInstance().dajPodatke();
    for (Paket p : paketi) {
      if (p.getVrijemePrijema().compareTo(trenutnoVrijeme) < 0
          && p.getVrijemePrijema().compareTo(zadnjeVrijeme) > 0) {
        listaPrimljenihPaketa.add(p);
        naplatiIznosDostave(p);
        zadnjeVrijeme = p.getVrijemePrijema();
      }
    }
  }

  private void naplatiIznosDostave(Paket paket) {
    List<VrstaPaketa> vrste = VrsteRepository.getInstance().dajPodatke();
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
    String poruka = "Prijem paketa " + paket.getOznaka() + " se naplaćuje: " + paket.getIznosPouzeca() + "kn";
    System.out.println(poruka);
    posaljiPorukuPretplatnicima(poruka);
  }

  public void dodajPretplatnika(Subscriber osoba){
    listaOsobaSubscriber.add(osoba);
  }

  public void posaljiPorukuPretplatnicima(String poruka){
    for (var osoba : listaOsobaSubscriber){
      osoba.notificiraj(poruka);
    }
  }
}
