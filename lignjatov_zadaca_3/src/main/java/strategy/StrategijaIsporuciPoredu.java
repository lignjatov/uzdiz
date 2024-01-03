package strategy;

import Composite.Ulica;
import entity.Dostava;
import entity.GPS;
import entity.Osoba;
import entity.Segment;
import implementation.Vozilo;
import singleton.DataRepository;
import singleton.VirtualnoVrijeme;
import java.sql.Timestamp;

public class StrategijaIsporuciPoredu implements StrategijeIsporuke{
    @Override
    public void pripremiIsporuku(Dostava dostava) {
        String gpsLokacijePocetka = DataRepository.getInstance().vratiPostavke().getProperty("--gps");
        String[] odvojeneLokacijePocetka = gpsLokacijePocetka.split(",");
        GPS gpsPocetni = new GPS(Float.parseFloat(odvojeneLokacijePocetka[0].trim()),
                Float.parseFloat(odvojeneLokacijePocetka[1].trim()));
        int i=0;
        Segment prijasnjiSegment = null;

        for(var segment : dostava.vratiSegmentiVoznje()){
            if(i==0){
                segment.postaviOdGPS(gpsPocetni);
                i++;
            }
            else{
                segment.postaviOdGPS(prijasnjiSegment.vratiDoGPS());

            }
            segment.postaviDoGPS(vratiInterpolaciju(segment));
            segment.postaviUdaljenost((float)izracunajUdaljenostDvijeTocke(segment.vratiOdGPS(),segment.vratiDoGPS()));
            prijasnjiSegment=segment;
        }
    }


    private Ulica vratiUlicuPrimatelj(String imeOsobe){
        Osoba trazenaOsoba = null;
        for(var osoba : DataRepository.getInstance().vratiListaOsoba()){
            if(osoba.vratiIme().compareTo(imeOsobe)==0){
                trazenaOsoba=osoba;
                break;
            }
        }
        if(trazenaOsoba==null){
            return null;
        }
        for(var element : DataRepository.getInstance().vratiListaUlica()){
            if(element.vratiId() == trazenaOsoba.vratiUlica()){
                return element;
            }
        }
        return null;
    }

    private int kucniBrojOsobe(String imeOsobe){
        Osoba trazenaOsoba = null;
        for(var osoba : DataRepository.getInstance().vratiListaOsoba()){
            if(osoba.vratiIme().compareTo(imeOsobe)==0){
                trazenaOsoba=osoba;
                break;
            }
        }
        if(trazenaOsoba==null){
            return 0;
        }
        return trazenaOsoba.vratiKbr();
    }

    private double izracunajUdaljenostDvijeTocke(GPS koord1, GPS koord2){
        return Math.sqrt(Math.pow(koord2.latitude - koord1.latitude, 2)+Math.pow(koord2.longitude-koord1.longitude,2));
    }

    private GPS vratiInterpolaciju(Segment segment){
        Ulica ulica = vratiUlicuPrimatelj(segment.paketDostave.getPrimatelj());
        int kbr = kucniBrojOsobe(segment.paketDostave.getPrimatelj());

        Float duljinaUlice =(float)izracunajUdaljenostDvijeTocke
                (new GPS(ulica.vratiGps_lat_1(), ulica.vratiGps_lon_1()),
                        new GPS(ulica.vratiGps_lat_2(), ulica.vratiGps_lon_2()));
        //dijeljenje kućnog broja osobe i maksimalnog kućnog broja
        Float postotakUdaljenosti = (float)kbr/(float)ulica.vratiNajveciKucniBroj();
        if(postotakUdaljenosti>1){
            postotakUdaljenosti =(float) 1.0;
        }
        Float trazenaUdaljenostOdPocetka = duljinaUlice*postotakUdaljenosti;

        Float deltaX = ulica.vratiGps_lat_2() - ulica.vratiGps_lat_1();
        Float deltaY = ulica.vratiGps_lon_2() - ulica.vratiGps_lon_1();

        Float noviGPSX = ulica.vratiGps_lat_1() + (deltaX*trazenaUdaljenostOdPocetka);
        Float noviGPSY = ulica.vratiGps_lon_1() + (deltaY*trazenaUdaljenostOdPocetka);

        GPS doGPS = new GPS(noviGPSX,noviGPSY);
        return doGPS;
    }
}
