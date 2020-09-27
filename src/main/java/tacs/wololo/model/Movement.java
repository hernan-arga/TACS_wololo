package tacs.wololo.model;

public abstract class Movement {
    private int finalGauchos;

    public Movement(int finalGauchos)
    {
        this.finalGauchos = finalGauchos;
    }

    public int getFinalGauchos() {
        return finalGauchos;
    }

    public void setFinalGauchos(int finalGauchos) {
        this.finalGauchos = finalGauchos;
    }
}
