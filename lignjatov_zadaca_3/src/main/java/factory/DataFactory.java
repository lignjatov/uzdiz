package factory;

import Composite.Ulica;

public class DataFactory {
    public <T> Citac<T> vratiCitac(VrsteCitaca vrstaCitaca){
        Citac citac = null;
        if(vrstaCitaca.equals(VrsteCitaca.Paketi)){
            citac = new PaketiCitac();
        }
        if(vrstaCitaca.equals(VrsteCitaca.VrstePaketa)){
            citac = new VrsteCitac();
        }
        if(vrstaCitaca.equals(VrsteCitaca.Vozila)){
            citac = new VozilaCitac();
        }

        if(vrstaCitaca.equals(VrsteCitaca.Ulica)){
            citac = new UliceCitac();
        }

        if(vrstaCitaca.equals(VrsteCitaca.Mjesto)){
            citac = new MjestaCitac();
        }

        if(vrstaCitaca.equals(VrsteCitaca.Osoba)) {
            citac = new OsobeCitac();
        }
        return citac;
    }
}
