package com.ipi.gestiondechampionnatapi.repository;

import com.ipi.gestiondechampionnatapi.models.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    @Override
    List<Team> findAll();

    @Query("SELECT team FROM Team team JOIN team.championships championship WHERE championship.id = :championshipId")
    List<Team> findTeamsByChampionshipId(Long championshipId);
}
