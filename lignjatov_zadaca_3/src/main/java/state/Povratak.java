package state;

import Composite.Spremiste;
import entity.GPS;
import entity.Paket;
import entity.Segment;
import implementation.Vozilo;
import singleton.DataRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Povratak extends State{
    public Povratak(Vozilo proslijedenoVozilo) {
        super(proslijedenoVozilo);
    }

    @Override
    public Segment ukrcaj(Paket paket, Spremiste spremiste) {
        return null;
    }


    @Override
    public void isporuci() {

    }

    @Override
    public void povratak() {
        Segment krajnjiSegment = new Segment();
        Segment prijasnjiSegment = vratiZadnjiSegment();

        //lokacija vraćanja
        krajnjiSegment.postaviOdGPS(prijasnjiSegment.vratiDoGPS());

        String lokacijaPocetka = DataRepository.getInstance().vratiPostavke().getProperty("--gps");
        String[] lokacijaPocetkaArray = lokacijaPocetka.split(",");
        GPS gpsPocetka = new GPS(Float.parseFloat(lokacijaPocetkaArray[0].trim()),Float.parseFloat(lokacijaPocetkaArray[1].trim()));
        krajnjiSegment.postaviDoGPS(gpsPocetka);
        krajnjiSegment.postaviUdaljenost((float)izracunajUdaljenostDvijeTocke(krajnjiSegment.vratiOdGPS(),gpsPocetka));

        //vrijemeVožnje
        Float vrijemeVoznje = krajnjiSegment.vratiUdaljenost()/vozilo.prosjecnaBrzina;
        krajnjiSegment.postaviVrijemePocetka(prijasnjiSegment.vratiVrijemeKraja());
        krajnjiSegment.postaviVrijemeKraja(dodajVrijemeUMinute
                (prijasnjiSegment.vratiVrijemeKraja(),vrijemeVoznje.intValue()));
        vozilo.vratiTrenutnuDostavu().dodajSegmentVoznja(krajnjiSegment);
        vozilo.trenutnoPodrucje=0;
        vozilo.promjeniStanje(new Ukrcavanje(vozilo));
        vozilo.vratiTrenutnuDostavu().status=false;
        System.out.println("Vozilo " + vozilo.registracija + " se vratilo");
    }

    private double izracunajUdaljenostDvijeTocke(GPS koord1, GPS koord2){
        return Math.sqrt(Math.pow(koord2.latitude - koord1.latitude, 2)+Math.pow(koord2.longitude-koord1.longitude,2));
    }

    public Timestamp dodajVrijemeUMinute(Timestamp vrijemeDodavanja, int minute) {
        LocalDateTime localDateTime = LocalDateTime.from(vrijemeDodavanja.toLocalDateTime());
        localDateTime = localDateTime.plusMinutes(minute);
        return Timestamp.valueOf(localDateTime);
    }

    private Segment vratiZadnjiSegment(){
        var d = vozilo.vratiTrenutnuDostavu();
        return d.vratiSegmentiVoznje().get(d.vratiSegmentiVoznje().size()-1);
    }
}
