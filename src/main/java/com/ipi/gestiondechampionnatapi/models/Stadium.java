package com.ipi.gestiondechampionnatapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "stadium")
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotNull(message = "Le nom du stade ne peut pas être null")
    @NotBlank(message = "Le nom du stade ne peut pas être vide")
    private String name;

    @Column(name = "address")
    @NotNull(message = "L'adresse du stade ne peut pas être null")
    @NotBlank(message = "L'adresse du stade ne peut pas être vide")
    private String address;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "phone")
    @NotNull(message = "Le numéro de téléphone du stade ne peut pas être null")
    @NotBlank(message = "Le numéro de téléphone du stade ne peut pas être vide")
    private String phone;

    /**
     * Constructeur par défaut
     */
    public Stadium() {}

    /**
     * Constructeur avec paramètres
     * @param name nom du stade
     * @param address adresse du stade
     * @param capacity capacité du stade
     * @param phone numéro de téléphone du stade
     */
    public Stadium(String name, String address, int capacity, String phone) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.phone = phone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
