package com.ipi.gestiondechampionnatapi.controller;

import com.ipi.gestiondechampionnatapi.models.Day;

import com.ipi.gestiondechampionnatapi.repository.ChampionshipRepository;
import com.ipi.gestiondechampionnatapi.repository.DayRepository;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "api/days")
public class DayController {


    /*
     * Repository
     */

    private final DayRepository dayRepository;
    private final ChampionshipRepository championshipRepository;


    /*
     * Controller
     */

    public DayController(DayRepository dayRepository, ChampionshipRepository championshipRepository) {
        this.dayRepository = dayRepository;
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

        return "Day pong";
    }


    /*
     * Récupérer la liste des journées
     */

    @GetMapping(value = "/")
    public List<Day> all() {

        return dayRepository.findAll();
    }


    /*
     * Récupérer la liste des journées suivant un Id de championnat
     */

    @GetMapping(value = "/championship/{championshipId}")
    public ResponseEntity<List<Day>> getAllDaysByChampionshipId(@PathVariable(value = "championshipId") Long championshipId) {
        if (!championshipRepository.existsById(championshipId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pas de championnat avec l'id : " + championshipId);
        }
        List<Day> days = dayRepository.findDaysByChampionshipId(championshipId);

        return new ResponseEntity<>(days, HttpStatus.OK);
    }


    /*
     * Récupérer une journée par Id
     */

    @GetMapping(value = "/{day}")
    public Day getOne(@PathVariable(name = "day", required = false) Day day) {
        if (day == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Journée introuvable"
            );
        }

        return day;
    }


    /*
     * Créer une journée pour un championnat
     */

    @PostMapping(value = "/")
    public ResponseEntity<Day> saveDay(@Valid @RequestBody Day day, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        } else {
            // Fix pour empêcher la création d'une journée par-dessus une journée déjà existant avec l'ID
            Optional<Day> existingDay = dayRepository.findById(day.getId());
            if(existingDay.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Une journée avec cet ID existe déjà. " +
                        "L'id est automatiquement géré et n'est pas attendu dans cette request");

            } else {
                dayRepository.save(day);

                return new ResponseEntity<>(day, HttpStatus.CREATED);
            }
        }
    }


    /*
     * Mettre à jour une journée
     */

    @PutMapping(value = "/{day}")
    public ResponseEntity<Day> updateDay(@PathVariable(name = "day") Day day,
                                           @Valid @RequestBody Day dayUpdate,
                                           BindingResult bindingResult) {
        if (day == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Journée à mettre à jours introuvable"
            );
        } else {
            if (bindingResult.hasErrors()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
            } else {
                dayUpdate.setId(day.getId());
                dayRepository.save(dayUpdate);

                return new ResponseEntity<>(dayUpdate, HttpStatus.CREATED);
            }
        }
    }

    /*
     * Supprimer une journée
     */

    @DeleteMapping(value = "/{day}")
    public ResponseEntity<String> deleteDay(@PathVariable(name = "day") Day day) {
        if (day == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Journée à supprimer introuvable"
            );
        } else {
            dayRepository.delete(day);

            return ResponseEntity.ok("L'équipe "+ day.getId() + " " + day.getNumber() + " a été supprimé avec succès");
        }
    }

}
