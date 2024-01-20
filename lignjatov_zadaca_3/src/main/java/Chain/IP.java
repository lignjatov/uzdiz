package Chain;

import decorator.IIspisTekstaIP;
import decorator.IspisPodatakaIP;
import entity.UredDostave;
import entity.UredPrijema;
import singleton.VirtualnoVrijeme;

public class IP extends Handler{
    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.startsWith("IP")){
            IspisPodatakaIP ispis = new IspisPodatakaIP(uredDostave);

            ispis.ispisiPodatke();
            return true;
        }
        return rukujSljedeceg(komanda, uredPrijema, uredDostave);
    }
}
