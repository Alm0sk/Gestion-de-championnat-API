package com.ipi.gestiondechampionnatapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team1Point")
    private Integer team1Point;

    @Column(name = "team2Point")
    private Integer team2Point;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTeam1", nullable = false)
    @NotNull
    private Team team1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTeam2", nullable = false)
    @NotNull
    private Team team2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idDay", nullable = false)
    @NotNull
    private Day day;


    public Game() {}

    /**
     * Objet Game
     * @param team1Point points de l'équipe 1
     * @param team2Point points de l'équipe 2
     * @param team1 objet équipe 1
     * @param team2 objet équipe 2
     * @param day journée associée
     */
    public Game(Integer team1Point, Integer team2Point, Team team1, Team team2, Day day) {
        this.team1Point = team1Point;
        this.team2Point = team2Point;
        this.team1 = team1;
        this.team2 = team2;
        this.day = day;
    }


    /*
     * **********************
     * GETTER & SETTER
     * **********************
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTeam1Point() {
        return team1Point;
    }

    public void setTeam1Point(Integer team1Point) {
        this.team1Point = team1Point;
    }

    public Integer getTeam2Point() {
        return team2Point;
    }

    public void setTeam2Point(Integer team2Point) {
        this.team2Point = team2Point;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Day getDay() {
        return day;
    }

}