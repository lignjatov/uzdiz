package main;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import Chain.*;
import entity.UredDostave;
import entity.UredPrijema;
import factory.Citac;
import factory.DataFactory;
import factory.VrsteCitaca;
import helper.PropertyFiller;
import singleton.*;


public class Tvrtka {

  public static void main(String[] args) {
  try{
    inicijalizirajStavke(args);
  }
  catch (Exception e){
    System.out.println(e.getMessage());
  }
    Scanner unos = new Scanner(System.in);
    Handler izbornik = new BaseHandler();
    inicijalizirajHandlere(izbornik);

    UredPrijema uredPrijema = new UredPrijema();
    UredDostave uredDostave = new UredDostave();
    VirtualnoVrijeme virtualno = VirtualnoVrijeme.getInstance();

    String input = null;
    do {
      System.out.println("Trenutno vrijeme: " + virtualno.vratiVrijemeString());
      System.out.println("Komanda: ");
      input = unos.nextLine();
      izbornik.handle(input,uredPrijema,uredDostave);
    } while (input.compareTo("Q") != 0 && !virtualno.prosloVrijemeRada());

    if(virtualno.prosloVrijemeRada()){
      System.out.println("Kraj radnog vremena!");
    }
    unos.close();
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
      instancaVrijeme.postaviPocetnoVrijeme(properties.getProperty("--pr"));
      instancaVrijeme.postaviKrajnjeVrijeme(properties.getProperty("--kr"));

      DataRepository data = DataRepository.getInstance();
      DataFactory factory = new DataFactory();
      data.postaviPostavke(properties);

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
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  private static void inicijalizirajHandlere(Handler pocetniHandler){
    pocetniHandler.postaviSljedeci(new VR())
            .postaviSljedeci(new IP())
            .postaviSljedeci(new PO())
            .postaviSljedeci(new PP())
            .postaviSljedeci(new PS())
            .postaviSljedeci(new SV())
            .postaviSljedeci(new VS())
            .postaviSljedeci(new VV())
            .postaviSljedeci(new SPV())
            .postaviSljedeci(new Q());
  }
}
