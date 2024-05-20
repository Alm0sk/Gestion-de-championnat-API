package com.ipi.gestiondechampionnatapi.controller;

import com.ipi.gestiondechampionnatapi.repository.ChampionshipRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/championships")
public class ChampionshipController {

    private final ChampionshipRepository championshipRepository;

    public ChampionshipController(ChampionshipRepository championshipRepository) {
        this.championshipRepository = championshipRepository;
    }

    /*
     * Ping de test
     */
    @GetMapping("ping")
    public String ping() {
        return "championship pong";
    }

}
