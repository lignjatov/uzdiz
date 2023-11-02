package helper;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import entity.VrstaPaketa;
import singleton.VrsteRepository;

public class FileDataChecker {
  public static int brojGresaka = 0;
  NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);

  public String provjeriVozilo(String[] linija) {
    boolean greska = false;
    String razlozi = "";
    for (int i = 0; i < linija.length; i++) {
      if (linija[i].isBlank()) {
        brojGresaka++;
        greska = true;
        razlozi += "Vrsta:Unesen prazni element;";
      }
    }
    if (linija.length != 5) {
      brojGresaka++;
      greska = true;
      razlozi += "Vozilo:Nisu uneseni svi parametri;";
    }
    try {
      if (format.parse(linija[2]).floatValue() == 0 || format.parse(linija[3]).floatValue() == 0) {
        brojGresaka++;
        greska = true;
        razlozi += "Vozilo:vrijednosti za prostor i/ili težinu ne mogu biti 0;";
      }
    } catch (ParseException e) {
      brojGresaka++;
      greska = true;
      razlozi += "Vozilo:Vrijednosti za prostor i/ili težina nisu dobro uneseni;";
    }
    return razlozi;
  };

  public String provjeriVrstu(String[] linija) {
    boolean greska = false;
    String razlozi = "";
    for (int i = 0; i < linija.length; i++) {
      if (linija[i].isBlank()) {
        brojGresaka++;
        greska = true;
        razlozi += "Vrsta:Unesen prazni element;";
      }
    }
    if (linija.length != 10) {
      brojGresaka++;
      greska = true;
      razlozi += "Vrsta:Nisu unesene sve vrijednosti;";
    }
    return razlozi;
  }

  public String provjeriPaket(String[] linija) {
    NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
    boolean greska = false;
    String razlozi = "";
    for (int i = 0; i < linija.length; i++) {
      if (linija[i].isBlank()) {
        brojGresaka++;
        greska = true;
        razlozi += "Vrsta:Unesen prazni element;";
      }
    }
    if (linija.length != 11) {
      brojGresaka++;
      greska = true;
      razlozi += "Paket:Nisu unesene sve vrijednosti;";
    }
    List<VrstaPaketa> vrste = VrsteRepository.getInstance().dajPodatke();

    List<String> oznake = new ArrayList<String>();
    for (VrstaPaketa e : vrste) {
      oznake.add(e.oznaka);
    }
    if (!oznake.contains(linija[4])) {
      brojGresaka++;
      greska = true;
      razlozi += "Paket:Vrsta paketa nepoznata;";
    }

    if (linija[4].compareTo("X") == 0) {
      if (linija[5].compareTo("0,0") == 0 || linija[6].compareTo("0,0") == 0
          || linija[7].compareTo("0,0") == 0 || linija[8].compareTo("0,0") == 0) {
        brojGresaka++;
        greska = true;
        razlozi += "Paket:Visina, Širina, Dužina i Težina ne mogu biti 0;";
      }
    } else {
      if (linija[5].compareTo("0,0") != 0 || linija[6].compareTo("0,0") != 0
          || linija[7].compareTo("0,0") != 0) {
        brojGresaka++;
        greska = true;
        razlozi += "Paket:Visina, Širina, Dužina moraju biti 0,0 ako su tipizirani paketi;";
      }
      try {
        if (format.parse(linija[10]).floatValue() != 0 && !linija[9].contains("P")) {
          brojGresaka++;
          greska = true;
          razlozi += "Paket:Tipizirani paketi nemaju iznos unesen;";
        }
      } catch (ParseException e2) {
        e2.printStackTrace();
      }


      Float maksTezina = (float) 0.0;
      for (var e : vrste) {
        if (e.oznaka.compareTo(linija[4]) == 0) {
          maksTezina = e.maksimalnaTezina;
          break;
        }
      }
      try {
        if (maksTezina < format.parse(linija[8]).floatValue()) {
          brojGresaka++;
          greska = true;
          razlozi += "Paket:Težina je veća nego je dopuštena u vrsti;";
        }
      } catch (ParseException e1) {
        e1.printStackTrace();
      }
    }


    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
      LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(linija[1]));
    } catch (Exception e) {
      brojGresaka++;
      greska = true;
      razlozi += "Datum u krivom obliku;";
    }
    return razlozi;
  };



  ;
}
