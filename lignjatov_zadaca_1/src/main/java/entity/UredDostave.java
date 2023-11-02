package entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import implementation.VoziloFactory;
import interfaces.Vozilo;
import singleton.Postavke;
import singleton.VozilaRepository;

public class UredDostave {
  List<Vozilo> vozniPark = new ArrayList<Vozilo>();
  List<Dostava> dostave = new ArrayList<Dostava>();

  public UredDostave() {
    this.incijalizirajVozila();
  }

  public void incijalizirajVozila() {
    VoziloFactory a = new VoziloFactory();
    Vozilo automobil = a.napraviAuto(VrstaVozila.Automobil);
    for (Vozilo b : VozilaRepository.getInstance().dajPodatke()) {
      automobil = b;
      vozniPark.add(automobil);
    }
  }

  public void radi() {
    for (int i = 1; i <= vozniPark.size(); i++) {

    }
  }

  public void dohvatiPaketeZaDostavu(List<Paket> paketi) {
    for (Paket e : paketi) {
      sloziDostavu(e);
    }
  }

  private void sloziDostavu(Paket e) {
    Dostava a = new Dostava();
    a.paket = e;
    LocalDateTime localDateTime = LocalDateTime.from(e.vrijemePrijema.toLocalDateTime());
    localDateTime =
        localDateTime.plusMinutes(Long.parseLong(Postavke.getInstance().dajPostavku("--vi")));
    a.vrijemeDostave = Timestamp.valueOf(localDateTime);
    a.status = false;
    dostave.add(a);
  }
}
