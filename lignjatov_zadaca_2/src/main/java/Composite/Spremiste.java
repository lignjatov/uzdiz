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

    public Podrucje vratiPodrucjePoUlici(Ulica ulica){
        for(Kutija element : djeca){
            Podrucje podrucja = (Podrucje)element;
            for(Kutija mjesta : podrucja.vratiListuKutija()){
                Mjesto mjesto = (Mjesto) mjesta;
                for(var ulice : mjesto.getUlice()){
                    Ulica ulicaa = (Ulica) ulice;
                    if(ulicaa.vratiNaziv().compareTo(ulica.vratiNaziv())==0){
                        return podrucja;
                    }
                }
            }
        }
        return null;
    }
}
