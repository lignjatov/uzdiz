package Chain;

import entity.UredDostave;
import entity.UredPrijema;
import implementation.Vozilo;
import visitor.PosjetiteljVoznji;
import visitor.KlijentiPosjetitelja;

import java.util.List;

public class VV extends Handler{
    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.startsWith("VV")){
            try{
                String vozilo = komanda.split(" ")[1];
                Vozilo ispisVozilo = null;
                for(var trazenoVozilo : uredDostave.dohvatiSveAute()){
                    if(trazenoVozilo.registracija.compareTo(vozilo)==0){
                        ispisVozilo=trazenoVozilo;
                    }
                }
                if(ispisVozilo==null){
                    System.out.println("Vozilo ne postoji");
                }
                else{
                    System.out.println("VRIJEME POČETKA | VRIJEME POVRATKA | TRAJANJE | BROJ PAKETA");
                    PosjetiteljVoznji posjetiteljVoznji = new PosjetiteljVoznji();
                    List<KlijentiPosjetitelja> dostave = ispisVozilo.vratiSveDostaveVisit();
                    posjetiteljVoznji.posaljiZahtjev(dostave);
                }
            }catch (Exception e){
                System.out.println("Neispravna komanda");
            }
            return true;
        }
        return rukujSljedeceg(komanda, uredPrijema, uredDostave);
    }
}
