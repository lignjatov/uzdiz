package visitor;

import entity.Dostava;
import entity.Segment;
import implementation.Vozilo;

import java.sql.Timestamp;
import java.util.List;

public class PosjetiteljVoznji implements VisitorI{

    public void posaljiZahtjev(List<KlijentiPosjetitelja> klijenti){
        for(KlijentiPosjetitelja klijent : klijenti){
            klijent.accept(this);
        }
    }

    @Override
    public void posjetiVozilo(Vozilo vozilo) {
        float km=0;
        int brojPaketa=0;
        int brojVoznji=0;
        for(var dostava : vozilo.vratiSveDostave()){
            for (var segment : dostava.vratiSegmentiVoznje()){
                if (segment.vratiUdaljenost()!=null) {
                    km+=segment.vratiUdaljenost();
                }
                if(segment.paketDostave!=null){
                    brojPaketa++;
                }
            }
            brojVoznji++;
        }

        float postotakTezine = (vozilo.trenutnaTezina/vozilo.tezina)*100;

        System.out.println(vozilo.opis+" | "+vozilo.status + " | " + km + " | " +brojPaketa + " | " + postotakTezine + " | "+ brojVoznji);
    }
    @Override
    public void posjetiDostavu(Dostava dostava) {
        int i =0;
        int brojPaket = 0;
        Timestamp pocetnoVrijeme = null;
        Timestamp zavrsnoVrijeme = null;
        for(var segmenti : dostava.vratiSegmentiVoznje()){
            if(i==0){
                pocetnoVrijeme=segmenti.vratiVrijemePocetka();
                i++;
            }
            if(segmenti.paketDostave!=null){
                brojPaket++;
            }
        }
        zavrsnoVrijeme = dostava.vratiSegmentiVoznje().get(dostava.vratiSegmentiVoznje().size()-1).vratiVrijemeKraja();
        System.out.println(pocetnoVrijeme + " | " + zavrsnoVrijeme + " | " +brojPaket);
    }

    @Override
    public void posjetiSegment(Segment segment) {
        System.out.println(segment.vratiVrijemePocetka() + " | " + segment.vratiVrijemeKraja() + " | " + segment.vratiUdaljenost() + " | " + segment.paketDostave.getOznaka());
    }
}
