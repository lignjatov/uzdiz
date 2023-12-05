package singleton;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class VirtualnoVrijeme {
  private static volatile VirtualnoVrijeme instance;

  private static volatile Timestamp trenutnoVrijeme;

  private static volatile LocalTime krajnjeVrijeme;

  private static volatile int korekcija;

  static {
    instance = new VirtualnoVrijeme();
  }

  private VirtualnoVrijeme() {}



  public static VirtualnoVrijeme getInstance() {
    return instance;
  }

  public void postaviKorekciju(int skalar) {
    korekcija = skalar;
  }

  public void postaviVrijeme(String pocetnoVrijeme) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(pocetnoVrijeme));
    trenutnoVrijeme = Timestamp.valueOf(localDateTime);
  }

  public void postaviKrajnjeVrijeme(String krajVrijeme){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    krajnjeVrijeme = LocalTime.parse(krajVrijeme, formatter);
  }

  public boolean prosloVrijemeRada(){
    LocalTime trenutniRadniSati = trenutnoVrijeme.toLocalDateTime().toLocalTime();
    if(trenutniRadniSati.isBefore(krajnjeVrijeme)){
      return false;
    }
    return true;
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
    String datumFormatiran = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(trenutnoVrijeme);
    return datumFormatiran;
  }
}
