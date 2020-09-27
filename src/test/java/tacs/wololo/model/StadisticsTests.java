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

        when(funnyGame.getState()).thenReturn(GameState.FINISHED);
        when(boringGame.getState()).thenReturn(GameState.IN_PROGRESS);
        when(mehGame.getState()).thenReturn(GameState.IN_PROGRESS);

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
        List<Integer> cantGamesPerState = new ArrayList<Integer>() {{ add(0); add(2); add(1); add(0); }};

        Assert.assertEquals(cantGamesPerState, statisticsCreator.getStatisticsByDates(minDate, maxDate));
    }

    @Test
    public void getIndividualStatistics() throws ParseException {
        List<Integer> cantGamesPerState = new ArrayList<Integer>() {{ add(0); add(1); add(1); add(0); }};

        Assert.assertEquals(cantGamesPerState, statisticsCreator.getIndividualStatistics("Carlos"));
    }
}
