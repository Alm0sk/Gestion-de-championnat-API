package com.ipi.gestiondechampionnatapi.controller;

import com.ipi.gestiondechampionnatapi.repository.ChampionshipRepository;
import com.ipi.gestiondechampionnatapi.repository.GameRepository;
import com.ipi.gestiondechampionnatapi.repository.TeamRepository;
import com.ipi.gestiondechampionnatapi.models.Championship;
import com.ipi.gestiondechampionnatapi.models.Game;
import com.ipi.gestiondechampionnatapi.models.Team;
import com.ipi.gestiondechampionnatapi.service.ClassementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final ChampionshipRepository championshipRepository;
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final ClassementService classementService;

    public HomeController(ChampionshipRepository championshipRepository, GameRepository gameRepository, TeamRepository teamRepository, ClassementService classementService) {
        this.championshipRepository = championshipRepository;
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.classementService = classementService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Championship> championships = championshipRepository.findAll();
        
        // Ajouter les infos au modèle
        model.addAttribute("championships", championships);
        model.addAttribute("championshipsCount", championshipRepository.count());
        model.addAttribute("teamsCount", teamRepository.count());
        model.addAttribute("matchesCount", gameRepository.count());
        
        return "home";
    }

    @GetMapping("/championship/{id}")
    public String championshipDetail(@PathVariable Long id, Model model) {
        Optional<Championship> championship = championshipRepository.findById(id);
        if (championship.isPresent()) {
            List<Game> games = gameRepository.findByChampionshipId(id);
            
            // Grouper les matchs par journée
            Map<String, List<Game>> gamesByDay = games.stream()
                .collect(Collectors.groupingBy(game -> game.getDay().getNumber()));
            
            model.addAttribute("championship", championship.get());
            model.addAttribute("games", games);
            model.addAttribute("gamesByDay", gamesByDay);
            return "championship-detail";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/championship/{id}/classement")
    public String championshipClassement(@PathVariable Long id, Model model) {
        Optional<Championship> championship = championshipRepository.findById(id);
        if (championship.isPresent()) {
            var classement = classementService.calculateClassement(id);
            
            model.addAttribute("championship", championship.get());
            model.addAttribute("classement", classement);
            return "championship-classement";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/team/{id}")
    public String teamDetail(@PathVariable Long id, Model model) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            model.addAttribute("team", team.get());
            return "team-detail";
        } else {
            return "redirect:/";
        }
    }
}