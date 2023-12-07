package Composite;

public class Ulica implements Kutija {
    int id;
    String naziv;
    Float gps_lat_1;
    Float gps_lon_1;
    Float gps_lat_2;
    Float gps_lon_2;
    int najveciKucniBroj;

    public int vratiId() {
        return id;
    }

    public void postaviId(int id) {
        this.id = id;
    }

    public String vratiNaziv() {
        return naziv;
    }

    public void postaviNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Float vratiGps_lat_1() {
        return gps_lat_1;
    }

    public void postaviGps_lat_1(Float gps_lat_1) {
        this.gps_lat_1 = gps_lat_1;
    }

    public Float vratiGps_lon_1() {
        return gps_lon_1;
    }

    public void postaviGps_lon_1(Float gps_lon_1) {
        this.gps_lon_1 = gps_lon_1;
    }

    public Float vratiGps_lat_2() {
        return gps_lat_2;
    }

    public void postaviGps_lat_2(Float gps_lat_2) {
        this.gps_lat_2 = gps_lat_2;
    }

    public Float vratiGps_lon_2() {
        return gps_lon_2;
    }

    public void postaviGps_lon_2(Float gps_lon_2) {
        this.gps_lon_2 = gps_lon_2;
    }

    public int vratiNajveciKucniBroj() {
        return najveciKucniBroj;
    }

    public void postaviNajveciKucniBroj(int najveciKucniBroj) {
        this.najveciKucniBroj = najveciKucniBroj;
    }

    @Override
    public void ispisiText() {
        System.out.println("--------Ulica je "+naziv);
    }
}
