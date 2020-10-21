package tacs.wololo.services;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import tacs.wololo.model.DTOs.GameInfoDto;
import tacs.wololo.model.Game;
import tacs.wololo.model.GameStyle;
import tacs.wololo.model.Municipality;
import tacs.wololo.model.User;
import tacs.wololo.repositories.GamesRepository;
import tacs.wololo.repositories.UserRepository;
import tacs.wololo.services.implementations.GameService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTests {
    @Mock
    private GamesRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GameService gameService;
    
    private GameInfoDto funnyGame;

    @Before
    public void init() {
        funnyGame = mock(GameInfoDto.class);
        when(funnyGame.getPlayersUsernames()).thenReturn(Arrays.asList("Lola", "Juan", "Santiago"));
        when(funnyGame.getProvinceName()).thenReturn("Chaco");
        when(funnyGame.getMunicipalitiesCant()).thenReturn(10);
        when(funnyGame.getGameStyle()).thenReturn(GameStyle.NORMAL);

        // mocking gameRepository
        Game lolaGame = mock(Game.class);
        Game nobodyCares = mock(Game.class);

        when(lolaGame.getId()).thenReturn(Long.valueOf(1));
        when(lolaGame.getPlayers()).thenReturn(Arrays.asList("Lola", "Juan", "Santiago"));

        Municipality machagai = mock(Municipality.class);
        Municipality quitilipi = mock(Municipality.class);
        Municipality fontana = mock(Municipality.class);

        when(machagai.getOwner()).thenReturn("Lola");
        when(quitilipi.getOwner()).thenReturn("Lola");
        when(fontana.getOwner()).thenReturn("Juan");

        when(lolaGame.getMunicipality("Machagai")).thenReturn(machagai);
        when(lolaGame.getMunicipality("Quitilipi")).thenReturn(quitilipi);
        when(lolaGame.getMunicipality("Fontana")).thenReturn(fontana);
        when(nobodyCares.getPlayers()).thenReturn(Arrays.asList("Juan", "Santiago"));

        List<Game> games = new ArrayList<>();
        games.add(lolaGame);
        games.add(nobodyCares);

        Mockito.when(gameRepository.findAll()).thenReturn(games);

        // mocking userRepository
        User lola = mock(User.class);
        when(lola.getEmail()).thenReturn("lola@hotmail.com");

        Mockito.when(userRepository.findByUsername("Lola")).thenReturn(Optional.of(lola));
        
    }

    @Test
    public void createGame() throws IOException {

        Game game = gameService.createGame(funnyGame);
        Assert.assertNotNull(game);
        Mockito.verify(gameRepository, Mockito.times(1)).save(ArgumentMatchers.any(Game.class));
        Mockito.verifyNoMoreInteractions(gameRepository);
    }

    @Test
    public void getGameAndGameExists(){
        Game game = gameService.getGame("Lola", (long) 1);

        Assert.assertNotNull(game);
        Mockito.verify(gameRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(gameRepository);
    }

    @Test(expected = RuntimeException.class)
    public void getGameAndGameDontExists(){
        Game game = gameService.getGame("Pablo", (long) 30);
    }

    @Test
    public void happyMoveGauchos(){
        Game game = gameService.moveGauchos("Lola", (long) 1,
                "Machagai", "Quitilipi", 10);
        Mockito.verify(gameRepository, Mockito.times(1)).save(ArgumentMatchers.any(Game.class));
    }

    @Test(expected = RuntimeException.class)
    public void moveGauchosAndSourceDontExists(){
        Game game = gameService.moveGauchos("Lola", (long) 1,
                "SomeRandom", "Quitilipi", 10);
    }

    @Test(expected = RuntimeException.class)
    public void moveGauchosAndInvalidUsername(){
        Game game = gameService.moveGauchos("Karina", (long) 1,
                "Machagai", "Quitilipi", 10);
    }

    @Test
    public void happyAttackMunicipality(){
        Game game = gameService.attackMunicipality("Lola", (long) 1,
                "Quitilipi", "Fontana");
        //Mockito.verify(gameRepository, Mockito.times(1)).save(ArgumentMatchers.any(Game.class));
    }

    @Test(expected = RuntimeException.class)
    public void attackMunicipalityAndSourceDontExists(){
        Game game = gameService.attackMunicipality("Lola", (long) 1,
                "Quitilipi", "SomeRandom");
    }

    @Test(expected = RuntimeException.class)
    public void attackMunicipalityAndInvalidUsername(){
        Game game = gameService.attackMunicipality("Karina", (long) 1,
                "Quitilipi", "Fontana");
    }

    @Test
    public void happyChangeMode() throws IOException {
        Game game = gameService.changeMode("Lola", (long) 1, "Quitilipi");
        //Mockito.verify(gameRepository, Mockito.times(1)).save(ArgumentMatchers.any(Game.class));
    }

    @Test(expected = RuntimeException.class)
    public void changeModeAndSourceDontExists() throws IOException {
        Game game = gameService.changeMode("Lola", (long) 1, "SomeRandom");
    }

    @Test(expected = RuntimeException.class)
    public void changeModeAndInvalidUsername() throws IOException {
        Game game = gameService.changeMode("Karina", (long) 1, "Fontana");
    }

    // TODO surrender
    // TODO getMovementsBy
}
