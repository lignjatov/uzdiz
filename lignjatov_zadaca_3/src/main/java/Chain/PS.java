package Chain;

import entity.UredDostave;
import entity.UredPrijema;

public class PS extends Handler{
    @Override
    public boolean handle(String komanda, UredPrijema uredPrijema, UredDostave uredDostave) {
        if(komanda.matches("PS")){
            try{
                String[] komande = komanda.split(" ");
                String registracija = komande[1];
                String status = komande[2];
                uredDostave.promijeniStatusVozila(registracija,status);
            }catch (Exception e){
                System.out.println("Neispravna komanda");
            }
            return true;
        }
        return rukujSljedeceg(komanda,uredPrijema,uredDostave);
    }
}
