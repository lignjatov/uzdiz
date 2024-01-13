package Chain;

import entity.UredDostave;
import entity.UredPrijema;

public class IP extends Handler{

    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.startsWith("IP")){
            System.out.println("Predivno stanje IP");
            return true;
        }
        return rukujSljedeceg(komanda, uredPrijema, uredDostave);
    }
}
