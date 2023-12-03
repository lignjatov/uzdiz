package implementation;

import java.sql.Timestamp;
import entity.Paket;
import interfaces.PaketBuilderI;

public class PaketBuilder implements PaketBuilderI {
  Paket paket;

  public PaketBuilder() {
    paket = new Paket();
  }

  @Override
  public Paket build() {
    return paket;
  }

  @Override
  public PaketBuilderI setOznaka(String oznaka) {
    paket.setOznaka(oznaka);
    return this;
  }

  @Override
  public PaketBuilderI setVrijemePrijema(Timestamp vrijemePrijema) {
    paket.setVrijemePrijema(vrijemePrijema);
    return this;
  }

  @Override
  public PaketBuilderI setPosiljatelj(String posiljatelj) {
    paket.setPosiljatelj(posiljatelj);
    return this;
  }

  @Override
  public PaketBuilderI setPrimatelj(String primatelj) {
    paket.setPrimatelj(primatelj);
    return this;
  }

  @Override
  public PaketBuilderI setVrstaPaketa(String vrstaPaketa) {
    paket.setVrstaPaketa(vrstaPaketa);
    return this;
  }

  @Override
  public PaketBuilderI setVisina(Float visina) {
    paket.setVisina(visina);
    return this;
  }

  @Override
  public PaketBuilderI setSirina(Float sirina) {
    paket.setSirina(sirina);
    return this;
  }

  @Override
  public PaketBuilderI setDuzina(Float duzina) {
    paket.setDuzina(duzina);
    return this;
  }

  @Override
  public PaketBuilderI setTezina(Float tezina) {
    paket.setTezina(tezina);
    return this;
  }

  @Override
  public PaketBuilderI setUslugaDostave(String uslugaDostave) {
    paket.setUslugaDostave(uslugaDostave);
    return this;
  }

  @Override
  public PaketBuilderI setIznosPouzeca(Float iznosPouzeca) {
    paket.setIznosPouzeca(iznosPouzeca);
    return this;
  }

}
