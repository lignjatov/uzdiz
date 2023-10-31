package interfaces;

import entity.Paket;

public interface Vozilo {
  public void utovari(Paket paket);

  public void dostavi();

  public boolean provjeriTezinu();

  public boolean provjeriProstor();
}
