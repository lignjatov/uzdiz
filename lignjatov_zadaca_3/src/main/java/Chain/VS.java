package Chain;

import entity.UredDostave;
import entity.UredPrijema;
import implementation.Vozilo;
import visitor.KlijentiPosjetitelja;
import visitor.PosjetiteljVoznji;
import java.util.List;

public class VS extends Handler{
    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.startsWith("VS")){
            try{
                String vozilo = komanda.split(" ")[1];
                String brojSegmenta = komanda.split(" ")[2];
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
                    List<KlijentiPosjetitelja> segment = ispisVozilo.vratiDostavu(Integer.parseInt(brojSegmenta));
                    posjetiteljVoznji.posaljiZahtjev(segment);
                }

            }catch (Exception e){
                System.out.println("Neispravna komanda");
            }
            return true;
        }
        return rukujSljedeceg(komanda,uredPrijema,uredDostave);
    }
}
