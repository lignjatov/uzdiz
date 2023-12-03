package singleton;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import entity.Paket;
import helper.FileDataChecker;
import implementation.PaketBuilder;

public class PaketiRepository {
  private static volatile PaketiRepository instance;
  private static List<Paket> listaPaketa = new ArrayList<Paket>();


  static {
    instance = new PaketiRepository();
  }

  private PaketiRepository() {}

  public static PaketiRepository getInstance() {
    return instance;
  }

  public List<Paket> dajPodatke() {
    return listaPaketa;
  }


  public void ucitajPodatke(String nazivDatoteke) throws IOException {

    var putanja = Path.of(nazivDatoteke);
    if (Files.exists(putanja) && (Files.isDirectory(putanja) || !Files.isWritable(putanja))) {
      throw new IOException(
          "Datoteka: '" + nazivDatoteke + "' nije datoteka ili nije moguće upisati u nju");
    }
    int i = 0;

    var citac = Files.newBufferedReader(putanja, Charset.forName("UTF-8"));
    while (true) {
      var redak = citac.readLine();
      if (redak == null)
        break;
      var odsjek = redak.split(";");
      if (odsjek.length != 11) {
        System.out.println("Nedovoljan broj atributa");
      } else {
        if (i == 0) {
          i++;
          continue;
        }

        FileDataChecker checker = new FileDataChecker();
        String greske = checker.provjeriPaket(odsjek);
        if (!greske.isEmpty()) {
          System.out.println(FileDataChecker.brojGresaka + ". " + greske);
          System.out.println(redak);
          continue;
        }

        listaPaketa.add(dodajPaket(odsjek));
      }
    }
  }

  private Paket dodajPaket(String[] odsjek) {
    NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
    PaketBuilder builder = new PaketBuilder();
    Paket a = null;
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
      LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(odsjek[1]));
      Timestamp paketVrijeme = Timestamp.valueOf(localDateTime);


      a = builder.setOznaka(odsjek[0]).setVrijemePrijema(paketVrijeme).setPosiljatelj(odsjek[2])
          .setPrimatelj(odsjek[3]).setVrstaPaketa(odsjek[4])
          .setVisina(format.parse(odsjek[5]).floatValue())
          .setSirina(format.parse(odsjek[6]).floatValue())
          .setDuzina(format.parse(odsjek[7]).floatValue())
          .setTezina(format.parse(odsjek[8]).floatValue()).setUslugaDostave(odsjek[9])
          .setIznosPouzeca(format.parse(odsjek[10]).floatValue()).build();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return a;
  }
}
