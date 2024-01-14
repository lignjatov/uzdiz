package Chain;

import entity.UredDostave;
import entity.UredPrijema;
import singleton.VirtualnoVrijeme;

import java.sql.Timestamp;

public class VR extends Handler{
    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.startsWith("VR")){
            var virtualno = VirtualnoVrijeme.getInstance();
            int pomak = 0;
            try {
                pomak = Integer.parseInt(komanda.split(" ")[1]);
                Timestamp pomaknutoVrijeme = virtualno.vrijemeVratiPomak(pomak);
                while(virtualno.usporediDvaVremena(virtualno.vratiVrijeme(),pomaknutoVrijeme)) {
                    System.out.println("Trenutno vrijeme: " + virtualno.vratiVrijemeString());
                    uredDostave.provjeriStanjaDostava();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    virtualno.pomakniVrijeme(1);
                    uredPrijema.radi();
                    //Provjeri je li prošao puni sat
                    if(virtualno.prosaoPuniSat()){
                        uredDostave.prihvatiPaketeZaDostavu(uredPrijema.prenesiListuZaDostavu());
                        uredDostave.pripremiDostave();
                        uredDostave.pokreniDostave();
                        virtualno.pomakniSljedeciPuniSat();
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return true;
        }
        return rukujSljedeceg(komanda, uredPrijema, uredDostave);
    }
}
