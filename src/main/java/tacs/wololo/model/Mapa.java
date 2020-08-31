package tacs.wololo.model;

import java.util.List;

public class Mapa {
    private Provincia provincia;
    private double maxAltura;
    private double minAltura;
    private double distMax;

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
