package entity;

import java.sql.Timestamp;

public class Paket {
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

}
