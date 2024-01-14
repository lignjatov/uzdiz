package Chain;

import entity.Osoba;
import entity.UredDostave;
import entity.UredPrijema;
import singleton.DataRepository;

public class PO extends Handler{
    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.startsWith("PO")) {
            try {
                String[] alibaba = komanda.split("'");
                int i = 0;
                String nazivOsobe = alibaba[1];
                String[] paketKomanda = alibaba[2].split(" ");
                String paketOznaka = paketKomanda[1];
                String komandaStanja = paketKomanda[2].trim();
                Osoba osoba = pronadiOsobu(nazivOsobe);
                if (osoba != null) {
                    for (var paket : DataRepository.getInstance().vratiListaPaketa()) {
                        if (paket.getOznaka().compareTo(paketOznaka) == 0) {
                            if (komandaStanja.contains("N")) {
                                paket.makniPretplatnika(osoba);
                                System.out.println("Osoba " + osoba.vratiIme() + " ne bude primala obavijesti za paket " + paket.getOznaka());
                                break;
                            }
                            if (komandaStanja.contains("D")) {
                                paket.dodajPretplatnika(osoba);
                                System.out.println("Osoba " + osoba.vratiIme() + " bude primala obavijesti za paket " + paket.getOznaka());
                                break;
                            }
                        }
                    }
                } else {
                    System.out.println("Osoba ne postoji");
                }
            } catch (Exception e) {
                System.out.println("Neispravna komanda");
            }
            return true;
        }
        return rukujSljedeceg(komanda, uredPrijema, uredDostave);
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
