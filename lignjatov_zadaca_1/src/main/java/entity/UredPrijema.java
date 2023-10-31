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
    zadnjeVrijeme = VirtualnoVrijeme.getInstance().vratiVrijeme();
  }

  public void radi() {
    provjeriNovePakete();

  };

  private void naplatiDostavu() {}

  private void provjeriNovePakete() {
    PaketiRepository sviPaketi = PaketiRepository.getInstance();
    var trenutnoVrijeme = VirtualnoVrijeme.getInstance().vratiVrijeme();
    if (zadnjeVrijeme.compareTo(trenutnoVrijeme) < 0) {
      zapisiPaket(trenutnoVrijeme);
    }
  }

  private void zapisiPaket(Timestamp trenutnoVrijeme) {
    List<Paket> paketi = PaketiRepository.getInstance().dajPodatke();
    for (Paket p : paketi) {
      if (p.getVrijemePrijema().compareTo(trenutnoVrijeme) < 0) {
        System.out.println(p.toString());
        listaPrimljenihPaketa.add(p);
      }
    }
  };
}
