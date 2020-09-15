package tacs.wololo.repositories;

import jdk.nashorn.internal.runtime.Debug;
import org.springframework.stereotype.Repository;
import tacs.wololo.model.DTOs.GameInfoDto;
import tacs.wololo.model.DTOs.ProvinceInfoDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GameRepository {

    private List<GameInfoDto> gamesInfo =  new ArrayList<GameInfoDto>();

    public void setGamesInfo(List<GameInfoDto> gamesInfo)
    {
        this.gamesInfo = gamesInfo;
    }

    public void addGame(GameInfoDto gameInfoDto){
        gamesInfo.add(gameInfoDto);
    }

    public List<GameInfoDto> getGamesInfoByUserName(String username) {
        return gamesInfo.stream().filter(gameInfoDto -> gameBelongsToUser(gameInfoDto, username)).collect(Collectors.toList());
    }

    private boolean gameBelongsToUser(GameInfoDto gameInfoDto, String username){
        return gameInfoDto.getPlayersUsernames().stream().anyMatch(playerUsername -> playerUsername.equals(username));
    }
}
