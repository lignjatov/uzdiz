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
    // odkomentirat kadabude potrebno
    /*
     * if (!args.length == 9) {
     * 
     * }
     */

    inicijializirajStavke(args);

    Scanner unos = new Scanner(System.in);

    UredPrijema uredPrijema = new UredPrijema();
    UredDostave uredDostave = new UredDostave();



    // paketiInstance.loadData(args[0]);

    String input = null;
    do {
      input = unos.nextLine();
      if (input.contains("VR")) {
        int pomak = Integer.parseInt(input.split(" ")[1]);
        VirtualnoVrijeme virtualno = VirtualnoVrijeme.getInstance();
        virtualno.pomakniVrijeme(pomak);

        uredPrijema.radi();
        uredDostave.radi();
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

  private static void inicijializirajStavke(String[] arg) {
    PaketiRepository paketiInstance = PaketiRepository.getInstance();
    VozilaRepository vozilaInstance = VozilaRepository.getInstance();
    VrsteRepository vrsteInstance = VrsteRepository.getInstance();


    // TODO: ovo napraviti varijabilnim
    Postavke postavke = Postavke.getInstance();
    postavke.postaviPostavku("mt", "100");
    postavke.postaviPostavku("vi", "10");
    postavke.postaviPostavku("vs", "20.10.2023. 08:00:00");
    postavke.postaviPostavku("ms", "600");
    postavke.postaviPostavku("pr", "07:00");
    postavke.postaviPostavku("kr", "19:00");

    try {
      paketiInstance.ucitajPodatke("/home/NWTiS_1/uzdiz/lignjatov_zadaca_1/podaci/DZ_1_paketi.csv");
      vozilaInstance.ucitajPodatke("/home/NWTiS_1/uzdiz/lignjatov_zadaca_1/podaci/DZ_1_vozila.csv");
      vrsteInstance.ucitajPodatke("/home/NWTiS_1/uzdiz/lignjatov_zadaca_1/podaci/DZ_1_vrste.csv");
      VirtualnoVrijeme.getInstance().postaviVrijeme(postavke.dajPostavku("vs"));
      VirtualnoVrijeme.getInstance().postaviKorekciju(Integer.parseInt(postavke.dajPostavku("ms")));
      VirtualnoVrijeme.getInstance().vratiVrijeme();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



}
