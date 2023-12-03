package singleton;

import java.util.Properties;

public class Postavke {
  private static volatile Postavke instance;
  private Properties postavke;

  static {
    instance = new Postavke();

  }

  private Postavke() {
    postavke = new Properties();
  }



  public static Postavke getInstance() {
    return instance;
  }

  public String dajPostavku(String kljuc) {
    return postavke.getProperty(kljuc);
  }

  public boolean postaviPostavku(String kljuc, String vrijednost) {
    if (postavke.containsKey(kljuc)) {
      return false;
    } else {
      postavke.setProperty(kljuc, vrijednost);
      return true;
    }
  }

  public boolean azurirajPostavku(String kljuc, String vrijednost) {
    if (!postavke.containsKey(kljuc)) {
      return false;
    } else {
      postavke.setProperty(kljuc, vrijednost);
      return true;
    }
  }
}
