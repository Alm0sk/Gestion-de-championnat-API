package com.ipi.gestiondechampionnatapi.controller;

import com.ipi.gestiondechampionnatapi.models.Day;
import com.ipi.gestiondechampionnatapi.models.Game;
import com.ipi.gestiondechampionnatapi.models.Team;
import com.ipi.gestiondechampionnatapi.repository.DayRepository;
import com.ipi.gestiondechampionnatapi.repository.GameRepository;
import com.ipi.gestiondechampionnatapi.repository.TeamRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "api/games")
public class GameController {


    /*
     * Repository
     */
    private final GameRepository gameRepository;
    private final DayRepository dayRepository;
    private final TeamRepository teamRepository;


    /*
     * Controller
     */
    public GameController(GameRepository gameRepository, DayRepository dayRepository, TeamRepository teamRepository
    ) {
        this.gameRepository = gameRepository;
        this.dayRepository = dayRepository;
        this.teamRepository = teamRepository;

    }


    /* *********************
     * Mapping
     ********************* */


    /*
     * Ping de test
     */
    @GetMapping("ping")
    public String ping() {

        return "Game pong";
    }


    /*
     * Récupérer la liste des résultats
     */
    @GetMapping(value = "/")
    public List<Game> all() {

        return gameRepository.findAll();
    }


    /*
     *  Récupérer la liste des résultats suivant l’id d’un championnat
     */
    @GetMapping(value = "/championship/{championshipId}")
    public List<Game> getGamesByChampionship(@PathVariable Long championshipId) {

        return gameRepository.findByChampionshipId(championshipId);
    }


    /*
     * Récupérer la liste des résultats suivant l’id d’une journée
     */
    @GetMapping("/day/{dayId}")
    public ResponseEntity<List<Game>> getAllGamesByDayId(@PathVariable(value = "dayId") Long dayId) {
        if (!dayRepository.existsById(dayId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pas de journée avec l'id : " + dayId);
        }
        List<Game> games = gameRepository.findGamesByDayId(dayId);

        return new ResponseEntity<>(games, HttpStatus.OK);
    }


     /*
      * Récupérer un résultat suivant son id
      */
     @GetMapping(value = "/{game}")
     public Game getOne(@PathVariable(name = "game", required = false) Game game) {
         if (game == null) {
             throw new ResponseStatusException(
                     HttpStatus.NOT_FOUND, "Journée introuvable"
             );
         }

         return game;
     }


     /*
      * Créer un résultat pour une journée
      */
     @PostMapping(value = "/")
     public ResponseEntity<Game> saveGame(@Valid @RequestBody Game game, BindingResult bindingResult) {
         if (bindingResult.hasErrors()) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
         } else {
             // Objets Teams et Day associés à partir des ID
             Team team1 = teamRepository.findById(game.getTeam1().getId()).orElseThrow();
             Team team2 = teamRepository.findById(game.getTeam2().getId()).orElseThrow();
             Day day = dayRepository.findById(game.getDay().getId()).orElseThrow();

             // Set
             game.setTeam1(team1);
             game.setTeam2(team2);
             game.setDay(day);

             gameRepository.save(game);
             return new ResponseEntity<>(game, HttpStatus.CREATED);
         }
     }


     /*
      * Mettre à jour un résultat
      */
     @PutMapping("/{gameId}")
     public ResponseEntity<Game> updateGame(@PathVariable Long gameId, @Valid @RequestBody Game gameUpdate, BindingResult bindingResult) {
         if (bindingResult.hasErrors()) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
         }

         return gameRepository.findById(gameId)
                 .map(existingGame -> {
                     existingGame.setTeam1Point(gameUpdate.getTeam1Point());
                     existingGame.setTeam2Point(gameUpdate.getTeam2Point());

                     // Modification des objets
                     if (gameUpdate.getTeam1() != null) {
                         Team team1 = teamRepository.findById(gameUpdate.getTeam1().getId())
                                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team 1 introuvable"));
                         existingGame.setTeam1(team1);
                     }
                     if (gameUpdate.getTeam2() != null) {
                         Team team2 = teamRepository.findById(gameUpdate.getTeam2().getId())
                                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team 2 introuvable"));
                         existingGame.setTeam2(team2);
                     }
                     if (gameUpdate.getDay() != null) {
                         Day day = dayRepository.findById(gameUpdate.getDay().getId())
                                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Day introuvable"));
                         existingGame.setDay(day);
                     }

                     // Fin
                     Game updatedGame = gameRepository.save(existingGame);
                     return new ResponseEntity<>(updatedGame, HttpStatus.OK);
                 })
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game introuvable"));
     }


    /*
    * Supprimer une game
    */
    @DeleteMapping(value = "{game}")
    public ResponseEntity<String> deleteGame(@PathVariable(name = "game", required = true) Game game) {
        if (game == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Journée introuvable"
            );
        } else {
            gameRepository.delete(game);

            return ResponseEntity.ok("La game "+ game.getId() + " a été supprimé avec succès");
        }
    }
}
