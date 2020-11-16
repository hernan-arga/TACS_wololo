package tacs.wololo.model;

import org.springframework.beans.factory.annotation.Autowired;
import tacs.wololo.repositories.GameRepository;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsCreator {
    @Autowired
    GameRepository gameRepository;

    public StatisticsCreator(GameRepository gameRepository)
    {
        this.gameRepository = gameRepository;
    }

    public HashMap<String, Integer> getStatistics(List<Game> games)
    {
        HashMap<String, Integer> statistics = new HashMap <String, Integer> ();
        final int[] cantStatesGames = {0};
        Arrays.asList(GameState.values()).forEach(s ->{
                statistics.put(s.name(), this.cantGamesHaveTheState(games, s));
                cantStatesGames[0] +=this.cantGamesHaveTheState(games, s);
        }
        );

        statistics.put(GameState.CREADO.toString(), cantStatesGames[0]);

        return statistics;
    }

    public HashMap<String, Integer> getStatisticsByDates(Date dateMin, Date dateMax)
    {
        List<Game> games = gameRepository.getAllByDateBetween(dateMin, dateMax);

        return this.getStatistics(games);
    }

    public HashMap<String, Integer> getIndividualStatistics(String username)
    {
        List<Game> userGames = gameRepository.getGamesFor(username);

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
