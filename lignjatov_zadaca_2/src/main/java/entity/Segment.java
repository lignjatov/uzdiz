package entity;

import java.sql.Timestamp;
import java.time.LocalTime;

public class Segment {
    GPS odGPS;
    GPS doGPS;
    Float udaljenost;
    Timestamp vrijemePocetka;
    Timestamp vrijemeKraja;
    Integer trajanjeIsporuke;
    Float ukupnoTrajanjeSegmenta;
    public Paket paketDostave;
    public boolean odraden=false;
    public GPS vratiOdGPS() {
        return odGPS;
    }

    public void postaviOdGPS(GPS odGPS) {
        this.odGPS = odGPS;
    }

    public GPS vratiDoGPS() {
        return doGPS;
    }

    public void postaviDoGPS(GPS doGPS) {
        this.doGPS = doGPS;
    }

    public Float vratiUdaljenost() {
        return udaljenost;
    }

    public void postaviUdaljenost(Float udaljenost) {
        this.udaljenost = udaljenost;
    }

    public Timestamp vratiVrijemePocetka() {
        return vrijemePocetka;
    }

    public void postaviVrijemePocetka(Timestamp vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public Timestamp vratiVrijemeKraja() {
        return vrijemeKraja;
    }

    public void postaviVrijemeKraja(Timestamp vrijemeKraja) {
        this.vrijemeKraja = vrijemeKraja;
    }

    public Integer vratiTrajanjeIsporuke() {
        return trajanjeIsporuke;
    }

    public void postaviTrajanjeIsporuke(Integer trajanjeIsporuke) {
        this.trajanjeIsporuke = trajanjeIsporuke;
    }

    public Float vratiUkupnoTrajanjeSegmenta() {
        return ukupnoTrajanjeSegmenta;
    }

    public void postaviUkupnoTrajanjeSegmenta(Float ukupnoTrajanjeSegmenta) {
        this.ukupnoTrajanjeSegmenta = ukupnoTrajanjeSegmenta;
    }



    public Float izracunajUdaljenost(GPS lokacija1, GPS lokacija2){
        return null;
    }
}
