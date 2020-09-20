package tacs.wololo.model;

public class MovementDefend extends Movement{
    private String attackingMunicipality;
    private boolean winTheBattle;

    public MovementDefend(int finalGauchos, String attackingMunicipality, boolean winTheBattle)
    {
        super(finalGauchos);
        this.attackingMunicipality = attackingMunicipality;
        this.winTheBattle = winTheBattle;
    }
}
