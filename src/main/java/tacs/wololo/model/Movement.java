package tacs.wololo.model;

public abstract class Movement {
    private Long id;
    private int finalGauchos;

    public Movement(int finalGauchos)
    {
        this.finalGauchos = finalGauchos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFinalGauchos() {
        return finalGauchos;
    }

    public void setFinalGauchos(int finalGauchos) {
        this.finalGauchos = finalGauchos;
    }
}
