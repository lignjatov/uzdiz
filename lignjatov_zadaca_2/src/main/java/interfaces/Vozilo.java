package interfaces;

import java.util.List;
import entity.Paket;

public interface Vozilo {

  public void utovari(Paket paket);

  public void dostavi();

  public void naplati(Paket paket);

  public int vratiRedoslijed();

  public boolean provjeriUkrcavanje(Paket paket);

  public boolean popunjenoPola();

  public List<Paket> dohvatiPakete();

  public String vratiIme();
}
