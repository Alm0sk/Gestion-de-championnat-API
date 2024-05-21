package com.ipi.gestiondechampionnatapi.repository;

import com.ipi.gestiondechampionnatapi.models.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    @Override
    List<Game> findAll();

    List<Game> findGamesByDayId(Long dayId);

    @Query("SELECT game FROM Game game WHERE game.day.championshipId = :championshipId")
    List<Game> findByChampionshipId(@Param("championshipId") Long championshipId);
}
