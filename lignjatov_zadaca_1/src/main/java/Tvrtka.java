import java.io.IOException;
import java.util.Scanner;
import entity.UredDostave;
import entity.UredPrijema;
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
        int pomak = Integer.parseInt(input.split(" ")[1]);
        VirtualnoVrijeme virtualno = VirtualnoVrijeme.getInstance();
        for (int i = 0; i < pomak; i++) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          virtualno.pomakniVrijeme(1);
          uredPrijema.radi();
          uredDostave.radi();
        }
        System.out.println("Trenutno vrijeme: " + virtualno.vratiVrijemeString());
        continue;
      }

      switch (input) {
        case "IP":
          PaketiRepository a = PaketiRepository.getInstance();
          VirtualnoVrijeme virtualno = VirtualnoVrijeme.getInstance();

          break;
        case "Q":
          System.out.println("Izlazim iz programa");
          break;
        default:
      }
    } while (input.compareTo("Q") != 0);
    unos.close();
  }

  private static void inicijalizirajStavke(String[] arg) {
    PaketiRepository paketiInstance = PaketiRepository.getInstance();
    VozilaRepository vozilaInstance = VozilaRepository.getInstance();
    VrsteRepository vrsteInstance = VrsteRepository.getInstance();


    Postavke postavke = Postavke.getInstance();
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
