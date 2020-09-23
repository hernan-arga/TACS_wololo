package tacs.wololo.model.DTOs;

import tacs.wololo.model.APIs.GeoData.Centroide;
import tacs.wololo.model.Movement;
import tacs.wololo.model.MunicipalityMode;

import java.util.ArrayList;
import java.util.List;

public class MunicipalityDto
{
    public Centroide centroide;

    public String id;

    public String nombre;

    private int gauchos;

    private double height;

    private MunicipalityMode mode;

    private String owner;

    private List<Movement> movements = new ArrayList<>();

    public MunicipalityDto() {
    }

    public MunicipalityDto(Centroide centroide, String id, String nombre, int gauchos, double height,
                 MunicipalityMode mode, String owner, List<Movement> movements)
    {
        this.centroide = centroide;
        this.id = id;
        this.nombre = nombre;
        this.gauchos = gauchos;
        this.height = height;
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

    public int getGauchos() {
        return gauchos;
    }

    public double getHeight() {
        return height;
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
