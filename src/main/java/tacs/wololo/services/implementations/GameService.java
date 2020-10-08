package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacs.wololo.model.APIs.AsterAPI;
import tacs.wololo.model.APIs.GeoRef;
import tacs.wololo.model.DTOs.GameInfoDto;
import tacs.wololo.model.Map;
import tacs.wololo.model.*;
import tacs.wololo.repositories.GameRepository;
import tacs.wololo.repositories.UserRepository;
import tacs.wololo.services.IGameService;

import java.io.IOException;
import java.util.*;

@Service
public class GameService implements IGameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    public GameService() {

    }

    public GameInfoDto createGameDto(Game game)
    {
        return new GameInfoDto((LinkedList) game.getPlayers(), game.getProvince(),
                game.getMunicipalityLimit(), game.getId());
    }

    public Game createGame(GameInfoDto gameInfoDto) throws IOException {
        List<String> playersUsernames = gameInfoDto.getPlayersUsernames();
        Collections.shuffle(playersUsernames);

        Queue<String> playerQueue = new LinkedList<String>(playersUsernames);

        Map map = new Map(gameInfoDto.getProvinceName());

        Game game = null;

        game = new Game(map, new Date(), playerQueue, GameState.CREATED,
                gameInfoDto.getMunicipalitiesCant(), new GeoRef(), new AsterAPI());


        //TODO hacer singleton las apis
        gameRepository.addGame(game.getId(), game);

        return game;
    }

    public List<Game> getGames(String username)
    {
        return gameRepository.getGamesFor(username);
    }

    public List<Game> getGamesByDate(Date from, Date to)
    {
        return gameRepository.getGamesByDates(from, to);
    }

    public Game getGame(String username, Long id)
    {
        Optional<Game> game = gameRepository.getGamesFor(username).stream().filter(g -> g.getId().equals(id)).findFirst();

        if(game.isPresent())
            return game.get();

        throw new RuntimeException("El juego no existe");
    }

    public Game moveGauchos(String username, Long id, String sourceS, String targetS, int ammount)
    {
        Game game = getGame(username, id);

        Municipality source = game.getMunicipality(sourceS);

        Municipality target = game.getMunicipality(targetS);

        if(source == null || target == null)
            throw new RuntimeException("Municipio no existente");

        if(!(source.getOwner().contentEquals(username) && target.getOwner().contentEquals(username)))
            throw new RuntimeException("Municipio no valido");

        game.moveGauchos(ammount, source, target);

        return game;
    }

    public List<Municipality> getMunicipalities(Long id, String username)
    {
        Game game =  gameRepository.getGamebyKey(id);

        if(!game.getPlayers().contains(username))
            throw new RuntimeException("Juego no encontrado");

        return game.getMunicipalities();
    }

    public Game attackMunicipality(String username, Long id, String sourceS, String targetS)
    {
        Game game = getGame(username, id);

        Municipality source = game.getMunicipality(sourceS);

        Municipality target = game.getMunicipality(targetS);

        if(source == null || target == null)
            throw new RuntimeException("Municipio no existente");

        if(!(source.getOwner().contentEquals(username)) || target.getOwner().contentEquals(username))
            throw new RuntimeException("Municipio no valido");

        source.attackMunicipality(target, game.getMap());

        return game;
    }

    public Game changeMode(String username, Long id, String sourceS) throws IOException {
        Game game = getGame(username, id);

        Municipality source = game.getMunicipality(sourceS);

        if(source == null)
            throw new RuntimeException("Municipio no existente");

        if(!source.getOwner().contentEquals(username))
            throw new RuntimeException("Municipio no valido");

        source.changeMode();

        return game;
    }

    public List<Movement> getMovementsBy(Long idGame, String username, String nameMunicipality)
    {
        Game game = getGame(username, idGame);

        Municipality municipality = game.getMunicipality(nameMunicipality);

        if(municipality == null)
            throw new RuntimeException("Municipio no existente");


        // TODO: SACAR. Dejo el comentario igual para testear, pero sacar para la entrega.
        // --------
        List<Movement> movements = new ArrayList<>();
        movements.add(new MovementDefend(10, "Chaco", true));
        movements.add(new MovementProduce(20, 5));

        municipality.setMovements(movements);
        // --------

        return municipality.getMovements();
    }
}
