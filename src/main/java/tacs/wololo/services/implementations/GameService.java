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

    public Game createGame(GameInfoDto gameInfoDto)
    {
        List<String> playersUsernames = gameInfoDto.getPlayersUsernames();
        Collections.shuffle(playersUsernames);

        Queue<String> playerQueue = new LinkedList<String>(playersUsernames);

        Map map = new Map(gameInfoDto.getProvinceName());

        Game game = new Game(map, new Date(),
                playerQueue, GameState.CREATED, gameInfoDto.getMunicipalitiesCant(), new GeoRef(), new AsterAPI());
        //TODO hacer singleton las apis
        gameRepository.addGame(game.getId(), game);

        return game;
    }

    public List<Game> getGames(String username)
    {
        return gameRepository.getGames(username);
    }

    public Game getGame(String username, Long id)
    {
        Optional<Game> game = gameRepository.getGames(username).stream().filter(g -> g.getId().equals(id)).findFirst();

        if(game.isPresent())
            return game.get();

        throw new RuntimeException();
    }

    public void moveGauchos(String username, Long id, String sourceS, String targetS, int ammount)
    {
        Game game = getGame(username, id);

        Municipality source = game.getMunicipality(sourceS);

        Municipality target = game.getMunicipality(targetS);

        if(source == null || target == null)
            throw new RuntimeException("Municipio no existente");

        if(!(source.getOwner().contentEquals(username) && target.getOwner().contentEquals(username)))
            throw new RuntimeException("Municipio no valido");

        game.moveGauchos(ammount, source, target);
    }

    public List<Municipality> getMunicipalities(Long id, String username)
    {
        Game game =  gameRepository.getGamebyKey(id);

        if(!game.getPlayers().contains(username))
            throw new RuntimeException("Juego no encontrado");

        return game.getMunicipalities();
    }

    public void attackMunicipality(String username, Long id, String sourceS, String targetS)
    {
        Game game = getGame(username, id);

        Municipality source = game.getMunicipality(sourceS);

        Municipality target = game.getMunicipality(targetS);

        if(source == null || target == null)
            throw new RuntimeException("Municipio no existente");

        if(!(source.getOwner().contentEquals(username)) || target.getOwner().contentEquals(username))
            throw new RuntimeException("Municipio no valido");

        source.attackMunicipality(target, game.getMap());
    }

    public List<Movement> getMovementsBy(Long idGame, String username, String idMunicipality)
    {
        Game game = getGame(username, idGame);

        if(game == null)
            return null; // FIXME, puse esto pero no se si es una buena idea

        Municipality municipality = game.getMunicipality(idMunicipality);

        if(municipality == null)
            return null; // FIXME, puse esto pero no se si es una buena idea


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
