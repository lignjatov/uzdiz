package main;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import Chain.Handler;
import Chain.IP;
import Chain.VR;
import Composite.*;
import entity.Osoba;
import entity.UredDostave;
import entity.UredPrijema;
import factory.Citac;
import factory.DataFactory;
import factory.VrsteCitaca;
import helper.PropertyFiller;
import implementation.Vozilo;
import singleton.*;
import visitor.KlijentiPosjetitelja;
import visitor.PosjetiteljVoznji;

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
    VR testPocetak = new VR();
    testPocetak.postaviSljedeci(new IP());

    UredPrijema uredPrijema = new UredPrijema();
    UredDostave uredDostave = new UredDostave();
    VirtualnoVrijeme virtualno = VirtualnoVrijeme.getInstance();

    String input = null;
    do {
    	System.out.println("Trenutno vrijeme: " + virtualno.vratiVrijemeString());
      System.out.println("Komanda: ");
      input = unos.nextLine();
      testPocetak.handle(input,uredPrijema,uredDostave);
      /*
      if (input.contains("VR")) {
        int pomak = 0;
        try {
          pomak = Integer.parseInt(input.split(" ")[1]);
          Timestamp pomaknutoVrijeme = virtualno.vrijemeVratiPomak(pomak);
          while(virtualno.usporediDvaVremena(virtualno.vratiVrijeme(),pomaknutoVrijeme)) {
            System.out.println("Trenutno vrijeme: " + virtualno.vratiVrijemeString());
            uredDostave.provjeriStanjaDostava();
            try {
              Thread.sleep(1000);
              //Thread.sleep(1000);
            } catch (InterruptedException e) {
              //System.out.println();
              e.printStackTrace();
            }
            virtualno.pomakniVrijeme(1);
            uredPrijema.radi();
            //Provjeri je li prošao puni sat
            if(virtualno.prosaoPuniSat()){
              uredDostave.prihvatiPaketeZaDostavu(uredPrijema.prenesiListuZaDostavu());
              uredDostave.pripremiDostave();
              uredDostave.pokreniDostave();
              virtualno.pomakniSljedeciPuniSat();
            }
          }

        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }

      if(input.contains("PS")){
        try{
          String[] komande = input.split(" ");
          String registracija = komande[1];
          String status = komande[2];
          uredDostave.promijeniStatusVozila(registracija,status);
        }catch (Exception e){
          System.out.println("Neispravna komanda");
        }
      }

      if(input.contains("VV")){
        try{
          String vozilo = input.split(" ")[1];
          Vozilo ispisVozilo = null;
          for(var trazenoVozilo : uredDostave.dohvatiSveAute()){
            if(trazenoVozilo.registracija.compareTo(vozilo)==0){
              ispisVozilo=trazenoVozilo;
            }
          }
          if(ispisVozilo==null){
            System.out.println("Vozilo ne postoji");
          }
          else{
            System.out.println("VRIJEME POČETKA | VRIJEME POVRATKA | TRAJANJE | BROJ PAKETA");

            PosjetiteljVoznji posjetiteljVoznji = new PosjetiteljVoznji();
            List<KlijentiPosjetitelja> dostave = ispisVozilo.vratiSveDostaveVisit();
            posjetiteljVoznji.posaljiZahtjev(dostave);
          }

        }catch (Exception e){
          System.out.println("Neispravna komanda");
        }
      }

      if(input.contains("PO")){
        try{
          String[] alibaba = input.split("'");
          int i=0;
          String nazivOsobe = alibaba[1];
          String[] paketKomanda = alibaba[2].split(" ");
          String paketOznaka = paketKomanda[1];
          String komanda = paketKomanda[2].trim();
          Osoba osoba = pronadiOsobu(nazivOsobe);
          if(osoba!=null){
            for(var paket : DataRepository.getInstance().vratiListaPaketa()){
              if(paket.getOznaka().compareTo(paketOznaka)==0){
                if(komanda.contains("N")) {
                  paket.makniPretplatnika(osoba);
                  System.out.println("Osoba " + osoba.vratiIme() + " ne bude primala obavijesti za paket "+paket.getOznaka());
                  break;
                }
                if(komanda.contains("D")){
                  paket.dodajPretplatnika(osoba);
                  System.out.println("Osoba " + osoba.vratiIme() + " bude primala obavijesti za paket "+paket.getOznaka());
                  break;
                }
              }
            }
          }
          else{
            System.out.println("Osoba ne postoji");
          }
        }catch (Exception e){
          System.out.println("Neispravna komanda");
        }
      }

      if(input.contains("VS")){
        try{
          String vozilo = input.split(" ")[1];
          String brojSegmenta = input.split(" ")[2];
          Vozilo ispisVozilo = null;
          for(var trazenoVozilo : uredDostave.dohvatiSveAute()){
            if(trazenoVozilo.registracija.compareTo(vozilo)==0){
              ispisVozilo=trazenoVozilo;
            }
          }
          if(ispisVozilo==null){
            System.out.println("Vozilo ne postoji");
          }
          else{
            System.out.println("VRIJEME POČETKA | VRIJEME POVRATKA | TRAJANJE | BROJ PAKETA");

            PosjetiteljVoznji posjetiteljVoznji = new PosjetiteljVoznji();
            List<KlijentiPosjetitelja> segment = ispisVozilo.vratiDostavu(Integer.parseInt(brojSegmenta));
            posjetiteljVoznji.posaljiZahtjev(segment);
          }

        }catch (Exception e){
          System.out.println("Neispravna komanda");
        }
      }
      switch (input) {
        case "IP":
          ispisiPodatke(uredDostave);
          break;
        case "PP":
          uredDostave.vratiSpremiste().ispisiText();
          break;
        case "SV":
          System.out.println("NAZIV | STATUS | UKUPNO KM | BROJ PAKETA | TRENUTNO % | BROJ VOŽNJI");
          PosjetiteljVoznji posjetiteljVoznji = new PosjetiteljVoznji();
          List<KlijentiPosjetitelja> vozila = uredDostave.dohvatiAuteKaoKlijente();
          posjetiteljVoznji.posaljiZahtjev(vozila);
          break;
        case "Q":
          System.out.println("Izlazim iz programa");
          break;
        default:
      }*/
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
      for (var v : e.vratiSveDostave()) {
        for(var p : v.vratiSegmentiVoznje()){
          if(p.paketDostave==null){
            continue;
          }
          String rezultat = e.registracija + " | ";
          rezultat += p.paketDostave.toString() + " | ";
          rezultat += p.paketDostave.getVrijemePrijema() + " | ";
          rezultat += p.paketDostave.getUslugaDostave() + " | ";
          rezultat += p.vratiVrijemeKraja() + " | ";
          rezultat += p.paketDostave.getIznosPouzeca() + " | ";
          if(virtualno.prosloTrenutno(p.vratiVrijemeKraja())){
            rezultat += " predano";
          }
          else{
            rezultat += " u tranzitu";
          }
          System.out.println(rezultat);
        }

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



  private static Osoba pronadiOsobu(String imeOsobe){
    for(var osoba : DataRepository.getInstance().vratiListaOsoba()){
      if(osoba.vratiIme().compareTo(imeOsobe)==0){
        return osoba;
      }
    }
    return null;
  }
}
