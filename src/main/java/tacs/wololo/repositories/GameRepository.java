package tacs.wololo.repositories;

import org.springframework.stereotype.Repository;
import tacs.wololo.model.Game;

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

    /*
    public List<Game> gamesitos() // TODO: SACAR
    {
        return this.games;
    }

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
