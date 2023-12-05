package main;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import entity.UredDostave;
import entity.UredPrijema;
import helper.ArgumentChecker;
import helper.PropertyFiller;
import singleton.PaketiRepository;
import singleton.Postavke;
import singleton.VirtualnoVrijeme;
import singleton.VozilaRepository;
import singleton.VrsteRepository;

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
    /*PaketiRepository paketiInstance = PaketiRepository.getInstance();
    VozilaRepository vozilaInstance = VozilaRepository.getInstance();
    VrsteRepository vrsteInstance = VrsteRepository.getInstance();


    Postavke postavke = Postavke.getInstance();


    try {
      vrsteInstance.ucitajPodatke(postavke.dajPostavku("--vp"));
      vozilaInstance.ucitajPodatke(postavke.dajPostavku("--pv"));
      paketiInstance.ucitajPodatke(postavke.dajPostavku("--pp"));


      VirtualnoVrijeme.getInstance().postaviVrijeme(postavke.dajPostavku("--vs"));
      VirtualnoVrijeme.getInstance()
          .postaviKorekciju(Integer.parseInt(postavke.dajPostavku("--ms")));
    } catch (IOException e) {
      e.printStackTrace();
    }*/

    //Zadaća 2
    PropertyFiller podaci = new PropertyFiller();
    Properties properties = new Properties();
    podaci.ucitajPodatke(arg[0],properties);

    if(!podaci.provjeriProperty(properties)) {
      System.exit(0);
    }

    System.out.println(properties.getProperty("--vs"));

    VirtualnoVrijeme.getInstance().postaviVrijeme(properties.getProperty("--vs"));
    VirtualnoVrijeme.getInstance()
            .postaviKorekciju(Integer.parseInt(properties.getProperty("--ms")));
    VirtualnoVrijeme.getInstance().postaviKrajnjeVrijeme(properties.getProperty("--kr"));

  }
}
