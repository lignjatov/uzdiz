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
      System.out.println("Komanda: ");
      input = unos.nextLine();
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
      switch (input) {
        case "IP":
          ispisiPodatke(uredDostave);
          break;
        case "PP":
          uredDostave.vratiSpremiste().ispisiText();
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
    /*
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
  */
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

}
