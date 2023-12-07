package Composite;

import java.util.ArrayList;
import java.util.List;

public class Spremiste implements Kutija{
    List<Kutija> djeca = new ArrayList<Kutija>();

    public void dodajKutiju(Kutija element){
        djeca.add(element);
    }

    @Override
    public void ispisiText() {
        for(Kutija element : djeca){
            element.ispisiText();
        }
    }
}
