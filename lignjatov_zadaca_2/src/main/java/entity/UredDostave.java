package entity;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import Composite.*;
import implementation.Vozilo;
import singleton.DataRepository;
import singleton.VirtualnoVrijeme;
import state.Isporuka;
import state.Povratak;
import state.Ukrcavanje;

public class UredDostave {
  List<Vozilo> vozniPark = new ArrayList<Vozilo>();

  Spremiste spremiste = new Spremiste();

  List<Paket> paketiZaDostavu = new ArrayList<Paket>();

  public UredDostave() {
    this.incijalizirajVozila();
    this.inicijalizirajPodrucja();
  }

  private void inicijalizirajPodrucja() {
    Properties properties = DataRepository.getInstance().vratiPostavke();
    dohvatiPodrucje(properties.getProperty("--pmu"),spremiste);
  }

  public void incijalizirajVozila() {
    vozniPark = DataRepository.getInstance().vratiListaVozila();
    for(Vozilo vozilo : vozniPark){
      vozilo.promjeniStanje(new Ukrcavanje(vozilo));
    }
  }

  public void prihvatiPaketeZaDostavu(List<Paket> paketiUredaPrijema){
    for(var paket : paketiUredaPrijema){
      paketiZaDostavu.add(paket);
    }
  }

  public void pripremiDostave(){
    for(Vozilo vozilo : vozniPark){
      vozilo.utovari(paketiZaDostavu, spremiste);
    }
  }
  public void pokreniDostave() {
    for(Vozilo vozilo : vozniPark){
      vozilo.dostavi();
    }
  }

  public Spremiste vratiSpremiste() {
    return spremiste;
  }

  private void dohvatiPodrucje(String nazivDatoteke, Spremiste spremiste) {
    var putanja = Path.of(nazivDatoteke);
    if (Files.exists(putanja) && (Files.isDirectory(putanja) || !Files.isWritable(putanja))) {
      System.out.println("Datoteka: '" + nazivDatoteke + "' nije datoteka ili nije moguće upisati u nju");
      return;
    }
    int i = 0;
    try{
      var citac = Files.newBufferedReader(putanja, Charset.forName("UTF-8"));
      while (true) {
        var redak = citac.readLine();
        if (redak == null)
          break;
        var odsjek = redak.split(";");
        if (odsjek.length != 2) {
          System.out.println("Nedovoljan broj atributa");
        } else {
          if (i == 0) {
            i++;
            continue;
          }

          Podrucje podrucje = new Podrucje();
          podrucje.postaviId(Integer.parseInt(odsjek[0]));
          String[] kombinacije = odsjek[1].split(",");

          for(String element : kombinacije){
            String idGrada = element.split(":")[0].trim();
            String idUlice = element.split(":")[1].trim();
            if(idUlice.startsWith("*")){
              for(Mjesto mjesto : DataRepository.getInstance().vratiListaMjesta()){
                if(mjesto.getId()==Integer.parseInt(idGrada.trim())){
                  podrucje.dodajMjesto(mjesto);
                  break;
                }
              }
            }
            else{
              if(postojiVrijednost(podrucje, vratiMjesto(Integer.parseInt(idGrada)))){
                Mjesto staroMjesto = (Mjesto)podrucje.vratiMjesto(Integer.parseInt(idGrada));
                staroMjesto.dodajUlicu(vratiUlicu(Integer.parseInt(idUlice)));
              }else{
                Mjesto novoMjesto = vratiMjesto(Integer.parseInt(idGrada));
                novoMjesto.dodajUlicu(vratiUlicu(Integer.parseInt(idUlice)));
                podrucje.dodajMjesto(novoMjesto);
              }
            }
          }
          spremiste.dodajKutiju(podrucje);
        }
      }
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }
  private Mjesto vratiMjesto(int idMjesta){
    for(var element : DataRepository.getInstance().vratiListaMjesta()){
      if(element.getId()==idMjesta){
        Mjesto novoMjesto = new Mjesto();
        novoMjesto.setId(element.getId());
        novoMjesto.setNaziv(element.getNaziv());
        novoMjesto.setUlice(new ArrayList<Kutija>());
        return novoMjesto;
      }
    }
    return null;
  }
  private Ulica vratiUlicu(int idUlice){
    for(var element : DataRepository.getInstance().vratiListaUlica()){
      if(element.vratiId()==idUlice){
        return element;
      }
    }
    return null;
  }
  private boolean postojiVrijednost(Podrucje podrucje, Mjesto mjesto){
    List<Kutija> trenutniElementi = podrucje.vratiListuKutija();
    for(var vrijednost : trenutniElementi){
      if(vrijednost instanceof Mjesto){
        Mjesto lokacija = (Mjesto) vrijednost;
        if(lokacija.getId()==mjesto.getId())
          return true;
      }
    }
    return false;
  }

  public void provjeriStanjaDostava() {
    for(var vozila : vozniPark){
      for(var dostava : vozila.dostava){
        if(dostava.status==true){
          for(var segment : dostava.vratiSegmentiVoznje()){
            if(!segment.odraden && VirtualnoVrijeme.getInstance().prosloTrenutno(segment.vrijemeKraja)){
              segment.odraden=true;
              segment.paketDostave.posaljiPorukuPretplatnicima(
                      "Paket "+ segment.paketDostave.getOznaka() +" je dostavljen na adresu");
              vozila.naplati(segment.paketDostave);
            }
          }
        }
        if(provjeriSvePaketeDostave(dostava)){
          vozila.promjeniStanje(new Povratak(vozila));
          vozila.povratak();
        }
      }
    }
  }

  public boolean provjeriSvePaketeDostave(Dostava dostava){
    boolean gotoviSegmenti  = true;
    for(var segment : dostava.vratiSegmentiVoznje()){
      if(!segment.odraden){
        gotoviSegmenti=false;
      }
    }
    return gotoviSegmenti;
  }

  public void promijeniStatusVozila(String vozilo, String status){
    boolean ispravnaKomanda = false;
    String[] ispravneKomande = {"A","NI","NA"};
    Vozilo promijenjenoVozilo = null;
    for(Vozilo automobil : vozniPark){
      if(automobil.registracija.compareTo(vozilo)==0){
        promijenjenoVozilo=automobil;
      }
    }
    for(var komanda : ispravneKomande){
      if(komanda.contains(status)){
        ispravnaKomanda=true;
      }
    }
    if(promijenjenoVozilo!=null && ispravnaKomanda){
      promijenjenoVozilo.status=status;
      System.out.println("Vozilu " + promijenjenoVozilo.registracija + " se postavlja status na " + status);
    }
    else{
      System.out.println("Vozilo nije pronađeno ili je neispravna komanda");
    }
  }
}
