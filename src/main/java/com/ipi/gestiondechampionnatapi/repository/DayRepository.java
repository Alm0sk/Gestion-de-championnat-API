package com.ipi.gestiondechampionnatapi.repository;

import com.ipi.gestiondechampionnatapi.models.Day;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayRepository extends CrudRepository<Day, Long> {
    @Override
    List<Day> findAll();

    List<Day> findDaysByChampionshipId(Long championshipId);
}
