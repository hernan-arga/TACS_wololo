package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tacs.wololo.model.*;
import tacs.wololo.model.DTOs.GameInfoDto;
import tacs.wololo.model.DTOs.GameStatusDto;
import tacs.wololo.model.DTOs.MunicipalityDto;
import tacs.wololo.model.DTOs.ProvinceInfoDto;
import tacs.wololo.model.Map;
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
                    .stream().map(Player::getUsername).collect(Collectors.toList());

            gameInfoDtos.add(new GameInfoDto(usernames, game.getProvince(), game.getMunicipalityLimit()
                    , game.getId()));
        }

        return gameInfoDtos;
    }

    public GameStatusDto getGame(Long gameId, String username)
    {
        Game game = gameRepository.getGame(gameId, username);

        List<String> playersNames = new ArrayList<>();

        List<MunicipalityDto> municipalityDtos = new ArrayList<>();

        for(Player player : game.getPlayers())
        {
            playersNames.add(player.getUsername());
        }

        for(Municipality municipality : game.getMunicipalities())
        {
            municipalityDtos.add(new MunicipalityDto(municipality.getCentroide(),
                    municipality.getId(), municipality.getNombre(), municipality.getProvincia()
            ,municipality.getGauchos(), municipality.getHeight(), municipality.getCoefDist()
            ,municipality.getCoefAlt(), municipality.getMode(), municipality.getOwner().getUsername(),
                    municipality.getMovements()));
        }

        return new GameStatusDto(game.getId(), game.getMap(), game.getProvince(), game.getDate()
        , playersNames, game.getState(), municipalityDtos, game.getMunicipalityLimit());
    }

    public int processAttack(String username, Long gameId, String attackMun, String defenceMun)
    {
        Game game = gameRepository.getGame(gameId, username);

        System.out.println(game);

        Municipality attack = game.getMunicipality(attackMun);
        Municipality defence = game.getMunicipality(defenceMun);

        System.out.println(attack);
        System.out.println(defence);

        if(attack == null || defence == null)
            return -1;

        if(attack.attackMunicipality(defence, game.getMap()))
            return 1;

        return 0;
    }
}
