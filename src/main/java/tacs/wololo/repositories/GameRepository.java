package tacs.wololo.repositories;

import org.springframework.stereotype.Repository;
import tacs.wololo.model.Game;

import java.util.*;
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

    public Optional<Game> getGame(Long gameId)
    {
        return games.values().stream().filter(g -> g.getId().equals(gameId)).findFirst();
    }

    public List<Game> getGames(String username)
    {
        return games.values().stream().filter(g -> g.getPlayers().contains(username))
                .collect(Collectors.toList());
    }

    public List<Game> getGamesByDates(Date dateMin, Date datemax)
    {
        return games.values().stream().filter(
                g -> g.getDate().after(dateMin) && g.getDate().before(datemax))
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
