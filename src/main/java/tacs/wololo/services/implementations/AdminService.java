package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacs.wololo.model.Game;
import tacs.wololo.model.StatisticsCreator;
import tacs.wololo.repositories.GameRepository;
import tacs.wololo.repositories.GamesRepository;
import tacs.wololo.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    GamesRepository gamesRepository;

    @Autowired
    UserRepository userRepository;

    StatisticsCreator statisticsCreator = new StatisticsCreator(gameRepository);


    public HashMap<String, Integer> getScoreBoard(Long id)
    {
        Optional<Game> game = getGame(id);

        if(game.isPresent())
        {
            return game.get().getScoreBoard();
        }

        throw new RuntimeException("El juego no existe");
    }


    public HashMap<String, Integer> getStatisticsByDates(Date from, Date to)
    {
        System.out.println("---------");
        System.out.println(from);
        System.out.println(to);
        List<Game> games = getAllGamesByDateBetween(from, to);
        System.out.println(games);

        HashMap<String, Integer> statistics = statisticsCreator.getStatistics(games);
        System.out.println(statistics);

        return statistics;
    }



    public HashMap<String, Integer> getIndividualStatistics(String userPlayer)
    {
        if(!userRepository.existsByUsername(userPlayer))
            throw new RuntimeException("El usuario no existe");

        List<Game> userGames = getGamesFor(userPlayer);
        HashMap<String, Integer> statistics = statisticsCreator.getStatistics(userGames);

        return statistics;
    }

    //TODO: Fixear estas funciones en gameRepository
    public List<Game> getAllGamesByDateBetween(Date dateMin, Date datemax)
    {
        List<Game> games = gamesRepository.findAll();
        return games.stream().filter(
                g -> {System.out.println(g); return g.getDate().after(dateMin) && g.getDate().before(datemax);})
                .collect(Collectors.toList());
    }

    public List<Game> getGamesFor(String username)
    {
        List<Game> games = gamesRepository.findAll();
        return games.stream().filter(g -> g.getPlayers().contains(username)).collect(Collectors.toList());
    }

    public Optional<Game> getGame(Long gameId)
    {
        List<Game> games = gamesRepository.findAll();
        return games.stream().filter(g -> g.getId().equals(gameId)).findFirst();
    }
}
