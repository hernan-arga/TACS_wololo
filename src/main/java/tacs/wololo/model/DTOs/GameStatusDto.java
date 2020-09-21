package tacs.wololo.model.DTOs;

import tacs.wololo.model.GameState;
import tacs.wololo.model.Map;
import tacs.wololo.model.Municipality;
import tacs.wololo.model.Player;

import java.util.Date;
import java.util.List;
import java.util.Queue;

public class GameStatusDto
{
    Long id;
    Map map;
    String province;
    Date date;
    List<String> players;
    GameState state;
    List<MunicipalityDto> municipalities;
    int municipalityLimit;

    public GameStatusDto() {
    }

    public GameStatusDto(Long id, Map map, String province, Date date, List<String> players, GameState state, List<MunicipalityDto> municipalities, int municipalityLimit) {
        this.id = id;
        this.map = map;
        this.province = province;
        this.date = date;
        this.players = players;
        this.state = state;
        this.municipalities = municipalities;
        this.municipalityLimit = municipalityLimit;
    }

    public Long getId() {
        return id;
    }

    public Map getMap() {
        return map;
    }

    public String getProvince() {
        return province;
    }

    public Date getDate() {
        return date;
    }

    public List<String> getPlayers() {
        return players;
    }

    public GameState getState() {
        return state;
    }

    public List<MunicipalityDto> getMunicipalities() {
        return municipalities;
    }

    public int getMunicipalityLimit() {
        return municipalityLimit;
    }
}
