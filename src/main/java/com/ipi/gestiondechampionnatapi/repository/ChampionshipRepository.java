package com.ipi.gestiondechampionnatapi.repository;

import com.ipi.gestiondechampionnatapi.models.Championship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChampionshipRepository extends CrudRepository<Championship, Integer> {

    /**
     * Affiche tout les championnat
     * @return liste de tous les championnats
     */
    @Override
    List<Championship> findAll();
}
