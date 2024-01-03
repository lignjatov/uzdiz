package strategy;

import Composite.Ulica;
import entity.Dostava;
import entity.GPS;
import entity.Osoba;
import entity.Segment;
import implementation.Vozilo;
import singleton.DataRepository;

import java.util.ArrayList;
import java.util.List;

public class StrategijaIsporuciNajblizi implements StrategijeIsporuke{
    @Override
    public void pripremiIsporuku(Dostava dostava) {
        List<Segment> noviRedoslijed = new ArrayList<>();
        String gpsLokacijePocetka = DataRepository.getInstance().vratiPostavke().getProperty("--gps");
        String[] odvojeneLokacijePocetka = gpsLokacijePocetka.split(",");
        GPS gpsPocetni = new GPS(Float.parseFloat(odvojeneLokacijePocetka[0].trim()),
                Float.parseFloat(odvojeneLokacijePocetka[1].trim()));
        int i=0;
        Segment prijasnjiSegment = null;
        for(var segment : dostava.vratiSegmentiVoznje()){
            if(i==0){
                segment.postaviOdGPS(gpsPocetni);
                izracunajSvePuteve(gpsPocetni,dostava);
                Segment najkraciSegment = odaberiNajmanjiSegment(dostava);
                najkraciSegment.postaviOdGPS(gpsPocetni);
                najkraciSegment.odraden=true;
                noviRedoslijed.add(najkraciSegment);
                prijasnjiSegment=najkraciSegment;
                i++;
            }else{
                izracunajSvePuteve(prijasnjiSegment.vratiDoGPS(),dostava);
                Segment najkraciSegment = odaberiNajmanjiSegment(dostava);
                najkraciSegment.postaviOdGPS(prijasnjiSegment.vratiDoGPS());
                najkraciSegment.odraden=true;
                noviRedoslijed.add(najkraciSegment);
                prijasnjiSegment=najkraciSegment;
            }
        }
        for(Segment segmenti : noviRedoslijed){
            segmenti.odraden=false;
        }
        dostava.postaviSegmenteVoznje(noviRedoslijed);
    }

    private Segment odaberiNajmanjiSegment(Dostava dostava) {
        float najmanji=1000;
        Segment najmanjiSegment = null;
        for(var segmenti : dostava.vratiSegmentiVoznje()){
            if(!segmenti.odraden){
                if(segmenti.vratiUdaljenost()<najmanji){
                    najmanji=segmenti.vratiUdaljenost();
                    najmanjiSegment=segmenti;
                }
            }
        }
        return najmanjiSegment;
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


    private void izracunajSvePuteve(GPS gps, Dostava dostava){
        for(var segmenti : dostava.vratiSegmentiVoznje()){
            if(!segmenti.odraden){
                GPS gpsLokacije = vratiInterpolaciju(segmenti);
                segmenti.postaviDoGPS(gpsLokacije);
                segmenti.postaviUdaljenost((float)izracunajUdaljenostDvijeTocke(gps,gpsLokacije));
            }
        }
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
