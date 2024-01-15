package Chain;

import entity.UredDostave;
import entity.UredPrijema;
import memento.Memento;
import singleton.VirtualnoVrijeme;

import java.util.ArrayList;
import java.util.List;

public class SPV extends Handler{
    public static List<Memento> listaStanja = new ArrayList<>();

    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.startsWith("SPV")){
            try{
                String[] podaciKomande = komanda.split("'");
                spremiStanjeDostave(uredDostave.stvoriSlikuUreda(podaciKomande[1]));
            }catch (Exception e){
                System.out.println("Neispravna komanda");
            }
            return true;
        }
        if(komanda.startsWith("PPV")){
            try{
                String[] podaciKomande = komanda.split("'");
                Memento stanje = vratiStanje(podaciKomande[1]);
                if(stanje==null)
                    return rukujSljedeceg(komanda,uredPrijema,uredDostave);
                uredDostave.vratiSliku(stanje);

                VirtualnoVrijeme.getInstance().postaviVrijeme(stanje.vratiVrijeme());
            }catch (Exception e){
                System.out.println("Neispravna komanda");
            }
            return true;
        }
        return rukujSljedeceg(komanda,uredPrijema,uredDostave);
    }

    private void spremiStanjeDostave(Memento trenutnoStanje){
        listaStanja.add(trenutnoStanje);
    }

    private Memento vratiStanje(String naziv){
        return listaStanja.stream().filter(x->x.getNazivStanja().matches(naziv)).findFirst().orElse(null);
    }
}