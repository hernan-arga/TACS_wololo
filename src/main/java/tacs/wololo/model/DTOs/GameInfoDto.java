package tacs.wololo.model.DTOs;

import java.util.List;

public class GameInfoDto {

    Long gameId;
    List<String> playersUsernames;
    String provinceName;
    int municipalitiesCant;

    public List<String> getPlayersUsernames() {
        return playersUsernames;
    }

    public void setPlayersUsernames(List<String> playersUsernames) {
        this.playersUsernames = playersUsernames;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getMunicipalitiesCant() {
        return municipalitiesCant;
    }

    public void setMunicipalitiesCant(int municipalitiesCant) {
        this.municipalitiesCant = municipalitiesCant;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public GameInfoDto() {
    }

    public GameInfoDto(List<String> playersUsernames, String provinceName, int municipalitiesCant, Long gameId) {
        this.playersUsernames = playersUsernames;
        this.provinceName = provinceName;
        this.municipalitiesCant = municipalitiesCant;
        this.gameId = gameId;
    }
}
