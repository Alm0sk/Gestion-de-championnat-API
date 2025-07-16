package com.ipi.gestiondechampionnatapi.models;

/**
 * Classe pour représenter les statistiques d'une équipe dans le classement
 */
public class TeamClassement {
    private Long teamId;
    private String teamName;
    private String teamLogo;
    private int position;
    private int points;
    private int matchesPlayed;
    private int wins;
    private int draws;
    private int losses;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;

    public TeamClassement() {}

    public TeamClassement(Long teamId, String teamName, String teamLogo) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamLogo = teamLogo;
        this.position = 0;
        this.points = 0;
        this.matchesPlayed = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.goalDifference = 0;
    }

    // Méthode pour calculer la différence de buts
    public void updateGoalDifference() {
        this.goalDifference = this.goalsFor - this.goalsAgainst;
    }

    // Méthode pour ajouter un match gagné
    public void addWin(int goalsFor, int goalsAgainst) {
        this.wins++;
        this.matchesPlayed++;
        this.points += 3;
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
        updateGoalDifference();
    }

    // Méthode pour ajouter un match nul
    public void addDraw(int goalsFor, int goalsAgainst) {
        this.draws++;
        this.matchesPlayed++;
        this.points += 1;
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
        updateGoalDifference();
    }

    // Méthode pour ajouter une défaite
    public void addLoss(int goalsFor, int goalsAgainst) {
        this.losses++;
        this.matchesPlayed++;
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
        updateGoalDifference();
    }

    // Getters et Setters
    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }
}
