package visitor;

import entity.Dostava;
import entity.Segment;
import implementation.Vozilo;

public interface VisitorI {
    void posjetiVozilo(Vozilo vozilo);
    void posjetiDostavu(Dostava dostava);
    void posjetiSegment(Segment segment);
}
