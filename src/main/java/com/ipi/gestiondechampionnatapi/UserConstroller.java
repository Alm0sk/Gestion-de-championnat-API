package com.ipi.gestiondechampionnatapi;

import com.ipi.gestiondechampionnatapi.models.User;
import com.ipi.gestiondechampionnatapi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.Binding;
import java.util.List;

@RestController
@RequestMapping(value ="/api/users")
public class UserConstroller {

    private UserRepository userRepository;

    public UserConstroller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
     * Ping de test
     */
    @GetMapping("/ping")
    public String ping() {

        return "pong";
    }

    /*
     * Récupérer la liste des utilisateurs
     */
    @GetMapping(value ="/")
    public List<User> all() {

        return userRepository.findAll();
    }

    /*
     * Récupérer un utilisateur
     */
    @GetMapping(value = "/{user}")
    public User getOne(@PathVariable(name = "user", required = false) User user) {
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Utilisateur introuvable"
            );
        }

        return user;
    }

    /*
     * Post d'un utilisateur
     */
    @PostMapping(value = "/")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        } else {
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }



}
