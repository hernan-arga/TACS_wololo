package tacs.wololo.repositories;

import org.springframework.stereotype.Repository;
import tacs.wololo.model.Game;
import tacs.wololo.model.GameState;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class GameRepository {

    Map<Long, Game> games = new HashMap<>();

    public void addGame(Long key, Game game) {
        games.put(key, game);
    }

    public Game getGamebyKey(Long key)
    {
        return games.get(key);
    }

    public List<Game> getGames(String username)
    {
        return games.values().stream().filter(g -> g.getPlayers().contains(username))
                .collect(Collectors.toList());
    }

    // proveer estadísticas de cantidad de partidas creadas, en curso, terminadas y canceladas
    // permitiendo seleccionar el rango de fechas

    public List<Game> getGamesByState(GameState state, Date dateMin, Date datemax)
    {
        return games.values().stream().filter(
                g -> g.getState().equals(state) && g.getDate().after(dateMin) && g.getDate().before(datemax))
                .collect(Collectors.toList());
    }

    /*

    public List<Game> getGames(String username)
    {
        List<Game> gamesList = games.stream().filter(g -> g.getPlayers().stream()
                .anyMatch(u -> u.getUsername().contentEquals(username))).collect(Collectors.toList());

        return gamesList;
    }

    public Game getGame(Long id, String username)
    {
        return getGames(username).stream().filter(g -> g.getId().equals(id)).findFirst().orElse(null);
    }
    */
}
