package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacs.wololo.model.Game;
import tacs.wololo.model.StatisticsCreator;
import tacs.wololo.repositories.GameRepository;
import tacs.wololo.repositories.UserRepository;

import java.util.*;

@Service
public class AdminService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    StatisticsCreator statisticsCreator = new StatisticsCreator(gameRepository);


    public HashMap<String, Integer> getScoreBoard(Long id)
    {
        Optional<Game> game = gameRepository.getGame(id);

        if(game.isPresent())
        {
            return game.get().getScoreBoard();
        }

        throw new RuntimeException("El juego no existe");
    }


    public HashMap<String, Integer> getStatisticsByDates(Date from, Date to)
    {
        List<Game> games = gameRepository.getGamesByDates(from, to);

        HashMap<String, Integer> statistics = statisticsCreator.getStatistics(games);

        return statistics;
    }

    public HashMap<String, Integer> getIndividualStatistics(String userPlayer)
    {
        if(!userRepository.existsByUsername(userPlayer))
            throw new RuntimeException("El usuario no existe");

        List<Game> userGames = gameRepository.getGamesFor(userPlayer);
        HashMap<String, Integer> statistics = statisticsCreator.getStatistics(userGames);

        return statistics;
    }
}
