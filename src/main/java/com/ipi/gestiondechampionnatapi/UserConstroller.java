package com.ipi.gestiondechampionnatapi;

import com.ipi.gestiondechampionnatapi.models.User;
import com.ipi.gestiondechampionnatapi.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value ="/api/users")
public class UserConstroller {

    private final UserRepository userRepository;

    public UserConstroller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Ping de test
    @GetMapping("/ping")
    public String ping() {

        return "pong";
    }

    // Récupérer la liste des utilisateurs
    @GetMapping("/")
    public List<User> all() {

        return userRepository.findAll();
    }


}
