package Chain;

import entity.UredDostave;
import entity.UredPrijema;
import visitor.KlijentiPosjetitelja;
import visitor.PosjetiteljVoznji;

import java.util.List;

public class SV extends Handler{
    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.matches("SV")){
            System.out.println("NAZIV | STATUS | UKUPNO KM | BROJ PAKETA | TRENUTNO % | BROJ VOŽNJI");
            PosjetiteljVoznji posjetiteljVoznji = new PosjetiteljVoznji();
            List<KlijentiPosjetitelja> vozila = uredDostave.dohvatiAuteKaoKlijente();
            posjetiteljVoznji.posaljiZahtjev(vozila);
            return true;
        }
        return rukujSljedeceg(komanda,uredPrijema,uredDostave);
    }
}
