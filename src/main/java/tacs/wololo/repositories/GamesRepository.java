package tacs.wololo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tacs.wololo.model.Game;
import tacs.wololo.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface GamesRepository extends JpaRepository<Game, Long>
{
    List<Game> getAllByDateBetween(Date from, Date to);
    List<Game> findAll();
}
