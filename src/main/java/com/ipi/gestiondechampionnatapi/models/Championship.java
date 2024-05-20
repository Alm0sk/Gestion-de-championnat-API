package com.ipi.gestiondechampionnatapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ipi.gestiondechampionnatapi.tools.championships.DateRange;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "championship")
@DateRange(message = "La date de début doit être inférieur ou égale à la date de fin")
public class Championship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotNull(message = "Le champ nom du championnat ne peut pas être null")
    @NotBlank(message = "Le champ nom du championnat ne peut pas être vide")
    private String name;

    @Column(name = "startDate")
    @NotNull(message = "Le champ date de création du championnat ne peut pas être null")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "endDate")
    @NotNull(message = "Le champ date de fin du championnat ne peut pas être null")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "wonPoint")
    @NotNull(message = "Le champ points de victoire du championnat ne peut pas être null")
    @Min(value = 0, message = "Le champ points de victoire du championnat ne peut pas être négatif")
    private int wonPoint;

    @Column(name = "lostPoint")
    @NotNull(message = "Le champ points de défaite du championnat ne peut pas être null")
    private int lostPoint;

    @Column(name = "drawPoint")
    @NotNull(message = "Le champ points pour un match null du championnat ne peut pas être null")
    private int drawPoint;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "TeamChampionship",
            joinColumns = { @JoinColumn(name = "championship_id") },
            inverseJoinColumns = { @JoinColumn(name = "team_id") })
    private Set<Team> teams = new HashSet<>();


    public Championship() {}

    /**
     * Objet championnat
     * @param name nom du championnat
     * @param startDate date de début du championnat
     * @param endDate date de fin du championnat
     * @param wonPoint point attribué pour une victoire à un match
     * @param lostPoint point attribué pour une défaite à un match
     * @param drawPoint point attribué pour un match null à un match
     */
    public Championship(String name, LocalDate startDate, LocalDate endDate, int wonPoint, int lostPoint, int drawPoint) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.wonPoint = wonPoint;
        this.lostPoint = lostPoint;
        this.drawPoint = drawPoint;
    }

    /*
     * **********************
     * GETTER & SETTER
     * **********************
     */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getWonPoint() {
        return wonPoint;
    }

    public void setWonPoint(int wonPoint) {
        this.wonPoint = wonPoint;
    }

    public int getLostPoint() {
        return lostPoint;
    }

    public void setLostPoint(int lostPoint) {
        this.lostPoint = lostPoint;
    }

    public int getDrawPoint() {
        return drawPoint;
    }

    public void setDrawPoint(int drawPoint) {
        this.drawPoint = drawPoint;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void removeTeam(long teamId) {
        Team team = this.teams.stream().filter(t -> t.getId() == teamId).findFirst().orElse(null);
        if(team != null) {
            this.teams.remove(team);
            team.getChampionships().remove(this);
        }
    }


}
