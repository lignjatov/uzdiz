package Chain;

import decorator.IIspisTekstaIP;
import decorator.IspisPodatakaIP;
import decorator.IspisCijenaDostava;
import entity.UredDostave;
import entity.UredPrijema;

public class IP extends Handler{
    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.startsWith("IP")){
            IIspisTekstaIP ispis = new IspisCijenaDostava(new IspisPodatakaIP());
            ispis.ispisiPodatke(uredDostave);
            return true;
        }
        return rukujSljedeceg(komanda, uredPrijema, uredDostave);
    }
}
