package com.ipi.gestiondechampionnatapi.controller;

import com.ipi.gestiondechampionnatapi.models.Championship;
import com.ipi.gestiondechampionnatapi.models.Team;
import com.ipi.gestiondechampionnatapi.models.Game;
import com.ipi.gestiondechampionnatapi.models.Stadium;
import com.ipi.gestiondechampionnatapi.models.Day;
import com.ipi.gestiondechampionnatapi.repository.ChampionshipRepository;
import com.ipi.gestiondechampionnatapi.repository.TeamRepository;
import com.ipi.gestiondechampionnatapi.repository.GameRepository;
import com.ipi.gestiondechampionnatapi.repository.StadiumRepository;
import com.ipi.gestiondechampionnatapi.repository.DayRepository;
import com.ipi.gestiondechampionnatapi.service.TeamChampionshipService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

@Controller
@RequestMapping("/backoffice")
public class BackOfficeController {

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private TeamChampionshipService teamChampionshipService;

    /**
     * Page d'accueil du back office
     */
    @GetMapping("")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("championshipsCount", championshipRepository.count());
        model.addAttribute("teamsCount", teamRepository.count());
        model.addAttribute("gamesCount", gameRepository.count());
        model.addAttribute("stadiumsCount", stadiumRepository.count());
        return "backoffice/dashboard";
    }

    // ================================
    // GESTION DES CHAMPIONNATS
    // ================================

    /**
     * Liste des championnats
     */
    @GetMapping("/championships")
    public String listChampionships(Model model) {
        model.addAttribute("championships", championshipRepository.findAllWithTeams());
        return "backoffice/championships/list";
    }

    /**
     * Formulaire de création d'un championnat
     */
    @GetMapping("/championships/new")
    public String newChampionship(Model model) {
        model.addAttribute("championship", new Championship());
        return "backoffice/championships/form";
    }

    /**
     * Formulaire d'édition d'un championnat
     */
    @GetMapping("/championships/edit/{id}")
    public String editChampionship(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Championship> championship = championshipRepository.findById(id);
            if (championship.isPresent()) {
                model.addAttribute("championship", championship.get());
                return "backoffice/championships/form";
            } else {
                redirectAttributes.addFlashAttribute("error", "Championnat non trouvé avec l'ID : " + id);
                return "redirect:/backoffice/championships";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la récupération du championnat : " + e.getMessage());
            return "redirect:/backoffice/championships";
        }
    }

    /**
     * Sauvegarde d'un championnat
     */
    @PostMapping("/championships/save")
    public String saveChampionship(@Valid @ModelAttribute Championship championship, 
                                 BindingResult result, 
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "backoffice/championships/form";
        }
        
        championshipRepository.save(championship);
        redirectAttributes.addFlashAttribute("success", "Championnat sauvegardé avec succès !");
        return "redirect:/backoffice/championships";
    }

    /**
     * Suppression d'un championnat
     */
    @PostMapping("/championships/delete/{id}")
    public String deleteChampionship(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            championshipRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Championnat supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/backoffice/championships";
    }

    // ================================
    // GESTION DES ÉQUIPES
    // ================================

    /**
     * Liste des équipes
     */
    @GetMapping("/teams")
    public String listTeams(Model model) {
        model.addAttribute("teams", teamRepository.findAllWithChampionships());
        return "backoffice/teams/list";
    }

    /**
     * Formulaire de création d'une équipe
     */
    @GetMapping("/teams/new")
    public String newTeam(Model model) {
        model.addAttribute("team", new Team());
        model.addAttribute("stadiums", stadiumRepository.findAll());
        model.addAttribute("championships", championshipRepository.findAll());
        return "backoffice/teams/form";
    }

    /**
     * Formulaire d'édition d'une équipe
     */
    @GetMapping("/teams/edit/{id}")
    public String editTeam(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Team> team = teamRepository.findById(id);
            if (team.isPresent()) {
                model.addAttribute("team", team.get());
                model.addAttribute("stadiums", stadiumRepository.findAll());
                model.addAttribute("championships", championshipRepository.findAll());
                return "backoffice/teams/form";
            } else {
                redirectAttributes.addFlashAttribute("error", "Équipe non trouvée avec l'ID : " + id);
                return "redirect:/backoffice/teams";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la récupération de l'équipe : " + e.getMessage());
            return "redirect:/backoffice/teams";
        }
    }

    /**
     * Sauvegarde d'une équipe
     */
    @PostMapping("/teams/save")
    public String saveTeam(@Valid @ModelAttribute Team team, 
                          BindingResult result, 
                          @RequestParam(required = false) Long stadiumId,
                          @RequestParam(required = false) List<Long> championshipIds,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("stadiums", stadiumRepository.findAll());
            model.addAttribute("championships", championshipRepository.findAll());
            return "backoffice/teams/form";
        }
        
        // Stade optionnel
        if (stadiumId != null) {
            Optional<Stadium> stadium = stadiumRepository.findById(stadiumId);
            stadium.ifPresent(team::setStadium);
        }
        

        Team savedTeam = teamRepository.save(team);
                
        Set<Championship> currentChampionships = new HashSet<>(savedTeam.getChampionships());
        
        Set<Long> selectedChampionshipIds = championshipIds != null ? 
            new HashSet<>(championshipIds) : new HashSet<>();
        
        for (Championship currentChampionship : currentChampionships) {
            if (!selectedChampionshipIds.contains(currentChampionship.getId())) {
                boolean removed = teamChampionshipService.removeTeamFromChampionship(
                    savedTeam.getName(), currentChampionship.getName());
                if (removed) {
                    redirectAttributes.addFlashAttribute("info", 
                        "Équipe retirée du championnat: " + currentChampionship.getName());
                }
            }
        }
        
        if (championshipIds != null && !championshipIds.isEmpty()) {
            for (Long championshipId : championshipIds) {
                Optional<Championship> championship = championshipRepository.findById(championshipId);
                if (championship.isPresent()) {
                    boolean associated = teamChampionshipService.associateTeamToChampionship(savedTeam, championship.get());
                    if (associated) {
                        redirectAttributes.addFlashAttribute("info", 
                            "Équipe associée au championnat: " + championship.get().getName());
                    }
                } else {
                    redirectAttributes.addFlashAttribute("warning", 
                        "Championnat non trouvé avec l'ID: " + championshipId);
                }
            }
        }
        
        redirectAttributes.addFlashAttribute("success", "Équipe sauvegardée avec succès !");
        return "redirect:/backoffice/teams";
    }

    /**
     * Suppression d'une équipe
     */
    @PostMapping("/teams/delete/{id}")
    public String deleteTeam(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            teamRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Équipe supprimée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/backoffice/teams";
    }

    // ================================
    // GESTION DES MATCHS
    // ================================

    /**
     * Liste des matchs
     */
    @GetMapping("/games")
    public String listGames(Model model) {
        List<Game> games = gameRepository.findAll();
        List<Championship> championships = championshipRepository.findAll();
        
        model.addAttribute("games", games);
        model.addAttribute("championships", championships);
        return "backoffice/games/list";
    }

    /**
     * Formulaire d'édition d'un match (pour saisir les résultats)
     */
    @GetMapping("/games/edit/{id}")
    public String editGame(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Game> game = gameRepository.findById(id);
            if (game.isPresent()) {
                Game foundGame = game.get();
                model.addAttribute("game", foundGame);
                
                // Récupre le championnat associé à partir de l'ID
                Long championshipId = foundGame.getDay().getChampionshipId();
                Optional<Championship> championship = championshipRepository.findById(championshipId);
                if (championship.isPresent()) {
                    model.addAttribute("championship", championship.get());
                }
                
                return "backoffice/games/form";
            } else {
                redirectAttributes.addFlashAttribute("error", "Match non trouvé avec l'ID : " + id);
                return "redirect:/backoffice/games";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la récupération du match : " + e.getMessage());
            return "redirect:/backoffice/games";
        }
    }

    /**
     * Formulaire de création d'un nouveau match
     */
    @GetMapping("/games/new")
    public String newGame(Model model, RedirectAttributes redirectAttributes) {
        try {
            Game game = new Game();
            List<Team> teams = teamRepository.findAll();
            List<Day> days = dayRepository.findAll();
            
            // List pour associer les journée avec les noms des championnats
            Map<Long, String> championshipNames = new HashMap<>();
            for (Day day : days) {
                Optional<Championship> championship = championshipRepository.findById(day.getChampionshipId());
                if (championship.isPresent()) {
                    championshipNames.put(day.getId(), championship.get().getName());
                }
            }
            
            model.addAttribute("game", game);
            model.addAttribute("teams", teams);
            model.addAttribute("days", days);
            model.addAttribute("championshipNames", championshipNames);
            model.addAttribute("isNewGame", true);
            
            return "backoffice/games/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création du formulaire : " + e.getMessage());
            return "redirect:/backoffice/games";
        }
    }

    /**
     * Sauvegarde d'un résultat de match
     */
    @PostMapping("/games/save")
    public String saveGame(@ModelAttribute Game game,
                          @RequestParam(required = false) Long team1Id,
                          @RequestParam(required = false) Long team2Id,
                          @RequestParam(required = false) Long dayId,
                          BindingResult result, 
                          Model model,
                          RedirectAttributes redirectAttributes) {
        
        // Cas : nouveau match
        boolean isNewGame = (game.getId() == null);
        
        if (isNewGame) {
            // Validation que les IDs sont fournis
            if (team1Id == null || team2Id == null || dayId == null) {
                redirectAttributes.addFlashAttribute("error", "Toutes les sélections sont obligatoires pour créer un match.");
                return "redirect:/backoffice/games/new";
            }
            
            // Validation que les équipes sont différentes
            if (team1Id.equals(team2Id)) {
                redirectAttributes.addFlashAttribute("error", "Les deux équipes doivent être différentes.");
                return "redirect:/backoffice/games/new";
            }
            
            try {
                // Récupération des entités
                Optional<Team> team1Opt = teamRepository.findById(team1Id);
                Optional<Team> team2Opt = teamRepository.findById(team2Id);
                Optional<Day> dayOpt = dayRepository.findById(dayId);
                
                if (team1Opt.isEmpty() || team2Opt.isEmpty() || dayOpt.isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "Une des sélections n'est plus valide.");
                    return "redirect:/backoffice/games/new";
                }
                
                // Attribution des entités au match
                game.setTeam1(team1Opt.get());
                game.setTeam2(team2Opt.get());
                game.setDay(dayOpt.get());
                
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Erreur lors de la récupération des données : " + e.getMessage());
                return "redirect:/backoffice/games/new";
            }
        }
        
        // Validation manuelle des champs obligatoires
        if (game.getTeam1() == null || game.getTeam2() == null || game.getDay() == null) {
            redirectAttributes.addFlashAttribute("error", "Toutes les données obligatoires doivent être fournies.");
            if (isNewGame) {
                return "redirect:/backoffice/games/new";
            } else {
                return "redirect:/backoffice/games";
            }
        }
        
        // Erreurs
        if (result.hasErrors()) {
            if (isNewGame) {
                // Recharger les données pour le formulaire
                model.addAttribute("teams", teamRepository.findAll());
                model.addAttribute("days", dayRepository.findAll());
                model.addAttribute("isNewGame", true);
            } else {
                // Pour l'édition, récupérer le championnat si possible
                if (game.getDay() != null && game.getDay().getChampionshipId() != null) {
                    Optional<Championship> championshipOpt = championshipRepository.findById(game.getDay().getChampionshipId());
                    championshipOpt.ifPresent(championship -> model.addAttribute("championship", championship));
                }
            }
            return "backoffice/games/form";
        }
        
        try {
            gameRepository.save(game);
            
            if (isNewGame) {
                redirectAttributes.addFlashAttribute("success", "Nouveau match créé avec succès !");
            } else {
                redirectAttributes.addFlashAttribute("success", "Résultat du match sauvegardé avec succès !");
            }
            return "redirect:/backoffice/games";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la sauvegarde : " + e.getMessage());
            return "redirect:/backoffice/games";
        }
    }
}
