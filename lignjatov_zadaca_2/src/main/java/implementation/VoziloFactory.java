package implementation;

import entity.VrstaVozila;
import interfaces.Vozilo;

public class VoziloFactory {

  public Vozilo napraviAuto(VrstaVozila vrsta) {
    if (VrstaVozila.Automobil == vrsta) {
      return new Automobil();
    }
    if (VrstaVozila.Bicikl == vrsta) {

    }
    if (VrstaVozila.Skuter == vrsta) {

    }
    return null;
  }
}
