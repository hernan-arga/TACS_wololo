package tacs.wololo.model;

import org.springframework.beans.factory.annotation.Autowired;
import tacs.wololo.repositories.GameRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Esta clase crea estadísticas de los juegos en base a la cantidad de juegos que hay para cada tipo
 * de estados de los juegos existentes
 */
public class StatisticsCreator {
    @Autowired
    GameRepository gameRepository;

    public StatisticsCreator(GameRepository gameRepository)
    {
        this.gameRepository = gameRepository;
    }

    /**
     * Método que devuelve las estadísticas en base a una lista de juegos
     * @param games Es la lista de juegos con la que se quiere usar como base para obtener las estadísticas
     * @return Estadisticas, en donde la key es un estado de juego
     * y el value es la cantidad de juegos que hay con ese estado de juego
     */
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

    /**
     * Obtiene las estadísticas que hay entre dos fechas
     * @param dateMin Es la fecha de inicio
     * @param dateMax Es la fecha de fin
     * @return Estadisticas, en donde la key es un estado de juego
     * y el value es la cantidad de juegos que hay con ese estado de juego
     */
    public HashMap<String, Integer> getStatisticsByDates(Date dateMin, Date dateMax)
    {
        List<Game> games = gameRepository.getAllByDateBetween(dateMin, dateMax);

        return this.getStatistics(games);
    }

    /**
     * Obtiene las estadísticas para un usuario determinado
     * @param username nombre de usuario al cual se quiere obtener las estadísticas
     * @return Estadisticas, en donde la key es un estado de juego
     * y el value es la cantidad de juegos que hay con ese estado de juego
     */
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
