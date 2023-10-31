package singleton;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VirtualnoVrijeme {
  private static volatile VirtualnoVrijeme instance;

  private static volatile Timestamp trenutnoVrijeme;
  private static volatile int korekcija;

  static {
    instance = new VirtualnoVrijeme();
  }

  private VirtualnoVrijeme() {}



  public static VirtualnoVrijeme getInstance() {
    return instance;
  }

  public void postaviKorekciju(int korekcija) {
    this.korekcija = korekcija;
  }

  public void postaviVrijeme(String pocetnoVrijeme) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(pocetnoVrijeme));
    trenutnoVrijeme = Timestamp.valueOf(localDateTime);
  }

  public void pomakniVrijeme(int sekunde) {
    LocalDateTime localDateTime = LocalDateTime.from(trenutnoVrijeme.toLocalDateTime());
    int sekundeNove = sekunde * korekcija;
    localDateTime = localDateTime.plusSeconds(sekundeNove);
    trenutnoVrijeme = Timestamp.valueOf(localDateTime);
  }

  public Timestamp vratiVrijeme() {
    return trenutnoVrijeme;
  }

  public String vratiVrijemeString() {
    String datumFormatiran = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(trenutnoVrijeme);
    return datumFormatiran;
  }
}
