package main;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import Composite.*;
import entity.UredDostave;
import entity.UredPrijema;
import factory.Citac;
import factory.DataFactory;
import factory.VrsteCitaca;
import helper.PropertyFiller;
import singleton.*;

import javax.xml.crypto.Data;

public class Tvrtka {

  public static void main(String[] args) {
  try{
    inicijalizirajStavke(args);
  }
  catch (Exception e){
    System.out.println(e.getMessage());
  }


    Scanner unos = new Scanner(System.in);

    UredPrijema uredPrijema = new UredPrijema();
    UredDostave uredDostave = new UredDostave();
    VirtualnoVrijeme virtualno = VirtualnoVrijeme.getInstance();

    String input = null;
    do {
      input = unos.nextLine();
      if (input.contains("VR")) {
        int pomak = 0;
        try {
          pomak = Integer.parseInt(input.split(" ")[1]);
          for (int i = 0; i < pomak; i++) {
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              System.out.println(e.getMessage());
            }
            virtualno.pomakniVrijeme(1);
            System.out.println(virtualno.vratiVrijemeString());
            uredPrijema.radi();
            uredDostave.dohvatiPaketeZaDostavu(uredPrijema.prenesiListuZaDostavu());
            uredDostave.ukrcavaj();
          }
          System.out.println("Trenutno vrijeme: " + virtualno.vratiVrijemeString());
        } catch (Exception e) {
          System.out.println("Greška u radu: Nije navedeno vrijeme");
        }
      }

      switch (input) {
        case "IP":
          ispisiPodatke(uredDostave);
          break;
        case "Q":
          System.out.println("Izlazim iz programa");
          break;
        default:
      }
    } while (input.compareTo("Q") != 0 && !virtualno.prosloVrijemeRada());

    if(virtualno.prosloVrijemeRada()){
      System.out.println("Kraj radnog vremena!");
    }
    unos.close();
  }

  private static void ispisiPodatke(UredDostave uredDostave) {
    VirtualnoVrijeme virtualno = VirtualnoVrijeme.getInstance();
    System.out.println("Trenutno vrijeme: " + virtualno.vratiVrijemeString());
    System.out.println(
        "VOZILO | PAKET | VRIJEME PRIJEMA | USLUGA | VRIJEME PREUZIMANJA | IZNOS DOSTAVE | STATUS");

    for (var e : uredDostave.dohvatiSveAute()) {
      for (var v : e.dohvatiPakete()) {
        String rezultat = e.vratiIme() + " | ";
        rezultat += v.toString();
        if (v.getVrijemePreuzimanja() == null) {
          rezultat += "|u primanju|";
        } else {
          if (virtualno.vratiVrijeme().compareTo(v.getVrijemePreuzimanja()) > 0) {
            e.naplati(v);
            rezultat += "|predano|";
          } else {
            rezultat += "|u dolasku|";
          }
        }
        System.out.println(rezultat);
      }

    }

  }

  private static void inicijalizirajStavke(String[] arg) throws IOException {

    try {
      //Zadaća 2
      PropertyFiller podaci = new PropertyFiller();
      Properties properties = new Properties();
      podaci.ucitajPodatke(arg[0],properties);

      if(!podaci.provjeriProperty(properties)) {
        System.exit(0);
      }

      VirtualnoVrijeme instancaVrijeme = VirtualnoVrijeme.getInstance();
      instancaVrijeme.postaviVrijeme(properties.getProperty("--vs"));
      instancaVrijeme.postaviKorekciju(Integer.parseInt(properties.getProperty("--ms")));
      instancaVrijeme.postaviKrajnjeVrijeme(properties.getProperty("--kr"));


      DataRepository data = DataRepository.getInstance();
      DataFactory factory = new DataFactory();

      Citac citaci = factory.vratiCitac(VrsteCitaca.VrstePaketa);
      data.postaviListuVrstaPaketa(citaci.ucitajPodatke(properties.getProperty("--vp")));

      citaci = factory.vratiCitac(VrsteCitaca.Paketi);
      data.postaviListuPaketa(citaci.ucitajPodatke(properties.getProperty("--pp")));

      citaci = factory.vratiCitac(VrsteCitaca.Vozila);
      data.postaviListuVozila(citaci.ucitajPodatke(properties.getProperty("--pv")));

      citaci = factory.vratiCitac(VrsteCitaca.Ulica);
      data.postaviListuUlica(citaci.ucitajPodatke(properties.getProperty("--pu")));

      citaci = factory.vratiCitac(VrsteCitaca.Mjesto);
      data.postaviListuMjesta(citaci.ucitajPodatke(properties.getProperty("--pm")));

      citaci = factory.vratiCitac(VrsteCitaca.Osoba);
      data.postaviListuOsoba(citaci.ucitajPodatke(properties.getProperty("--po")));

      for(var a : data.vratiListaOsoba()){
        System.out.println(a.vratiIme());
      }

      data.postaviPostavke(properties);

      Spremiste spremiste = data.vratiSpremiste();

      dohvatiPodrucje(properties.getProperty("--pmu"),spremiste);


    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void dohvatiPodrucje(String nazivDatoteke, Spremiste spremiste) {
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
  public static Mjesto vratiMjesto(int idMjesta){
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
  public static Ulica vratiUlicu(int idUlice){
    for(var element : DataRepository.getInstance().vratiListaUlica()){
      if(element.vratiId()==idUlice){
        return element;
      }
    }
    return null;
  }
  public static boolean postojiVrijednost(Podrucje podrucje, Mjesto mjesto){
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
}
