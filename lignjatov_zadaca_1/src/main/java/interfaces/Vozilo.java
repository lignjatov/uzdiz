package interfaces;

import entity.Paket;

public interface Vozilo {

  public boolean utovari(Paket paket);

  public void dostavi();

  public void naplati(Paket paket);
}
