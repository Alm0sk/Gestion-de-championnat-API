package com.ipi.gestiondechampionnatapi.Controller;

import com.ipi.gestiondechampionnatapi.models.User;
import com.ipi.gestiondechampionnatapi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value ="/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
     * Ping de test
     */
    @GetMapping("/ping")
    public String ping() {

        return "user pong";
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
     * Récupérer un utilisateur en fonction de son user et password
     */
    @GetMapping(value = "/getUserByEMailAndPassword")
    public ResponseEntity<User> getUserByEMailAndPassword(@RequestParam String email, @RequestParam String password) {
        List<User> users = userRepository.findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .filter(user -> user.getPassword().equals(password))
                .toList();
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable");
        }
        return new ResponseEntity<>(users.get(0), HttpStatus.OK);
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
