package factory;

import entity.Osoba;
import helper.FileDataChecker;
import implementation.Vozilo;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class OsobeCitac implements Citac<Osoba>{
    @Override
    public List<Osoba> ucitajPodatke(String nazivDatoteke) {
        List<Osoba> listaOsoba = new ArrayList<Osoba>();
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
                if (odsjek.length != 4) {
                    System.out.println("Osoba: Nedovoljan broj atributa");
                    System.out.println(odsjek.length);
                } else {
                    if (i == 0) {
                        i++;
                        continue;
                    }
                    /*
                    FileDataChecker checker = new FileDataChecker();
                    String greske = checker.provjeriVozilo(odsjek);
                    if (!greske.isEmpty()) {
                        System.out.println(FileDataChecker.brojGresaka + ". " + greske);
                        System.out.println(redak);
                        continue;
                    }
                    */

                    listaOsoba.add(dodajOsobu(odsjek));
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return listaOsoba;
    }

    private Osoba dodajOsobu(String[] odsjek) {
        Osoba novaOsoba = new Osoba();
        novaOsoba.postaviIme(odsjek[0]);
        novaOsoba.postaviUlica(Integer.parseInt(odsjek[2].trim()));
        novaOsoba.postaviKbr(Integer.parseInt(odsjek[3].trim()));
        return novaOsoba;
    }
}
