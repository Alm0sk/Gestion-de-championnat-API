package com.ipi.gestiondechampionnatapi.service;

import com.ipi.gestiondechampionnatapi.models.Championship;
import com.ipi.gestiondechampionnatapi.models.Team;
import com.ipi.gestiondechampionnatapi.repository.ChampionshipRepository;
import com.ipi.gestiondechampionnatapi.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service for managing associations between teams and championships.
 * This service provides methods to add and remove teams from championships,
 */
@Service
@Transactional
public class TeamChampionshipService {

    private static final Logger log = LoggerFactory.getLogger(TeamChampionshipService.class);

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ChampionshipRepository championshipRepository;

    /**
     * Add a team to a championship
     *
     * @param teamName name of the team
     * @param championshipName name of the championship
     * @return true if the association was created, false otherwise
     */
    public boolean addTeamToChampionship(String teamName, String championshipName) {
        Team team = teamRepository.findByName(teamName);
        Optional<Championship> championshipOpt = championshipRepository.findByName(championshipName);

        if (team != null && championshipOpt.isPresent()) {
            Championship championship = championshipOpt.get();

            // Check if the association already exists
            if (!team.getChampionships().contains(championship)) {
                team.getChampionships().add(championship);
                championship.getTeams().add(team);
                
                teamRepository.save(team);
                championshipRepository.save(championship);
                
                log.info("Équipe '{}' associée au championnat '{}'", teamName, championshipName);
                return true;
            } else {
                log.info("L'équipe '{}' est déjà associée au championnat '{}'", teamName, championshipName);
                return false;
            }
        } else {
            log.warn("Impossible d'associer l'équipe '{}' au championnat '{}' - équipe ou championnat introuvable", 
                     teamName, championshipName);
            return false;
        }
    }

    /**
     * Remove a team from a championship
     *
     * @param teamName name of the team
     * @param championshipName name of the championship
     * @return true if the association was removed, false otherwise
     */
    public boolean removeTeamFromChampionship(String teamName, String championshipName) {
        Team team = teamRepository.findByName(teamName);
        Optional<Championship> championshipOpt = championshipRepository.findByName(championshipName);

        if (team != null && championshipOpt.isPresent()) {
            Championship championship = championshipOpt.get();
            
            if (team.getChampionships().contains(championship)) {
                team.getChampionships().remove(championship);
                championship.getTeams().remove(team);
                
                teamRepository.save(team);
                championshipRepository.save(championship);
                
                log.info("Équipe '{}' retirée du championnat '{}'", teamName, championshipName);
                return true;
            } else {
                log.info("L'équipe '{}' n'était pas associée au championnat '{}'", teamName, championshipName);
                return false;
            }
        } else {
            log.warn("Impossible de retirer l'équipe '{}' du championnat '{}' - équipe ou championnat introuvable", 
                     teamName, championshipName);
            return false;
        }
    }

    /**
     * Retrieve a team with its championships in a transactional manner
     *
     * @param teamName name of the team
     * @return the team with its championships loaded, or null if not found
     */
    @Transactional(readOnly = true)
    public Team getTeamWithChampionships(String teamName) {
        Team team = teamRepository.findByName(teamName);
        if (team != null) {
            // Force le chargement de la collection championships
            int size = team.getChampionships().size();
            log.debug("Équipe '{}' chargée avec {} championnats", teamName, size);
        }
        return team;
    }

    /**
     * Exemple d'utilisation pour associer une équipe à plusieurs championnats
     * Cette méthode montre comment utiliser la flexibilité de la relation ManyToMany
     *
     * @param teamName name of the team
     * @param championshipNames names of the championships
     */
    public void associateTeamToMultipleChampionships(String teamName, String... championshipNames) {
        log.info("Association de l'équipe '{}' à {} championnats", teamName, championshipNames.length);
        
        for (String championshipName : championshipNames) {
            addTeamToChampionship(teamName, championshipName);
        }
        
        // Afficher le résultat final
        Team team = teamRepository.findByName(teamName);
        if (team != null) {
            log.info("L'équipe '{}' est maintenant associée à {} championnat(s) :", 
                     teamName, team.getChampionships().size());
            for (Championship championship : team.getChampionships()) {
                log.info("  - {}", championship.getName());
            }
        }
    }

    /**
     * Associate a team with a championship in a secure manner (for LoadData)
     *
     * @param team the team to associate
     * @param championship the championship
     * @return true if the association was created, false otherwise
     */
    @Transactional
    public boolean associateTeamToChampionship(Team team, Championship championship) {
        try {
            // Récupérer l'équipe et le championnat avec leurs associations
            Team managedTeam = teamRepository.findById(team.getId()).orElse(null);
            Championship managedChampionship = championshipRepository.findById(championship.getId()).orElse(null);
            
            if (managedTeam == null || managedChampionship == null) {
                log.warn("Équipe ou championnat non trouvé pour association");
                return false;
            }

            // Check if the association already exists
            if (!managedTeam.getChampionships().contains(managedChampionship)) {
                // Add the association on both sides
                managedTeam.getChampionships().add(managedChampionship);
                managedChampionship.getTeams().add(managedTeam);

                // Save both entities to ensure persistence
                teamRepository.save(managedTeam);
                championshipRepository.save(managedChampionship);

                // Flush to force immediate persistence
                teamRepository.flush();
                championshipRepository.flush();
                
                log.info("Association créée: {} -> {}", managedTeam.getName(), managedChampionship.getName());
                return true;
            } else {
                log.debug("Association déjà existante: {} -> {}", managedTeam.getName(), managedChampionship.getName());
                return false;
            }
        } catch (Exception e) {
            log.error("Erreur lors de l'association équipe-championnat", e);
            return false;
        }
    }
}
