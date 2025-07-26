package com.ipi.gestiondechampionnatapi.repository;

import com.ipi.gestiondechampionnatapi.models.Championship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {

    /**
     * @return List of all championships
     */
    @Override
    List<Championship> findAll();

    /**
     * @return List of all championships with their teams loaded (FETCH JOIN)
     */
    @Query("SELECT DISTINCT c FROM Championship c LEFT JOIN FETCH c.teams")
    List<Championship> findAllWithTeams();

    /**
     * Find a championship by its name
     * @param name the name of the championship
     * @return the championship corresponding to the name
     */
    Optional<Championship> findByName(String name);

}
