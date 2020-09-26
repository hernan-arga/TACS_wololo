package tacs.wololo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tacs.wololo.model.Game;
import tacs.wololo.repositories.GameRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameRepositoryTests {

    @Autowired
    private GameRepository gameRepository;
    private Game funnyGame;
    private Game mehGame;

    @Before
    public void init()
    {

        Queue<String> playersFunnyGame =new LinkedList<String>();
        Queue<String> playersBoringGame =new LinkedList<String>();
        Queue<String> playersMehGame =new LinkedList<String>();

        playersFunnyGame.add("Lucia"); playersFunnyGame.add("Carolina");
        playersFunnyGame.add("Cristian"); playersFunnyGame.add("Sebastian");

        playersBoringGame.add("Lucia"); playersBoringGame.add("Cristian");

        playersMehGame.add("Lucia"); playersMehGame.add("Carolina");
        playersMehGame.add("Sebastian");

        //List<String> playersFunnyGame = Stream.of("Lucia", "Carolina", "Cristian", "Sebastian").collect(Collectors.toList());
        //List<String> playersBoringGame = Stream.of("Lucia", "Cristian").collect(Collectors.toList());
        //List<String> playersMehGame = Stream.of("Lucia", "Carolina", "Sebastian").collect(Collectors.toList());

        //
        funnyGame = new Game();
        Game boringGame = new Game();
        mehGame = new Game();

        funnyGame.setPlayers(playersFunnyGame);
        boringGame.setPlayers(playersBoringGame);
        mehGame.setPlayers(playersMehGame);


        gameRepository = new GameRepository();
        gameRepository.addGame((long) 1, funnyGame);
        gameRepository.addGame((long)2, boringGame);
        gameRepository.addGame((long)3, mehGame);

    }

    @Test
    public void getGamesInfoByUserName()
    {
        List<Game> gamesBelongsToSebastian = new ArrayList<Game>();
        gamesBelongsToSebastian.add(funnyGame);
        gamesBelongsToSebastian.add(mehGame);

        Assert.assertEquals(gamesBelongsToSebastian, gameRepository.getGames("Sebastian"));
    }

}
