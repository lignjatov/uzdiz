package Chain;

import entity.UredDostave;
import entity.UredPrijema;

public class BaseHandler extends Handler{

    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        return rukujSljedeceg(komanda, uredPrijema, uredDostave);
    }
}
