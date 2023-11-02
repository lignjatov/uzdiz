package entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import singleton.PaketiRepository;
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
    for (Paket e : listaPrimljenihPaketa) {
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
        naplatiDostavu(p);
        zadnjeVrijeme = p.getVrijemePrijema();
      }
    }
  }

  private void naplatiDostavu(Paket paket) {
    System.out.println(
        "Dostava " + paket.getOznaka() + " se naplaćuje: " + paket.getIznosPouzeca() + "kn");
  }


}
