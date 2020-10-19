package tacs.wololo.model.DTOs;

import tacs.wololo.model.GameStyle;

import java.util.List;

public class GameInfoDto {

    Long gameId;
    List<String> playersUsernames;
    String provinceName;
    int municipalitiesCant;
    int styleIndex;
    GameStyle gameStyle;

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

    public GameStyle getGameStyle() {
        return gameStyle;
    }

    public void setGameStyle(GameStyle gameStyle) {
        this.gameStyle = gameStyle;
    }

    public void setStyleIndex(int style) {
        this.styleIndex = style;
        this.gameStyle = GameStyle.values()[styleIndex];
    }

    public int getStyleIndex() {
        return styleIndex;
    }

    public GameInfoDto() {
    }

    public GameInfoDto(List<String> playersUsernames, String provinceName, int municipalitiesCant,
                       int styleIndex, Long gameId) {
        this.playersUsernames = playersUsernames;
        this.provinceName = provinceName;
        this.municipalitiesCant = municipalitiesCant;
        this.gameId = gameId;
        this.styleIndex = styleIndex;
        this.gameStyle = GameStyle.values()[styleIndex];

    }
}
