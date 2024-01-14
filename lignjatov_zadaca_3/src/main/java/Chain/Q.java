package Chain;

import entity.UredDostave;
import entity.UredPrijema;

public class Q extends Handler{
    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.matches("Q")){
            System.out.println("Kraj programa pokrenut komandom!");
            return true;
        }
        return rukujSljedeceg(komanda, uredPrijema, uredDostave);
    }
}
