package Chain;

import entity.UredDostave;
import entity.UredPrijema;

public class VR extends Handler{
    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.startsWith("VR")){
            System.out.println("Predivno stanje VR");
            return true;
        }
        return rukujSljedeceg(komanda, uredPrijema, uredDostave);
    }
}
