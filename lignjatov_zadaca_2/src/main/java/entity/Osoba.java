package entity;

import observer.Subscriber;

public class Osoba implements Subscriber {
    String ime;
    int grad;
    int ulica;
    int kbr;
    boolean statusPoruke;

    @Override
    public void notificiraj(String context) {
        System.out.println(ime + "primilo poruku: " + context);
    }
}
