package entity;

import Composite.Podrucje;
import visitor.KlijentiPosjetitelja;
import visitor.VisitorI;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Dostava implements KlijentiPosjetitelja {

  List<Segment> segmentiVoznje = new ArrayList<>();
  Podrucje podrucjeDostave;
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
}
