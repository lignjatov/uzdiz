package interfaces;

import java.sql.Timestamp;
import entity.Paket;

public interface PaketBuilderI {
  Paket build();

  PaketBuilderI setOznaka(String oznaka);

  PaketBuilderI setVrijemePrijema(Timestamp vrijemePrijema);

  PaketBuilderI setPosiljatelj(String posiljatelj);

  PaketBuilderI setPrimatelj(String primatelj);

  PaketBuilderI setVrstaPaketa(String vrstaPaketa);

  PaketBuilderI setVisina(Float visina);

  PaketBuilderI setSirina(Float sirina);

  PaketBuilderI setDuzina(Float duzina);

  PaketBuilderI setTezina(Float tezina);

  PaketBuilderI setUslugaDostave(String uslugaDostave);

  PaketBuilderI setIznosPouzeca(Float iznosPouzeca);
}
