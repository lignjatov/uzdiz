package Chain;

import entity.UredDostave;
import entity.UredPrijema;

public class PP extends Handler{
    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.matches("PP")){
            uredDostave.vratiSpremiste().ispisiText();
            return true;
        }
        return rukujSljedeceg(komanda, uredPrijema, uredDostave);
    }
}
