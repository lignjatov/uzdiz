package entity;

import Prototype.PrototypePaket;
import observer.Subscriber;
import singleton.DataRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Paket implements PrototypePaket {
  String oznaka;
  Timestamp vrijemePrijema;
  Timestamp vrijemePreuzimanja;
  String posiljatelj;
  String primatelj;
  String vrstaPaketa;
  Float visina;
  Float sirina;
  Float duzina;
  Float tezina;
  String uslugaDostave;
  Float iznosPouzeca;
  public boolean status=false;

  List<Subscriber> listaOsobaSubscriber = new ArrayList<Subscriber>();

  public Paket(Paket target) {
    super();
    if (target != null) {
      this.oznaka = target.oznaka;
      this.vrijemePrijema = target.vrijemePrijema;
      this.posiljatelj = target.posiljatelj;
      this.primatelj = target.primatelj;
      this.vrstaPaketa = target.vrstaPaketa;
      this.visina = target.visina;
      this.sirina = target.sirina;
      this.duzina = target.duzina;
      this.tezina = target.tezina;
      this.uslugaDostave = target.uslugaDostave;
      this.iznosPouzeca = target.iznosPouzeca;
      this.listaOsobaSubscriber = target.listaOsobaSubscriber;
    }
  }

  public Timestamp getVrijemePreuzimanja() {
    return vrijemePreuzimanja;
  }

  public void setVrijemePreuzimanja(Timestamp vrijemePreuzimanja) {
    this.vrijemePreuzimanja = vrijemePreuzimanja;
  }

  public Paket() {}

  public String getOznaka() {
    return oznaka;
  }

  public void setOznaka(String oznaka) {
    this.oznaka = oznaka;
  }

  public Timestamp getVrijemePrijema() {
    return vrijemePrijema;
  }

  public void setVrijemePrijema(Timestamp vrijemePrijema) {
    this.vrijemePrijema = vrijemePrijema;
  }

  public String getPosiljatelj() {
    return posiljatelj;
  }

  public void setPosiljatelj(String posiljatelj) {
    this.posiljatelj = posiljatelj;
  }

  public String getPrimatelj() {
    return primatelj;
  }

  public void setPrimatelj(String primatelj) {
    this.primatelj = primatelj;
  }

  public String getVrstaPaketa() {
    return vrstaPaketa;
  }

  public void setVrstaPaketa(String vrstaPaketa) {
    this.vrstaPaketa = vrstaPaketa;
  }

  public Float getVisina() {
    return visina;
  }

  public void setVisina(Float visina) {
    this.visina = visina;
  }

  public Float getSirina() {
    return sirina;
  }

  public void setSirina(Float sirina) {
    this.sirina = sirina;
  }

  public Float getDuzina() {
    return duzina;
  }

  public void setDuzina(Float duzina) {
    this.duzina = duzina;
  }

  public Float getTezina() {
    return tezina;
  }

  public void setTezina(Float tezina) {
    this.tezina = tezina;
  }

  public String getUslugaDostave() {
    return uslugaDostave;
  }

  public void setUslugaDostave(String uslugaDostave) {
    this.uslugaDostave = uslugaDostave;
  }

  public Float getIznosPouzeca() {
    return iznosPouzeca;
  }

  public void setIznosPouzeca(Float iznosPouzeca) {
    this.iznosPouzeca = iznosPouzeca;
  }

  public void dodajPretplatnika(Subscriber osoba){
    if(!listaOsobaSubscriber.contains(osoba)){
      listaOsobaSubscriber.add(osoba);
    }
  }

  public void makniPretplatnika(Osoba osoba){
    boolean osobaPronadena = false;
    if(listaOsobaSubscriber.contains(osoba)){
      osobaPronadena=true;
      listaOsobaSubscriber.remove(osoba);
    }
    if(osobaPronadena){
      //System.out.println("Osobi " + osoba.vratiIme() + "je promijenjen status primanja obavijesti za paket "+this.getOznaka());
    }
  }

  @Override
  public String toString() {
    return this.oznaka;
  }

  public void ispisiPretplatnike(){
    for (Subscriber osoba : listaOsobaSubscriber){
      System.out.println("Osobi " + osoba.toString());
    }
  }

  public void posaljiPorukuPretplatnicima(String poruka){
    for (var osoba : listaOsobaSubscriber){
       osoba.notificiraj(poruka);
    }
  }

  @Override
  public Paket kloniraj() {
    return new Paket(this);
  }
}
