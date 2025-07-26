package com.ipi.gestiondechampionnatapi.repository;

import com.ipi.gestiondechampionnatapi.models.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {


    /**
     * @return liste de toutes les équipes
     */
    @Override
    List<Team> findAll();


    /**
     * @param championshipId championship ID
     * @return List of teams linked to a championship through the TeamChampionship join table
     */
    @Query("SELECT team FROM Team team JOIN team.championships championship WHERE championship.id = :championshipId")
    List<Team> findTeamsByChampionshipId(Long championshipId);

    /**
     * Find all teams with their championships loaded
     * @return List of teams with championships
     */
    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.championships")
    List<Team> findAllWithChampionships();

}
