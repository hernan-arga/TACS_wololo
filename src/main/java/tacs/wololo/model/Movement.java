package tacs.wololo.model;

public abstract class Movement {
    private Long id;
    private int finalGauchos;

    public Movement(int finalGauchos)
    {
        this.finalGauchos = finalGauchos;
    }
}
