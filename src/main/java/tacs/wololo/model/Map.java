package tacs.wololo.model;

public class Map
{
    private Province province;
    private double maxHeight;
    private double minHeight;
    private double distMax;
    private double distMin;

    public Map() {
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

    public Province getProvince() {
        return province;
    }


}
