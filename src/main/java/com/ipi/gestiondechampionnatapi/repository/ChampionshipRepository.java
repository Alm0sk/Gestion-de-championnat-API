package com.ipi.gestiondechampionnatapi.repository;

import com.ipi.gestiondechampionnatapi.models.Championship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ChampionshipRepository extends CrudRepository<Championship, Long> {

    /**
     * @return List of all championships
     */
    @Override
    List<Championship> findAll();

    /**
     * Find championship by name
     * @param name name of the championship
     * @return Optional championship
     */
    Optional<Championship> findByName(String name);

    /**
     * Find all championships with their teams loaded
     * @return List of championships with teams
     */
    @Query("SELECT DISTINCT c FROM Championship c LEFT JOIN FETCH c.teams")
    List<Championship> findAllWithTeams();

}
