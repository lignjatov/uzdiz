package state;

import Composite.Spremiste;
import entity.Paket;
import entity.Segment;
import implementation.Vozilo;

public abstract class State {

    protected Vozilo vozilo;

    State(Vozilo proslijedenoVozilo){
        vozilo = proslijedenoVozilo;
    }
    abstract public Segment ukrcaj(Paket paket, Spremiste spremiste);

    abstract public void isporuci();

    abstract public void povratak();
}
