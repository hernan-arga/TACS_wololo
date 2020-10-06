package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacs.wololo.model.ElementScoreBoard;
import tacs.wololo.model.Game;
import tacs.wololo.model.StatisticsCreator;
import tacs.wololo.repositories.GameRepository;
import tacs.wololo.repositories.UserRepository;

import java.util.*;

@Service
public class StatisticsService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    StatisticsCreator statisticsCreator = new StatisticsCreator(gameRepository);


    public List<ElementScoreBoard> getScoreBoard(Long id)
    {
        /*Optional<Game> game = gameRepository.getGame(id);

        if(game.isPresent())
        {
            return game.get().getScoreBoard();
        }

        throw new RuntimeException("El juego no existe");*/

        List<ElementScoreBoard> scoreBoard = new ArrayList<>(); // TODO: SACAR
        scoreBoard.add(new ElementScoreBoard("Melisa", 15));
        scoreBoard.add(new ElementScoreBoard("Carlos", 10));
        return  scoreBoard;
    }


    public HashMap<String, Integer> getStatisticsByDates(Date from, Date to)
    {
        //HashMap<String, Integer> statistics = statisticsCreator.getStatisticsByDates(from, to);

        HashMap<String, Integer> statistics = new HashMap <String, Integer> (); // TODO: SACAR
        statistics.put("CANCELLED", 0); statistics.put("IN_PROGRESS", 20);
        statistics.put("FINISHED", 10); statistics.put("CREATED", 0);

        return statistics;
    }

    public HashMap<String, Integer> getIndividualStatistics(String userPlayer)
    {
        /*if(!userRepository.existsByUsername(userPlayer))
            throw new RuntimeException("El usuario no existe");

        HashMap<String, Integer> statistics = statisticsCreator.getIndividualStatistics(userPlayer);*/

        HashMap<String, Integer> statistics = new HashMap <String, Integer> (); // TODO: SACAR
        statistics.put("CANCELLED", 0); statistics.put("IN_PROGRESS", 2);
        statistics.put("FINISHED", 1); statistics.put("CREATED", 0);

        return statistics;
    }
}
