package decorator;

import entity.UredDostave;
import singleton.VirtualnoVrijeme;

public class IspisPodatakaIP implements IIspisTekstaIP{

    UredDostave uredDostave;
    public IspisPodatakaIP(UredDostave ured){
        uredDostave = ured;
    }
    public void ispisiPodatke() {
        VirtualnoVrijeme virtualno = VirtualnoVrijeme.getInstance();
        System.out.println("Trenutno vrijeme: " + virtualno.vratiVrijemeString());
        System.out.println(
                "VOZILO | PAKET | VRIJEME PRIJEMA | USLUGA | VRIJEME PREUZIMANJA | IZNOS DOSTAVE | STATUS");

        for (var e : uredDostave.dohvatiSveAute()) {
            for (var v : e.vratiSveDostave()) {
                for(var p : v.vratiSegmentiVoznje()){
                    if(p.paketDostave==null){
                        continue;
                    }
                    String rezultat = e.registracija + " | ";
                    rezultat += p.paketDostave.toString() + " | ";
                    rezultat += p.paketDostave.getVrijemePrijema() + " | ";
                    rezultat += p.paketDostave.getUslugaDostave() + " | ";
                    rezultat += p.vratiVrijemeKraja() + " | ";
                    rezultat += p.paketDostave.getIznosPouzeca() + " | ";
                    if(virtualno.prosloTrenutno(p.vratiVrijemeKraja())){
                        rezultat += " predano";
                    }
                    else{
                        rezultat += " u tranzitu";
                    }
                    System.out.println(rezultat);
                }
            }
        }
    }
}
