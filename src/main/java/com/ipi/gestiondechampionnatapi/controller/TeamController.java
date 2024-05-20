package com.ipi.gestiondechampionnatapi.controller;

import com.ipi.gestiondechampionnatapi.models.Championship;
import com.ipi.gestiondechampionnatapi.models.Team;
import com.ipi.gestiondechampionnatapi.repository.ChampionshipRepository;
import com.ipi.gestiondechampionnatapi.repository.TeamRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ChampionshipRepository championshipRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /*
     * Ping de test
     */
    @GetMapping("ping")
    public String ping() {
        return "Team pong";
    }

    /*
     * Récupérer la liste des équipes
     */
    @GetMapping(value = "/")
    public List<Team> all() {

        return teamRepository.findAll();
    }

    /*
     * Récupérer la liste des équipes suivant un Id de championnat
     */
    @GetMapping("/championship/{championshipId}")
    public ResponseEntity<List<Team>> getAllTagsByTutorialId(@PathVariable(value = "championshipId") Long championshipId) {
        if (!championshipRepository.existsById(championshipId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pas d'équipe avec l'id : " + championshipId);
        }

        List<Team> teams = teamRepository.findTeamsByChampionshipId(championshipId);
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    /*
     * Récupérer la liste des équipes par Id
     */
    @GetMapping(value = "/{team}")
    public Team getOne(@PathVariable(name = "team", required = false) Team team) {
        if (team == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Equipe introuvable"
            );
        }

        return team;
    }

    /*
     * Post d'une team
     */
    @PostMapping(value = "/")
    public ResponseEntity<Team> saveTeam(@Valid @RequestBody Team team, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        } else {
            // Fix pour empêcher la création d'un championnat par-dessus une équipe déjà existant avec l'ID
            Optional<Team> existingTeam = teamRepository.findById(team.getId());
            if(existingTeam.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Une équipe avec cet ID existe déjà. " +
                        "L'id est automatiquement géré et n'est pas attendu dans cette request");

            } else {
                teamRepository.save(team);
                return new ResponseEntity<>(team, HttpStatus.CREATED);
            }
        }
    }

    /*
     * Ajouter une équipe à un championnat
     */

    @PostMapping(value = "championship/{championshipId}")
    public ResponseEntity<Team> addTeam(@PathVariable(value = "championshipId") Long championshipId, @RequestBody Team teamRequest) {
        Team team = championshipRepository.findById(championshipId).map(championship -> {
            long teamId = teamRequest.getId();

            if (teamId != 0L) {
                // Fix pour empêcher la création d'une équipe par-dessus une équipe déjà existant avec l'ID
                Team _team = teamRepository.findById(teamId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pas d'équipe avec l'Id : " + teamId));
                championship.addTeam(_team);
                championshipRepository.save(championship);
                return _team;
            } else {
                if (teamRequest.getId() != 0L && teamRepository.existsById(teamRequest.getId())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Une équipe avec cet ID existe déjà. L'id est automatiquement géré et n'est pas attendu dans cette request");
                }

                // Ajouter et créer une nouvelle équipe
                championship.addTeam(teamRequest);
                return teamRepository.save(teamRequest);
            }
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pas de championnat avec l'Id : " + championshipId));

        return new ResponseEntity<>(team, HttpStatus.CREATED);
    }

    /*
     * Mettre à jour une équipe
     */
    @PutMapping(value = "/{team}")
    public ResponseEntity<Team> updateTeam(@PathVariable(name = "team", required = true) Team team,
                                           @Valid @RequestBody Team teamUpdate,
                                           BindingResult bindingResult) {
        if (team == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Equipe introuvable"
            );
        } else {
            if (bindingResult.hasErrors()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
            } else {
                teamUpdate.setId(team.getId());
                teamRepository.save(teamUpdate);
                return new ResponseEntity<>(teamUpdate, HttpStatus.CREATED);
            }
        }
    }

    /*
     * Supprimer une équipe
     */
    @DeleteMapping(value = "{team}")
    public ResponseEntity<String> deleteTeam(@PathVariable(name = "team", required = true) Team team) {
        if (team == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Equipe introuvable"
            );
        } else {
            teamRepository.delete(team);
            return ResponseEntity.ok("L'équipe "+ team.getId() + " " + team.getName() + " a été supprimé avec succès");
        }
    }

}
