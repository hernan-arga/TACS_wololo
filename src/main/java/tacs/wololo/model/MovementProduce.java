package tacs.wololo.model;

public class MovementProduce extends Movement{
    private int gauchosProduced;

    public MovementProduce(int finalGauchos, int gauchosProduced)
    {
        super(finalGauchos);
        this.gauchosProduced = gauchosProduced;
    }
}
