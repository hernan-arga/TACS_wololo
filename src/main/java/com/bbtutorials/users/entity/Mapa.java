package com.bbtutorials.users.entity;

import java.util.List;

public class Mapa {
    private List<Municipio> municipios;
    private double maxAltura;
    private double minAltura;
    private double distMax;

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public double getMaxAltura() {
        return maxAltura;
    }

    public void setMaxAltura(double maxAltura) {
        this.maxAltura = maxAltura;
    }

    public double getMinAltura() {
        return minAltura;
    }

    public void setMinAltura(double minAltura) {
        this.minAltura = minAltura;
    }

    public double getDistMax() {
        return distMax;
    }

    public void setDistMax(double distMax) {
        this.distMax = distMax;
    }

    public double getDistMin() {
        return distMin;
    }

    public void setDistMin(double distMin) {
        this.distMin = distMin;
    }

    private double distMin;

}
