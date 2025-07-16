package com.ipi.gestiondechampionnatapi.repository;

import com.ipi.gestiondechampionnatapi.models.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Long> {

    /**
     * @return liste de tous les pays
     */
    @Override
    List<Country> findAll();
}
