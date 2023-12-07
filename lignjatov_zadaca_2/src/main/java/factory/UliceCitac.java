package factory;

import Composite.Mjesto;
import Composite.Ulica;
import helper.FileDataChecker;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UliceCitac implements Citac<Ulica>{
    @Override
    public List<Ulica> ucitajPodatke(String nazivDatoteke) {
        List<Ulica> listaUlica = new ArrayList<Ulica>();
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
                if (odsjek.length != 7) {
                    System.out.println("Ulica: Nedovoljan broj atributa");
                } else {
                    if (i == 0) {
                        i++;
                        continue;
                    }
                    //TODO: Napraviti provjere za ulice
                    /*FileDataChecker checker = new FileDataChecker();
                    String greske = checker.provjeriMjesto(odsjek);
                    if (!greske.isEmpty()) {
                        System.out.println(FileDataChecker.brojGresaka + ". " + greske);
                        System.out.println(redak);
                        continue;
                    }*/
                    listaUlica.add(dodajUlicu(odsjek));
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return listaUlica;
    }

    private Ulica dodajUlicu(String[] odsjek) {
        Ulica e = new Ulica();
        e.postaviId(Integer.parseInt(odsjek[0]));
        e.postaviNaziv(odsjek[1]);
        e.postaviGps_lat_1(Float.parseFloat(odsjek[2].trim()));
        e.postaviGps_lon_1(Float.parseFloat(odsjek[3].trim()));
        e.postaviGps_lat_2(Float.parseFloat(odsjek[4].trim()));
        e.postaviGps_lon_2(Float.parseFloat(odsjek[5].trim()));
        e.postaviNajveciKucniBroj(Integer.parseInt(odsjek[6]));
        return e;
    }
}
