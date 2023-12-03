package singleton;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import helper.FileDataChecker;
import implementation.Automobil;
import interfaces.Vozilo;

public class VozilaRepository {
  private static volatile VozilaRepository instance;
  private static List<Vozilo> listaVozila = new ArrayList<Vozilo>();

  static {
    instance = new VozilaRepository();
  }

  private VozilaRepository() {}

  public static VozilaRepository getInstance() {
    return instance;
  }

  public List<Vozilo> dajPodatke() {
    return listaVozila;
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
      if (odsjek.length != 5) {
        System.out.println("Vozila: Nedovoljan broj atributa");
      } else {
        if (i == 0) {
          i++;
          continue;
        }
        FileDataChecker checker = new FileDataChecker();
        String greske = checker.provjeriVozilo(odsjek);
        if (!greske.isEmpty()) {
          System.out.println(FileDataChecker.brojGresaka + ". " + greske);
          System.out.println(redak);
          continue;
        }
        listaVozila.add(dodajVozilo(odsjek));
      }
    }
  }

  private Automobil dodajVozilo(String[] odsjek) {
    NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
    var a = new Automobil();

    try {
      a.registracija = odsjek[0];
      a.opis = odsjek[1];
      a.tezina = format.parse(odsjek[2]).floatValue();
      a.prostor = format.parse(odsjek[3]).floatValue();
      a.redoslijed = Integer.parseInt(odsjek[4]);

    } catch (ParseException e) {
      e.printStackTrace();
    }
    return a;

  }

}
