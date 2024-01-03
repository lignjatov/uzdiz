package implementation;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Composite.Spremiste;
import entity.Dostava;
import entity.Paket;
import entity.Segment;
import entity.UredDostave;
import singleton.DataRepository;
import singleton.VirtualnoVrijeme;
import state.Isporuka;
import state.State;
import strategy.StrategijaIsporuciNajblizi;
import strategy.StrategijaIsporuciPoredu;
import strategy.StrategijeIsporuke;
import visitor.KlijentiPosjetitelja;
import visitor.VisitorI;

public class Vozilo implements KlijentiPosjetitelja {
  public State stanjeVozila;
  public List<Dostava> dostava = new ArrayList<Dostava>();
  public String registracija;
  public String opis;
  public Float tezina;
  public Float prostor;
  public Integer redoslijed;
  public Integer prosjecnaBrzina;
  public List<Integer> podrucja = new ArrayList<>();
  public String status;
  public Float trenutnaTezina = (float) 0;
  public Float trenutnaVelicina = (float) 0;
  public Integer trenutnoPodrucje = 0;

  StrategijeIsporuke strategijeIsporuke;

  public Vozilo(){
    if(DataRepository.getInstance().vratiPostavke().getProperty("--isporuka").contains("2")){
      strategijeIsporuke = new StrategijaIsporuciPoredu();
    }
    else {
      strategijeIsporuke = new StrategijaIsporuciNajblizi();
    }
  }

  public void utovari(List<Paket> paketi, Spremiste spremiste) {
    Dostava novaDostava = new Dostava();
    for(Paket paket : paketi){
      Segment noviSegment = stanjeVozila.ukrcaj(paket,spremiste);
      if(noviSegment!=null){
        novaDostava.dodajSegmentVoznja(noviSegment);
      }
    }
    if(!novaDostava.provjeriPrazniSegment()){
      novaDostava.status=true;
      dodajDostavu(novaDostava);
    }
  }
  public void dostavi() {
    if(this.status.compareTo("A")==0){
      for(var elementDostave : dostava){
        if(elementDostave.status){
          strategijeIsporuke.pripremiIsporuku(elementDostave);
          promjeniStanje(new Isporuka(this));
        }
      }
      stanjeVozila.isporuci();
    }
  }

  public void povratak(){
    stanjeVozila.povratak();
  }
  public void naplati(Paket paket) {
    trenutnaTezina -= paket.getTezina();
    trenutnaVelicina -= paket.getDuzina() * paket.getSirina() * paket.getVisina();
  }

  public void dodajTezinuVelicinuPaketa(Paket paket){
    trenutnaTezina += paket.getTezina();
    trenutnaVelicina += paket.getDuzina() * paket.getSirina() * paket.getVisina();
  }

  public boolean provjeriUkrcavanje(Paket paket) {
    Float paketMjere = paket.getDuzina() * paket.getSirina() * paket.getVisina();
    if (paket.getTezina() + trenutnaTezina <= tezina && trenutnaVelicina + paketMjere <= prostor) {
      return true;
    }
    return false;
  }

  public void promjeniStanje(State stanje){
    stanjeVozila=stanje;
  }

  @Override
  public String toString() {

    return "opis: " + this.opis + " tezina: " + this.tezina + " prostor: " + this.prostor;
  }

  public void dodajDostavu(Dostava novaDostava) {
    dostava.add(novaDostava);
  }

  public Dostava vratiTrenutnuDostavu(){
    for(var d : dostava){
      if(d.status){
        return d;
      }
    }
    return null;
  }
  public void accept(VisitorI visitor){
    visitor.posjetiVozilo(this);
  }

  public List<Dostava> vratiSveDostave(){
    return dostava;
  }

  public List<KlijentiPosjetitelja> vratiSveDostaveVisit(){
    List<KlijentiPosjetitelja> klijenti = new ArrayList<>();
    for(KlijentiPosjetitelja vozilo : dostava){
      klijenti.add(vozilo);
    }
    return klijenti;
  }

  public List<KlijentiPosjetitelja> vratiDostavu(int n){
    List<KlijentiPosjetitelja> klijenti = new ArrayList<>();
    klijenti.add(dostava.get(n-1));
    return klijenti;
  }
}
