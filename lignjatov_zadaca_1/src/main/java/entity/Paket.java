package entity;

import java.sql.Timestamp;
import interfaces.PaketPrototype;

public class Paket implements PaketPrototype {
  String oznaka;
  Timestamp vrijemePrijema;
  String posiljatelj;
  String primatelj;
  String vrstaPaketa;
  Float visina;
  Float sirina;
  Float duzina;
  Float tezina;
  String uslugaDostave;
  Float iznosPouzeca;

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
    }
  }

  @Override
  public String toString() {
    return "Oznaka: " + oznaka + " | " + " Vrijeme Prijema: " + vrijemePrijema;
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

  @Override
  public PaketPrototype kloniraj() {
    return new Paket(this);
  }

}
