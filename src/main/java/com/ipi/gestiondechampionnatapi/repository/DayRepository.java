package com.ipi.gestiondechampionnatapi.repository;

import com.ipi.gestiondechampionnatapi.models.Day;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DayRepository extends CrudRepository<Day, Long> {


    /**
     * @return liste de toutes les journées
     */
    @Override
    List<Day> findAll();


    /**
     * @param championshipId championnat cible
     * @return liste de toutes les journées liées au championnat en paramètre
     */
    List<Day> findDaysByChampionshipId(Long championshipId);

}
