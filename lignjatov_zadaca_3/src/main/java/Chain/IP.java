package Chain;

import entity.UredDostave;
import entity.UredPrijema;
import singleton.VirtualnoVrijeme;

public class IP extends Handler{

    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.startsWith("IP")){
            ispisiPodatke(uredDostave);
            return true;
        }
        return rukujSljedeceg(komanda, uredPrijema, uredDostave);
    }


    private void ispisiPodatke(UredDostave uredDostave) {
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
