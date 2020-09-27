package tacs.wololo.model;

public class ElementScoreBoard {
    private String player;
    private int cantMunicipalitiesHavePlayer;

    public ElementScoreBoard(String player, int cantMunicipalitiesHavePlayer) {
        this.player = player;
        this.cantMunicipalitiesHavePlayer = cantMunicipalitiesHavePlayer;
    }

    public String getPlayer() {
        return player;
    }

    public int getCantMunicipalitiesHavePlayer() {
        return cantMunicipalitiesHavePlayer;
    }
}
