package helper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertyFiller {
    public boolean provjeriProperty(Properties properties){

        ArgumentChecker checker = new ArgumentChecker();
        String[] dodatneKomande = {"--po","--pm","--pu","--pmu", "--gps", "--isporuka"};
        for(String key : properties.stringPropertyNames()){
            if(!checker.provjeriArgument(key)){
                boolean postoji = false;
                //Provjera dodatnih komandi
                for(String p : dodatneKomande) {
                    if (p.compareTo(key) == 0) {
                        postoji = true;
                        break;
                    }
                }
                if(!postoji){
                    System.out.println("Nisu svi uneseni argumenti");
                    return false;
                }
            }
        }
        return true;
    }


    public void ucitajPodatke(String nazivDatoteke, Properties properties) throws IOException {

        var putanja = Path.of(nazivDatoteke);
        if (Files.exists(putanja) && (Files.isDirectory(putanja) || !Files.isWritable(putanja))) {
            throw new IOException(
                    "Datoteka: '" + nazivDatoteke + "' nije datoteka ili nije moguće upisati u nju");
        }

        var citac = Files.newBufferedReader(putanja, Charset.forName("UTF-8"));
        while (true) {
            var redak = citac.readLine();
            if (redak == null)
                break;
            var odsjek = redak.split("=");
            if (odsjek.length != 2) {
                System.out.println("Neispravni podatak");
            } else {
                if(odsjek[0].startsWith("--")){
                    properties.setProperty(odsjek[0],odsjek[1]);
                }
                else{
                    String kljuc = "--"+odsjek[0];
                    properties.setProperty(kljuc,odsjek[1]);
                }
            }
        }
    }
}
