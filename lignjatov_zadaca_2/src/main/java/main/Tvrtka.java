package main;
import java.io.IOException;
import java.util.Scanner;
import entity.UredDostave;
import entity.UredPrijema;
import helper.ArgumentChecker;
import singleton.PaketiRepository;
import singleton.Postavke;
import singleton.VirtualnoVrijeme;
import singleton.VozilaRepository;
import singleton.VrsteRepository;

public class Tvrtka {

  public static void main(String[] args) {

    inicijalizirajStavke(args);

    Scanner unos = new Scanner(System.in);

    UredPrijema uredPrijema = new UredPrijema();
    UredDostave uredDostave = new UredDostave();

    String input = null;
    do {
      input = unos.nextLine();
      if (input.contains("VR")) {
        int pomak = 0;
        try {
          pomak = Integer.parseInt(input.split(" ")[1]);
        } catch (Exception e) {
          System.out.println("Greška u radu: Nije navedeno vrijeme");
        }

        VirtualnoVrijeme virtualno = VirtualnoVrijeme.getInstance();
        for (int i = 0; i < pomak; i++) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            System.out.println(e.getMessage());
          }

          virtualno.pomakniVrijeme(1);
          uredPrijema.radi();
          uredDostave.dohvatiPaketeZaDostavu(uredPrijema.prenesiListuZaDostavu());
          uredDostave.ukrcavaj();
        }
        System.out.println("Trenutno vrijeme: " + virtualno.vratiVrijemeString());
        continue;
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
    } while (input.compareTo("Q") != 0);
    unos.close();
  }

  private static void ispisiPodatke(UredDostave uredDostave) {
    System.out.println(
        "VOZILO | PAKET | VRIJEME PRIJEMA | USLUGA | VRIJEME PREUZIMANJA | IZNOS DOSTAVE | STATUS");
    VirtualnoVrijeme virtualno = VirtualnoVrijeme.getInstance();
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

  private static void inicijalizirajStavke(String[] arg) {
    PaketiRepository paketiInstance = PaketiRepository.getInstance();
    VozilaRepository vozilaInstance = VozilaRepository.getInstance();
    VrsteRepository vrsteInstance = VrsteRepository.getInstance();


    Postavke postavke = Postavke.getInstance();

    ArgumentChecker provjeraArgumenata = new ArgumentChecker();

    if (arg.length != 18) {
      System.out.println("Nedovoljan broj argumenata!");
      System.exit(0);
    }
    for (int i = 0; i < arg.length; i += 2) {
      postavke.postaviPostavku(arg[i], arg[i + 1]);
    }

    try {
      vrsteInstance.ucitajPodatke(postavke.dajPostavku("--vp"));
      vozilaInstance.ucitajPodatke(postavke.dajPostavku("--pv"));
      paketiInstance.ucitajPodatke(postavke.dajPostavku("--pp"));


      VirtualnoVrijeme.getInstance().postaviVrijeme(postavke.dajPostavku("--vs"));
      VirtualnoVrijeme.getInstance()
          .postaviKorekciju(Integer.parseInt(postavke.dajPostavku("--ms")));
      VirtualnoVrijeme.getInstance().vratiVrijeme();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
