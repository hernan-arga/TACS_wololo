package tacs.wololo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tacs.wololo.model.Game;

import java.util.Date;
import java.util.List;

@Repository
public interface GamesRepository extends JpaRepository<Game, Long>
{
    List<Game> getAllByDateBetween(Date from, Date to);
}
