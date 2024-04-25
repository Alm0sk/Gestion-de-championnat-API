package com.ipi.gestiondechampionnatapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = user)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstname")
    @NotNull(message = "Le champ firstname ne peut pas être null")
    @NotBlank(message = "Le champ firstname ne peut pas être vide")
    private String firstname;

    @Column(name = "lastname")
    @NotNull(message = "Le champ lastname ne peut pas être null")
    @NotBlank(message = "Le champ lastname ne peut pas être vide")
    private String lastname;

    @Column(name = "email")
    @NotNull(message = "Le champ email ne peut pas être null")
    @NotBlank(message = "Le champ email ne peut pas être vide")
    private String email;

    // TODO : voir pour ajouter de la complexité
    @Column(name = "password")
    @NotNull(message = "Le champ password ne peut pas être null")
    @NotBlank(message = "Le champ password ne peut pas être vide")
    private String password;

    @Column(name = "creationDate")
    @NotNull(message = "Le champ date de création ne peut pas être null")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String creationDate;

    public User() {}

    public User(long id, String creationDate, String password, String email, String lastname, String firstname) {
        this.id = id;
        this.creationDate = creationDate;
        this.password = password;
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
