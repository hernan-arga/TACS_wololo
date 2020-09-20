package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacs.wololo.model.Game;
import tacs.wololo.model.Movement;
import tacs.wololo.model.Municipality;
import tacs.wololo.repositories.GameRepository;

import java.util.List;

@Service
public class MovementService {
    @Autowired
    GameRepository gameRepository; // FIXME, no se si esta bien esto

    public List<Movement> getMovementsBy(Long idGame, String username, String idMunicipality)
    {
        Game game = gameRepository.getGame(idGame, username);

        Municipality municipality = game.getMunicipality(idMunicipality);

        return municipality.getMovements();
    }
}
