package tacs.wololo.model.DTOs;

import tacs.wololo.model.APIs.GeoData.Centroide;
import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.model.Movement;
import tacs.wololo.model.MunicipalityMode;
import tacs.wololo.model.Player;

import java.util.ArrayList;
import java.util.List;

public class MunicipalityDto {
    public Centroide centroide;
    public String id;
    public String nombre;
    public Provincia provincia;

    private int gauchos;

    private double height;

    private double coefDist = 2;

    private double coefAlt = 2;

    private MunicipalityMode mode;

    private String owner;

    private List<Movement> movements = new ArrayList<>();

    public MunicipalityDto() {
    }

    public MunicipalityDto(Centroide centroide, String id, String nombre, Provincia provincia, int gauchos, double height, double coefDist, double coefAlt, MunicipalityMode mode, String owner, List<Movement> movements) {
        this.centroide = centroide;
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
        this.gauchos = gauchos;
        this.height = height;
        this.coefDist = coefDist;
        this.coefAlt = coefAlt;
        this.mode = mode;
        this.owner = owner;
        this.movements = movements;
    }

    public Centroide getCentroide() {
        return centroide;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public int getGauchos() {
        return gauchos;
    }

    public double getHeight() {
        return height;
    }

    public double getCoefDist() {
        return coefDist;
    }

    public double getCoefAlt() {
        return coefAlt;
    }

    public MunicipalityMode getMode() {
        return mode;
    }

    public String getOwner() {
        return owner;
    }

    public List<Movement> getMovements() {
        return movements;
    }
}
