package tacs.wololo.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tacs.wololo.repositories.GameRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StadisticsTests {
    @Autowired
    GameRepository gameRepository;

    private StatisticsCreator statisticsCreator;
    private Game funnyGame;
    private Game boringGame;
    private Game mehGame;
    private Date minDate;
    private Date maxDate;

    @Before
    public void init() throws ParseException {
        gameRepository = new GameRepository();
        funnyGame = mock(Game.class);
        boringGame = mock(Game.class);
        mehGame = mock(Game.class);
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");

        Date dateGame = objSDF.parse("12-09-2020");

        minDate = objSDF.parse("10-09-2020");
        maxDate = objSDF.parse("20-09-2020");

        when(funnyGame.getDate()).thenReturn(dateGame);
        when(boringGame.getDate()).thenReturn(dateGame);
        when(mehGame.getDate()).thenReturn(dateGame);

        when(funnyGame.getState()).thenReturn(GameState.FINALIZADO);
        when(boringGame.getState()).thenReturn(GameState.EN_PROGRESO);
        when(mehGame.getState()).thenReturn(GameState.EN_PROGRESO);

        Queue<String> playersWithCarlos =  new LinkedList<>(Arrays.asList("Carlos", "Juan", "Marcos"));
        Queue<String> playersWithoutCarlos = new LinkedList<>(Arrays.asList("Juan", "Marcos"));

        when(funnyGame.getPlayers()).thenReturn(playersWithCarlos);
        when(boringGame.getPlayers()).thenReturn(playersWithoutCarlos);
        when(mehGame.getPlayers()).thenReturn(playersWithCarlos);

        gameRepository.addGame((long) 1, funnyGame);
        gameRepository.addGame((long) 2, boringGame);
        gameRepository.addGame((long) 3, mehGame);
        statisticsCreator = new StatisticsCreator(gameRepository);
    }

    @Test
    public void getStatisticsByDates() throws ParseException {
        HashMap<String, Integer> statistics = new HashMap <String, Integer> ();
        statistics.put("CANCELADO", 0); statistics.put("EN_PROGRESO", 2);
        statistics.put("FINALIZADO", 1); statistics.put("CREADO", 0);

        Assert.assertEquals(statistics, statisticsCreator.getStatisticsByDates(minDate, maxDate));
    }

    @Test
    public void getIndividualStatistics() throws ParseException {
        HashMap<String, Integer> statistics = new HashMap <String, Integer> ();
        statistics.put("CANCELADO", 0); statistics.put("EN_PROGRESO", 1);
        statistics.put("FINALIZADO", 1); statistics.put("CREADO", 0);

        Assert.assertEquals(statistics, statisticsCreator.getIndividualStatistics("Carlos"));
    }

    @Test
    public void getIndividualStatisticsWhenPlayerDontHaveGames() throws ParseException {
        HashMap<String, Integer> statistics = new HashMap <String, Integer> ();
        statistics.put("CANCELADO", 0); statistics.put("EN_PROGRESO", 0);
        statistics.put("FINALIZADO", 0); statistics.put("CREADO", 0);

        Assert.assertEquals(statistics, statisticsCreator.getIndividualStatistics("Adrian"));
    }
}
