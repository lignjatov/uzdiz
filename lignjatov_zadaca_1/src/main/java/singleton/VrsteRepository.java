package singleton;

public class VrsteRepository {
  private static volatile VrsteRepository instance;


  static {
    instance = new VrsteRepository();
  }

  private VrsteRepository() {}

  public static VrsteRepository getInstance() {
    return instance;
  }

  // TODO: Postavi da se vrati paket
  public Object dajPodatke() {
    return null;
  }


  public void ucitajPodatke(String fileName) {
    // TODO: napiši tu nekakav kod za učivatanje podataka
    // Trebalo bude preko buildera?
  }

}
