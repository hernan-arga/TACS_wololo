package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacs.wololo.model.ElementScoreBoard;
import tacs.wololo.model.Game;
import tacs.wololo.model.StatisticsCreator;
import tacs.wololo.repositories.GameRepository;
import tacs.wololo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class StatisticsService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    StatisticsCreator statisticsCreator = new StatisticsCreator(gameRepository);


    public List<ElementScoreBoard> getScoreBoard(Long id)
    {
        Optional<Game> game = gameRepository.getGame(id);

        if(game.isPresent())
        {
            return game.get().getScoreBoard();
        }

        throw new RuntimeException("El juego no existe");
    }

    public HashMap<String, Integer> getStatistics()
    {
        HashMap<String, Integer> statistics = statisticsCreator.getGeneralStatistics();

        return statistics;
    }

    public HashMap<String, Integer> getIndividualStatistics(String userPlayer)
    {
        if(!userRepository.existsByUsername(userPlayer))
            throw new RuntimeException("El usuario no existe");

        HashMap<String, Integer> statistics = statisticsCreator.getIndividualStatistics(userPlayer);

        return statistics;
    }
}
