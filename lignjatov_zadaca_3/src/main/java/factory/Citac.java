package factory;

import java.util.List;


//Product interface
public interface Citac<T> {
    List<T> ucitajPodatke(String nazivDatoteke);
}
