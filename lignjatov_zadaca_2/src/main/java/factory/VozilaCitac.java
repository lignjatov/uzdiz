package factory;

import entity.Paket;
import helper.FileDataChecker;
import implementation.Automobil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VozilaCitac implements Citac<Automobil>{

    public List<Automobil> ucitajPodatke(String nazivDatoteke) {
        List<Automobil> listaAutomobila = new ArrayList<Automobil>();
        var putanja = Path.of(nazivDatoteke);
        if (Files.exists(putanja) && (Files.isDirectory(putanja) || !Files.isWritable(putanja))) {
            System.out.println("Datoteka: '" + nazivDatoteke + "' nije datoteka ili nije moguće upisati u nju");
            return null;
        }
        int i = 0;
        try{
            var citac = Files.newBufferedReader(putanja, Charset.forName("UTF-8"));
            while (true) {
                var redak = citac.readLine();
                if (redak == null)
                    break;
                var odsjek = redak.split(";");
                if (odsjek.length != 8) {
                    System.out.println("Vozilo: Nedovoljan broj atributa");
                    System.out.println(odsjek.length);
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

                    listaAutomobila.add(dodajVozilo(odsjek));
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return listaAutomobila;
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


