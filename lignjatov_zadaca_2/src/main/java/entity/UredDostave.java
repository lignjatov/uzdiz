package entity;

import java.util.ArrayList;
import java.util.List;
import implementation.VoziloFactory;
import interfaces.Vozilo;
import singleton.VozilaRepository;

public class UredDostave {
  List<Vozilo> vozniPark = new ArrayList<Vozilo>();
  List<Paket> dostave = new ArrayList<Paket>();

  public UredDostave() {
    this.incijalizirajVozila();
  }

  public void incijalizirajVozila() {
    VoziloFactory a = new VoziloFactory();
    int redoslijed = 1;
    Vozilo automobil = a.napraviAuto(VrstaVozila.Automobil);
    for (Vozilo b : VozilaRepository.getInstance().dajPodatke()) {
      for (Vozilo e : VozilaRepository.getInstance().dajPodatke()) {
        if (e.vratiRedoslijed() == redoslijed) {
          vozniPark.add(e);
          redoslijed++;
        }
      }
    }
  }

  public void ukrcavaj() {
    // prvo se ubacuju sa H
    List<Paket> tempLista = dostave;
    for (Paket e : tempLista) {
      if (e.getOznaka().compareTo("H") == 0) {
        for (Vozilo v : vozniPark) {
          if (v.provjeriUkrcavanje(e)) {
            v.utovari(e);
            break;
          } else {
            System.out.println("Vozilo" + v.vratiIme() + " se pokrenulo");
            v.dostavi();
          }
        }
      }
    }

    for (Paket e : tempLista) {
      if (e.getOznaka().compareTo("H") != 0) {
        for (Vozilo v : vozniPark) {
          if (v.provjeriUkrcavanje(e)) {
            v.utovari(e);
            break;
          } else {
            if (v.dohvatiPakete().size() > 0) {
              v.dostavi();
            }
          }
        }
      }
    }
    dostave.clear();
  }

  public List<Vozilo> dohvatiSveAute() {
    return vozniPark;
  }

  public void dohvatiPaketeZaDostavu(List<Paket> paketi) {
    dostave = paketi;
  }
}
