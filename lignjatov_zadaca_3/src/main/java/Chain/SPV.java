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
                System.out.println("Spremljeno stanje vozila i paketa!");
                spremiStanjeDostave(uredDostave.stvoriSlikuUreda(podaciKomande[1]));

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            return true;
        }
        if(komanda.startsWith("PPV")){
            try{
                String[] podaciKomande = komanda.split("'");
                Memento stanje = vratiStanje(podaciKomande[1]);
                if(stanje==null){
                    System.out.println("Stanje nije pronađeno!");
                    return rukujSljedeceg(komanda,uredPrijema,uredDostave);
                }
                System.out.println("Vraćeno stanje vozila i paketa na zadano");
                uredDostave.vratiSliku(stanje);
                uredPrijema.vratiSliku(stanje);
                VirtualnoVrijeme.getInstance().postaviVrijeme(stanje.vratiVrijeme());
                VirtualnoVrijeme.getInstance().postaviPocetnoVrijeme(stanje.vratiVrijeme().toLocalDateTime().toLocalTime().toString());
                VirtualnoVrijeme.getInstance().pomakniSljedeciPuniSat();

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
