package com.ipi.gestiondechampionnatapi.repository;

import com.ipi.gestiondechampionnatapi.models.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GameRepository extends CrudRepository<Game, Long> {


    /**
     * @return liste de toutes les games
     */
    @Override
    List<Game> findAll();


    /**
     * @param dayId journée cible
     * @return liste de toutes les games liées à la journée en paramètre
     */
    List<Game> findGamesByDayId(Long dayId);


    /**
     * @param championshipId championnat cible
     * @return liste de toutes les games liée à un championnat en utilisant la journée qui fait le lien entre les deux
     */
    @Query("SELECT game FROM Game game WHERE game.day.championshipId = :championshipId")
    List<Game> findByChampionshipId(@Param("championshipId") Long championshipId);

}
