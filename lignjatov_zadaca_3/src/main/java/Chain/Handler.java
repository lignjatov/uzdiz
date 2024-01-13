package Chain;

import entity.UredDostave;
import entity.UredPrijema;

public abstract class Handler {
    protected Handler sljedeci;

    public Handler postaviSljedeci(Handler sljedeciHandler){
        sljedeci = sljedeciHandler;
        return sljedeciHandler;
    }

    protected boolean rukujSljedeceg(String komanda, UredPrijema uredPrijema, UredDostave uredDostave){
        if(sljedeci==null){
            System.out.println("Nije pronađena komanda!");
            return false;
        }
        return sljedeci.handle(komanda,uredPrijema,uredDostave);
    }
    public abstract boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave);
}