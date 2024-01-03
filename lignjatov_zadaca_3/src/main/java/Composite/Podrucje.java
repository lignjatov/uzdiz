package Composite;

import java.util.ArrayList;
import java.util.List;

public class Podrucje implements Kutija {

    int id;
    List<Kutija> listaMjesta = new ArrayList<Kutija>();

    public void dodajMjesto(Kutija mjesto){
        listaMjesta.add(mjesto);
    }

    public List<Kutija> vratiListuKutija(){
        return listaMjesta;
    }

    public Kutija vratiMjesto(int idMjesta){
        for(var a : listaMjesta){
            if(a instanceof Mjesto){
                Mjesto mjesto = (Mjesto) a;
                if(mjesto.getId()==idMjesta){
                    return a;
                }
            }
        }
        return null;
    }

    public int vratiId() {
        return id;
    }

    public void postaviId(int id) {
        this.id = id;
    }

    @Override
    public void ispisiText() {
        System.out.println("Podrucje je: " + id);
        for(Kutija mjesto : listaMjesta){
            mjesto.ispisiText();
        }
    }
}
