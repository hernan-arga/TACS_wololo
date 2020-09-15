package tacs.wololo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tacs.wololo.model.DTOs.GameInfoDto;
import tacs.wololo.repositories.GameRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameRepositoryTests {
    @Autowired
    private GameRepository gameRepository;
    private GameInfoDto funnyGame;
    private GameInfoDto mehGame;

    @Before
    public void init()
    {
        List<GameInfoDto> gamesInfoDtos = new ArrayList<>();

        List<String> playersFunnyGame = Stream.of("Lucia", "Carolina", "Cristian", "Sebastian").collect(Collectors.toList());
        List<String> playersBoringGame = Stream.of("Lucia", "Cristian").collect(Collectors.toList());
        List<String> playersMehGame = Stream.of("Lucia", "Carolina", "Sebastian").collect(Collectors.toList());

        funnyGame = new GameInfoDto(playersFunnyGame, "Chaco",25);
        GameInfoDto boringGame = new GameInfoDto(playersBoringGame, "Tierra del Fuego",3);
        mehGame = new GameInfoDto(playersMehGame, "Chaco",20);

        gamesInfoDtos.add(funnyGame);
        gamesInfoDtos.add(boringGame);
        gamesInfoDtos.add(mehGame);

        gameRepository = new GameRepository();
        gameRepository.setGamesInfo(gamesInfoDtos);

    }

    @Test
    public void getGamesInfoByUserName()
    {
        List<GameInfoDto> gamesBelongsToSebastian = new ArrayList<GameInfoDto>();
        gamesBelongsToSebastian.add(funnyGame);
        gamesBelongsToSebastian.add(mehGame);

        Assert.assertEquals(gamesBelongsToSebastian, gameRepository.getGamesInfoByUserName("Sebastian"));
    }

    @Test
    public void getGamesInfoByUserNameWithEmptyRepository()
    {
        List<GameInfoDto> emptyGames = new ArrayList<>();
        gameRepository.setGamesInfo(emptyGames);

        List<String> emptyUsernames = new ArrayList<>();

        Assert.assertEquals(emptyUsernames, gameRepository.getGamesInfoByUserName("Sebastian"));
    }
}
