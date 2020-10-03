package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacs.wololo.model.ElementScoreBoard;
import tacs.wololo.model.Game;
import tacs.wololo.repositories.GameRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatisticsService {
    @Autowired
    GameRepository gameRepository;

    public List<ElementScoreBoard> getScoreBoard(Long id)
    {
        Optional<Game> game = gameRepository.getGame(id);

        if(game.isPresent())
        {
            return game.get().getScoreBoard(); //TODO: SACAR COMENTARIO
        }

        throw new RuntimeException("El juego no existe");
    }
}
