package entity;

import Composite.Podrucje;
import Prototype.PrototypeDostava;
import visitor.KlijentiPosjetitelja;
import visitor.VisitorI;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Dostava implements KlijentiPosjetitelja, PrototypeDostava {

  public Dostava(){

  }
  public Dostava(Dostava dostava){
    super();
    for(var a : dostava.vratiSegmentiVoznje()){
      Segment kopiraniSegment = a.kloniraj();
      this.segmentiVoznje.add(kopiraniSegment);
    }
    this.status = dostava.status;
  }
  List<Segment> segmentiVoznje = new ArrayList<>();
  public boolean status=false;


  public void dodajSegmentVoznja(Segment segment){
    segmentiVoznje.add(segment);
  }

  public boolean provjeriPrazniSegment(){
    return segmentiVoznje.isEmpty();
  }

  public List<Segment> vratiSegmentiVoznje() {
    return segmentiVoznje;
  }

  public void postaviSegmenteVoznje(List<Segment> noviRedoslijed){
    segmentiVoznje=noviRedoslijed;
  }
  public void accept(VisitorI visitor){
    visitor.posjetiDostavu(this);
  }

  @Override
  public Dostava kloniraj() {
    return new Dostava(this);
  }
}
