package com.ipi.gestiondechampionnatapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true)
    @NotNull(message = "Le champ nom de l'équipe ne peut pas être null")
    @NotBlank(message = "Le champ nom de l'équipe ne peut pas être vide")
    private String name;

    @Column(name = "creationDate")
    @NotNull(message = "Le champ date de création de l'équipe ne peut pas être null")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @Column(name = "logo")
    @NotNull(message = "Le champ logo de l'équipe ne peut pas être null")
    @NotBlank(message = "Le champ logo de l'équipe ne peut pas être vide")
    private String logo;

    @Column(name = "coach")
    @NotNull (message = "Le champ coach de l'équipe ne peut pas être null")
    @NotBlank(message = "Le champ coach de l'équipe ne peut pas être vide")
    private String coach;

    @Column(name = "president")
    @NotNull(message = "Le champ président de l'équipe ne peut pas être null")
    @NotBlank(message = "Le champ président de l'équipe ne peut pas être vide")
    private String president;

    @Column(name = "status")
    @NotNull(message = "Le champ statut de l'équipe ne peut pas être null")
    @NotBlank(message = "Le champ statut de l'équipe ne peut pas être vide")
    private String status;

    @Column(name = "siege")
    @NotNull(message = "Le champ siège de l'équipe ne peut pas être null")
    @NotBlank(message = "Le champ siège de l'équipe ne peut pas être vide")
    private String siege;

    @Column(name = "phone")
    @NotNull(message = "Le champ téléphone de l'équipe ne peut pas être null")
    @NotBlank(message = "Le champ téléphone de l'équipe ne peut pas être vide")
    private String phone;

    @Column(name = "webSite")
    @NotNull(message = "Le champ site web de l'équipe ne peut pas être null")
    @NotBlank(message = "Le champ site web de l'équipe ne peut pas être vide")
    private String webSite;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        },
        mappedBy = "teams")
    @JsonIgnore
    private Set<Championship> championships = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Stadium stadium;


    public Team() {}

    /**
     * Objet équipe
     * @param name nom de l'équipe (unique)
     * @param creationDate date de création de l'équipe
     * @param logo logo de l'équipe
     * @param coach nom du coach de l'équipe
     * @param president nom du président de l'équipe
     * @param status statut de l'équipe
     * @param siege siège de l'équipe
     * @param phone numéro de téléphone de l'équipe
     * @param webSite site web de l'équipe
     */
    public Team(String name, LocalDate creationDate, String logo, String coach, String president, String status, String siege, String phone, String webSite) {
        this.name = name;
        this.creationDate = creationDate;
        this.logo = logo;
        this.coach = coach;
        this.president = president;
        this.status = status;
        this.siege = siege;
        this.phone = phone;
        this.webSite = webSite;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSiege() {
        return siege;
    }

    public void setSiege(String siege) {
        this.siege = siege;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Set<Championship> getChampionships() {

        return championships;
    }

    public void setChampionships(Set<Championship> championships) {
        this.championships = championships;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

}
