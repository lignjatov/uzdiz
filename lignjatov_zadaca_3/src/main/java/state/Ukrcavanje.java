package state;

import Composite.*;
import entity.Dostava;
import entity.Osoba;
import entity.Paket;
import entity.Segment;
import implementation.Vozilo;
import singleton.DataRepository;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Ukrcavanje extends State{
    public Ukrcavanje(Vozilo vozilo) {
        super(vozilo);
    }

    @Override
    public Segment ukrcaj(Paket paket, Spremiste spremiste) {
        if(vozilo.status.compareTo("A")!=0){
            return null;
        }
        Dostava novaDostava = new Dostava();
            //vozilo.utovari(paket);
            //paket zauzet
            if(paket.status){
                return null;
            }

            //ako ne može ukrcavat, provjeri sljedeći
            if(!vozilo.provjeriUkrcavanje(paket)){
                return null;
            }

            Ulica ulicaDostave = vratiUlicuPrimatelj(paket.getPrimatelj());
            if(ulicaDostave==null){
                return null;
            }

            //Ulica paketa dostave
            Podrucje podrucje = spremiste.vratiPodrucjePoUlici(ulicaDostave);
            if(voziloPodrucja(vozilo,podrucje)){
                if(vozilo.trenutnoPodrucje==0){
                    vozilo.trenutnoPodrucje=podrucje.vratiId();
                    //kreiraj segment
                    Segment segmentVoznje = new Segment();
                    segmentVoznje.paketDostave=paket;
                    //dodaj veličinu i težinu paketa
                    vozilo.dodajTezinuVelicinuPaketa(paket);
                    //dodaj dostavi
                    novaDostava.dodajSegmentVoznja(segmentVoznje);
                    //stavi pakiranje na true
                    paket.status=true;
                    paket.posaljiPorukuPretplatnicima("Paket " + paket.getOznaka() + " se ubacuje u automobil s registracijom: " + vozilo.registracija);
                    return segmentVoznje;
                }
                else{
                    if(vozilo.trenutnoPodrucje==podrucje.vratiId()){
                        //kreiraj segment
                        Segment segmentVoznje = new Segment();
                        segmentVoznje.paketDostave=paket;
                        //dodaj veličinu i težinu paketa
                        vozilo.dodajTezinuVelicinuPaketa(paket);
                        //dodaj dostavi
                        novaDostava.dodajSegmentVoznja(segmentVoznje);
                        //stavi pakiranje na true
                        paket.status=true;
                        paket.posaljiPorukuPretplatnicima("Paket " + paket.getOznaka() + " se ubacuje u automobil s registracijom: " + vozilo.registracija);
                        return segmentVoznje;
                    }
                }
            }
        return null;
        }

    @Override
    public void isporuci() {

    }

    @Override
    public void povratak() {
    }
    private boolean voziloPodrucja(Vozilo vozilo, Podrucje podrucje){
        for(var podrucja : vozilo.podrucja){
            if(podrucje.vratiId()==podrucja){
                return true;
            }
        }
        return false;
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
}
