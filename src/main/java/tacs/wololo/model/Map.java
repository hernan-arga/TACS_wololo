package tacs.wololo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embeddable;

@Embeddable
public class Map
{
    @JsonIgnore
    private String province;
    private double maxHeight;
    private double minHeight;
    private double distMax;
    private double distMin;
    private double latMax;
    private double latMin;
    private double lonMax;
    private double lonMin;

    public Map() {
    }

    public Map(String province) {
        this.province = province;
    }

    public double getLatMax() {
        return latMax;
    }

    public void setLatMax(double latMax) {
        this.latMax = latMax;
    }

    public double getLatMin() {
        return latMin;
    }

    public void setLatMin(double latMin) {
        this.latMin = latMin;
    }

    public double getLonMax() {
        return lonMax;
    }

    public void setLonMax(double lonMax) {
        this.lonMax = lonMax;
    }

    public double getLonMin() {
        return lonMin;
    }

    public void setLonMin(double lonMin) {
        this.lonMin = lonMin;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public double getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(double minHeight) {
        this.minHeight = minHeight;
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

    public String getProvince() {
        return province;
    }


}
