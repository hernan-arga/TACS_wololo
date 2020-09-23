
package tacs.wololo.model.APIs.GeoData;


public class Centroide {

    public Double lat;

    public Centroide() {
    }

    public Double lon;

    public Centroide(Double lat, Double lon)
    {
        this.lat = lat;
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
