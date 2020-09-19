package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tacs.wololo.model.DTOs.GameInfoDto;
import tacs.wololo.model.DTOs.ProvinceInfoDto;
import tacs.wololo.model.Game;
import tacs.wololo.model.GameState;
import tacs.wololo.model.Map;
import tacs.wololo.model.Player;
import tacs.wololo.repositories.GameRepository;
import tacs.wololo.repositories.ProvinceRepository;
import tacs.wololo.repositories.UserRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    public GameService() {

    }

    public void createGame(GameInfoDto gameInfoDto)
    {
        List<String> playersUsernames = gameInfoDto.getPlayersUsernames();
        Collections.shuffle(playersUsernames);

        Queue<Player> players = new LinkedList<>();

        for(String username : playersUsernames)
        {
            Player player = new Player(username);
            players.add(player);
        }

        Game game = new Game(new Map(), gameInfoDto.getProvinceName(), new Date(),
                players, GameState.CREATED, gameInfoDto.getMunicipalitiesCant());

        gameRepository.addGame(game);
    }

    public List<GameInfoDto> getGames(String username)
    {
        List<Game> games = gameRepository.getGames(username);

        if(games.isEmpty())
            return new ArrayList<>();

        List<GameInfoDto> gameInfoDtos = new ArrayList<>();

        for(Game game : games)
        {
            List<String> usernames = new ArrayList<Player>(game.getPlayers())
                    .stream().map(p -> p.getUsername()).collect(Collectors.toList());

            gameInfoDtos.add(new GameInfoDto(usernames, game.getProvince(), game.getMunicipalityLimit()));
        }

        return gameInfoDtos;
    }

    //todo: get game by id
}
