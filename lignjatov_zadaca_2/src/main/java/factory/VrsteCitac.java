package factory;

import entity.Paket;
import entity.VrstaPaketa;
import helper.FileDataChecker;
import implementation.PaketBuilder;

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

public class VrsteCitac implements Citac<VrstaPaketa>{
    @Override
    public List<VrstaPaketa> ucitajPodatke(String nazivDatoteke) {
        List<VrstaPaketa> listaVrsti = new ArrayList<VrstaPaketa>();
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
                if (odsjek.length != 10) {
                    System.out.println("Nedovoljan broj atributa");
                } else {
                    if (i == 0) {
                        i++;
                        continue;
                    }
                    FileDataChecker checker = new FileDataChecker();
                    String greske = checker.provjeriVrstu(odsjek);
                    if (!greske.isEmpty()) {
                        System.out.println(FileDataChecker.brojGresaka + ". " + greske);
                        System.out.println(redak);
                        continue;
                    }

                    listaVrsti.add(dodajPaket(odsjek));
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return listaVrsti;
    }

    private VrstaPaketa dodajPaket(String[] odsjek) {
        NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
        VrstaPaketa a = new VrstaPaketa();
        try {
            a.oznaka = odsjek[0];
            a.opis = odsjek[1];
            a.visina = format.parse(odsjek[2]).floatValue();
            a.sirina = format.parse(odsjek[3]).floatValue();
            a.duzina = format.parse(odsjek[4]).floatValue();
            a.maksimalnaTezina = format.parse(odsjek[5]).floatValue();
            a.cijena = format.parse(odsjek[6]).floatValue();
            a.cijenaHitno = format.parse(odsjek[7]).floatValue();
            a.cijenaP = format.parse(odsjek[8]).floatValue();
            a.cijenaT = format.parse(odsjek[9]).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return a;
    }
}
