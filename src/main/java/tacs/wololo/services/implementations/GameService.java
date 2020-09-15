package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacs.wololo.model.DTOs.GameInfoDto;
import tacs.wololo.model.DTOs.ProvinceInfoDto;
import tacs.wololo.repositories.GameRepository;
import tacs.wololo.repositories.ProvinceRepository;

import java.util.List;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    public GameService() {

    }

    public List<GameInfoDto> getGamesListByUsername(String username) {
        return gameRepository.getGamesInfoByUserName(username);
    }

    public void createGame(GameInfoDto gameInfoDto){
        gameRepository.addGame(gameInfoDto);
    }
}
