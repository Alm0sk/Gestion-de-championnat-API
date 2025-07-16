package com.ipi.gestiondechampionnatapi.repository;

import com.ipi.gestiondechampionnatapi.models.Stadium;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StadiumRepository extends CrudRepository<Stadium, Long> {

    /**
     * @return liste de tous les stades
     */
    @Override
    List<Stadium> findAll();

}
