package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tacs.wololo.model.*;
import tacs.wololo.model.DTOs.GameInfoDto;
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
        System.out.println("Cambia algo");
        List<Game> games = gameRepository.getGames(username);

        if(games.isEmpty())
        {
            System.out.println("Estoy vacio");
            return new ArrayList<>();
        }


        List<GameInfoDto> gameInfoDtos = new ArrayList<>();

        for(Game game : games)
        {
            List<String> usernames = new ArrayList<Player>(game.getPlayers())
                    .stream().map(Player::getUsername).collect(Collectors.toList());

            for(Player player : game.getPlayers())
            {
                System.out.println("Hola");
                System.out.println(player.getMunicipalities());
            }

            gameInfoDtos.add(new GameInfoDto(usernames, game.getProvince(), game.getMunicipalityLimit()
                    , game.getId()));
        }

        return gameInfoDtos;
    }

    public Game getGame(Long gameId, String username)
    {
        return gameRepository.getGame(gameId, username);
    }

    public List<Movement> getMovementsBy(Long idGame, String username, String idMunicipality)
    {
        Game game = gameRepository.getGame(idGame, username);
        if(game == null)
            return null; // FIXME, puse esto pero no se si es una buena idea

        Municipality municipality = game.getMunicipality(idMunicipality);

        if(municipality == null)
            return null; // FIXME, puse esto pero no se si es una buena idea


        // TODO: SACAR. Dejo el comentario igual para testear, pero sacar para la entrega.
        // --------
        /*
        List<Movement> movements = new ArrayList<>();
        movements.add(new MovementDefend(10, "Chaco", true));
        movements.add(new MovementProduce(20, 5));

        municipality.setMovements(movements);*/
        // --------

        return municipality.getMovements();
    }

}
