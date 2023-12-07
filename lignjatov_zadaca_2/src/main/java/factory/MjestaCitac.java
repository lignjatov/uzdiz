package factory;

import Composite.Kutija;
import Composite.Mjesto;
import Composite.Ulica;
import entity.VrstaPaketa;
import helper.FileDataChecker;
import singleton.DataRepository;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MjestaCitac implements Citac<Mjesto>{
    @Override
    public List<Mjesto> ucitajPodatke(String nazivDatoteke) {

        List<Mjesto> listaMjesta = new ArrayList<Mjesto>();
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
                if (odsjek.length != 3) {
                    System.out.println("Nedovoljan broj atributa");
                } else {
                    if (i == 0) {
                        i++;
                        continue;
                    }
                    FileDataChecker checker = new FileDataChecker();
                    String greske = checker.provjeriMjesto(odsjek);
                    if (!greske.isEmpty()) {
                        System.out.println(FileDataChecker.brojGresaka + ". " + greske);
                        System.out.println(redak);
                        continue;
                    }
                    listaMjesta.add(dodajMjesto(odsjek));
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return listaMjesta;
    }

    private Mjesto dodajMjesto(String[] odsjek) {
        Mjesto e = new Mjesto();
        e.setId(Integer.parseInt(odsjek[0]));
        e.setNaziv(odsjek[1]);
        List<Kutija> ulice = new ArrayList<Kutija>();
        for(String element : odsjek[2].split(",")){
            ulice.add((Kutija)vratiUlicu(Integer.parseInt(element.trim())));
        }
        e.setUlice(ulice);
        return e;
    }

    public static Ulica vratiUlicu(int idUlice){
        for(var element : DataRepository.getInstance().vratiListaUlica()){
            if(element.vratiId()==idUlice){
                return element;
            }
        }
        return null;
    }
}
