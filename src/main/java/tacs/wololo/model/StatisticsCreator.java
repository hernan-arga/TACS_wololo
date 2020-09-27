package tacs.wololo.model;

import org.springframework.beans.factory.annotation.Autowired;
import tacs.wololo.repositories.GameRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticsCreator {
    @Autowired
    GameRepository gameRepository;

    public StatisticsCreator(GameRepository gameRepository)
    {
        this.gameRepository = gameRepository;
    }

    // cada posición de la lista de ints corresponde
    // a la cantidad de juegos que hay para el gameState.ordinal()
    // (CREATED.ordinal() = 0, IN_PROGRESS.ordinal = 1...)

    public List<Integer> getStatistics(List<Game> games)
    {
        List<Integer> cantForEachGameState = new ArrayList<>();

        Arrays.asList(GameState.values()).forEach(s ->
            cantForEachGameState.add(this.cantGamesHaveTheState(games, s))
        );

        return cantForEachGameState;
    }

    public List<Integer> getStatisticsByDates(Date dateMin, Date dateMax)
    {
        List<Game> games = gameRepository.getGamesByDates(dateMin, dateMax);
        return this.getStatistics(games);
    }

    public List<Integer> getIndividualStatistics(String username)
    {
        List<Game> userGames = gameRepository.getGames(username);
        return this.getStatistics(userGames);
    }

    private int cantGamesHaveTheState(List<Game> games, GameState state)
    {
        List<Game> gamesByState = games.stream().filter(g -> state.equals(g.getState())).collect(Collectors.toList());
        if (gamesByState == null)
            return 0;
        return gamesByState.size();
    }


}
