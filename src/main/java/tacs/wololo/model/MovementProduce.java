package tacs.wololo.model;

public class MovementProduce extends Movement{
    private int gauchosProduced;

    public MovementProduce(int finalGauchos, int gauchosProduced)
    {
        super(finalGauchos);
        this.gauchosProduced = gauchosProduced;
    }

    public int getGauchosProduced() {
        return gauchosProduced;
    }

    public void setGauchosProduced(int gauchosProduced) {
        this.gauchosProduced = gauchosProduced;
    }
}
