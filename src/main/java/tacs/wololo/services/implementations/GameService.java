package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacs.wololo.model.APIs.AsterAPI;
import tacs.wololo.model.APIs.GeoRef;
import tacs.wololo.model.DTOs.GameInfoDto;
import tacs.wololo.model.Map;
import tacs.wololo.model.*;
import tacs.wololo.model.TimerTasks.NotifyTurn;
import tacs.wololo.repositories.GamesRepository;
import tacs.wololo.repositories.UserRepository;
import tacs.wololo.services.IGameService;

import javax.persistence.Transient;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService implements IGameService {

    Timer t = null;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    GmailService gmailService;

    @Autowired
    GamesRepository gameRepository;

    public GameService() {

    }

    public GameInfoDto createGameDto(Game game)
    {
        return new GameInfoDto(game.getPlayers(), game.getProvince(),
                game.getMunicipalityLimit(), game.getStyle().ordinal(), game.getId());
    }

    public Game createGame(GameInfoDto gameInfoDto) throws IOException
    {
        List<String> playersUsernames = gameInfoDto.getPlayersUsernames();
        Collections.shuffle(playersUsernames);

        //Queue<String> playerQueue = new LinkedList<String>(playersUsernames);

        Map map = new Map(gameInfoDto.getProvinceName());

        Game game = null;

        game = new Game(map, new Date(), playersUsernames, GameState.EN_PROGRESO,
                gameInfoDto.getMunicipalitiesCant(), new GeoRef(), new AsterAPI(), gameInfoDto.getGameStyle());

        gameRepository.save(game);

        return game;
    }

    public List<Game> getGames(String username)
    {
        List<Game> games = gameRepository.findAll();
        return games.stream().filter(g -> g.getPlayers().contains(username)).collect(Collectors.toList());
    }

    public List<Game> getGamesByDate(Date from, Date to)
    {
        return gameRepository.getAllByDateBetween(from, to);
    }

    public Game getGame(String username, Long id)
    {
        Optional<Game> game = getGames(username).stream().filter(g -> g.getId().equals(id)).findFirst();

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

        game.changeTurn();
        this.notifyPlayer(game.getPlayers().get(0));

        gameRepository.save(game);

        return game;
    }

    public List<Municipality> getMunicipalities(Long id, String username)
    {
        Game game =  gameRepository.getOne(id);

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

        game.changeTurn();

        if(game.getPlayers().size()<=1){
            changeState(game, GameState.FINALIZADO);
        }

        this.notifyPlayer(game.getPlayers().get(0));

        gameRepository.save(game);

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

        game.changeTurn();
        this.notifyPlayer(game.getPlayers().get(0));

        gameRepository.save(game);

        return game;
    }

    public Game changeTurn(String username, Long id)
    {
        Game game = getGame(username, id);

        game.changeTurn();
        this.notifyPlayer(game.getPlayers().get(0));

        gameRepository.save(game);

        return game;
    }

    private void notifyPlayer(String username){
        if (this.t != null){
            this.t.cancel();
        }
        this.t = new Timer();

        String email = userRepository.findByUsername(username).get().getEmail();
        NotifyTurn notifyTurn = new NotifyTurn(email);


        //Timer t = new Timer();
        this.t.schedule(notifyTurn,60000);
    }

    public Game surrender(String username, Long id)
    {
        Game game = getGame(username, id);

        game.surrender(username);

        if(game.getPlayers().size()<=1){
            changeState(game, GameState.CANCELADO);
        }


        gameRepository.save(game);

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

    public void changeState(Game game, GameState state){
        game.setState(state);
        gameRepository.save(game);
    }

}
