package singleton;

public class VozilaRepository {
  private static volatile VozilaRepository instance;

  static {
    instance = new VozilaRepository();
  }

  private VozilaRepository() {}

  public static VozilaRepository getInstance() {
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
