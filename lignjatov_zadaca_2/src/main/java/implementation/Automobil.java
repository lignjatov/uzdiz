package implementation;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import entity.Paket;
import interfaces.Vozilo;
import singleton.DataRepository;
import singleton.VirtualnoVrijeme;

public class Automobil implements Vozilo {
  protected List<Paket> utovareniPaketi = new ArrayList<Paket>();
  public String opis;
  public Float tezina;
  public Float prostor;
  public String registracija;
  public Integer redoslijed;
  Float trenutnaTezina = (float) 0;
  Float trenutnaVelicina = (float) 0;

  @Override
  public void utovari(Paket paket) {
    Float paketMjere = paket.getDuzina() * paket.getSirina() * paket.getVisina();
    if (provjeriUkrcavanje(paket)) {
      utovareniPaketi.add(paket);
      trenutnaTezina += paket.getTezina();
      trenutnaVelicina += paketMjere;
    }
  }

  @Override
  public void dostavi() {
    Timestamp vrijeme = VirtualnoVrijeme.getInstance().vratiVrijeme();
    LocalDateTime localDateTime = LocalDateTime.from(vrijeme.toLocalDateTime());
    for (var e : utovareniPaketi) {
      localDateTime =
          localDateTime.plusMinutes(Long.parseLong(DataRepository.getInstance().vratiPostavke().getProperty("--vi")));
      vrijeme = Timestamp.valueOf(localDateTime);
      e.setVrijemePreuzimanja(vrijeme);
    }
  }

  @Override
  public void naplati(Paket paket) {
    trenutnaTezina -= paket.getTezina();
    trenutnaVelicina -= paket.getDuzina() * paket.getSirina() * paket.getVisina();
  }

  @Override
  public String toString() {

    return "opis: " + this.opis + " tezina: " + this.tezina + " prostor: " + this.prostor;
  }

  @Override
  public int vratiRedoslijed() {
    return redoslijed;
  }

  @Override
  public boolean provjeriUkrcavanje(Paket paket) {
    Float paketMjere = paket.getDuzina() * paket.getSirina() * paket.getVisina();
    if (paket.getTezina() + trenutnaTezina <= tezina && trenutnaVelicina + paketMjere <= prostor) {
      return true;
    }
    return false;
  }

  @Override
  public boolean popunjenoPola() {
    if (trenutnaTezina > tezina / 2 || trenutnaVelicina > prostor / 2) {
      return true;
    }
    return false;
  }

  @Override
  public List<Paket> dohvatiPakete() {
    return utovareniPaketi;
  }

  @Override
  public String vratiIme() {
    return registracija;
  }



}
