package com.ipi.gestiondechampionnatapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotNull(message = "Le nom du pays ne peut pas être null")
    @NotBlank(message = "Le nom du pays ne peut pas être vide")
    private String name;

    @Column(name = "logo")
    @NotNull(message = "Le logo du pays ne peut pas être null")
    @NotBlank(message = "Le logo du pays ne peut pas être vide")
    private String logo;


    public Country(String name, String logo) {
        this.name = name;
        this.logo = logo;
    }


    /*
     * **********************
     * GETTER & SETTER
     * **********************
     */

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
