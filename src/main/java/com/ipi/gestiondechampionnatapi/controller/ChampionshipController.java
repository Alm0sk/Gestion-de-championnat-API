package com.ipi.gestiondechampionnatapi.controller;

import com.ipi.gestiondechampionnatapi.models.Championship;
import com.ipi.gestiondechampionnatapi.repository.ChampionshipRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/championships")
public class ChampionshipController {


    /*
     * Repository
     */
    private final ChampionshipRepository championshipRepository;


    /*
     * Controller
     */
    public ChampionshipController(ChampionshipRepository championshipRepository) {
        this.championshipRepository = championshipRepository;
    }


    /* *********************
     * Mapping
     ********************* */


    /*
     * Ping de test
     */

    @GetMapping(value = "ping")
    public String ping() {

        return "championship pong";
    }


    /*
     * Récupérer la liste des championnats
     */

    @GetMapping(value = "/")
    public List<Championship> all() {

        return championshipRepository.findAll();
    }


    /*
     * Récupérer un championnat par son ID
     */

    @GetMapping(value = "/{championship}")
    public Championship getOne(@PathVariable(name = "championship", required = false) Championship championship) {
        if (championship == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Championnat introuvable"
            );
        }

        return championship;
    }

    /*
     * Post d'un championnat
     */

    @PostMapping(value = "/")
    public ResponseEntity<Championship> saveChampionship(@Valid @RequestBody Championship championship, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        } else {
            // Fix pour empêcher la création d'un championnat par-dessus un championnat déjà existant avec l'ID
            Optional<Championship> existingChampionship = championshipRepository.findById(championship.getId());
            if(existingChampionship.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un championnat avec cet ID existe déjà. " +
                        "L'id est automatiquement géré et n'est pas attendu dans cette request");

            } else {
                championshipRepository.save(championship);

                return new ResponseEntity<>(championship, HttpStatus.CREATED);
            }
        }
    }


    /*
     * Mettre à jour un championnat
     */

    @PutMapping(value = "/{championship}")
    public ResponseEntity<Championship> updateChampionship(@PathVariable(name = "championship") Championship championship,
                                                           @Valid @RequestBody Championship championshipUpdate,
                                                           BindingResult bindingResult) {
        if (championship == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Championat introuvable"
            );
        } else {
            if (bindingResult.hasErrors()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
            } else {
                championshipUpdate.setId(championship.getId());
                championshipRepository.save(championshipUpdate);

                return new ResponseEntity<>(championshipUpdate, HttpStatus.CREATED);
            }
        }
    }


    /*
     * Supprimer un championnat
     */
    @DeleteMapping(value = "{championship}")
    public ResponseEntity<String> deleteChampionship(@PathVariable(name = "championship") Championship championship) {
        if (championship == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Championnat introuvable"
            );
        } else {
            championshipRepository.delete(championship);

            return ResponseEntity.ok("Le championnat "+ championship.getId() + " " + championship.getName() + " a été supprimé avec succès");
        }
    }

}
