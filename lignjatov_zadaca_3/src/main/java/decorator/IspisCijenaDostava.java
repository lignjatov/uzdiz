package decorator;

import entity.UredDostave;
import singleton.VirtualnoVrijeme;

public class IspisCijenaDostava extends IPDecorator{
    public IspisCijenaDostava(IspisPodatakaIP wrappee) {
        super(wrappee);
    }

    @Override
    public void ispisiPodatke(UredDostave ured) {
        super.ispisiPodatke(ured);
        VirtualnoVrijeme virtualno = VirtualnoVrijeme.getInstance();
        System.out.println("Trenutno vrijeme: " + virtualno.vratiVrijemeString());
        System.out.println("Ukupna iznos pouzeća dostave: ");
        Float cijenaDostave;
        int i=1;
        for (var e : ured.dohvatiSveAute()) {
            i=1;
            for (var v : e.vratiSveDostave()) {
                cijenaDostave= 0.0F;
                for(var p : v.vratiSegmentiVoznje()){
                    if(p.paketDostave==null) {
                        continue;
                    }
                    cijenaDostave += p.paketDostave.getIznosPouzeca();
                }
                if(i==1)
                    System.out.println("Vozilo: "+e.registracija);
                if(cijenaDostave>=0)
                    System.out.println("Iznos pouzeća za pakete dostave " + i++ + " iznosi = " + cijenaDostave);
            }
        }
    }
}
