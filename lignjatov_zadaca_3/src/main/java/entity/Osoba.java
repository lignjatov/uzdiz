package entity;

import observer.Subscriber;

public class Osoba implements Subscriber {
    String ime;
    int grad;
    int ulica;
    int kbr;
    boolean statusPoruke;

    public String vratiIme() {
        return ime;
    }

    public void postaviIme(String ime) {
        this.ime = ime;
    }

    public int vratiGrad() {
        return grad;
    }

    public void postaviGrad(int grad) {
        this.grad = grad;
    }

    public int vratiUlica() {
        return ulica;
    }

    public void postaviUlica(int ulica) {
        this.ulica = ulica;
    }

    public int vratiKbr() {
        return kbr;
    }

    public void postaviKbr(int kbr) {
        this.kbr = kbr;
    }

    public boolean isStatusPoruke() {
        return statusPoruke;
    }

    public void postaviStatusPoruke(boolean statusPoruke) {
        this.statusPoruke = statusPoruke;
    }

    @Override
    public void notificiraj(String context) {
        System.out.println(ime + " primio poruku: " + context);
    }
}
