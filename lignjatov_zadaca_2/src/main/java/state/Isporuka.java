package state;

import Composite.Spremiste;
import entity.Dostava;
import entity.Paket;
import entity.Segment;
import implementation.Vozilo;
import singleton.DataRepository;
import singleton.VirtualnoVrijeme;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Isporuka extends State{
    public Isporuka(Vozilo proslijedenoVozilo) {
        super(proslijedenoVozilo);
    }



    @Override
    public Segment ukrcaj(Paket paket, Spremiste spremiste) {
        return null;
    }


    @Override
    public void isporuci() {
        izracunajVrijemeIsporuke(pronadiAktivnuDostavu());
    }

    private void izracunajVrijemeIsporuke(Dostava aktivnaDostava) {
        int i=0;
        Segment prijasnjiSegment = null;
        for(var segment : aktivnaDostava.vratiSegmentiVoznje()){
            if(i==0){
                segment.postaviVrijemePocetka(VirtualnoVrijeme.getInstance().vratiVrijeme());
                i++;
            }
            else {
                segment.postaviVrijemePocetka(prijasnjiSegment.vratiVrijemeKraja());
            }
             //Trajanje isporuke
             segment.postaviTrajanjeIsporuke(Integer.parseInt(
                     (DataRepository.getInstance().vratiPostavke().getProperty("--vi"))));

            segment.postaviVrijemeKraja(dodajVrijemeUMinute
                    (segment.vratiVrijemePocetka(),segment.vratiTrajanjeIsporuke()));
             //vrijemeVožnje
             Float vrijemeVoznje = segment.vratiUdaljenost()/vozilo.prosjecnaBrzina;

             segment.postaviVrijemeKraja(dodajVrijemeUMinute
                     (segment.vratiVrijemeKraja(),vrijemeVoznje.intValue()));
            prijasnjiSegment=segment;
        }
    }

    private Dostava pronadiAktivnuDostavu(){
        for(var dostave : vozilo.dostava){
            if(dostave.status){
                return dostave;
            }
        }
        return null;
    }


    public Timestamp dodajVrijemeUMinute(Timestamp vrijemeDodavanja, int minute) {
        LocalDateTime localDateTime = LocalDateTime.from(vrijemeDodavanja.toLocalDateTime());
        localDateTime = localDateTime.plusMinutes(minute);
        return Timestamp.valueOf(localDateTime);
    }

    @Override
    public void povratak() {
    }
}
