package com.ipi.gestiondechampionnatapi.service;

import com.ipi.gestiondechampionnatapi.models.Game;
import com.ipi.gestiondechampionnatapi.models.Team;
import com.ipi.gestiondechampionnatapi.models.TeamClassement;
import com.ipi.gestiondechampionnatapi.repository.GameRepository;
import com.ipi.gestiondechampionnatapi.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassementService {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    public ClassementService(GameRepository gameRepository, TeamRepository teamRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
    }

    /**
     * Calcule le classement pour un championnat donné
     * @param championshipId ID du championnat
     * @return Liste des équipes classées par position
     */
    public List<TeamClassement> calculateClassement(Long championshipId) {
        // Get tous les matchs du championnat
        List<Game> games = gameRepository.findByChampionshipId(championshipId);
        
        // Get toutes les équipes (on va filtrer plus tard)
        List<Team> allTeams = teamRepository.findAll();
        
        // List les statistiques de chaque équipe
        Map<Long, TeamClassement> classementMap = new HashMap<>();
        
        Set<Long> participatingTeamIds = new HashSet<>();
        for (Game game : games) {
            participatingTeamIds.add(game.getTeam1().getId());
            participatingTeamIds.add(game.getTeam2().getId());
        }
        
        // Initialise les statistiques pour chaque équipe participante
        for (Team team : allTeams) {
            if (participatingTeamIds.contains(team.getId())) {
                classementMap.put(team.getId(), new TeamClassement(team.getId(), team.getName(), team.getLogo()));
            }
        }
        
        // Calculer les statistiques à partir des matchs
        for (Game game : games) {
            Long team1Id = game.getTeam1().getId();
            Long team2Id = game.getTeam2().getId();
            
            TeamClassement team1Classement = classementMap.get(team1Id);
            TeamClassement team2Classement = classementMap.get(team2Id);
            
            int team1Goals = game.getTeam1Point();
            int team2Goals = game.getTeam2Point();
            
            if (team1Goals > team2Goals) {
                // Équipe 1 gagne
                team1Classement.addWin(team1Goals, team2Goals);
                team2Classement.addLoss(team2Goals, team1Goals);
            } else if (team1Goals < team2Goals) {
                // Équipe 2 gagne
                team1Classement.addLoss(team1Goals, team2Goals);
                team2Classement.addWin(team2Goals, team1Goals);
            } else {
                // Match nul
                team1Classement.addDraw(team1Goals, team2Goals);
                team2Classement.addDraw(team2Goals, team1Goals);
            }
        }
        
        // Convertir en liste et trier par classement
        List<TeamClassement> classement = new ArrayList<>(classementMap.values());
        classement.sort((a, b) -> {
            // Trier par points (décroissant)
            int pointsComparison = Integer.compare(b.getPoints(), a.getPoints());
            if (pointsComparison != 0) {
                return pointsComparison;
            }
            
            // En cas d'égalité de points, trier par différence de buts (décroissant)
            int goalDiffComparison = Integer.compare(b.getGoalDifference(), a.getGoalDifference());
            if (goalDiffComparison != 0) {
                return goalDiffComparison;
            }
            
            // En cas d'égalité de différence de buts, trier par nombre de buts marqués (décroissant)
            return Integer.compare(b.getGoalsFor(), a.getGoalsFor());
        });
        
        // Attribuer les positions
        for (int i = 0; i < classement.size(); i++) {
            classement.get(i).setPosition(i + 1);
        }
        
        return classement;
    }
    
    /**
     * Obtient le classement pour League 1
     * @return Liste des équipes de League 1 classées
     */
    public List<TeamClassement> getLeague1Classement() {
        return calculateClassement(1L);
    }
    
    /**
     * Obtient le classement pour League 2
     * @return Liste des équipes de League 2 classées
     */
    public List<TeamClassement> getLeague2Classement() {
        return calculateClassement(2L);
    }
}
