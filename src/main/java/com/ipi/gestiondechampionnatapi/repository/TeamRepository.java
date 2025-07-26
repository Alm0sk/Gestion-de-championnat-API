package com.ipi.gestiondechampionnatapi.repository;

import com.ipi.gestiondechampionnatapi.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {


    /**
     * @return liste de toutes les équipes
     */
    @Override
    List<Team> findAll();

    /**
     * @return List of all teams with their championships loaded (FETCH JOIN)
     */
    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.championships")
    List<Team> findAllWithChampionships();

    /**
     * @param championshipId championship ID
     * @return List of teams linked to a championship through the TeamChampionship join table
     */
    @Query("SELECT team FROM Team team JOIN team.championships championship WHERE championship.id = :championshipId")
    List<Team> findTeamsByChampionshipId(Long championshipId);

    /**
     * Find a team by its name
     * @param name the name of the team
     * @return the team corresponding to the name
     */
    Team findByName(String name);
}
