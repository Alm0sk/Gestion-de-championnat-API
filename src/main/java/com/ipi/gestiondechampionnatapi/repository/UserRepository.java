package com.ipi.gestiondechampionnatapi.repository;

import com.ipi.gestiondechampionnatapi.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
