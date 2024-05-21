package com.ipi.gestiondechampionnatapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "day")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number")
    @NotNull(message = "Le champ nom de journée ne peut pas être null")
    @NotBlank(message = "Le champ nom de journée ne peut pas être vide")
    private String number;

    @Column(name = "championship_id", nullable = false)
    @NotNull(message = "L'id de championnat ne peut pas être null")
    @Min(value = 1, message = "L'id de championnat ne peut être négatif")
    private Long championshipId;


    public Day() {}

    /**
     * Objet journée
     * @param number numéro / nom de la journée
     * @param championshipId championnat associé
     */
    public Day(String number, Long championshipId) {
        this.number = number;
        this.championshipId = championshipId;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    // Affichage de l'attribut championshipId dans le GET
    @JsonProperty("championshipId")
    public Long getChampionshipId() {

        return championshipId;
    }

}
