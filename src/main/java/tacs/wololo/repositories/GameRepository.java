package tacs.wololo.repositories;

import org.springframework.stereotype.Repository;
import tacs.wololo.model.Game;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class GamesRepositoryImpl implements GamesRepositoryCustom {

    Map<Long, Game> games = new HashMap<>();

    public void addGame(Long key, Game game) {
        games.put(key, game);
    }

    public Game getGamebyKey(Long key)
    {
        return games.get(key);
    }

    public List<Game> getGames()
    {
        return new ArrayList<>(this.games.values());
    }

    public Optional<Game> getGame(Long gameId)
    {
        return games.values().stream().filter(g -> g.getId().equals(gameId)).findFirst();
    }

    public List<Game> getGamesFor(String username)
    {
        return games.values().stream().filter(g -> g.getPlayers().contains(username)).collect(Collectors.toList());
    }

    public List<Game> getAllByDateBetween(Date dateMin, Date datemax)
    {
        System.out.println("1--------");
        System.out.println(games);
        System.out.println(games.values());
        return games.values().stream().filter(
                g -> {System.out.println(g); return g.getDate().after(dateMin) && g.getDate().before(datemax);})
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
