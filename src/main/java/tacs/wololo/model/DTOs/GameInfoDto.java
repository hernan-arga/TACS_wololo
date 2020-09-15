package tacs.wololo.model.DTOs;

import java.util.List;

public class GameInfoDto {

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


    public GameInfoDto() {
    }

    public GameInfoDto(List<String> playersUsernames, String provinceName, int municipalitiesCant) {
        this.playersUsernames = playersUsernames;
        this.provinceName = provinceName;
        this.municipalitiesCant = municipalitiesCant;
    }
}
