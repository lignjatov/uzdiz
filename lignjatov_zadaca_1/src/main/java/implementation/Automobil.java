package implementation;

import java.util.ArrayList;
import java.util.List;
import entity.Paket;
import interfaces.Vozilo;

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
  public boolean utovari(Paket paket) {
    Float paketMjere = paket.getDuzina() * paket.getSirina() * paket.getVisina();
    if (paket.getTezina() + trenutnaTezina <= tezina && trenutnaVelicina + paketMjere <= prostor) {
      utovareniPaketi.add(paket);
      trenutnaTezina += paket.getTezina();
      trenutnaVelicina += paketMjere;
      return true;
    }
    return false;
  }

  @Override
  public void dostavi() {}

  @Override
  public void naplati(Paket paket) {
    System.out.println("Paket " + paket.getOznaka() + " se naplaćuje: " + paket.getIznosPouzeca());
  }

  @Override
  public String toString() {

    return "opis: " + this.opis + " tezina: " + this.tezina + " prostor: " + this.prostor;
  }



}
