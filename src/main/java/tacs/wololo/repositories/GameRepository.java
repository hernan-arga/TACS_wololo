package tacs.wololo.repositories;

import org.springframework.stereotype.Repository;
import tacs.wololo.model.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GameRepository {

    private List<Game> games = new ArrayList<Game>();

    public void setGame(List<Game> games) {
        this.games = games;
    }

    public void addGame(Game game) {
        games.add(game);
    }

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
}
